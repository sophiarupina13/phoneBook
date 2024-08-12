package org.example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findAll();
    Contact findById(long contactId);
    Contact save(Contact contact);
    void deleteById(long contactId);

    @Modifying
    @Transactional
    @Query("UPDATE Contact c SET c.email = :email WHERE c.id = :contactId")
    void updateEmail(@Param("contactId") long contactId, @Param("email") String email);

    @Modifying
    @Transactional
    @Query("UPDATE Contact c SET c.phoneNumber = :phoneNumber WHERE c.id = :contactId")
    void updatePhone(@Param("contactId") long contactId, @Param("phoneNumber") String phoneNumber);

    @Modifying
    @Transactional
    default void batchSave(List<Contact> contacts) {
        for (Contact contact : contacts) {
            save(contact);
        }
    }

}
