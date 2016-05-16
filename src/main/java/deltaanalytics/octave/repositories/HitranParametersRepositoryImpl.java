package deltaanalytics.octave.repositories;

import deltaanalytics.octave.entity.HitranParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class HitranParametersRepositoryImpl implements HitranParametersRepositoryCustom {
    private Logger LOGGER = LoggerFactory.getLogger(HitranParametersRepositoryImpl.class);
    private HitranParametersRepository hitranParametersRepository;

    @Override
    public void saveAndMarkNewDefaults(HitranParameters hitranParameters) {
        LOGGER.info("saveAndMarkNewDefaults " + hitranParameters);
        HitranParameters actualDefaults = hitranParametersRepository.findByCurrentDefaultTrue();
        if (actualDefaults != null) {
            LOGGER.info("actualDefaults before " + actualDefaults);
            actualDefaults.setCurrentDefault(false);
            hitranParametersRepository.save(actualDefaults);
        }
        hitranParameters.setCurrentDefault(true);
        hitranParametersRepository.save(hitranParameters);
    }

    @Autowired
    public void setHitranParametersRepository(HitranParametersRepository hitranParametersRepository) {
        this.hitranParametersRepository = hitranParametersRepository;
    }
}
