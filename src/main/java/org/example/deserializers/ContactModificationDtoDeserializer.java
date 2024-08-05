package org.example.deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.example.controllers.ContactModificationDto;

import java.io.IOException;

public class ContactModificationDtoDeserializer extends JsonDeserializer<ContactModificationDto> {

    @Override
    public ContactModificationDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        if (node.size() != 1) {
            throw new IllegalArgumentException("Invalid request body, only one key is required");
        }

        String field = node.fieldNames().next();
        String value = node.get(field).asText();

        ContactModificationDto contactModificationDto = new ContactModificationDto();
        contactModificationDto.setField(field);
        contactModificationDto.setValue(value);

        return contactModificationDto;
    }
}
