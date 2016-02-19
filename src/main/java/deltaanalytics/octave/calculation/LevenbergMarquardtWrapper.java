package deltaanalytics.octave.calculation;

import dk.ange.octave.OctaveEngine;
import deltaanalytics.octave.input.LevenberqMarquartInputParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LevenbergMarquardtWrapper {
    private Logger LOGGER = LoggerFactory.getLogger(LevenbergMarquardtWrapper.class);    
    public void initializeLevenbergMarquardt(OctaveEngine octave, LevenberqMarquartInputParameters lmParameters) {
        // parameters for Levenberg Marquardt
        // dp = fractional incr of p for numerical partials in dfdp, default= .001*ones(size(pin)), if dp=0 parameter is fixed
        octave.eval(lmParameters.getFdefEval());
        octave.eval(lmParameters.getDpEval());  // baseline_step = 0 => no fit of baseline shape
        octave.eval(lmParameters.getPinEval());  // initial guess: offset, res, FOV, mixing ratio, baseline scale, wavenumber shift
        octave.eval(lmParameters.getMinValuesEval());
        octave.eval(lmParameters.getMaxValuesEval());

        octave.eval(lmParameters.getBaselineEval());  // only fit offset, no baseline distortion
        octave.eval(lmParameters.getDfdpEval()); // estimated derivative use with dfdp.m
        octave.eval(lmParameters.getStatWeightsEval());  // default 1.0
        octave.eval(lmParameters.getStolEval());  // scalar tolerances on fractional improvement in ss, default stol=.0001
        octave.eval(lmParameters.getNiterEval());  // scalar max no. of iterations, default = 20
        octave.eval(lmParameters.getMinstepEval());   // Iteration stops if fractional change in parameters on two consecutive iterations is less than values in minstep
        octave.eval(lmParameters.getMaxstepEval());  // if .bounds is used
        octave.eval(lmParameters.getOptFractPrecisionEval());  // desired fractional precisions in parameter estimates
        octave.eval(lmParameters.getOptFractChangeEval());  // maximum fractional step changes in parameter vector
        octave.eval(lmParameters.getOptBoundsEval());

        //octave.eval("A = [0; 0; 0; 0; 0; 0; -1; 1];");  // constraints p7<=p8
        //octave.eval("B = 0;");
        //octave.eval("options.inequc = {A, B};");
    }

    public void startLevenbergMarquardt(OctaveEngine octave) {
        octave.eval("global verbose;");
        octave.eval("verbose=0;");
        //octave.eval("options");
        long time1 = System.currentTimeMillis();
        String fit = "" //
                + "[f1, p1, kvg1, iter1, corp1, covp1, covr1, stdresid1, Z1, r21] = ...\n" //
                + "leasqr(wav(idx1:idx2), ab(idx1:idx2), pin, F, stol, niter, wt1, dp, dFdp, options);\n" //
                + "";
        octave.eval(fit);  // F calls leasqrfunc or leasqrfunc2 (includes optimization of baseline/offset)
        long time2 = System.currentTimeMillis();
        double time = (time2 - time1) / 1000;
        String timeEvalString = "fitting_time = " + String.valueOf(time)+ ";";
        octave.eval(timeEvalString);
        LOGGER.info("Time in sec for Levenberg Marquardt fit = " + time);
    }
}
