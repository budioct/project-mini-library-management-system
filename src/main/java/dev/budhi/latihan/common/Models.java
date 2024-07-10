package dev.budhi.latihan.common;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

public class Models<T> {

    public Specification<T> where(Map<String, Object> request) {
        Map<String, Object> filter = new HashMap<>(request);
        filter.remove("page");
        filter.remove("size");
        filter.remove("sort");
        return new Specification<>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                for (var data : filter.entrySet()) {
                    if (Objects.nonNull(data)) {
//                        log.info("data type: {}", data.getValue());
                        predicates.add(criteriaBuilder.or(
                                criteriaBuilder.like(root.get(data.getKey()).as(String.class), "%" + data.getValue() + "%")
                        ));
                    }
                }
                return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
            }
        };
    }

    public PageRequest pageable(Map<String, Object> page) {
        int pageNumber = 0;
        int pageSize = 10;

        if (page.containsKey("page") && page.get("page") != "") {

            pageNumber = Integer.parseInt(page.get("page").toString());
        }

        if (page.containsKey("size") && page.get("size") != "") {

            pageSize = Integer.parseInt(page.get("size").toString());
        }

        return PageRequest.of(pageNumber, pageSize);

    }

    public PageRequest pageableSort(Map<String, Object> page) {
        int pageNumber = 0;
        int pageSize = 10;
        String sortPage = "createdAt";

        if (page.containsKey("page") && page.get("page") != "") {

            pageNumber = Integer.parseInt(page.get("page").toString());
        }

        if (page.containsKey("size") && page.get("size") != "") {

            pageSize = Integer.parseInt(page.get("size").toString());
        }

        if (page.containsKey("sort") && page.get("sort") != "") {
            sortPage = String.valueOf(page.get("sort"));
            return PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.asc(sortPage)));
        }

        return PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.desc(sortPage)));

    }

}
