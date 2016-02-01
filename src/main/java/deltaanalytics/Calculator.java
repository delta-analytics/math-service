package deltaanalytics;

import dk.ange.octave.OctaveEngine;
import dk.ange.octave.OctaveEngineFactory;
import dk.ange.octave.type.OctaveDouble;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.util.Precision;

import java.io.File;

public class Calculator {

    public static void main(String[] args) {
        OctaveEngineFactory factory = new OctaveEngineFactory();
        factory.setOctaveProgram(new File("C:\\Octave\\Octave-4.0.0\\bin\\octave-cli"));
        OctaveEngine octave = factory.getScriptEngine();

        octave.eval("pkg load optim");  // load optimization package for levenberg marquart leasqr.m, etc.

        // molecules 1=H2O, 2=CO2, 3=N2O, 4=CO, 5=CH4, 6=NO, 7=NO2
        initializeHitran(octave, "mo=4");  // mo is the variable name in octave

        getHitranData(octave);

        // name of bruker data file
        getFtirData(octave, "ftir_data='Test1.10.dpt'");  // ftir_data is the variable name in octave

        boolean nl_corr = false;
        initializeLevenbergMarquardt(octave, nl_corr);

        startLevenbergMarquardt(octave);

        outputResult(octave);

        octave.close();
    }

    private static void initializeHitran(OctaveEngine octave, String molecule) {
        octave.eval(molecule);  // molecule to investigate
        octave.eval("hitran_input = {'01.csv', '02.csv', '04.csv', '05.csv', '06.csv', '08.csv', '10.csv'};");
        octave.eval("hitran_input = hitran_input{mo}");
        octave.eval("MW = [18.015, 44.010, 44.013, 28.010, 16.043, 30.006, 46,0055];");   // molecular weight
        octave.eval("call_hitran = [false, false, false, false, false, false, false];");  // call Hitran_function2
        octave.eval("low = [3860, 3470, 2500, 2080, 2900, 3730, 2840];");
        octave.eval("high = [3965, 3760, 2600, 2141, 3165 3780, 2940];");
        octave.eval("baseline_step = 0;");  // baseline correction, baseline_step = 0: no correction, only offset

        octave.eval("MW = MW(mo)");
        octave.eval("anfang = low(mo)");
        octave.eval("ende = high(mo)");

        // parameters for hitran line shape function
        octave.eval("stp = 0.02");  // frequency comb spacing in cm-1, corresponds to 0.04 resolution in cm-1
        octave.eval("intensThres1 = 1e-25");
        octave.eval("isotopo1 = 1;");
        octave.eval("intensThres2 = 1e-25");
        octave.eval("isotopo2 = 2;");
        octave.eval("sf = 1");  // scaling factor isotopo2
        octave.eval("Temp = 313");  // Temperature 40 °C jüke
        octave.eval("Patm = 1 "); // total Pressure in ATM units jüke
        octave.eval("Dd = 5;");   // wings of each line

        octave.eval("global ratio_E_vs_E_filt;");  // normalize the area under the curve after convolution
    }

    //fest
    private static void getHitranData(OctaveEngine octave) {
        // get Hitran data
        String hitran_spectrum = "" //
                + "if(call_hitran(mo))\n" //
                + " [grd, SgmvTot, v0, gV, STot, STot2] = Hitran_function2( hitran_input, anfang, ende, stp, intensThres1, isotopo1, intensThres2, isotopo2, sf, Temp, Patm, MW, Dd );\n" //
                + " grd = grd';\n" //
                + " SgmvTot = SgmvTot';\n" //
                + " A = [grd, SgmvTot];\n" //  save as 2 column matrix
                + "save('-ascii', strcat('HitranSpektrum_',hitran_input,'_sf(',num2str(sf),')_wn',num2str(anfang),'-',num2str(ende)), 'A');\n" //
                + "save('-ascii', strcat('HitranSpektrum_',hitran_input,'_sf(',num2str(sf),')_wn',num2str(anfang),'-',num2str(ende),'_STot'), 'STot');\n" //
                + "save('-ascii', strcat('HitranSpektrum_',hitran_input,'_sf(',num2str(sf),')_wn',num2str(anfang),'-',num2str(ende),'_STot2'), 'STot2');\n" //
                + "else\n" //
                + " A = load('-ascii', strcat('HitranSpektrum_',hitran_input,'_sf(',num2str(sf),')_wn',num2str(anfang),'-',num2str(ende)));\n" //
                + " grd = A(:,1);\n" //
                + " SgmvTot = A(:,2);\n" //
                + " STot = load('-ascii', strcat('HitranSpektrum_',hitran_input,'_sf(',num2str(sf),')_wn',num2str(anfang),'-',num2str(ende),'_STot'));\n" //
                + " STot2 = load('-ascii', strcat('HitranSpektrum_',hitran_input,'_sf(',num2str(sf),')_wn',num2str(anfang),'-',num2str(ende),'_STot2'));\n" //
                + "endif\n" //
                + "";
        octave.eval(hitran_spectrum);

        // caluclate Extinction
        octave.eval("nL = 2.47937e19;");  //  at 296 K in 1/cm³ Loschmidt number(Navo/MolV)=2.68678e25  at 273.15 K in 1/m^3
        octave.eval("dist = 5.4;");  // distance in m (4.8 for Bruker Alpha, 5.33 for Bruker Matrix at 1ATM)
        octave.eval("Ext_Hitr = log10(e) * SgmvTot * nL * 296/Temp * dist*100;");  // SgmvTot in cm^2/(molecule*ATM), pressure in ATM, log10(e) = 0.43429
        octave.eval("AHitr = [grd, Ext_Hitr];");  // save as 2 column matrix
        octave.eval("save('-ascii', 'HitranExtinktion', 'AHitr');");
        octave.eval("integral_Extinction_Hitr = trapz(grd, Ext_Hitr);");  // should be a little less then pure sum of S
        octave.eval("Extinction_Hitr = log10(e) * STot * nL * 296/Temp * dist*100;");  // raw numbers from Hitran sum of S
    }

