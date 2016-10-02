package deltaanalytics.octave.input;

import deltaanalytics.Calculator;
import deltaanalytics.octave.entity.HitranParameters;

public class HitranInputParameters {
    private boolean callHitran;  // do fitting with/without call to Hitran function
    private double lowWN;  // low wavenumbers
    private double highWN;  // high wavenumbers
    private int molecule;  // the molecule = 1,2,3,4,5,6,7  1=H2O  2=CO2  3=N2O  4=CO  5=CH4  6=NO  7=NO2
    private int baselineStep;  // sampling point interval to fit a baseline curve
    private double stp;  // default 0.02; frequency hitran spacing in cm-1, 0.02 corresponds to 0.04 resolution in cm-1
    private double intensThres1; // intensity threshold1 1e-25
    private int isotopo1;  //default 1
    private double intensThres2; // intensity threshold2 1e-25
    private int isotopo2;  // default 2
    private double sf;  // default 1; scaling factor isotopo2
    private double Temp;  // default = 313; Temperature 40 °C
    private double Patm;  // default = 1; total Pressure in ATM units
    private int Dd;  // default = 5; wings of each line in wavenumbers of cm-1

    public HitranInputParameters(){   
    }
    
    public HitranInputParameters(HitranParameters hitranPars) {
        this.callHitran = hitranPars.getCallHitran();
        this.lowWN = hitranPars.getLowWN();
        this.highWN = hitranPars.getHighWN();
        this.molecule = hitranPars.getMolecule();
        this.baselineStep = hitranPars.getBaselineStep();
        this.stp = hitranPars.getStp();
        this.intensThres1 = hitranPars.getIntensThres1();
        this.isotopo1 = hitranPars.getIsotopo1();
        this.intensThres2 = hitranPars.getIntensThres2();
        this.isotopo2 = hitranPars.getIsotopo2();
        this.sf = hitranPars.getSf();
        this.Temp = hitranPars.getTemp();
        this.Patm = hitranPars.getPatm();
        this.Dd = hitranPars.getDd();
    }
      
    public boolean getCallHitran() {
        return callHitran;
    }

    public void setCallHitran(boolean callHitran) {
        this.callHitran = callHitran;
    }

    public int getMolecule() {
        return molecule;
    }

    public void setMolecule(int molecule) {
        this.molecule = molecule;
    }
    
    public String getMoleculeEval(){   //"hitran_input = hitran_input{molecule};"
        return "hitran_input = hitran_input{" + getMolecule() + "};";                
    }
    
    public String getMoEval(){
        return "mo = " + getMolecule() + ";";
    }
    
    public String getMoStrEval(){
        return "mo_str = '" + Calculator.MOLECULE.get(getMolecule()-1) + "';";
    }

    public int getBaselineStep() {
        return baselineStep;
    }
    public void setBaselineStep(int baselineStep) {
        this.baselineStep = baselineStep;
    }  
    public String getBaselineStepEval(){
        return "baseline_step = " + getBaselineStep() + ";"; 
    }
    
    public String getMolWeightEval() {      
        return "MW = MW(" + getMolecule() + ");";
    }
    
    public double getLowWN() {
        return lowWN;
    }
    public void setLowWN(double lowWN) {
        this.lowWN = lowWN;
    }

    public double getHighWN() {
        return highWN;
    }
    public void setHighWN(double highWN) {
        this.highWN = highWN;
    }
    
    public String getAnfangEval() {
        return "anfang = " + getLowWN() + ";";
    }
    public String getEndeEval() {        
        return "ende = " + getHighWN() + ";";
    }    

    public double getStp() {
        return stp;
    }
    public void setStp(double stp) {
        this.stp = stp;
    }
    public String getStpEval() {
        return "stp = " + getStp() + ";";
    }

    public double getIntensThres1() {
        return intensThres1;
    }
    public void setIntensThres1(double intensThres1) {
        this.intensThres1 = intensThres1;
    }
    public String getIntensThres1Eval() {
        return "intensThres1 = " + getIntensThres1() + ";";
    }
    
    public int getIsotopo1() {
        return isotopo1;
    }
    public void setIsotopo1(int isotopo1) {
        this.isotopo1 = isotopo1;
    }
    public String getIsotopo1Eval() {
        return "isotopo1 = " + getIsotopo1() + ";";
    }    

    public double getIntensThres2() {
        return intensThres2;
    }
    public void setIntensThres2(double intensThres2) {
        this.intensThres2 = intensThres2;
    }
    public String getIntensThres2Eval() {
        return "intensThres2 = " + getIntensThres2() + ";";
    }
    
    public int getIsotopo2() {
        return isotopo2;
    }
    public void setIsotopo2(int isotopo2) {
        this.isotopo2 = isotopo2;
    }
    public String getIsotopo2Eval() {
        return "isotopo2 = " + getIsotopo2() + ";";
    } 
    
    public double getSf() {
        return sf;
    }
    public void setSf(double sf) {
        this.sf = sf;
    }
    public String getSfEval() {
        return "sf = " + getSf() + ";";
    }     

    public double getTemp() {
        return Temp;
    }
    public void setTemp(double Temp) {
        this.Temp = Temp;
    }
    public String getTempEval() {
        return "Temp = " + getTemp() + ";";
    } 
    
    public double getPatm() {
        return Patm;
    }
    public void setPatm(double Patm) {
        this.Patm = Patm;
    }
    public String getPatmEval() {
        return "Patm = " + getPatm() + ";";
    } 
    
    public int getDd() {
        return Dd;
    }
    public void setDd(int Dd) {
        this.Dd = Dd;
    }
    public String getDdEval() {
        return "Dd = " + getDd() + ";";
    }
    
    public String getNormalizeEvsEfilt(){
        return "global ratio_E_vs_E_filt;";
    }

    @Override
    public String toString() {
        return "HitranInputParameters{" + "callHitran=" + callHitran
                + ", lowWN=" + lowWN + ", highWN=" + highWN
                + ", molecule=" + molecule
                + ", baselineStep=" + baselineStep
                + ", stp=" + stp
                + ", intensThres1=" + intensThres1 + ", isotopo1=" + isotopo1
                + ", intensThres2=" + intensThres2 + ", isotopo2=" + isotopo2
                + ", sf=" + sf
                + ", Temp=" + Temp + ", Patm=" + Patm
                + ", Dd=" + Dd + '}';
    }   
}
