package deltaanalytics.octave.input;

import deltaanalytics.octave.dto.LevenbergMarquardtParameters;

import java.util.Arrays;


public class LevenberqMarquardtInputParameters {
    private final int LINEARSIZE = 6;  // reduce array size from 8 to 6 if nlCorr = false
    private int molecule;
    private double[] dp;
    private double[] pin;
    private double[] minValues;
    private double[] maxValues;
    private double stol;  // tolerance criterium for aborting fit
    private int niter;  // max number of iterations
    private boolean nlCorr;  // non linear correction  yes/no
    
    public LevenberqMarquardtInputParameters() {
        
    }

    public LevenberqMarquardtInputParameters(LevenbergMarquardtParameters LmPars){
        this.molecule = LmPars.getMolecule();
        this.nlCorr = LmPars.isNlCorr();

        if(this.nlCorr == true){
            this.dp = LmPars.getDp();
            this.pin = LmPars.getPin();
            this.minValues = LmPars.getMinValues();
            this.maxValues = LmPars.getMaxValues();            
        }else {
            this.dp = Arrays.copyOf(LmPars.getDp(), LINEARSIZE);
            this.pin = Arrays.copyOf(LmPars.getPin(), LINEARSIZE);
            this.minValues = Arrays.copyOf(LmPars.getMinValues(), LINEARSIZE);
            this.maxValues = Arrays.copyOf(LmPars.getMaxValues(), LINEARSIZE);             
        }
        this.stol = LmPars.getStol();
        this.niter = LmPars.getNiter();
    }

    
    public double getStol() {
        return stol;
    }
    public void setStol(double stol) {
        this.stol = stol;
    }
    public String getStolEval(){
        return "stol = " + getStol() + ";";
    }

    public int getNiter() {
        return niter;
    }
    public void setNiter(int niter) {
        this.niter = niter;
    }
    public String getNiterEval(){
        return "niter = " + getNiter() + ";";
    }

    public boolean isNlCorr() {
        return nlCorr;
    }

    public double[] getDp() {
        return dp;
    }
    public void setDp(double[] dp) {
        this.dp = dp;
    }
    public String getDpEval() {
        return "dp = " + Arrays.toString(dp) + ";";
    }

    public double[] getPin() {
        return pin;
    }
    public void setPin(double[] pin) {
        this.pin = pin;
    }
    public String getPinEval() {
        return "pin = " + Arrays.toString(pin) + ";";
    }
    
    public double[] getMinValues() {
        return minValues;
    }
    public void setMinValues(double[] minValues) {
        this.minValues = minValues;
    }
    public String getMinValuesEval(){
        return "minvalues = " + Arrays.toString(minValues) + ";" ;
    }

    public double[] getMaxValues() {
        return maxValues;
    }
    public void setMaxValues(double[] maxValues) {
        this.maxValues = maxValues;
    }
    public String getMaxValuesEval(){
        return "maxvalues = " + Arrays.toString(maxValues) + ";" ;
    }
    
    public String getFdefEval() {
        if (this.nlCorr == true){
            return "F = 'leasqrfunc2';";   
        } else {
            return "F = 'leasqrfunc';";
        }
    }    
    public String getBaselineEval(){
        return "if(baseline_step == 0), dp(5) = 0; end";
    }
    public String getDfdpEval(){
        return "dFdp = 'dfdp';";
    }
    public String getStatWeightsEval(){
        return "wt1 = ones(length(wav(idx1:idx2)),1);";
    }
    public String getMinstepEval() {
        return "minstep = ones(length(pin),1)*0;";
    }
    public String getMaxstepEval() {
        return "maxstep = ones(length(pin),1)*Inf;";
    }    
    public String getOptFractPrecisionEval() {
        return "options.fract_prec = minstep;";
    }
    public String getOptFractChangeEval() {
        return "options.fract_change = maxstep;";
    }
    public String getOptBoundsEval() {
        return "options.bounds = [minvalues', maxvalues'];";
    }

    @Override
    public String toString() {
        return "LevenberqMarquartInputParameters{" 
                + "dp=" + Arrays.toString(dp) + ", pin=" + Arrays.toString(pin)
                + ", minValues=" + Arrays.toString(minValues) + ", maxValues=" + Arrays.toString(maxValues)
                + ", stol=" + stol + ", niter=" + niter
                + ", nlCorr=" + nlCorr
                + "}";
    }

    public int getMolecule() {
        return molecule;
    }

    public void setMolecule(int molecule) {
        this.molecule = molecule;
    }  
       
}
