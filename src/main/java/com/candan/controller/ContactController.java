package com.candan.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;

//import com.candan.configuration.ConfigurationReader;
import com.candan.db.Contact;
import com.candan.exceptions.BadResourceException;
import com.candan.exceptions.ResourceAlreadyExistsException;
import com.candan.services.ContactService;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;

@RestController
@RequestMapping("/api")
public class ContactController {

    private final Logger logger = Logger.getLogger(this.getClass());

    private final int ROW_PER_PAGE =5;

    @Autowired
    private ContactService contactService;

    @GetMapping(value = "/contacts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Contact>> findAll(
            @RequestParam(value="page", defaultValue="1") int pageNumber,
            @RequestParam(required=false) String name) {
        logger.info("Trying to find  all skin elements by page number ["+ pageNumber+"] and name ["+name+"]");
        if (StringUtils.isEmpty(name)) {
            return ResponseEntity.ok(contactService.findAll(pageNumber, ROW_PER_PAGE));
        }
        else {
            return ResponseEntity.ok(contactService.findAllByName(name, pageNumber, ROW_PER_PAGE));
        }
    }

    @GetMapping(value = "/contacts/{contactId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Contact> findContactById(@PathVariable long contactId) {
        try {
            logger.info("Finding skin contact by id ["+contactId+"]");
            Contact book = contactService.findById(contactId);
            return ResponseEntity.ok(book);  // return 200, with json body
        } catch (Exception ex) {
            logger.error("Exception on ",ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @PostMapping(value = "/contacts")
    public ResponseEntity<Contact> addContact(@Valid @RequestBody Contact contact)
            throws URISyntaxException {
        try {
            logger.info("Adding new skin contact value ["+contact.toString()+"]");
            Contact newContact = contactService.save(contact);
            return ResponseEntity.created(new URI("/api/contacts/" + newContact.getId()))
                    .body(contact);
        } catch (ResourceAlreadyExistsException ex) {
            logger.error("Exception on ",ex);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (BadResourceException ex) {
            logger.error("Exception on ",ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(value = "/contacts/{contactId}")
    public ResponseEntity<Contact> updateContact(@Valid @RequestBody Contact contact,
                                                 @PathVariable long contactId) {
        try {
            logger.info("updating contact with id ["+contactId+"] "+contact.toString());
            contact.setId(contactId);
            contactService.update(contact);
            return ResponseEntity.ok().build();
        } catch (BadResourceException ex) {
            logger.error("Exception on ",ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception ex) {
            logger.error("Exception on ",ex);
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/contacts/{contactId}")
    public ResponseEntity<Void> updateAddress(@PathVariable long contactId,
                                              @RequestBody String address) {
        logger.info("Updating attributes updateAddress ["+ contactId+"] address ["+address+"]");
        contactService.updateAddress(contactId, address);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path="/contacts/{contactId}")
    public ResponseEntity<Void> deleteContactById(@PathVariable long contactId) {
        logger.info("Deleting contactId by ["+contactId+"]");
        contactService.deleteById(contactId);
        return ResponseEntity.ok().build();
    }
}