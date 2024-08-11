package org.example;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

@Service
public class InMemoryContactService implements ContactsService {

    private final ContactDao contactDao;

    public InMemoryContactService(org.example.ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    @Override
    public void addContacts(File contactsFilePath) {
        StringBuilder textFromFile = new StringBuilder();
        try (FileChannel inChannel = new FileInputStream(contactsFilePath).getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate((int) inChannel.size());
            inChannel.read(buffer);
            buffer.flip();
            textFromFile.append(StandardCharsets.UTF_8.decode(buffer));
        } catch (Exception e) {
            System.out.println("Cannot read file");
        }
        convertTextToContacts(textFromFile.toString());
    }

    private void convertTextToContacts(String textFromFile) {
        List<Contact> contactsToAdd = new LinkedList<>();
        String[] lines = textFromFile.split("\r\n");
        for (String line : lines) {
            contactsToAdd.add(convertLineToContact(line));
        }
        contactDao.addContacts(contactsToAdd);
    }

    private Contact convertLineToContact(String line) {
        String[] fields = line.split(", ");
        String[] nameAndSurname = fields[0].split(" ");
        String name = nameAndSurname[0];
        String surname = nameAndSurname[1];
        return new Contact(name, surname, fields[1], fields[2]);
    }

}
