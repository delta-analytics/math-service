package deltaanalytics.octave.spectrum;

import dk.ange.octave.OctaveEngine;

public class SpectrumWrapper {
    public void getFtirData(OctaveEngine octave, String ftir_file_name) {
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

}
