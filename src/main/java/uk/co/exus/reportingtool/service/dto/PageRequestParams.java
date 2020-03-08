package uk.co.exus.reportingtool.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Min;

/**
 * Groups together the page request criteria (page, size, sort and literal)
 *
 * @author Chris Lytsikas
 */
@Data
@Builder
@AllArgsConstructor
@Slf4j
public class PageRequestParams {

    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final String DEFAULT_DB_SORT = "id";

    @Min(0)
    private Integer page = 0;

    private Integer size = DEFAULT_PAGE_SIZE;

    private String sort = DEFAULT_DB_SORT;

    private String direction = "desc";

    /**
     * Helper method to return a {@link org.springframework.data.domain.Sort.Direction} from the literal direction
     */
    public Sort.Direction getPageDirection() {
        if (direction == null) {
            return Sort.Direction.DESC;
        }
        switch (direction.toLowerCase()) {
            case "ascend":
            case "asc":
                return Sort.Direction.ASC;
            case "desc":
            case "descend":
                return Sort.Direction.DESC;
            default:
                log.warn("Direction {} is unknown: defaulting to DESC", direction);
                return Sort.Direction.DESC;
        }
    }

    public int getPage() {
        return page != null ? page : 0;
    }

    public int getSize() {
        return (size == null || size == 0) ? DEFAULT_PAGE_SIZE : size;
    }

    /**
     * Creates a {@link PageRequest} from the provided params.
     * if the sort is missing defaults to sid
     */
    public PageRequest toPageRequestWithDefaultSort() {
        return PageRequest.of(getPage(), getSize(), getPageDirection(), getSortOrDefault());
    }


    public String getSortOrDefault() {
        return sort != null ? sort : DEFAULT_DB_SORT;
    }

    protected PageRequestParams() {
    }

    public static PageRequestParamsBuilder builder() {
        return new PageRequestParamsBuilder();
    }

    public static final class PageRequestParamsBuilder {

        private final PageRequestParams pageRequestParam;

        private PageRequestParamsBuilder() {
            pageRequestParam = new PageRequestParams();
        }

        public PageRequestParamsBuilder page(int page) {
            pageRequestParam.setPage(page);
            return this;
        }

        public PageRequestParamsBuilder size(int size) {
            pageRequestParam.setSize(size);
            return this;
        }

        public PageRequestParamsBuilder sort(String sort) {
            pageRequestParam.setSort(sort);
            return this;
        }

        public PageRequestParamsBuilder direction(String direction) {
            pageRequestParam.setDirection(direction);
            return this;
        }

        public PageRequestParams build() {
            return pageRequestParam;
        }
    }

}
