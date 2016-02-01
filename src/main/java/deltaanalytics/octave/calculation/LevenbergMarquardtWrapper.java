package deltaanalytics.octave.calculation;

import dk.ange.octave.OctaveEngine;

public class LevenbergMarquardtWrapper {
    public void initializeLevenbergMarquardt(OctaveEngine octave, boolean nlcorr) {
        // parameters for Levenberg Marquardt
        // dp = fractional incr of p for numerical partials in dfdp, default= .001*ones(size(pin)), if dp=0 parameter is fixed
        if (nlcorr) {
            octave.eval("F = 'leasqrfunc2'");
            octave.eval("dp = [0.01; 0.01; 0.01; 0.01; 0.01; 0.01; 0.01; 0.01];");
            octave.eval("pin = [1e-4; 1.0; 22.5*1e-3; 2e-6; 0; 0; -1e-5; 1e-10];");
            octave.eval("minvalues = [-0.1; 0.5; 5*1e-3;     0; 0; -0.1; -1e-2; 0];");
            octave.eval("maxvalues = [ 0.1; 2.0; 40*1e-3; 1e-3; 1;  0.1; 0;  1e-2];");
        } else {
            octave.eval("F = 'leasqrfunc'");
            octave.eval("dp = [0.01; 0.01; 0.01; 0.01; 0.01; 0.01];");  // baseline_step = 0 => no fit of baseline shape
            octave.eval("pin = [1e-4; 1.0; 22.5*1e-3; 2e-6; 0; 0];");  //  initial guess: offset, res, FOV, mixing ratio, baseline scale, wavenumber shift
            octave.eval("minvalues = [-0.1; 0.5; 5*1e-3;     0; 0; -0.1];");
            octave.eval("maxvalues = [ 0.1; 2.0; 40*1e-3; 1e-3; 1;  0.1];");
        }
        octave.eval("if(baseline_step == 0), dp(5) = 0; end");  // only fit offset, no baseline distortion
        octave.eval("dFdp = 'dfdp';"); // estimated derivative use with dfdp.m
        octave.eval("wt1 = ones(length(wav(idx1:idx2)),1);");  // default 1.0
        octave.eval("stol = 1e-4;");  // scalar tolerances on fractional improvement in ss, default stol=.0001
        octave.eval("niter = 15;");  // scalar max no. of iterations, default = 20
        octave.eval("minstep = ones(length(pin),1)*0;");   // Iteration stops if fractional change in parameters on two consecutive iterations is less than values in minstep
        octave.eval("maxstep = ones(length(pin),1)*Inf;");  // if .bounds is used
        octave.eval("options.fract_prec = minstep;");
        octave.eval("options.fract_change = maxstep;");
        octave.eval("options.bounds = [minvalues, maxvalues];");

        //octave.eval("A = [0; 0; 0; 0; 0; 0; -1; 1];");  // constraints p7<=p8
        //octave.eval("B = 0;");
        //octave.eval("options.inequc = {A, B};");
    }

    public void startLevenbergMarquardt(OctaveEngine octave) {
        System.out.println("Starte Levenberg-Marquardt nichtlineare Regression");
        octave.eval("global verbose;");
        octave.eval("verbose=0;");
        long time1 = System.currentTimeMillis();
        String fit = "" //
                + "[f1, p1, kvg1, iter1, corp1, covp1, covr1, stdresid1, Z1, r21] = ...\n" //
                + "leasqr(wav(idx1:idx2), ab(idx1:idx2), pin, F, stol, niter, wt1, dp, dFdp, options);\n" //
                + "";
        octave.eval(fit);  // leasqrfunc2 includes optimization of baseline/offset
        long time2 = System.currentTimeMillis();
        System.out.println("Time in sec for Levenberg Marquardt fit = " + (time2 - time1) / 1000);
    }
}
