package org.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.util.List;

@SpringBootApplication
public class ContactsMain {
    public static void main(String[] args) {

        SpringApplication.run(ContactsMain.class, args);

    }

    @Bean
    public CommandLineRunner demo(ContactRepository contactRepository, ContactsService contactsService) {
        return args -> {
            contactRepository.deleteAllInBatch();

            var newContact1 = contactRepository.save(new Contact("Marina", "Syvcheva", "+79123", "marinasyvh@mail.ru"));
            var newContact2 = contactRepository.save(new Contact("Psychologist", "+79234", "nastasya-nerud@mail.ru"));
            var newContact3 = contactRepository.save(new Contact("Honey", "+79732948"));

            System.out.println(contactRepository.findById(newContact1.getId()));
            System.out.println(contactRepository.findById(newContact2.getId()));
            System.out.println(contactRepository.findById(newContact3.getId()));

            contactRepository.updateEmail(newContact3.getId(), "alexeev@mail.ru");
            contactRepository.updatePhone(newContact1.getId(), "+79123783490");
            contactRepository.deleteById(newContact3.getId());

            File path = new File("./src/main/resources/contactsToAdd.csv");
            contactsService.addContacts(path);

            List<Contact> contacts = contactRepository.findAll();

            System.out.println(contacts);

        };
    }
}