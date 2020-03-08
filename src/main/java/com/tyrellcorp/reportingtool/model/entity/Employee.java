package com.tyrellcorp.reportingtool.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "EMPLOYEES")
@SequenceGenerator(name = "EmployeeIdSeq", sequenceName = "employee_id_seq")
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EmployeeIdSeq")
    @Column(name = "ID")
    private Long id;

    public enum Title {
        MR, MS, MRS, DR, PROF
    }

    @Column(name = "TITLE", length = 50)
    @Enumerated(EnumType.STRING)
    private Title title;

    @Column(name = "FIRST_NAME", length = 50, nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", length = 50, nullable = false)
    private String lastName;

    @Column(name = "USERNAME", length = 50, nullable = false)
    private String username;

    @Column(name = "EMAIL", length = 100, nullable = false)
    private String email;

    public enum Gender {
        MALE,
        FEMALE
    }

    @Column(name = "GENDER", length = 50)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "COMMENTS", length = 1000)
    private String comments;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id.equals(employee.id);
    }
}