    private static void getFtirData(OctaveEngine octave, String ftir_file_name) {
        // ftir data
        octave.eval(ftir_file_name);
        octave.eval("FTIR_AB = load('-ascii', ftir_data);");   // name of matrix is FTIR_AB: columns of data, 1st wavenumber. 2nd absorbance
        octave.eval("N = length(FTIR_AB(:,1));");
        octave.eval("wav = flipud(FTIR_AB(1:N,1));");   //col vector
        octave.eval("ab = flipud(FTIR_AB(1:N,2));");    //col vector

        octave.eval("[idx1, wnr1] = vpos(wav, anfang);");   //call function vpos.m, 1st data point
        octave.eval("[idx2, wnr2] = vpos(wav, ende);");     //call function vpos.m, last data point

        octave.eval("center_wn = (anfang+ende)/2;");        //center wavenumber of selected range

        // baseline, used if baseline_step != 0
        String base_line = "" //
                + "if (baseline_step != 0)\n" //
                + " global baseline_corr = baseline(anfang, ende, baseline_step, wav, ab);\n" //  ## baseline_step in cm-1
                + "else\n" //
                + " global baseline_corr = zeros(length(wav(idx1:idx2)),1);\n" //  ## col vector
                + "endif\n" //
                + "";
        octave.eval(base_line);
    }

    private static void initializeLevenbergMarquardt(OctaveEngine octave, boolean nlcorr) {
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

    private static void startLevenbergMarquardt(OctaveEngine octave) {
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

    private static void outputResult(OctaveEngine octave) {
        System.out.println("initial guess=" + new ArrayRealVector(((OctaveDouble) octave.get("pin")).getData()));
        System.out.println("dp=" + new ArrayRealVector(((OctaveDouble) octave.get("dp")).getData()));

        OctaveDouble fitParams = (OctaveDouble) octave.get("p1");
        System.out.println("fitParams=" + new ArrayRealVector(fitParams.getData()));
        OctaveDouble r_squared = (OctaveDouble) octave.get("r21");
        double r2 = Precision.round(r_squared.get(1), 4);
        System.out.println("r2=" + r2);

        // compute the integrals under the curve
        octave.eval("ab_minus_offset = (ab(idx1:idx2) - baseline_corr*p1(5)) - p1(1);");
        octave.eval("area_ab_base_line_corrected = trapz(wav(idx1:idx2), ab_minus_offset);");

        octave.eval("scale1 = area_ab_base_line_corrected/integral_Extinction_Hitr* 1e6;");  //
        octave.eval("scale2 = area_ab_base_line_corrected/Extinction_Hitr* 1e6;");  //

        OctaveDouble mixing_ratio_ppm_from_integral = (OctaveDouble) octave.get("scale1");
        double ppms_integral = Precision.round(mixing_ratio_ppm_from_integral.get(1), 3);

        OctaveDouble mixing_ratio_ppm_from_hitran_sum = (OctaveDouble) octave.get("scale2");
        double ppms_hitran_sum = Precision.round(mixing_ratio_ppm_from_hitran_sum.get(1), 3);

        //Ergebnisse
        System.out.println("mixing ratio from Integral under the curve = " + ppms_integral);
        System.out.println("mixing ratio from hitran sum = " + ppms_hitran_sum);

        //Ergebnisse
        double estimated_fov = Precision.round(fitParams.get(3) * 1E3, 2);
        System.out.println("estimated fov = " + estimated_fov + " mRad");
        OctaveDouble center_wavenumber = (OctaveDouble) octave.get("center_wn");
        double fov_line_shift = Precision.round(fitParams.get(3) * fitParams.get(3) / 4 * center_wavenumber.get(1), 3);
        System.out.println("fov line shift = " + fov_line_shift + " cm-1");
        double additional_line_shift = Precision.round(fitParams.get(6), 6);
        System.out.println("additional line shift = " + additional_line_shift + " cm-1");
        double effective_resolution = Precision.round(fitParams.get(2), 2);
        System.out.println("effective resolution = " + effective_resolution + " cm-1");
        double amplitude_fit_factor = Precision.round(fitParams.get(4), 8);
        System.out.println("amplitude fit factor = " + amplitude_fit_factor);
        double offset_fit_constant = Precision.round(fitParams.get(1), 6);
        System.out.println("offset fit constant = " + offset_fit_constant);

        octave.eval("graphics_toolkit ('gnuplot')");
        octave.eval("figure()");
        octave.eval("title('Levenberg Marquart fit to FTIR spectrum');");
        octave.eval("plot(wav(idx1:idx2), ab_minus_offset, '-b;data baselin correctd;', wav(idx1:idx2), baseline_corr,'-r;baseline;', wav(idx1:idx2), (f1 - baseline_corr*p1(5)) - p1(1),'-k;output from LM fit;')");
        octave.eval("axis([min(wav(idx1:idx2)) max(wav(idx1:idx2)) min(ab_minus_offset) max(ab_minus_offset)*1.02]);");
        octave.eval("set(gca(),'XDir','reverse');");
        octave.eval("grid('minor')");
        octave.eval("drawnow()");
        octave.close();
    }
}
