package deltaanalytics;

import deltaanalytics.octave.calculation.LevenbergMarquardtWrapper;
import deltaanalytics.octave.calculation.ResultWrapper;
import deltaanalytics.octave.hitran.HitranWrapper;
import deltaanalytics.octave.initialize.OctaveEngineWrapper;
import deltaanalytics.octave.input.HitranInputParameters;
import deltaanalytics.octave.input.LevenberqMarquartInputParameters;
import deltaanalytics.octave.input.SpectrumInput;
import deltaanalytics.octave.spectrum.SpectrumWrapper;
import dk.ange.octave.OctaveEngine;
import dk.ange.octave.OctaveEngineFactory;

public class Calculator {
    static final String octaveCliPath = "C:\\Octave\\Octave-4.0.0\\bin\\octave-cli";
    private static OctaveEngine octave;
    private static HitranInputParameters inputParameter;
    private static SpectrumInput brukerSpectrum;
    private static LevenberqMarquartInputParameters lmParameters;

    public static void main(String[] args) {
        octave = OctaveEngineWrapper.build(new OctaveEngineFactory(), octaveCliPath);
        
        HitranWrapper hitranWrapper = new HitranWrapper();
        hitranWrapper.initialize(octave, inputParameter);
        hitranWrapper.getHitranData(octave);

        brukerSpectrum.setDataPointTableFile("Test1.10.dpt");  // debug
        SpectrumWrapper spectrumWrapper = new SpectrumWrapper();
        spectrumWrapper.getFtirData(octave, brukerSpectrum);

        LevenbergMarquardtWrapper levenbergMarquardtWrapper = new LevenbergMarquardtWrapper();
        levenbergMarquardtWrapper.initializeLevenbergMarquardt(octave, lmParameters);
        levenbergMarquardtWrapper.startLevenbergMarquardt(octave);

        ResultWrapper resultWrapper = new ResultWrapper();
        resultWrapper.outputResult(octave);
        resultWrapper.showGnuGraph(octave);
        
        octave.close();
    }
}