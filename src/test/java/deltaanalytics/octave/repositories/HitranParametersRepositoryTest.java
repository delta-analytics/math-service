package deltaanalytics.octave.repositories;

import deltaanalytics.Application;
import deltaanalytics.octave.entity.HitranParameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@Transactional
public class HitranParametersRepositoryTest {
    @Autowired
    private HitranParametersRepository hitranParametersRepository;

    @Test
    public void initializeFor7MoleculesMathConfiguration() throws Exception {
        assertThat(hitranParametersRepository.count(), is(7l));
    }
}