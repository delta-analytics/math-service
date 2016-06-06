package deltaanalytics.octave.repositories;

import deltaanalytics.octave.entity.LevenbergMarquardtParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class LevenberqMarquardtParametersRepositoryImpl implements LevenberqMarquardtParametersRepositoryCustom {
    private Logger LOGGER = LoggerFactory.getLogger(LevenberqMarquardtParametersRepositoryImpl.class);
    private LevenberqMarquardtParametersRepository levenberqMarquardtParametersRepository;

    @Override
    public void saveAndMarkNewDefaults(LevenbergMarquardtParameters levenbergMarquardtParameters) {
        LOGGER.info("saveAndMarkNewDefaults " + levenbergMarquardtParameters);
        LevenbergMarquardtParameters actualDefaults = levenberqMarquardtParametersRepository.findByCurrentDefaultTrueAndMolecule(levenbergMarquardtParameters.getMolecule());
        if (actualDefaults != null) {
            LOGGER.info("actualDefaults before " + actualDefaults);
            actualDefaults.setCurrentDefault(false);
            levenberqMarquardtParametersRepository.save(actualDefaults);
        }
        levenbergMarquardtParameters.setCurrentDefault(true);
        levenberqMarquardtParametersRepository.save(levenbergMarquardtParameters);
    }

    @Autowired
    public void setLevenberqMarquardtParametersRepository(LevenberqMarquardtParametersRepository levenberqMarquardtParametersRepository) {
        this.levenberqMarquardtParametersRepository = levenberqMarquardtParametersRepository;
    }
}
