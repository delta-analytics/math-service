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
import java.util.Arrays;
import java.util.List;

@Configuration
public class MathConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(MathConfiguration.class);
    private HitranParametersRepository hitranParametersRepository;
    private LevenberqMarquardtParametersRepository levenberqMarquardtParametersRepository;

    @PostConstruct
    public void setup() {
        // Molecules 1=H2O  2=CO2  3=N2O  4=CO  5=CH4  6=NO  7=NO2
        HitranParameters hitranParameters1 = hitranParametersRepository.findByCurrentDefaultTrueAndMolecule(1);
        if(hitranParameters1 == null) { hitranParametersRepository.saveAndMarkNewDefaults(buildHitranParametersMoleculeH2O()); }
        HitranParameters hitranParameters2 = hitranParametersRepository.findByCurrentDefaultTrueAndMolecule(2);
        if(hitranParameters2 == null) { hitranParametersRepository.saveAndMarkNewDefaults(buildHitranParametersMoleculeCO2()); }
        HitranParameters hitranParameters3 = hitranParametersRepository.findByCurrentDefaultTrueAndMolecule(3);
        if(hitranParameters3 == null) { hitranParametersRepository.saveAndMarkNewDefaults(buildHitranParametersMoleculeN2O()); }
        HitranParameters hitranParameters4 = hitranParametersRepository.findByCurrentDefaultTrueAndMolecule(4);
        if(hitranParameters4 == null) { hitranParametersRepository.saveAndMarkNewDefaults(buildHitranParametersMoleculeCO()); }
        HitranParameters hitranParameters5 = hitranParametersRepository.findByCurrentDefaultTrueAndMolecule(5);
        if(hitranParameters5 == null) { hitranParametersRepository.saveAndMarkNewDefaults(buildHitranParametersMoleculeCH4()); }
        HitranParameters hitranParameters6 = hitranParametersRepository.findByCurrentDefaultTrueAndMolecule(6);
        if(hitranParameters6 == null) { hitranParametersRepository.saveAndMarkNewDefaults(buildHitranParametersMoleculeNO()); }
        HitranParameters hitranParameters7 = hitranParametersRepository.findByCurrentDefaultTrueAndMolecule(7);
        if(hitranParameters7 == null) { hitranParametersRepository.saveAndMarkNewDefaults(buildHitranParametersMoleculeNO2()); }

        LevenbergMarquardtParameters levenbergMarquardtParameters1 = levenberqMarquardtParametersRepository.findByCurrentDefaultTrueAndMolecule(1);
        if(levenbergMarquardtParameters1 == null) { levenberqMarquardtParametersRepository.saveAndMarkNewDefaults(builLevenbergMarquardtParameters(1)); }
        LevenbergMarquardtParameters levenbergMarquardtParameters2 = levenberqMarquardtParametersRepository.findByCurrentDefaultTrueAndMolecule(2);
        if(levenbergMarquardtParameters2 == null) { levenberqMarquardtParametersRepository.saveAndMarkNewDefaults(builLevenbergMarquardtParametersForCO2()); }
        LevenbergMarquardtParameters levenbergMarquardtParameters3 = levenberqMarquardtParametersRepository.findByCurrentDefaultTrueAndMolecule(3);
        if(levenbergMarquardtParameters3 == null) { levenberqMarquardtParametersRepository.saveAndMarkNewDefaults(builLevenbergMarquardtParameters(3)); }
        LevenbergMarquardtParameters levenbergMarquardtParameters4 = levenberqMarquardtParametersRepository.findByCurrentDefaultTrueAndMolecule(4);
        if(levenbergMarquardtParameters4 == null) { levenberqMarquardtParametersRepository.saveAndMarkNewDefaults(builLevenbergMarquardtParameters(4)); }
        LevenbergMarquardtParameters levenbergMarquardtParameters5 = levenberqMarquardtParametersRepository.findByCurrentDefaultTrueAndMolecule(5);
        if(levenbergMarquardtParameters5 == null) { levenberqMarquardtParametersRepository.saveAndMarkNewDefaults(builLevenbergMarquardtParameters(5)); }
        LevenbergMarquardtParameters levenbergMarquardtParameters6 = levenberqMarquardtParametersRepository.findByCurrentDefaultTrueAndMolecule(6);
        if(levenbergMarquardtParameters6 == null) { levenberqMarquardtParametersRepository.saveAndMarkNewDefaults(builLevenbergMarquardtParameters(6)); }
        LevenbergMarquardtParameters levenbergMarquardtParameters7 = levenberqMarquardtParametersRepository.findByCurrentDefaultTrueAndMolecule(7);
        if(levenbergMarquardtParameters7 == null) { levenberqMarquardtParametersRepository.saveAndMarkNewDefaults(builLevenbergMarquardtParameters(7)); }

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
        levenbergMarquardtParameters.setDp( Arrays.asList(0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01) );
        levenbergMarquardtParameters.setPin( Arrays.asList(1e-4, 1.0, 22.5 * 1e-3, 1e-8, 1e-5, 0.0, -1e-5, 1e-10) );
        levenbergMarquardtParameters.setMinValues( Arrays.asList(-0.1, 0.5, 5 * 1e-3, 0.0, 0.0, -0.1, -1e-2, 0.0) );
        levenbergMarquardtParameters.setMaxValues( Arrays.asList(0.1, 2.0, 40 * 1e-3, 1e-3, 1.0, 0.1, 0.0, 1e-2) );
        levenbergMarquardtParameters.setMolecule(molecule);
        levenbergMarquardtParameters.setStol(1e-4);
        levenbergMarquardtParameters.setNiter(15);
        levenbergMarquardtParameters.setNlCorr(false);
        return levenbergMarquardtParameters;
    }
    private LevenbergMarquardtParameters builLevenbergMarquardtParametersForCO2() {
        LevenbergMarquardtParameters levenbergMarquardtParameters = new LevenbergMarquardtParameters();
        levenbergMarquardtParameters.setDp( Arrays.asList(0.01, 0.01, 0.01, 0.01, 0.0, 0.01, 0.01, 0.01) );
        levenbergMarquardtParameters.setPin( Arrays.asList(1e-4, 1.0, 22.5 * 1e-3, 1e-5, 0.0, 0.0, -1e-5, 1e-10) );
        levenbergMarquardtParameters.setMinValues( Arrays.asList(-0.1, 0.5, 5 * 1e-3, 0.0, 0.0, -0.1, -1e-2, 0.0) );
        levenbergMarquardtParameters.setMaxValues( Arrays.asList(0.1, 2.0, 40 * 1e-3, 1e-3, 1.0, 0.1, 0.0, 1e-2) );
        levenbergMarquardtParameters.setMolecule(2);
        levenbergMarquardtParameters.setStol(1e-4);
        levenbergMarquardtParameters.setNiter(15);
        levenbergMarquardtParameters.setNlCorr(false);
        return levenbergMarquardtParameters;
    }
}
