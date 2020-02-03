package com.candan.interfaces;

import com.candan.db.UserInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkinRepository extends PagingAndSortingRepository<UserInfo, Long>,
        JpaSpecificationExecutor<UserInfo> {
}