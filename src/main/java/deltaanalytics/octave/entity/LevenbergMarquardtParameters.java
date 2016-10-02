package deltaanalytics.octave.entity;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class LevenbergMarquardtParameters implements Serializable {
    private long id;
    private boolean currentDefault;
    private int molecule;
    @ElementCollection
    private List<Double> dp = new ArrayList<>(); // initial fractional change in parameter offset, res, FOV, mixing ratio, baseline scale, wavenumber shift
    @ElementCollection
    private List<Double> pin = new ArrayList<>();  // initial parameter input
    @ElementCollection
    private List<Double> minValues = new ArrayList<>();  // max constraint
    @ElementCollection
    private List<Double> maxValues = new ArrayList<>();  // min constraint
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

    @ElementCollection
    public List<Double> getDp() {
        return dp;
    }

    public void setDp(List<Double> dp) {
        this.dp = dp;
    }

    @ElementCollection
    public List<Double> getPin() {
        return pin;
    }

    public void setPin(List<Double> pin) {
        this.pin = pin;
    }

    @ElementCollection
    public List<Double> getMinValues() {
        return minValues;
    }

    public void setMinValues(List<Double> minValues) {
        this.minValues = minValues;
    }

    @ElementCollection
    public List<Double> getMaxValues() {
        return maxValues;
    }

    public void setMaxValues(List<Double> maxValues) {
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
                "id=" + id +
                ", currentDefault=" + currentDefault +
                ", molecule=" + molecule +
                ", dp=" + dp +
                ", pin=" + pin +
                ", minValues=" + minValues +
                ", maxValues=" + maxValues +
                ", stol=" + stol +
                ", niter=" + niter +
                ", nlCorr=" + nlCorr +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LevenbergMarquardtParameters that = (LevenbergMarquardtParameters) o;

        if (id != that.id) return false;
        if (currentDefault != that.currentDefault) return false;
        if (molecule != that.molecule) return false;
        if (Double.compare(that.stol, stol) != 0) return false;
        if (niter != that.niter) return false;
        if (nlCorr != that.nlCorr) return false;
        if (dp != null ? !dp.equals(that.dp) : that.dp != null) return false;
        if (pin != null ? !pin.equals(that.pin) : that.pin != null) return false;
        if (minValues != null ? !minValues.equals(that.minValues) : that.minValues != null) return false;
        return maxValues != null ? maxValues.equals(that.maxValues) : that.maxValues == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (currentDefault ? 1 : 0);
        result = 31 * result + molecule;
        result = 31 * result + (dp != null ? dp.hashCode() : 0);
        result = 31 * result + (pin != null ? pin.hashCode() : 0);
        result = 31 * result + (minValues != null ? minValues.hashCode() : 0);
        result = 31 * result + (maxValues != null ? maxValues.hashCode() : 0);
        temp = Double.doubleToLongBits(stol);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + niter;
        result = 31 * result + (nlCorr ? 1 : 0);
        return result;
    }
}