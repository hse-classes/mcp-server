package kmo.dev.service;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import kmo.dev.model.Student;
import kmo.dev.repository.StudentRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Tool(description = "Findet alle Studenten eines bestimmten Studiengangs. " +
            "Nutze dieses Tool wenn nach Studenten eines speziellen Studiengangs gefragt wird.")
    public String findeStudentenNachStudiengang(StudiengangRequest request) {
        List<Student> studenten = studentRepository.findByStudiengang(request.studiengang());

        if (studenten.isEmpty()) {
            return "Keine Studenten im Studiengang '" + request.studiengang() + "' gefunden.";
        }

        return studenten.stream()
                .map(s -> String.format("- %s %s (Matrikelnummer: %s, Semester: %d)",
                        s.getVorname(), s.getNachname(), s.getMatrikelnummer(), s.getSemester()))
                .collect(Collectors.joining("\n",
                        "Studenten im Studiengang '" + request.studiengang() + "':\n", ""));
    }

    @Tool(description = "Findet einen Studenten anhand seiner Matrikelnummer. " +
            "Nutze dieses Tool wenn nach einem Student mit einer bestimmten Matrikelnummer gesucht wird.")
    public String findeStudentNachMatrikelnummer(MatrikelnummerRequest request) {
        return studentRepository.findByMatrikelnummer(request.matrikelnummer())
                .map(s -> String.format("Student gefunden:\n" +
                                "Name: %s %s\n" +
                                "Matrikelnummer: %s\n" +
                                "Studiengang: %s\n" +
                                "Semester: %d",
                        s.getVorname(), s.getNachname(), s.getMatrikelnummer(),
                        s.getStudiengang(), s.getSemester()))
                .orElse("Kein Student mit Matrikelnummer '" + request.matrikelnummer() + "' gefunden.");
    }

    @Tool(description = "Sucht Studenten deren Nachname den Suchbegriff enthält. " +
            "Die Suche ist nicht case-sensitive und findet auch Teile des Nachnamens.")
    public String sucheStudentenNachNachname(NachnameRequest request) {
        List<Student> studenten = studentRepository.findByNachnameContainingIgnoreCase(request.nachname());

        if (studenten.isEmpty()) {
            return "Keine Studenten mit Nachname '" + request.nachname() + "' gefunden.";
        }

        return studenten.stream()
                .map(s -> String.format("- %s %s (%s) - %s, %d. Semester",
                        s.getVorname(), s.getNachname(), s.getMatrikelnummer(),
                        s.getStudiengang(), s.getSemester()))
                .collect(Collectors.joining("\n",
                        "Gefundene Studenten:\n", ""));
    }

    @Tool(description = "Gibt eine Liste aller registrierten Studenten zurück. " +
            "Nutze dieses Tool für allgemeine Übersichten oder wenn nach allen Studenten gefragt wird.")
    public String alleStudenten() {
        List<Student> studenten = studentRepository.findAll();

        if (studenten.isEmpty()) {
            return "Keine Studenten in der Datenbank.";
        }

        return studenten.stream()
                .map(s -> String.format("- %s %s (%s) - %s, %d. Semester",
                        s.getVorname(), s.getNachname(), s.getMatrikelnummer(),
                        s.getStudiengang(), s.getSemester()))
                .collect(Collectors.joining("\n",
                        "Alle Studenten (" + studenten.size() + "):\n", ""));
    }

    @Tool(description = "Zeigt eine Statistik der Studentenanzahl pro Studiengang. " +
            "Nutze dieses Tool wenn nach Statistiken oder Zahlen zu Studiengängen gefragt wird.")
    public String studentenStatistik() {
        List<Student> alleStudenten = studentRepository.findAll();

        if (alleStudenten.isEmpty()) {
            return "Keine Studenten in der Datenbank.";
        }

        var statistik = alleStudenten.stream()
                .collect(Collectors.groupingBy(
                        Student::getStudiengang,
                        Collectors.counting()
                ));

        return statistik.entrySet().stream()
                .map(e -> String.format("- %s: %d Studenten", e.getKey(), e.getValue()))
                .collect(Collectors.joining("\n",
                        "Studenten-Statistik:\n",
                        "\n\nGesamt: " + alleStudenten.size() + " Studenten"));
    }

    @JsonClassDescription("Request zum Finden von Studenten nach Studiengang")
    public record StudiengangRequest(
            @JsonProperty(required = true)
            @JsonPropertyDescription("Der Studiengang (z.B. 'Informatik', 'BWL', 'Maschinenbau')")
            String studiengang) {
    }

    @JsonClassDescription("Request zum Finden eines Studenten nach Matrikelnummer")
    public record MatrikelnummerRequest(
            @JsonProperty(required = true)
            @JsonPropertyDescription("Die Matrikelnummer des Studenten (z.B. '12345')")
            String matrikelnummer) {
    }

    @JsonClassDescription("Request zum Suchen von Studenten nach Nachname")
    public record NachnameRequest(
            @JsonProperty(required = true)
            @JsonPropertyDescription("Der gesuchte Nachname oder Teil davon. Die Suche ist case-insensitive.")
            String nachname) {
    }
}