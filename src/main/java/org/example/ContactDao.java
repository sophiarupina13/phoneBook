package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactDao {

    private String GET_ALL_CONTACTS_SQL = "from Contact";
    private String UPDATE_CONTACT_EMAIL_SQL = "update Contact c set c.email=:email where c.id=:id";
    private String UPDATE_CONTACT_PHONENUMBER_SQL = "update Contact c set c.phoneNumber=:phoneNumber where c.id=:id";

    private final SessionFactory sessionFactory;

    @Autowired
    public ContactDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public long saveContact(Contact contact) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            var contactId = (long) session.save(contact);
            transaction.commit();
            return contactId;
        }
    }

    public Contact getContact(long contactId) {
        try (var session = sessionFactory.openSession()) {
            return session.get(Contact.class, contactId);
        }
    }

    public void updateContact(Contact contact) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            session.update(contact);
            transaction.commit();
        }
    }

    public void updateContactEmail(long contactId, String email) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            var personId = getContact(contactId);
            var query = session.createQuery(UPDATE_CONTACT_EMAIL_SQL);
            query.setParameter("email", email);
            query.setParameter("id", contactId);
            query.executeUpdate();
            transaction.commit();
        }
    }

    public void updateContactPhoneNumber(long contactId, String phoneNumber) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            var personId = getContact(contactId);
            var query = session.createQuery(UPDATE_CONTACT_PHONENUMBER_SQL);
            query.setParameter("phoneNumber", phoneNumber);
            query.setParameter("id", contactId);
            query.executeUpdate();
            transaction.commit();
        }
    }

    public void deleteContact(long contactId) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            var contact = getContact(contactId);
            if (contact != null) {
                session.delete(contact);
            }
            transaction.commit();
        }
    }

    public List<Contact> getAllContacts() {
        try (var session = sessionFactory.openSession()) {
            var query = session.createQuery(GET_ALL_CONTACTS_SQL);
            return query.getResultList();
        }
    }

    public void addContacts(List<Contact> contacts) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();

            for (int i = 0; i < contacts.size(); i++) {
                session.save(contacts.get(i));

                if (i % 50 == 0) {
                    session.flush();
                    session.clear();
                }
            }

            transaction.commit();
        }
    }

}
