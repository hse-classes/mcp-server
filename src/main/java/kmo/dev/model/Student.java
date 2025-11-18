package kmo.dev.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Student Entity für die Datenbank
 */
@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String vorname;

    @Column(nullable = false)
    private String nachname;

    @Column(nullable = false, unique = true)
    private String matrikelnummer;

    @Column(nullable = false)
    private String studiengang;

    private Integer semester;

    public Student(String vorname, String nachname, String matrikelnummer, 
                   String studiengang, Integer semester) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.matrikelnummer = matrikelnummer;
        this.studiengang = studiengang;
        this.semester = semester;
    }
}