package org.example.controllers;

import org.example.facade.ContactsFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactsController {

    private final ContactsFacade contactsFacade;

    @Autowired
    public ContactsController(ContactsFacade contactsFacade) {
        this.contactsFacade = contactsFacade;
    }

    @GetMapping
    public List<ContactDto> getAllContacts() {
        return contactsFacade.getAllContact();
    }

    @GetMapping("/{contactId}")
    public ContactDto getContact(@PathVariable long contactId) {
        return contactsFacade.getContact(contactId);
    }

    @PostMapping
    public ContactDto createContact(@RequestBody ContactCreationDto contactCreationDto) {
        return contactsFacade.createContact(contactCreationDto);
    }

    @PutMapping("/{contactId}")
    public ContactDto editContact(
            @PathVariable long contactId,
            @RequestBody ContactModificationDto contactModificationDto) {
        return contactsFacade.editContact(contactId, contactModificationDto);
    }
}
