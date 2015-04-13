package com.github.blacky0x0.pocketbook.storage;

import com.github.blacky0x0.pocketbook.model.Contact;

import java.util.List;

/**
 * User: blacky
 * Date: 12.04.15
 */
public interface IStorage {

    Contact get (String uuid);

    List<Contact> getByName (String name);

    List<Contact> getAll ();

    void add (Contact contact);

    void delete (String uuid);

}
