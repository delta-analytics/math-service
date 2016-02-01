package deltaanalytics.octave.hitran;

import deltaanalytics.octave.input.HitranInput;
import deltaanalytics.octave.input.InputParameter;
import deltaanalytics.octave.input.MW;
import dk.ange.octave.OctaveEngine;

public class HitranWrapper {
    public void initialize(OctaveEngine octaveEngine, String molecule, InputParameter inputParameter) {
        octaveEngine.eval(molecule);  // molecule to investigate => mo=..
        octaveEngine.eval(HitranInput.values());
        octaveEngine.eval("hitran_input = hitran_input{mo}");
        octaveEngine.eval(MW.values());   // molecular weight
        octaveEngine.eval(inputParameter.getCallHitranEval());  // call Hitran_function2
        octaveEngine.eval("low = [3860, 3470, 2500, 2080, 2900, 3730, 2840];");
        octaveEngine.eval("high = [3965, 3760, 2600, 2141, 3165 3780, 2940];");
        octaveEngine.eval("baseline_step = 0;");  // baseline correction, baseline_step = 0: no correction, only offset

        octaveEngine.eval("MW = MW(mo)");
        octaveEngine.eval("anfang = low(mo)");
        octaveEngine.eval("ende = high(mo)");

        // parameters for hitran line shape function
        octaveEngine.eval("stp = 0.02");  // frequency comb spacing in cm-1, corresponds to 0.04 resolution in cm-1
        octaveEngine.eval("intensThres1 = 1e-25");
        octaveEngine.eval("isotopo1 = 1;");
        octaveEngine.eval("intensThres2 = 1e-25");
        octaveEngine.eval("isotopo2 = 2;");
        octaveEngine.eval("sf = 1");  // scaling factor isotopo2
        octaveEngine.eval("Temp = 313");  // Temperature 40 °C
        octaveEngine.eval("Patm = 1 "); // total Pressure in ATM units
        octaveEngine.eval("Dd = 5;");   // wings of each line

        octaveEngine.eval("global ratio_E_vs_E_filt;");  // normalize the area under the curve after convolution
    }

    public void getHitranData(OctaveEngine octave) {
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
}
