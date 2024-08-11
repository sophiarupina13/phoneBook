package org.example;

import org.example.config.ContactsConfig;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.util.List;

public class ContactsMain {
    public static void main(String[] args) {

        var applicationContext = new AnnotationConfigApplicationContext(ContactsConfig.class);

        var sessionFactory = applicationContext.getBean(SessionFactory.class);
        var contactDao = applicationContext.getBean(ContactDao.class);
        var contactService = applicationContext.getBean(ContactsService.class);

        try (var session = sessionFactory.openSession()) {

            var newContact1Id = contactDao.saveContact(new Contact("Marina", "Syvcheva", "+79123", "marinasyvh@mail.ru"));
            var newContact2Id = contactDao.saveContact(new Contact("Psychologist", "+79234", "nastasya-nerud@mail.ru"));
            var newContact3Id = contactDao.saveContact(new Contact("Honey", "+79732948"));

            System.out.println(contactDao.getContact(newContact1Id));
            System.out.println(contactDao.getContact(newContact2Id));
            System.out.println(contactDao.getContact(newContact3Id));

            contactDao.updateContactPhoneNumber(newContact1Id, "+79123783490");
            contactDao.updateContactEmail(newContact3Id, "alexeev@mail.ru");
            contactDao.updateContact(new Contact(newContact2Id, "Anastasia psychologist", "+79234"));
            contactDao.deleteContact(newContact3Id);

            File path = new File("./src/main/resources/contactsToAdd.csv");
            contactService.addContacts(path);

            List<Contact> contacts = contactDao.getAllContacts();

            System.out.println(contacts);
        }

    }
}