package org.sora.fx.services;

import org.sora.fx.entity.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Serger
 * Date: 08.09.2015
 * Time: 7:39
 */
@Service
//@Transactional
public class ContactServiceImpl implements ContactService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ObservableList<Contact> data = FXCollections.observableArrayList();

    public ObservableList<Contact> getData() {
        return data;
    }

    public void loadData() {
        data.clear();
        data.addAll(jdbcTemplate.query("SELECT * FROM contact ", new RowMapper<Contact>() {
            public Contact mapRow(ResultSet rs, int arg1) throws SQLException {
                return new Contact(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"));
            }
        }));
    }

    @Override
    public int addContact(Contact contact) {
        String sql = "INSERT INTO contact(name, email, phone) VALUES(?,?,?)";
        return jdbcTemplate.update(sql, contact.getName(),
                contact.getEmail(), contact.getPhone());
    }
}

