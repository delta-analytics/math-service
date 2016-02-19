package deltaanalytics;

import deltaanalytics.gui.math.HitranParameters;
import deltaanalytics.gui.math.LevenbergMarquartParameters;
import deltaanalytics.octave.calculation.LevenbergMarquardtWrapper;
import deltaanalytics.octave.calculation.ResultWrapper;
import deltaanalytics.octave.hitran.HitranWrapper;
import deltaanalytics.octave.initialize.OctaveEngineWrapper;
import deltaanalytics.octave.input.HitranInputParameters;
import deltaanalytics.octave.input.LevenberqMarquartInputParameters;
import deltaanalytics.octave.input.SpectrumInput;
import deltaanalytics.octave.output.Result;
import deltaanalytics.octave.spectrum.SpectrumWrapper;
import dk.ange.octave.OctaveEngine;
import dk.ange.octave.OctaveEngineFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;


public class Calculator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Calculator.class);    
    private static final String OCTAVE_CLI_PATH = "/usr/bin/octave-cli";  //"C:\\Octave\\Octave-4.0.0\\bin\\octave-cli";
    private static final List<String> MOLECULE = Arrays.asList("H2O", "CO2" ,"N2O", "CO", "CH4", "NO", "NO2");
    private static final int cores = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService SCHEDULER = Executors.newFixedThreadPool(cores); //newSingleThreadExecutor();

    
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        //calculator.firstTry();
        //calculator.secondTry();
        
        calculator.startOctaveForOneMolecule(4);

    }//main
    
    private void secondTry() {
        try {
            ExecutorService executorService = Executors.newCachedThreadPool(); //newFixedThreadPool(cores); //newWorkStealingPool();
            
            List<Callable<String>> callables = new ArrayList<>();
            
            List<Integer> molculeList = Arrays.asList(4, 5);

            long time1 = System.currentTimeMillis();            
            molculeList.stream().forEach((Integer molecule) -> {
                callables.add((Callable<String>) () -> startOctaveForOneMolecule(molecule)); //executeCommand(String.format("ping -c %d google.com", molecule)));
            });
            
            List<Future<String>> futures = executorService.invokeAll(callables);
            long time2 = System.currentTimeMillis();
            double time = (time2 - time1) / 1000;            
            
            futures.stream().forEach((future) -> {
                try {
                    System.out.println("future.get = " + future.get());
                } catch (InterruptedException | ExecutionException ex) {
                    java.util.logging.Logger.getLogger(Calculator.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            LOGGER.info("Time in sec for ... = " + time);
                    
            executorService.shutdown();
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(Calculator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void firstTry(){
        List<Future<String>> resultList = new ArrayList<>();      
        
        List<Integer> molculeList = Arrays.asList(4, 5);


        long time1 = System.currentTimeMillis();        
        molculeList.stream().forEach((molecule) -> {
            resultList.add(SCHEDULER.submit(() -> {
                   return executeCommand(String.format("ping -c %d google.com", molecule)); //startOctaveForOneMolecule(molecule);
               }
           ));
        });
                
        // check if all tasks are done
        Predicate<Future<String>> taskDone =
                (Future<String> r) -> r.isDone() == true;        
        boolean listen = true;
        while (listen) {
            if (resultList.stream().allMatch(taskDone)) {
                listen = false;
            }
        }
   
        // print out result
        resultList.stream().forEach((Future<String> fut) -> {
            try {
                System.out.println(new Date()+ "::"+fut.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        long time2 = System.currentTimeMillis();
        double time = (time2 - time1) / 1000;         
        LOGGER.info("Time in sec for ... = " + time);
        //shut down the executor service after all tasks are done
        SCHEDULER.shutdown();         
    }  
    
    private String startOctaveForOneMolecule(int molecule) {
        OctaveEngine octave = OctaveEngineWrapper.build(new OctaveEngineFactory(), OCTAVE_CLI_PATH);

        // change to individual directory for each molecule
        octave.eval(String.format("cd lib%s%s", File.separator, MOLECULE.get(molecule-1)));
        
        LOGGER.info("Hole HITRAN Daten für " + MOLECULE.get(molecule-1));        
        HitranInputParameters inputParameter = 
                new HitranInputParameters(new HitranParameters(), molecule);
        HitranWrapper hitranWrapper =
                new HitranWrapper();
        hitranWrapper.initialize(octave, inputParameter);
        hitranWrapper.getHitranData(octave, inputParameter);

        LOGGER.info("Hole Bruker Spektrum");
        // @ToDo inject the actual file
        String brukerFileName = "'Test1.10.dpt'";
        SpectrumInput brukerSpectrum =
                new SpectrumInput();
        brukerSpectrum.setDataPointTableFile(brukerFileName);
        SpectrumWrapper spectrumWrapper =
                new SpectrumWrapper();
        spectrumWrapper.getFtirData(octave, brukerSpectrum);    


        
        LOGGER.info("Starte Levenberg-Marquardt nichtlineare Regression");
        LevenberqMarquartInputParameters lmParameters = 
                new LevenberqMarquartInputParameters(new LevenbergMarquartParameters());
        LevenbergMarquardtWrapper levenbergMarquardtWrapper = new LevenbergMarquardtWrapper();
        levenbergMarquardtWrapper.initializeLevenbergMarquardt(octave, lmParameters);
        levenbergMarquardtWrapper.startLevenbergMarquardt(octave);

        LOGGER.info("Übergebe Ergebnisse für " + MOLECULE.get(molecule-1));
        ResultWrapper resultWrapper = new ResultWrapper();
        Result result = resultWrapper.outputResult(octave);
        
        LOGGER.info("Mix Ratio from Hitan sum = "
                + String.valueOf(result.getMixingRatioFromHitranSum()));
        LOGGER.info("Mix Ratio from area under curve = "
                + String.valueOf(result.getMixingRatioFromIntegralUnderTheCurve()));
        LOGGER.info("R squared = "
                + String.valueOf(result.getR2()));        
        resultWrapper.showGnuGraph(octave);

        octave.close();
        
        return result.toString();
    }
    
    // do some stuff with runtime process
    private String executeCommand(String command) {

        StringBuilder output = new StringBuilder();

        Process p;
        try {
                p = Runtime.getRuntime().exec(command);
                p.waitFor();
                BufferedReader reader = 
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

                String line = "";			
                while ((line = reader.readLine())!= null) {
                        output.append(line + "\n");
                }

        } catch (Exception e) {
                e.printStackTrace();
        }

        return output.toString();
    }

     

}