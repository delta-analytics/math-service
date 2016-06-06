package deltaanalytics.octave.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Arrays;

@Entity
public class LevenbergMarquardtParameters {
    private long id;
    private boolean currentDefault;
    private int molecule;
    private double[] dp;  // initial fractional change in parameter
    private double[] pin;  // initial parameter input
    private double[] minValues;  // max constraint
    private double[] maxValues;  // min constraint
    private double stol;  // tolerance criterium of aborting fit
    private int niter;  // max number of iterations
    private boolean nlCorr;  // nonlinear correction for spectrum

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isCurrentDefault() {
        return currentDefault;
    }

    public void setCurrentDefault(boolean currentDefault) {
        this.currentDefault = currentDefault;
    }

    public double[] getDp() {
        return dp;
    }

    public void setDp(double[] dp) {
        this.dp = dp;
    }

    public double[] getPin() {
        return pin;
    }

    public void setPin(double[] pin) {
        this.pin = pin;
    }

    public double[] getMinValues() {
        return minValues;
    }

    public void setMinValues(double[] minValues) {
        this.minValues = minValues;
    }

    public double[] getMaxValues() {
        return maxValues;
    }

    public void setMaxValues(double[] maxValues) {
        this.maxValues = maxValues;
    }

    public double getStol() {
        return stol;
    }

    public void setStol(double stol) {
        this.stol = stol;
    }

    public int getNiter() {
        return niter;
    }

    public void setNiter(int niter) {
        this.niter = niter;
    }

    public boolean isNlCorr() {
        return nlCorr;
    }

    public void setNlCorr(boolean nlCorr) {
        this.nlCorr = nlCorr;
    }

    public int getMolecule() {
        return molecule;
    }

    public void setMolecule(int molecule) {
        this.molecule = molecule;
    }

    @Override
    public String toString() {
        return "LevenbergMarquardtParameters{" +
                "molecule=" + molecule +
                ", dp=" + Arrays.toString(dp) +
                ", pin=" + Arrays.toString(pin) +
                ", minValues=" + Arrays.toString(minValues) +
                ", maxValues=" + Arrays.toString(maxValues) +
                ", stol=" + stol +
                ", niter=" + niter +
                ", nlCorr=" + nlCorr +
                '}';
    }
}