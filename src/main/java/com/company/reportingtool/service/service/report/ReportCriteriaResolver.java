package com.company.reportingtool.service.service.report;

import com.company.reportingtool.model.entity.Report;
import com.company.reportingtool.service.dto.PageRequestParams;
import com.company.reportingtool.service.dto.report.ReportSearchCriteriaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ReportCriteriaResolver {

    @Autowired
    ReportSpecification reportSpecification;

    public Specification<Report> resolveReportCriteria(ReportSearchCriteriaDto criteria) {
        return Specification
                .where(reportSpecification.usernameFilter(criteria.getUsername())
                        .and(reportSpecification.priorityFilter(criteria.getPriority()))
                      );
    }

    public PageRequest resolveReportPageParams(PageRequestParams pageRequestParams) {
        if (pageRequestParams.getSort() == null) {
            return createPageRequest(pageRequestParams);
        }
        switch (pageRequestParams.getSort()) {
            case "createdOn":
                return createPageRequest(pageRequestParams, "createdOn");
            case "editedOn":
                return createPageRequest(pageRequestParams, "editedOn");
            case "username":
                return createPageRequest(pageRequestParams, "employee.username");
            default:
                return pageRequestParams.toPageRequestWithDefaultSort();
        }

    }

    private PageRequest createPageRequest(PageRequestParams pageRequestParams, String mappedFieldName) {
        return PageRequest.of(pageRequestParams.getPage(), pageRequestParams.getSize(), pageRequestParams.getPageDirection(), mappedFieldName);
    }

    private PageRequest createPageRequest(PageRequestParams pageRequestParams) {
        return PageRequest.of(pageRequestParams.getPage(), pageRequestParams.getSize());
    }


}
