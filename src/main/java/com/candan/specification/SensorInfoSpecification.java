package com.candan.specification;

import com.candan.db.Contact;
import com.candan.db.SensorInfo;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class SensorInfoSpecification implements Specification<SensorInfo> {

    private  SensorInfo filter;
    public SensorInfoSpecification(SensorInfo filter) {
        super();
        this.filter=filter;
    }

    @Override
    public Predicate toPredicate(Root<SensorInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate p = cb.disjunction();

        if (filter.getType() != null) {
            p.getExpressions().add(cb.like(root.get("type"), "%" + filter.getType() + "%"));
        }
        return p;
    }
}
