package org.example;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InMemoryContactsDao implements ContactsDao {

    private long contactId = 1L;
    private final Map<Long, Contact> phoneBook;

    public InMemoryContactsDao() {
        this.phoneBook = new HashMap<>();
    }

    @Override
    public Contact addContact(String name, String surname, String phoneNumber, String email) {
        Contact contact = new Contact(name, surname, phoneNumber, email);
        phoneBook.put(contactId++, contact);
        return contact;
    }

    @Override
    public Optional<Contact> findContact(long contactId) {
        return Optional.ofNullable(phoneBook.get(contactId));
    }

    @Override
    public Contact getContact(long contactId) {
        return findContact(contactId)
                .orElseThrow(() -> new IllegalArgumentException("Contact with this ID does not exist: " + contactId));
    }

    @Override
    public Contact editContact(long contactId, String field, String value) {
        Contact contact = getContact(contactId);
        if (phoneBook.containsValue(contact)) {
            switch (field) {
                case "name":
                    contact.setName(value);
                    break;
                case "surname":
                    contact.setSurname(value);
                    break;
                case "phoneNumber":
                    contact.setPhoneNumber(value);
                    break;
                case "email":
                    contact.setEmail(value);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field: " + field);
            }
        }
        return contact;
    }

    @Override
    public List<Contact> getAllContacts() {
        return new ArrayList<>(phoneBook.values());
    }
}
