package deltaanalytics.octave.initialize;

import deltaanalytics.octave.entity.HitranParameters;
import deltaanalytics.octave.entity.LevenbergMarquardtParameters;
import deltaanalytics.octave.repositories.HitranParametersRepository;
import deltaanalytics.octave.repositories.LevenberqMarquardtParametersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class MathConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(MathConfiguration.class);
    private HitranParametersRepository hitranParametersRepository;
    private LevenberqMarquardtParametersRepository levenberqMarquardtParametersRepository;

    @PostConstruct
    public void setup() {
        // Molecules 1=H2O  2=CO2  3=N2O  4=CO  5=CH4  6=NO  7=NO2
        hitranParametersRepository.saveAndMarkNewDefaults(buildHitranParametersMoleculeH2O());
        hitranParametersRepository.saveAndMarkNewDefaults(buildHitranParametersMoleculeCO2());
        hitranParametersRepository.saveAndMarkNewDefaults(buildHitranParametersMoleculeN2O());
        hitranParametersRepository.saveAndMarkNewDefaults(buildHitranParametersMoleculeCO());
        hitranParametersRepository.saveAndMarkNewDefaults(buildHitranParametersMoleculeCH4());
        hitranParametersRepository.saveAndMarkNewDefaults(buildHitranParametersMoleculeNO());
        hitranParametersRepository.saveAndMarkNewDefaults(buildHitranParametersMoleculeNO2());

        levenberqMarquardtParametersRepository.saveAndMarkNewDefaults(builLevenbergMarquardtParameters(1));
        levenberqMarquardtParametersRepository.saveAndMarkNewDefaults(builLevenbergMarquardtParametersForCO2());
        levenberqMarquardtParametersRepository.saveAndMarkNewDefaults(builLevenbergMarquardtParameters(3));
        levenberqMarquardtParametersRepository.saveAndMarkNewDefaults(builLevenbergMarquardtParameters(4));
        levenberqMarquardtParametersRepository.saveAndMarkNewDefaults(builLevenbergMarquardtParameters(5));
        levenberqMarquardtParametersRepository.saveAndMarkNewDefaults(builLevenbergMarquardtParameters(6));
        levenberqMarquardtParametersRepository.saveAndMarkNewDefaults(builLevenbergMarquardtParameters(7));

    }

    @Autowired
    public void setHitranParametersRepository(HitranParametersRepository hitranParametersRepository) {
        this.hitranParametersRepository = hitranParametersRepository;
    }

    @Autowired
    public void setLevenberqMarquardtParametersRepository(LevenberqMarquardtParametersRepository levenberqMarquardtParametersRepository) {
        this.levenberqMarquardtParametersRepository = levenberqMarquardtParametersRepository;
    }

    private HitranParameters buildHitranParametersMoleculeH2O() {
        HitranParameters hitranParameters = new HitranParameters();
        hitranParameters.setMolecule(1);
        hitranParameters.setCallHitran(false);
        hitranParameters.setLowWN(3860);
        hitranParameters.setHighWN(3965);
        hitranParameters.setBaselineStep(0);
        hitranParameters.setStp(0.02);
        hitranParameters.setIntensThres1(1e-25);
        hitranParameters.setIsotopo1(1);
        hitranParameters.setIntensThres2(1e-25);
        hitranParameters.setIsotopo2(2);
        hitranParameters.setSf(1);
        hitranParameters.setDd(5);
        return hitranParameters;
    }

    private HitranParameters buildHitranParametersMoleculeCO2() {
        HitranParameters hitranParameters = new HitranParameters();
        hitranParameters.setMolecule(2);
        hitranParameters.setCallHitran(false);
        hitranParameters.setLowWN(3470);
        hitranParameters.setHighWN(3760);
        hitranParameters.setBaselineStep(0);
        hitranParameters.setStp(0.02);
        hitranParameters.setIntensThres1(1e-25);
        hitranParameters.setIsotopo1(1);
        hitranParameters.setIntensThres2(1e-25);
        hitranParameters.setIsotopo2(2);
        hitranParameters.setSf(1);
        hitranParameters.setDd(5);
        return hitranParameters;
    }

    private HitranParameters buildHitranParametersMoleculeN2O() {
        HitranParameters hitranParameters = new HitranParameters();
        hitranParameters.setMolecule(3);
        hitranParameters.setCallHitran(false);
        hitranParameters.setLowWN(2500);
        hitranParameters.setHighWN(2600);
        hitranParameters.setBaselineStep(50);
        hitranParameters.setStp(0.02);
        hitranParameters.setIntensThres1(1e-25);
        hitranParameters.setIsotopo1(1);
        hitranParameters.setIntensThres2(1e-25);
        hitranParameters.setIsotopo2(2);
        hitranParameters.setSf(1);
        hitranParameters.setDd(5);
        return hitranParameters;
    }

    private HitranParameters buildHitranParametersMoleculeCO() {
        HitranParameters hitranParameters = new HitranParameters();
        hitranParameters.setMolecule(4);
        hitranParameters.setCallHitran(false);
        hitranParameters.setLowWN(2080);
        hitranParameters.setHighWN(2141);
        hitranParameters.setBaselineStep(0);
        hitranParameters.setStp(0.02);
        hitranParameters.setIntensThres1(1e-25);
        hitranParameters.setIsotopo1(1);
        hitranParameters.setIntensThres2(1e-25);
        hitranParameters.setIsotopo2(2);
        hitranParameters.setSf(1);
        hitranParameters.setDd(5);
        return hitranParameters;
    }

    private HitranParameters buildHitranParametersMoleculeCH4() {
        HitranParameters hitranParameters = new HitranParameters();
        hitranParameters.setMolecule(5);
        hitranParameters.setCallHitran(false);
        hitranParameters.setLowWN(2900);
        hitranParameters.setHighWN(3165);
        hitranParameters.setBaselineStep(0);
        hitranParameters.setStp(0.02);
        hitranParameters.setIntensThres1(1e-25);
        hitranParameters.setIsotopo1(1);
        hitranParameters.setIntensThres2(1e-25);
        hitranParameters.setIsotopo2(2);
        hitranParameters.setSf(1);
        hitranParameters.setDd(5);
        return hitranParameters;
    }

    private HitranParameters buildHitranParametersMoleculeNO() {
        HitranParameters hitranParameters = new HitranParameters();
        hitranParameters.setMolecule(6);
        hitranParameters.setCallHitran(false);
        hitranParameters.setLowWN(3730);
        hitranParameters.setHighWN(3780);
        hitranParameters.setBaselineStep(50);
        hitranParameters.setStp(0.02);
        hitranParameters.setIntensThres1(1e-25);
        hitranParameters.setIsotopo1(1);
        hitranParameters.setIntensThres2(1e-25);
        hitranParameters.setIsotopo2(2);
        hitranParameters.setSf(1);
        hitranParameters.setDd(5);
        return hitranParameters;
    }

    private HitranParameters buildHitranParametersMoleculeNO2() {
        HitranParameters hitranParameters = new HitranParameters();
        hitranParameters.setMolecule(7);
        hitranParameters.setCallHitran(false);
        hitranParameters.setLowWN(2840);
        hitranParameters.setHighWN(2940);
        hitranParameters.setBaselineStep(50);
        hitranParameters.setStp(0.02);
        hitranParameters.setIntensThres1(1e-25);
        hitranParameters.setIsotopo1(1);
        hitranParameters.setIntensThres2(1e-25);
        hitranParameters.setIsotopo2(2);
        hitranParameters.setSf(1);
        hitranParameters.setDd(5);
        return hitranParameters;
    }

    private LevenbergMarquardtParameters builLevenbergMarquardtParameters(int molecule) {
        LevenbergMarquardtParameters levenbergMarquardtParameters = new LevenbergMarquardtParameters();
        levenbergMarquardtParameters.setDp(new double[]{0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01});
        levenbergMarquardtParameters.setPin(new double[]{1e-4, 1.0, 22.5 * 1e-3, 1e-8, 1e-5, 0.0, -1e-5, 1e-10});
        levenbergMarquardtParameters.setMinValues(new double[]{-0.1, 0.5, 5 * 1e-3, 0.0, 0.0, -0.1, -1e-2, 0});
        levenbergMarquardtParameters.setMaxValues(new double[]{0.1, 2.0, 40 * 1e-3, 1e-3, 1.0, 0.1, 0.0, 1e-2});
        levenbergMarquardtParameters.setMolecule(molecule);
        levenbergMarquardtParameters.setStol(1e-4);
        levenbergMarquardtParameters.setNiter(15);
        levenbergMarquardtParameters.setNlCorr(false);
        return levenbergMarquardtParameters;
    }
    private LevenbergMarquardtParameters builLevenbergMarquardtParametersForCO2() {
        LevenbergMarquardtParameters levenbergMarquardtParameters = new LevenbergMarquardtParameters();
        levenbergMarquardtParameters.setDp(new double[]{0.01, 0.01, 0.01, 0.01, 0, 0.01, 0.01, 0.01});
        levenbergMarquardtParameters.setPin(new double[]{1e-4, 1.0, 22.5 * 1e-3, 1e-5, 0.0, 0.0, -1e-5, 1e-10});
        levenbergMarquardtParameters.setMinValues(new double[]{-0.1, 0.5, 5 * 1e-3, 0.0, 0.0, -0.1, -1e-2, 0});
        levenbergMarquardtParameters.setMaxValues(new double[]{0.1, 2.0, 40 * 1e-3, 1e-3, 1.0, 0.1, 0.0, 1e-2});
        levenbergMarquardtParameters.setMolecule(2);
        levenbergMarquardtParameters.setStol(1e-4);
        levenbergMarquardtParameters.setNiter(15);
        levenbergMarquardtParameters.setNlCorr(false);
        return levenbergMarquardtParameters;
    }
}
