package uk.co.exus.reportingtool.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "REPORTS")
@SequenceGenerator(name = "ReportIdSeq", sequenceName = "report_id_seq")
@Getter
@Setter
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ReportIdSeq")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPLOYEE_ID", nullable = false)
    private Employee employee;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "DESCRIPTION", length = 1000)
    private String description;

    public enum Priority {
        HIGH, LOW
    }

    @Column(name = "PRIORITY", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.LOW;

    @Column(name = "CREATED_ON", nullable = false)
    @Setter(value = AccessLevel.NONE)
    private LocalDateTime createdOn;

    @Column(name = "EDITED_ON")
    @Setter(value = AccessLevel.NONE)
    private LocalDateTime editedOn;

    @PrePersist
    public void onPersist() {
        this.createdOn = LocalDateTime.now();
        this.editedOn = LocalDateTime.now();
    }

    public void setEdited(LocalDateTime localDateTime) {
        this.editedOn = localDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return id.equals(report.id);
    }
}
