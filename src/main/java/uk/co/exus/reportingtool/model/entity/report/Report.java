package uk.co.exus.reportingtool.model.entity.report;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import uk.co.exus.reportingtool.model.entity.employee.Employee;

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
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "title")
    private String title;

    @Column(name = "description", length = 1000)
    private String description;

    public enum Priority {
        HIGH(), LOW
    }

    @Column(name = "priority", length = 50)
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(name = "timestamp")
    @Setter(value = AccessLevel.NONE)
    private LocalDateTime timestamp;

    @PrePersist
    public void onPersist() {
        timestamp = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        timestamp = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return id.equals(report.id);
    }
}
