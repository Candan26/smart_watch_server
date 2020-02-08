package com.candan.interfaces;

import com.candan.db.Environment;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EnvironmentRepository extends PagingAndSortingRepository<Environment, Long>,
        JpaSpecificationExecutor<Environment> {
}