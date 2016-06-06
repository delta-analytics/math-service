package deltaanalytics.octave.repositories;

import deltaanalytics.octave.entity.HitranParameters;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HitranParametersRepository extends JpaRepository<HitranParameters, Long>, HitranParametersRepositoryCustom {
    HitranParameters findByCurrentDefaultTrueAndMolecule(int molecule);
}
