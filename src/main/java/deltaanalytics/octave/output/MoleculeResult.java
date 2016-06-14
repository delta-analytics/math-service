package deltaanalytics.octave.output;

public class MoleculeResult {
    private int molecule;
    private double mixingRatioFromIntegralUnderTheCurve;
    private double mixingRatioFromHitranSum;
    private double r2;
    private double TimeInSecForLevenbergMarquardtFit;
    //private ArrayRealVector fitParams;   //  includes FOV, RES, offset,... see 6 parameters below
    private double estimatedFov;
    private double fovLineShift;
    private double additionalLineShift;
    private double effectiveResolution;
    private double amplitudeFitFactor;
    private double offsetFitConstant;
    //private ArrayRealVector initialGuess;   // -> LevenbergMarquardtParameters
    //private ArrayRealVector dp;   // -> LevenbergMarquardtParameters

    public double getMixingRatioFromIntegralUnderTheCurve() {
        return mixingRatioFromIntegralUnderTheCurve;
    }

    public void setMixingRatioFromIntegralUnderTheCurve(double mixingRatioFromIntegralUnderTheCurve) {
        this.mixingRatioFromIntegralUnderTheCurve = mixingRatioFromIntegralUnderTheCurve;
    }

    public double getMixingRatioFromHitranSum() {
        return mixingRatioFromHitranSum;
    }

    public void setMixingRatioFromHitranSum(double mixingRatioFromHitranSum) {
        this.mixingRatioFromHitranSum = mixingRatioFromHitranSum;
    }

    public double getR2() {
        return r2;
    }

    public void setR2(double r2) {
        this.r2 = r2;
    }

    public double getTimeInSecForLevenbergMarquardtFit() {
        return TimeInSecForLevenbergMarquardtFit;
    }

    public void setTimeInSecForLevenbergMarquardtFit(double timeInSecForLevenbergMarquardtFit) {
        TimeInSecForLevenbergMarquardtFit = timeInSecForLevenbergMarquardtFit;
    }

    public double getEstimatedFov() {
        return estimatedFov;
    }

    public void setEstimatedFov(double estimatedFov) {
        this.estimatedFov = estimatedFov;
    }

    public double getFovLineShift() {
        return fovLineShift;
    }

    public void setFovLineShift(double fovLineShift) {
        this.fovLineShift = fovLineShift;
    }

    public double getAdditionalLineShift() {
        return additionalLineShift;
    }

    public void setAdditionalLineShift(double additionalLineShift) {
        this.additionalLineShift = additionalLineShift;
    }

    public double getEffectiveResolution() {
        return effectiveResolution;
    }

    public void setEffectiveResolution(double effectiveResolution) {
        this.effectiveResolution = effectiveResolution;
    }

    public double getAmplitudeFitFactor() {
        return amplitudeFitFactor;
    }

    public void setAmplitudeFitFactor(double amplitudeFitFactor) {
        this.amplitudeFitFactor = amplitudeFitFactor;
    }

    public double getOffsetFitConstant() {
        return offsetFitConstant;
    }

    public void setOffsetFitConstant(double offsetFitConstant) {
        this.offsetFitConstant = offsetFitConstant;
    }

    public int getMolecule() {
        return molecule;
    }

    public void setMolecule(int molecule) {
        this.molecule = molecule;
    }    
    
    @Override
    public String toString() {
        return "Result{"
                + "molecule=" + molecule
                + ", mixingRatioFromIntegralUnderTheCurve=" + mixingRatioFromIntegralUnderTheCurve 
                + ", mixingRatioFromHitranSum=" + mixingRatioFromHitranSum 
                + ", r2=" + r2 
                + ", TimeInSecForLevenbergMarquardtFit=" + TimeInSecForLevenbergMarquardtFit 
                + ", estimatedFov=" + estimatedFov 
                + ", fovLineShift=" + fovLineShift 
                + ", additionalLineShift=" + additionalLineShift 
                + ", effectiveResolution=" + effectiveResolution 
                + ", amplitudeFitFactor=" + amplitudeFitFactor 
                + ", offsetFitConstant=" + offsetFitConstant 
                + ", time in sec for fit=" + TimeInSecForLevenbergMarquardtFit 
                + "}";
    }
    
}
