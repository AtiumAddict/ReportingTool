package uk.co.exus.reportingtool.service.service.report;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uk.co.exus.reportingtool.model.entity.Report;

/**
 * Contains all of the search filters when querying for {@link Report}.
 */
@Service
public class ReportSpecification {

    /**
     * Creates the criteria to search by username
     */
    public Specification<Report> usernameFilter(String username) {
        return (root, query, criteriaBuilder) -> username == null ? criteriaBuilder.isTrue(criteriaBuilder.literal(true)) :
                criteriaBuilder.like(root.get("employee")
                        .get("username"), "%" + username + "%");
    }

    /**
     * Creates the criteria to search by priority
     */
    public Specification<Report> priorityFilter(String priority) {
        return (root, query, criteriaBuilder) -> priority == null ? criteriaBuilder.isTrue(criteriaBuilder.literal(true)) :
                criteriaBuilder.and(criteriaBuilder.equal(root.get("priority")
                        .as(String.class), priority));
    }
}
