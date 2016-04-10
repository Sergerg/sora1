package org.sora.fx.services;

import org.sora.fx.entity.Contact;
import javafx.collections.ObservableList;

/**
 * Created with IntelliJ IDEA.
 * User: Serger
 * Date: 08.09.2015
 * Time: 7:48
 */
public interface ContactService {

    public ObservableList<Contact> getData();

    public void loadData();

    public int addContact(Contact contact);

}
