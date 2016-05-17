package deltaanalytics.octave.repositories;

import deltaanalytics.octave.Application;
import deltaanalytics.octave.entity.HitranParameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@Transactional
public class HitranParametersRepositoryTest {
    @Autowired
    private HitranParametersRepository hitranParametersRepository;

    @Test
    public void save() throws Exception {
        HitranParameters hitranParameters = new HitranParameters();
        int[] baselineStep = {1, 2, 3};
        hitranParameters.setBaselineStep(baselineStep);

        hitranParametersRepository.save(hitranParameters);

        assertThat(hitranParametersRepository.count(), is(1l));
        assertThat(hitranParametersRepository.findOne(hitranParameters.getId()).getBaselineStep(), is(baselineStep));
    }
}