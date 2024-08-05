package org.example.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.Contact;

public class ContactDto {

    @JsonProperty("name")
    private final String name;
    @JsonProperty("surname")
    private final String surname;
    @JsonProperty("phoneNumber")
    private final String phoneNumber;
    @JsonProperty("email")
    private final String email;

    public ContactDto(Contact contact) {
        this.name = contact.getName();
        this.surname = contact.getSurname();
        this.phoneNumber = contact.getPhoneNumber();
        this.email = contact.getEmail();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
