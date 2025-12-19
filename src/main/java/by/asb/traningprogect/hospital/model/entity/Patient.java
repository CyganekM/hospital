package by.asb.traningprogect.hospital.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @Column(name = "id", nullable = false,  length = 45)
    private String id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "surname", nullable = false, length = 100)
    private String surname;

    @Column(name = "second_name", nullable = false, length = 100)
    private String secondName;

    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Lob
    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany(mappedBy = "patient")
    private List<Card> cards;

    @Column(name = "phone", length = 45)
    private String phone;

    @Column(name = "status", nullable = false)
    private Byte status;

    @Size(max = 255)
    @Column(name = "email")
    private String email;

}