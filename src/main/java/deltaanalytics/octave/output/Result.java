package deltaanalytics.octave.output;

import org.apache.commons.math3.linear.ArrayRealVector;

public class Result {
    private double mixingRatioFromIntegralUnderTheCurve;
    private double mixingRatioFromHitranSum;
    private double r2;
    private double TimeInSecForLevenbergMarquardtFit;
    private ArrayRealVector fitParams;
    private double estimatedFov;
    private double fovLineShift;
    private double additionalLineShift;
    private double effectiveResolution;
    private double amplitudeFitFactor;
    private double offsetFitConstant;
    private ArrayRealVector initialGuess;
    private ArrayRealVector dp;

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

    public ArrayRealVector getFitParams() {
        return fitParams;
    }

    public void setFitParams(ArrayRealVector fitParams) {
        this.fitParams = fitParams;
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

    public ArrayRealVector getInitialGuess() {
        return initialGuess;
    }

    public void setInitialGuess(ArrayRealVector initialGuess) {
        this.initialGuess = initialGuess;
    }

    public ArrayRealVector getDp() {
        return dp;
    }

    public void setDp(ArrayRealVector dp) {
        this.dp = dp;
    }

    @Override
    public String toString() {
        return "Result{" + "mixingRatioFromIntegralUnderTheCurve=" + mixingRatioFromIntegralUnderTheCurve + ", mixingRatioFromHitranSum=" + mixingRatioFromHitranSum + ", r2=" + r2 + ", TimeInSecForLevenbergMarquardtFit=" + TimeInSecForLevenbergMarquardtFit + ", fitParams=" + fitParams + ", estimatedFov=" + estimatedFov + ", fovLineShift=" + fovLineShift + ", additionalLineShift=" + additionalLineShift + ", effectiveResolution=" + effectiveResolution + ", amplitudeFitFactor=" + amplitudeFitFactor + ", offsetFitConstant=" + offsetFitConstant + ", initialGuess=" + initialGuess + ", dp=" + dp + '}';
    }
    
}
