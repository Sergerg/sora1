package org.sora.fx.services;

import org.sora.fx.entity.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import org.springframework.beans.factory.annotation.Autowired;
import javax.inject.Inject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: Serger
 * Date: 08.09.2015
 * Time: 7:39
 */
@Service
//@Transactional
public class ContactServiceImpl implements ContactService {

    //@Autowired
    @Inject
    private JdbcTemplate jdbcTemplate;

    private ObservableList<Contact> data = FXCollections.observableArrayList();

    public ObservableList<Contact> getData() {
        return data;
    }

    public void loadData() {
        data.clear();
        data.addAll(jdbcTemplate.query("SELECT * FROM contact ", (rs, arg1) -> {
            return new Contact(
                    rs.getString("nick"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"));
        }));
    }

    @Override
    public int addContact(Contact contact) {
        String sql = "INSERT INTO contact(nick, name, email, phone) VALUES(?,?,?,?)";
        return jdbcTemplate.update(sql, contact.getNick(), contact.getName(),
                contact.getEmail(), contact.getPhone());
    }

    @Override
    public int edit(Contact contact) {
        String sql = "UPDATE contact set name=?, email=?, phone=? WHERE nick=?";
        return jdbcTemplate.update(sql, contact.getName(), contact.getEmail(),
                contact.getPhone(), contact.getNick());
    }

    @Override
    public int delContact(Contact contact) {
        String sql = "DELETE contact WHERE nick=?";
        return jdbcTemplate.update(sql, contact.getNick());

    }
}

