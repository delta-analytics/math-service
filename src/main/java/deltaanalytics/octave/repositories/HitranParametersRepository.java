package deltaanalytics.octave.repositories;

import deltaanalytics.octave.entity.HitranParameters;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HitranParametersRepository extends JpaRepository<HitranParameters, Long>, HitranParametersRepositoryCustom {
    HitranParameters findByCurrentDefaultTrueAndMolecule(int molecule);
    List<HitranParameters> findByCurrentDefaultTrue();   // FJ how does this method be able to return a List?
}
