package deltaanalytics.octave.hitran;

import deltaanalytics.octave.input.HitranInput;
import deltaanalytics.octave.input.HitranInputParameters;
import dk.ange.octave.OctaveEngine;

public class HitranWrapper {
    public void initialize(OctaveEngine octaveEngine, HitranInputParameters inputParameter) {
        octaveEngine.eval(HitranInput.csvFiles());  // Hitran csv files
        octaveEngine.eval(HitranInput.molWeights());   // Hitran molecular weights
        
        octaveEngine.eval(inputParameter.getMoleculeEval());  // hitran_input=
        octaveEngine.eval(inputParameter.getMoEval());  // mo=
        octaveEngine.eval(inputParameter.getMoStrEval());  // mo_str=
        octaveEngine.eval(inputParameter.getBaselineStepEval());  // baseline correction, baseline_step = 0: no correction, only offset
        octaveEngine.eval(inputParameter.getMolWeightEval());  // MW=
        octaveEngine.eval(inputParameter.getAnfangEval());  // anfang=
        octaveEngine.eval(inputParameter.getEndeEval());  // ende=

        // parameters for hitran line shape function
        octaveEngine.eval(inputParameter.getStpEval());  // stp = 0.02
        octaveEngine.eval(inputParameter.getIntensThres1Eval()); //intensThres1 = 1e-25
        octaveEngine.eval(inputParameter.getIsotopo1Eval());  //isotopo1 = 1
        octaveEngine.eval(inputParameter.getIntensThres2Eval()); //intensThres2 = 1e-25
        octaveEngine.eval(inputParameter.getIsotopo2Eval());  //isotopo2 = 2
        octaveEngine.eval(inputParameter.getSfEval());  // sf = 1; scaling factor isotopo2
        octaveEngine.eval(inputParameter.getTempEval());  //Temp = 313; Temperature 40 °C
        octaveEngine.eval(inputParameter.getPatmEval());  //Patm = 1; total Pressure in ATM units
        octaveEngine.eval(inputParameter.getDdEval());  //Dd = 5; wings of each line

        octaveEngine.eval(inputParameter.getNormalizeEvsEfilt());  // normalize the area under the curve after convolution
        //octaveEngine.eval("whos");
        //octaveEngine.eval("type('mo')");        
        //octaveEngine.eval("type('mo_str')");
    }

    public void getHitranData(OctaveEngine octave, HitranInputParameters inputParameter) {
        // get Hitran data, calculate from Hitran_function2 or load from file
        String hitran_spectrum = "";
        if (inputParameter.getCallHitran()){
            hitran_spectrum = "" //
                + " [grd, SgmvTot, v0, gV, STot, STot2] = Hitran_function2( hitran_input, anfang, ende, stp, intensThres1, isotopo1, intensThres2, isotopo2, sf, Temp, Patm, MW, Dd );\n" //
                + " grd = grd';\n" //
                + " SgmvTot = SgmvTot';\n" //
                + " A = [grd, SgmvTot];\n" //  save as 2 column matrix
                + "save('-ascii', strcat('HitranSpektrum_',hitran_input,'_sf(',num2str(sf),')_wn',num2str(anfang),'-',num2str(ende)), 'A');\n" //
                + "save('-ascii', strcat('HitranSpektrum_',hitran_input,'_sf(',num2str(sf),')_wn',num2str(anfang),'-',num2str(ende),'_STot'), 'STot');\n" //
                + "save('-ascii', strcat('HitranSpektrum_',hitran_input,'_sf(',num2str(sf),')_wn',num2str(anfang),'-',num2str(ende),'_STot2'), 'STot2');\n" //
                + ""; //
            
        } else {
            hitran_spectrum = "" //
                + " A = load('-ascii', strcat('HitranSpektrum_',hitran_input,'_sf(',num2str(sf),')_wn',num2str(anfang),'-',num2str(ende)));\n" //
                + " grd = A(:,1);\n" //
                + " SgmvTot = A(:,2);\n" //
                + " STot = load('-ascii', strcat('HitranSpektrum_',hitran_input,'_sf(',num2str(sf),')_wn',num2str(anfang),'-',num2str(ende),'_STot'));\n" //
                + " STot2 = load('-ascii', strcat('HitranSpektrum_',hitran_input,'_sf(',num2str(sf),')_wn',num2str(anfang),'-',num2str(ende),'_STot2'));\n" //
                + "";                   
        }
        octave.eval(hitran_spectrum);

        // caluclate Extinction
        octave.eval("nL = 2.47937e19;");  //  at 296 K in 1/cm³ Loschmidt number(Navo/MolV)=2.68678e25  at 273.15 K in 1/m^3
        octave.eval("dist = 5.4;");  // distance in m (4.8 for Bruker Alpha, 5.33 for Bruker Matrix at 1ATM)
        octave.eval("Ext_Hitr = log10(e) * SgmvTot * nL * 296/Temp * dist*100;");  // SgmvTot in cm^2/(moleculeString*ATM), pressure in ATM, log10(e) = 0.43429
        octave.eval("AHitr = [grd, Ext_Hitr];");  // save as 2 column matrix
        String hitranExtinktion = String.format("save('-ascii', 'HitranExtinktion%d', 'AHitr');", inputParameter.getMolecule());
        octave.eval(hitranExtinktion);
        octave.eval("integral_Extinction_Hitr = trapz(grd, Ext_Hitr);");  // should be a little less then pure sum of S
        octave.eval("Extinction_Hitr = log10(e) * STot * nL * 296/Temp * dist*100;");  // raw numbers from Hitran sum of S
    }
}
