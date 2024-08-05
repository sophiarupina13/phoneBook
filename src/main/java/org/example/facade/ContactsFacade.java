package org.example.facade;

import org.example.Contact;
import org.example.ContactsDao;
import org.example.controllers.ContactCreationDto;
import org.example.controllers.ContactDto;
import org.example.controllers.ContactModificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContactsFacade {

    private final ContactsDao contactsDao;

    @Autowired
    public ContactsFacade(ContactsDao contactsDao) {
        this.contactsDao = contactsDao;
    }

    public ContactDto createContact(ContactCreationDto contactCreationDto) {
        var contact = contactsDao.addContact(
                contactCreationDto.getName(),
                contactCreationDto.getSurname(),
                contactCreationDto.getPhoneNumber(),
                contactCreationDto.getEmail());
        return new ContactDto(contact);
    }

    public ContactDto getContact(long contactId) {
        var contact = contactsDao.getContact(contactId);
        return new ContactDto(contact);
    }

    public ContactDto editContact(long contactId, ContactModificationDto contactModificationDto) {
        var contact = contactsDao.editContact(
                contactId,
                contactModificationDto.getField(),
                contactModificationDto.getValue());
        return new ContactDto(contact);
    }

    public List<ContactDto> getAllContact() {
        List<Contact> contacts = contactsDao.getAllContacts();
        return contacts.stream()
                .map(ContactDto::new)
                .collect(Collectors.toList());
    }
}
