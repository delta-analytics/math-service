package deltaanalytics.octave.repositories;

import deltaanalytics.octave.entity.HitranParameters;

public interface HitranParametersRepositoryCustom {
    void saveAndMarkNewDefaults(HitranParameters hitranParameters);
}
