package by.asb.traningprogect.hospital.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "preliminary_diagnosis", nullable = false, length = 45)
    private String preliminaryDiagnosis;

    @Column(name = "preliminary_diagnosis_date", nullable = false)
    private LocalDate preliminaryDiagnosisDate;

    @Column(name = "final_diagnosis", length = 45)
    private String finalDiagnosis;

    @Column(name = "final_diagnosis_date")
    private LocalDate finalDiagnosisDate;

    @Lob
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "card")
    private List<Procedure> procedures;

}