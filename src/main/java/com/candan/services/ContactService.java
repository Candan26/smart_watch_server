package com.candan.services;

import java.util.ArrayList;
import java.util.List;

import com.candan.exceptions.BadResourceException;
import com.candan.exceptions.ResourceAlreadyExistsException;
import com.candan.db.Contact;
import com.candan.interfaces.ContactRepository;
import com.candan.specification.ContactSpecification;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

//TODO write task on github
//TODO User Info add e-mail contact address
//TODO Add transaction id logic
//TODO add grafana in code
@Service
public class ContactService {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ContactRepository contactRepository;


    private boolean existsById(Long id) {
        return contactRepository.existsById(id);
    }

    public Contact findById(Long id) throws ResourceNotFoundException {
        Contact contact = contactRepository.findById(id).orElse(null);
        logger.info("Searching data for id["+id.toString()+"] on contact");
        if (contact==null) {
            throw new ResourceNotFoundException("Cannot find Contact with id: [" + id+"]");
        }
        else return contact;
    }

    public List<Contact> findAll(int pageNumber, int rowPerPage) {
        logger.info("finding All numbers fromPage ["+pageNumber +"] to row for page ["+rowPerPage+"] ");
        List<Contact> contacts = new ArrayList<>();
        contactRepository.findAll(PageRequest.of(pageNumber - 1, rowPerPage)).forEach(contacts::add);
        return contacts;
    }

    public List<Contact> findAllByName(String name, int pageNumber, int rowPerPage) {
        logger.info("finding All numbers from name  ["+name +"] ");
        Contact filter = new Contact();
        filter.setName(name);
        Specification<Contact> spec = new ContactSpecification(filter);

        List<Contact> contacts = new ArrayList<>();
        contactRepository.findAll(spec, PageRequest.of(pageNumber - 1, rowPerPage)).forEach(contacts::add);
        return contacts;
    }

    public Contact save(Contact contact) throws BadResourceException, ResourceAlreadyExistsException {
        if (!StringUtils.isEmpty(contact.getName())) {
            logger.info("Trying to update contact which is ["+contact.getName()+"]");
            if (contact.getId() != null && existsById(contact.getId())) {
                logger.error("Resource already exist throwing exception");
                throw new ResourceAlreadyExistsException("Contact with id: " + contact.getId() +
                        " already exists");
            }
            return contactRepository.save(contact);
        }
        else {
            BadResourceException exc = new BadResourceException("Failed to save contact");
            exc.addErrorMessage("Throwing exception "+exc);
            throw exc;
        }
    }

    public void update(Contact contact)
            throws BadResourceException, ResourceNotFoundException {
        if (!StringUtils.isEmpty(contact.getName())) {
            if (!existsById(contact.getId())) {
                throw new ResourceNotFoundException("Cannot find Contact with id: " + contact.getId());
            }
            logger.info("Trying to save contact which has ["+contact.getId()+"] id name");
            contactRepository.save(contact);
        }
        else {
            BadResourceException exc = new BadResourceException("Failed to save contact");
            exc.addErrorMessage("Contact is null or empty");
            logger.error("Throwing exception "+ exc);
            throw exc;
        }
    }

    public void updateAddress(Long id, String address)
            throws ResourceNotFoundException {
        Contact contact = findById(id);
        contact.setAddress1(address);
        contact.setAddress2(address);
        contact.setAddress3(address);
        contact.setPostalCode(address);
        contactRepository.save(contact);
    }

    public void deleteById(Long id) throws ResourceNotFoundException {
        if (!existsById(id)) {
            throw new ResourceNotFoundException("Cannot find contact with id: " + id);
        }
        else {
            logger.info("Deleting Contact name which has ["+id+"] id");
            contactRepository.deleteById(id);
        }
    }

    public Long count() {
        logger.info("counting numbers in count table  ["+contactRepository.count() +"] ");
        return contactRepository.count();
    }
}
