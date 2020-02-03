package com.candan.interfaces;


import com.candan.db.Contact;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends PagingAndSortingRepository<Contact, Long>,
        JpaSpecificationExecutor<Contact> {
}