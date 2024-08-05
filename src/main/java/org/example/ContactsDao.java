package org.example;

import java.util.List;
import java.util.Optional;

public interface ContactsDao {
    Contact addContact(String name, String surname, String phoneNumber, String email);
    Optional<Contact> findContact(long contactId);
    Contact getContact(long contactId);
    Contact editContact(long contactId, String field, String value);
    List<Contact> getAllContacts();
}
