package com.candan.interfaces;

import com.candan.db.Heart;
import com.candan.db.SensorInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeartRepository extends PagingAndSortingRepository<Heart, Long>,
        JpaSpecificationExecutor<Heart> {
}