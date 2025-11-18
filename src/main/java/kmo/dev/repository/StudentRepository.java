package kmo.dev.repository;

import kmo.dev.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository für Studenten-Daten
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByMatrikelnummer(String matrikelnummer);

    List<Student> findByStudiengang(String studiengang);

    List<Student> findByNachnameContainingIgnoreCase(String nachname);
}