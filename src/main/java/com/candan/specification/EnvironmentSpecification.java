package com.candan.specification;

import com.candan.db.Environment;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class EnvironmentSpecification implements Specification<Environment> {
    private Environment filter;

    public EnvironmentSpecification(Environment filter) {
        super();
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Environment> root, CriteriaQuery<?> cq,
                                 CriteriaBuilder cb) {
        Predicate p = cb.disjunction();

        if (filter.getType() != null)
            p.getExpressions().add(cb.like(root.get("type"), "%" + filter.getType() + "%"));

        if (filter.getData()!= null)
            p.getExpressions().add(cb.like(root.get("data"), "%" + filter.getData() + "%"));

        if(filter.getDate()!=null)
            p.getExpressions().add(cb.like(root.get("date"), "%" + filter.getData() + "%"));

        return p;
    }
}
