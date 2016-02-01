package deltaanalytics.octave.input;

import java.math.BigDecimal;

public class LevenberqMarquartParameters {
    private BigDecimal stol;
    private int niter;
    boolean nlCorr;
    double baselineStep;

    public BigDecimal getStol() {
        return stol;
    }

    public void setStol(BigDecimal stol) {
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

    public double getBaselineStep() {
        return baselineStep;
    }

    public void setBaselineStep(double baselineStep) {
        this.baselineStep = baselineStep;
    }
}
