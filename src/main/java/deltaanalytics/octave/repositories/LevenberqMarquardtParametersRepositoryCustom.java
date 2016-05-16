package deltaanalytics.octave.repositories;

import deltaanalytics.octave.entity.LevenbergMarquardtParameters;

public interface LevenberqMarquardtParametersRepositoryCustom {
    void saveAndMarkNewDefaults(LevenbergMarquardtParameters levenbergMarquardtParameters);
}
