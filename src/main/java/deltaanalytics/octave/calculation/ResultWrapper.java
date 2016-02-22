package deltaanalytics.octave.calculation;

import deltaanalytics.octave.output.Result;
import dk.ange.octave.OctaveEngine;
import dk.ange.octave.type.OctaveDouble;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.util.Precision;

public class ResultWrapper {
    public Result outputResult(OctaveEngine octave) {
        Result result = new Result();
        OctaveDouble mo = (OctaveDouble) octave.get("mo");
        result.setMolecule((int) mo.get(1));
        
        result.setInitialGuess(new ArrayRealVector(((OctaveDouble) octave.get("pin")).getData()));
        result.setDp(new ArrayRealVector(((OctaveDouble) octave.get("dp")).getData()));

        OctaveDouble fitParams = (OctaveDouble) octave.get("p1");
        result.setFitParams(new ArrayRealVector(fitParams.getData()));
        OctaveDouble r_squared = (OctaveDouble) octave.get("r21");
        double r2 = Precision.round(r_squared.get(1), 4);
        result.setR2(r2);

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
        result.setMixingRatioFromIntegralUnderTheCurve(ppms_integral);
        result.setMixingRatioFromHitranSum(ppms_hitran_sum);

        //Ergebnisse
        double estimated_fov = Precision.round(fitParams.get(3) * 1E3, 2);
        result.setEstimatedFov(estimated_fov);
        OctaveDouble center_wavenumber = (OctaveDouble) octave.get("center_wn");
        double fov_line_shift = Precision.round(fitParams.get(3) * fitParams.get(3) / 4 * center_wavenumber.get(1), 3);
        result.setFovLineShift(fov_line_shift);
        double additional_line_shift = Precision.round(fitParams.get(6), 6);
        result.setAdditionalLineShift(additional_line_shift);
        double effective_resolution = Precision.round(fitParams.get(2), 2);
        result.setEffectiveResolution(effective_resolution);
        double amplitude_fit_factor = Precision.round(fitParams.get(4), 8);
        result.setAmplitudeFitFactor(amplitude_fit_factor);
        double offset_fit_constant = Precision.round(fitParams.get(1), 6);
        result.setOffsetFitConstant(offset_fit_constant);
        
        OctaveDouble time = (OctaveDouble) octave.get("fitting_time");
        result.setTimeInSecForLevenbergMarquardtFit(Precision.round(time.get(1), 2));
        
        //System.out.println(result.toString());

        return result;
    }
    
    public void showGnuGraph(OctaveEngine octave) {
        octave.eval("graphics_toolkit ('gnuplot')");
        octave.eval("figure()");
        octave.eval("title('L. M. fit to FTIR spectrum');");
        octave.eval("plot(wav(idx1:idx2), ab_minus_offset, '-b;data baselin correctd;', wav(idx1:idx2), baseline_corr,'-r;baseline;', wav(idx1:idx2), (f1 - baseline_corr*p1(5)) - p1(1),'-k;output from LM fit;')");
        octave.eval("axis([min(wav(idx1:idx2)) max(wav(idx1:idx2)) min(ab_minus_offset) max(ab_minus_offset)*1.02]);");
        octave.eval("set(gca(),'XDir','reverse');");
        octave.eval("grid('minor')");
        octave.eval("legend(strcat('molecule=', mo_str));");
        octave.eval("drawnow('expose')");      
    }
}
