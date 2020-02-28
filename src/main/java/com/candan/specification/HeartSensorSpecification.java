package com.candan.specification;

import com.candan.db.Heart;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class HeartSensorSpecification implements Specification<Heart> {
    private Heart filter;

    public HeartSensorSpecification(Heart filter) {
        super();
        this.filter=filter;
    }

    @Override
    public Predicate toPredicate(Root<Heart> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        Predicate p = cb.disjunction();
        if(filter.getType()!=null)
            p.getExpressions().add(cb.like(root.get("type"), "%" + filter.getType() + "%"));

        if(filter.getData()!=null)
            p.getExpressions().add(cb.like(root.get("data"),"%"+filter.getData()+"%"));

        if(filter.getDate()!=null)
            p.getExpressions().add(cb.like(root.get("date"),"%"+filter.getDate()+"%"));

        if(filter.getPerson()!=null)
            p.getExpressions().add(cb.like(root.get("person"),"%"+filter.getPerson()+"%"));

        return  p;
    }

}
