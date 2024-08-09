package org.example;

import org.example.config.ContactsConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;


class ContactsDaoTest {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ContactsConfig.class);

    ContactsDao contactDao = applicationContext.getBean(ContactsDao.class);

    @Test
    void addContact() {
        Contact contactExpected = new Contact(56, "Olivia", "+79345");

        Assertions.assertEquals(contactExpected, contactDao.addContact("Olivia", "+79345"));
    }

    @Test
    void getContact() {
        Contact contactExpected = new Contact(56, "Olivia", "+79345");
        Assertions.assertEquals(contactExpected, contactDao.getContact(56));
    }

    @Test
    void editContact() {
        Contact contactExpected = new Contact(56, "Olivia", "+79123");
        contactDao.editContact(56, "phoneNumber", "+79123");
        Assertions.assertEquals(contactExpected, contactDao.getContact(56));
    }

    @Test
    void getAllContacts() {
        List<Contact> contactsExpected = List.of(new Contact(54, "Olivia", "+79345"), new Contact(55, "Olivia", "+79345"), new Contact(56, "Olivia", "+79123"));
        Assertions.assertEquals(contactsExpected, contactDao.getAllContacts());
    }

    @Test
    void deleteContact() {
        List<Contact> contactsExpected = List.of();
        contactDao.deleteContact(54);
        contactDao.deleteContact(55);
        contactDao.deleteContact(56);
        Assertions.assertEquals(contactsExpected, contactDao.getAllContacts());
    }

}