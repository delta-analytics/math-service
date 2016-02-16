package deltaanalytics;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Calculator {
    static private Logger LOGGER = LoggerFactory.getLogger(Calculator.class);    
    static final String octaveCliPath = "/usr/bin/octave-cli";  //"C:\\Octave\\Octave-4.0.0\\bin\\octave-cli";
    private static OctaveEngine octave;
    private static HitranInputParameters inputParameter;
    private static SpectrumInput brukerSpectrum;
    private static LevenberqMarquartInputParameters lmParameters;

    public static void main(String[] args) {
        octave = OctaveEngineWrapper.build(new OctaveEngineFactory(), octaveCliPath);
        octave.eval("cd lib");

        LOGGER.info("Hole HITRAN Daten");        
        inputParameter = new HitranInputParameters();
        HitranWrapper hitranWrapper = new HitranWrapper();
        hitranWrapper.initialize(octave, inputParameter);
        hitranWrapper.getHitranData(octave);

        LOGGER.info("Hole Bruker Spektrum");   
        brukerSpectrum = new SpectrumInput();
        brukerSpectrum.setDataPointTableFile("'Test1.10.dpt'");  // debug
        SpectrumWrapper spectrumWrapper = new SpectrumWrapper();
        spectrumWrapper.getFtirData(octave, brukerSpectrum);

        lmParameters = new LevenberqMarquartInputParameters();
        LevenbergMarquardtWrapper levenbergMarquardtWrapper = new LevenbergMarquardtWrapper();
        levenbergMarquardtWrapper.initializeLevenbergMarquardt(octave, lmParameters);
        
        LOGGER.info("Starte Levenberg-Marquardt nichtlineare Regression");
        levenbergMarquardtWrapper.startLevenbergMarquardt(octave);

        ResultWrapper resultWrapper = new ResultWrapper();
        Result result = resultWrapper.outputResult(octave);
        
        LOGGER.info("Mix Ratio from Hitan sum = "
                + String.valueOf(result.getMixingRatioFromHitranSum()));
        LOGGER.info("Mix Ratio from area under curve = "
                + String.valueOf(result.getMixingRatioFromIntegralUnderTheCurve()));
        resultWrapper.showGnuGraph(octave);

        octave.close();
    }
}