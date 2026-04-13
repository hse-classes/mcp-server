package kmo.dev.configuration;

import kmo.dev.model.Student;
import kmo.dev.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final StudentRepository studentRepository;

    public DataInitializer(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(String... args) {
        studentRepository.save(new Student(
                "Max", "Mustermann", "12345", "Informatik", 3
        ));
        studentRepository.save(new Student(
                "Anna", "Schmidt", "12346", "Informatik", 5
        ));
        studentRepository.save(new Student(
                "Tom", "Meyer", "12347", "BWL", 2
        ));
        studentRepository.save(new Student(
                "Lisa", "Müller", "12348", "Informatik", 1
        ));
        studentRepository.save(new Student(
                "Paul", "Weber", "12349", "Maschinenbau", 4
        ));
        studentRepository.save(new Student(
                "Sarah", "Wagner", "12350", "BWL", 6
        ));

        System.out.println("✅ Datenbank mit " + studentRepository.count() + " Studenten initialisiert");
    }
}