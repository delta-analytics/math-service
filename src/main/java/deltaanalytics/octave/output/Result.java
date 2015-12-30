package deltaanalytics.octave.output;

import java.math.BigDecimal;

public class Result {
    private double mixingRatioFromIntegralUnderTheCurve;
    private double mixingRatioFromHitranSum;
    private double r2;
    private double TimeInSecForLevenbergMarquardtFit;
    private double[] fitParams;
    private double estimatedFov;
    private BigDecimal fovLineShift;
    private BigDecimal additionalLineShift;
    private BigDecimal effectiveResolution;
    private BigDecimal amplitudeFitFactor;
    private double offsetFitConstant;
    private BigDecimal[] initialGuess;
    private BigDecimal[] dp;
}
