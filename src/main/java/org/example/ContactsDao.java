package org.example;

import java.util.List;
import java.util.Optional;

public interface ContactsDao {

    Contact addContact(String name, String surname, String phoneNumber, String email);
    Contact addContact(String name, String phoneNumber, String email);
    Contact addContact(String name, String phoneNumber);
    Contact getContact(long contactId);
    void editContact(long contactId, String field, String value);
    List<Contact> getAllContacts();
    void deleteContact(long contactId);

}
