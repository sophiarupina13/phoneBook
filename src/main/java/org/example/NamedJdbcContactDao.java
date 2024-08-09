package org.example;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NamedJdbcContactDao implements ContactsDao {

    private static final String CREATE_CONTACT_SQL =
            "INSERT INTO contacts (name, surname, phone_number, email) " +
            "VALUES (:name, :surname, :phone_number, :email)";

    private static final String CREATE_CONTACT_WITHOUT_SURNAME_SQL =
            "INSERT INTO contacts (name, phone_number, email) " +
            "VALUES (:name, :phone_number, :email)";

    private static final String CREATE_CONTACT_WITHOUT_SURNAME_AND_EMAIL_SQL =
            "INSERT INTO contacts (name, phone_number) " +
            "VALUES (:name, :phone_number)";

    private static final String GET_CONTACT_SQL =
            "SELECT * " +
            "FROM contacts " +
            "WHERE id = :id";

    private static String UPDATE_CONTACT_SQL(String column) {
        return "UPDATE contacts " +
                "SET " + column + " = :value " +
                "WHERE id = :id";
    }

    private static final String GET_ALL_CONTACTS_SQL =
            "select * from contacts";

    private static final String DELETE_CONTACT_SQL =
            "DELETE " +
            "FROM contacts " +
            "WHERE id = :id";

    private static final RowMapper<Contact> CONTACT_ROW_MAPPER =
            (rs, i) -> new Contact(rs.getLong("id"), rs.getString("name"), rs.getString("surname"), rs.getString("phone_number"), rs.getString("email"));

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public NamedJdbcContactDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Contact addContact(String name, String surname, String phoneNumber, String email) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(
                CREATE_CONTACT_SQL,
                new MapSqlParameterSource()
                        .addValue("name", name)
                        .addValue("surname", surname)
                        .addValue("phone_number", phoneNumber)
                        .addValue("email", email),
                keyHolder,
                new String[] { "id" }
        );

        var contactId = keyHolder.getKey().longValue();
        return new Contact(contactId, name, surname, phoneNumber, email);

    }

    @Override
    public Contact addContact(String name, String phoneNumber, String email) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(
                CREATE_CONTACT_WITHOUT_SURNAME_SQL,
                new MapSqlParameterSource()
                        .addValue("name", name)
                        .addValue("phone_number", phoneNumber)
                        .addValue("email", email),
                keyHolder,
                new String[] { "id" }
        );

        var contactId = keyHolder.getKey().longValue();
        return new Contact(contactId, name, phoneNumber, email);
    }

    @Override
    public Contact addContact(String name, String phoneNumber) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(
                CREATE_CONTACT_WITHOUT_SURNAME_AND_EMAIL_SQL,
                new MapSqlParameterSource()
                        .addValue("name", name)
                        .addValue("phone_number", phoneNumber),
                keyHolder,
                new String[] { "id" }
        );

        var contactId = keyHolder.getKey().longValue();
        return new Contact(contactId, name, phoneNumber);
    }

    @Override
    public Contact getContact(long contactId) {
        return namedParameterJdbcTemplate.queryForObject(
                GET_CONTACT_SQL,
                new MapSqlParameterSource()
                        .addValue("id", contactId),
                CONTACT_ROW_MAPPER
        );
    }

    @Override
    public void editContact(long contactId, String field, String value) {
        field = field.equals("phoneNumber") ? "phone_number" : field;
        String sqlQuery = UPDATE_CONTACT_SQL(field);
        namedParameterJdbcTemplate.update(
            sqlQuery,
            new MapSqlParameterSource()
                    .addValue("id", contactId)
                    .addValue("value", value)
        );
        System.out.printf("Contact with ID %s successfully updated%n", contactId);
    }

    @Override
    public List<Contact> getAllContacts() {
        return namedParameterJdbcTemplate.query(
                GET_ALL_CONTACTS_SQL,
                CONTACT_ROW_MAPPER);
    }

    @Override
    public void deleteContact(long contactId) {
        namedParameterJdbcTemplate.update(
                DELETE_CONTACT_SQL,
                new MapSqlParameterSource()
                        .addValue("id", contactId)
        );

        System.out.printf("Contact with ID %s successfully deleted%n", contactId);
    }

}
