package com.candan.specification;

import com.candan.db.UserInfo;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public class UserInfoSpecification implements Specification<UserInfo> {

    private  UserInfo filter;

    public UserInfoSpecification(UserInfo filter){
        this.filter=filter;
    }

    @Override
    public Specification<UserInfo> and(Specification<UserInfo> other) {
        return null;
    }

    @Override
    public Specification<UserInfo> or(Specification<UserInfo> other) {
        return null;
    }

    @Override
    public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
       Predicate p = criteriaBuilder.disjunction();

        if(filter.getName() !=null)
            p.getExpressions().add(criteriaBuilder.like(root.get("name"), "%" + filter.getName() + "%"));

        if(filter.getAge() !=null)
            p.getExpressions().add(criteriaBuilder.like(root.get("age"), "%" + filter.getAge() + "%"));

        if(filter.getHeight() !=null)
            p.getExpressions().add(criteriaBuilder.like(root.get("height"), "%" + filter.getHeight() + "%"));

        if(filter.getSurname() !=null)
            p.getExpressions().add(criteriaBuilder.like(root.get("surname"), "%" + filter.getSurname() + "%"));

        if(filter.getWeight() !=null)
            p.getExpressions().add(criteriaBuilder.like(root.get("weight"), "%" + filter.getWeight() + "%"));

        return p;
    }
}
