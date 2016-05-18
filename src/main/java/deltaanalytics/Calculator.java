package deltaanalytics;

import deltaanalytics.octave.calculation.LevenbergMarquardtWrapper;
import deltaanalytics.octave.calculation.ResultWrapper;
import deltaanalytics.octave.entity.HitranParameters;
import deltaanalytics.octave.entity.LevenbergMarquardtParameters;
import deltaanalytics.octave.hitran.HitranWrapper;
import deltaanalytics.octave.initialize.OctaveEngineWrapper;
import deltaanalytics.octave.input.HitranInputParameters;
import deltaanalytics.octave.input.LevenberqMarquardtInputParameters;
import deltaanalytics.octave.input.SpectrumInput;
import deltaanalytics.octave.output.Result;
import deltaanalytics.octave.spectrum.SpectrumWrapper;
import dk.ange.octave.OctaveEngine;
import dk.ange.octave.OctaveEngineFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Calculator {
    public static final List<String> MOLECULE = Arrays.asList("H2O", "CO2" ,"N2O", "CO", "CH4", "NO", "NO2");    
    private static String OCTAVE_CLI_PATH = "/usr/bin/octave-cli";  //"C:\\Octave\\Octave-4.0.0\\bin\\octave-cli";
    private static final Logger LOGGER = LoggerFactory.getLogger(Calculator.class);    
    
    //private static final int CORES = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService SCHEDULER = Executors.newCachedThreadPool();  //newFixedThreadPool(CORES);

    private List<Integer> moleculeList;
    private HitranParameters hitranParameters;
    private List<LevenbergMarquardtParameters> levenbergMarquardtParameterList;
    private String brukerFileName = "'Test1.10.dpt'";   // test file
    
    public Calculator(List<Integer> integerList, HitranParameters hitranPars, List<LevenbergMarquardtParameters> lmParsList, String brukerFileName){
        this.moleculeList = integerList;
        this.hitranParameters = hitranPars;
        this.levenbergMarquardtParameterList = lmParsList;
        this.brukerFileName = brukerFileName;
    }

    // constructor for main class
    public Calculator(){   
    }
    
    // main class for testing
    public static void main(String[] args) {
        List<Integer> molculeList = Arrays.asList(4, 5);

        new Calculator().doCalculations(molculeList);         
    }
    
    private void doCalculations(List<Integer> moleculeList) {
        try {           
            List<Callable<Result>> callables = new ArrayList<>();
                      
            moleculeList
                .stream()
                .forEach((Integer molecule) -> {
                    callables.add((Callable<Result>) () 
                            -> startOctaveForOneMolecule(molecule)); //executeCommand(String.format("ping -c %d google.com", molecule)));
                });
            
            SCHEDULER
                .invokeAll(callables)
                .stream()
                .map((Future<Result> future) -> {
                    try {
                        return future.get();
                    }
                    catch (InterruptedException | ExecutionException e) {
                        throw new IllegalStateException(e);
                    }
                })
                .forEach((Result r) -> LOGGER.info(r.toString()));
            
            SCHEDULER.shutdown(); 
                                
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(Calculator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private Result startOctaveForOneMolecule(int molecule) {
        OctaveEngineWrapper octaveWrapper = new OctaveEngineWrapper();
        OctaveEngine octave = octaveWrapper.build(new OctaveEngineFactory(), getOCTAVE_CLI_PATH());

        // change to individual directory for each molecule
        octave.eval(String.format("cd lib%s%s", File.separator, MOLECULE.get(molecule-1)));
        
        LOGGER.info("Hole HITRAN Daten für " + MOLECULE.get(molecule-1));     
        HitranInputParameters inputParameter = 
                new HitranInputParameters(
                        hitranParameters == null ?
                        new HitranParameters() : hitranParameters, molecule);
        
        HitranWrapper hitranWrapper = new HitranWrapper();
        
        hitranWrapper.initialize(octave, inputParameter);
        hitranWrapper.getHitranData(octave, inputParameter);

        LOGGER.info("Hole Bruker Spektrum");      
        SpectrumInput brukerSpectrum = new SpectrumInput();      
        brukerSpectrum.setDataPointTableFile(brukerFileName);
        
        SpectrumWrapper spectrumWrapper = new SpectrumWrapper();       
        spectrumWrapper.getFtirData(octave, brukerSpectrum);    
       
        LOGGER.info("Starte Levenberg-Marquardt nichtlineare Regression");
        LevenberqMarquardtInputParameters lmParameters = 
                new LevenberqMarquardtInputParameters(
                        levenbergMarquardtParameterList == null ?
                            new LevenbergMarquardtParameters(molecule) :
                                levenbergMarquardtParameterList
                                    .stream()
                                    .filter(lmPars -> lmPars.getMolecule() == molecule)
                                    .findFirst()
                                    .get());
        
        LevenbergMarquardtWrapper levenbergMarquardtWrapper = new LevenbergMarquardtWrapper();
        levenbergMarquardtWrapper.initializeLevenbergMarquardt(octave, lmParameters);
        levenbergMarquardtWrapper.startLevenbergMarquardt(octave);

        LOGGER.info("Übergebe der Ergebnisse für " + MOLECULE.get(molecule-1));
        ResultWrapper resultWrapper = new ResultWrapper();
        Result result = resultWrapper.outputResult(octave);
     
        LOGGER.info("Mix Ratio from Hitan sum = "
                + String.valueOf(result.getMixingRatioFromHitranSum()));
        LOGGER.info("Mix Ratio from area under curve = "
                + String.valueOf(result.getMixingRatioFromIntegralUnderTheCurve()));
        LOGGER.info("R squared = "
                + String.valueOf(result.getR2()) + "\n\n");

        resultWrapper.showGnuGraph(octave);
        
        octave.eval(String.format("cd ..%s..", File.separator));

        //octave.close();  // dont close if you want to use GNU octave graph
        
        return result;
    }
    
    public static String getOCTAVE_CLI_PATH() {
        return OCTAVE_CLI_PATH;
    }

    public static void setOCTAVE_CLI_PATH(String aOCTAVE_CLI_PATH) {
        OCTAVE_CLI_PATH = aOCTAVE_CLI_PATH;
    }    
    
}