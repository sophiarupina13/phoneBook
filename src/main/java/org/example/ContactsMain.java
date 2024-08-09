package org.example;

import org.example.config.ContactsConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class ContactsMain {
    public static void main(String[] args) {

        var applicationContext = new AnnotationConfigApplicationContext(ContactsConfig.class);

        var contactDao = applicationContext.getBean(ContactsDao.class);

        var newContact1 = contactDao.addContact("Marina", "Syvcheva", "+79123", "marinasyvh@mail.ru");
        var newContact2 = contactDao.addContact("Psychologist", "+79234", "nastasya-nerud@mail.ru");
        var newContact3 = contactDao.addContact("Honey", "+79732948");

        System.out.println(newContact1);
        System.out.println(newContact2);
        System.out.println(newContact3);

        contactDao.editContact(newContact2.getId(), "name", "Anastasia psychologist");

        System.out.println(contactDao.getContact(newContact2.getId()));

        contactDao.deleteContact(newContact1.getId());

        List<Contact> contacts = contactDao.getAllContacts();

        System.out.println(contacts);

    }
}