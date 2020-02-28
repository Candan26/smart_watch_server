package com.candan.specification;


import com.candan.db.Skin;
import org.springframework.data.jpa.domain.Specification;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

//TODO check every database member name with entitiies
//TODO check all warnings
//TODO check id issue
public class SkinSpecification implements Specification<Skin> {
    private Skin filter;

    public SkinSpecification(Skin filter) {
        super();
        this.filter = filter;
    }


    @Override
    public Predicate toPredicate(Root<Skin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate p = cb.disjunction();
        if (filter.getType() !=null)
            p.getExpressions().add(cb.like(root.get("type"),"%"+filter.getType()+"%"));

        if (filter.getData()!=null)
            p.getExpressions().add(cb.like(root.get("data"),"%"+filter.getData()+"%"));

        if (filter.getDate()!=null)
            p.getExpressions().add(cb.like(root.get("time"),"%"+filter.getDate()+"%"));

        if (filter.getPerson()!=null)
            p.getExpressions().add(cb.like(root.get("person"),"%"+filter.getPerson()+"%"));

        return p;
    }
}