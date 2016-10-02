package deltaanalytics.octave.input;

import deltaanalytics.octave.entity.LevenbergMarquardtParameters;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class LevenberqMarquardtInputParameters {
    private final int LINEARSIZE = 6;  // reduce array size from 8 to 6 if nlCorr = false
    private int molecule;
    private List<Double> dp;
    private List<Double> pin;
    private List<Double> minValues;
    private List<Double> maxValues;
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
            this.dp = LmPars.getDp().subList(0,6);
            this.pin = LmPars.getPin().subList(0,6);
            this.minValues = LmPars.getMinValues().subList(0,6);
            this.maxValues = LmPars.getMaxValues().subList(0,6);
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

    public List<Double> getDp() {
        return dp;
    }
    public void setDp(List<Double> dp) {
        this.dp = dp;
    }
    public String getDpEval() {
        return "dp = [" + dp.stream().map(Object::toString).collect(Collectors.joining(", ")) + "];";
    }

    public List<Double> getPin() {
        return pin;
    }
    public void setPin(List<Double> pin) {
        this.pin = pin;
    }
    public String getPinEval() {
        return "pin = [" + pin.stream().map(Object::toString).collect(Collectors.joining(", ")) + "];";
    }
    
    public List<Double> getMinValues() {
        return minValues;
    }
    public void setMinValues(List<Double> minValues) {
        this.minValues = minValues;
    }
    public String getMinValuesEval(){
        return "minvalues = [" + minValues.stream().map(Object::toString).collect(Collectors.joining(", ")) + "];" ;
    }

    public List<Double> getMaxValues() {
        return maxValues;
    }
    public void setMaxValues(List<Double> maxValues) {
        this.maxValues = maxValues;
    }
    public String getMaxValuesEval(){
        return "maxvalues = [" + maxValues.stream().map(Object::toString).collect(Collectors.joining(", ")) + "];" ;
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
                + "dp=" + dp.stream().map(Object::toString).collect(Collectors.joining(", "))
                + ", pin=" + pin.stream().map(Object::toString).collect(Collectors.joining(", "))
                + ", minValues=" + minValues.stream().map(Object::toString).collect(Collectors.joining(", "))
                + ", maxValues=" + maxValues.stream().map(Object::toString).collect(Collectors.joining(", "))
                + ", stol=" + stol + ", niter=" + niter + ", nlCorr=" + nlCorr
                + "}";
    }

    public int getMolecule() {
        return molecule;
    }

    public void setMolecule(int molecule) {
        this.molecule = molecule;
    }  
       
}
