package deltaanalytics.octave.controller;

import deltaanalytics.octave.entity.HitranParameters;
import deltaanalytics.octave.repositories.HitranParametersRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HitranParameterControllerTest {
    private HitranParameterController hitranParameterController;
    private HitranParametersRepository hitranParametersRepository;

    @Before
    public void setup() {
        hitranParameterController = new HitranParameterController();
        hitranParametersRepository = mock(HitranParametersRepository.class);
        ReflectionTestUtils.setField(hitranParameterController, "hitranParametersRepository", hitranParametersRepository);
    }

    @Test
    public void getActualHitran() throws Exception {
        hitranParameterController.getActualHitran();

        verify(hitranParametersRepository).findByCurrentDefaultTrue();
    }

    @Test
    public void setHitranNewDefaults() throws Exception {
        HitranParameters hitranParameters = new HitranParameters();

        hitranParameterController.setHitranNewDefaults(hitranParameters);

        verify(hitranParametersRepository).saveAndMarkNewDefaults(hitranParameters);
    }
}