package org.example.controllers;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.example.deserializers.ContactModificationDtoDeserializer;

@JsonDeserialize(using = ContactModificationDtoDeserializer.class)
public class ContactModificationDto {

    private String field;
    private String value;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
