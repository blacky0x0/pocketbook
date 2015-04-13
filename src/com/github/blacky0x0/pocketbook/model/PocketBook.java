package com.github.blacky0x0.pocketbook.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;

/**
 * User: blacky
 * Date: 12.04.15
 */
@XmlType
@XmlRootElement(name = "pocketbook")
@XmlAccessorType(XmlAccessType.FIELD)
public class PocketBook {

    @XmlElementWrapper(name = "contacts")
    @XmlElement(name = "contact")
    private List<Contact> contacts = new ArrayList<Contact>();

    public Contact get (String uuid) {

        for (Contact item : contacts) {
            if (item.getUuid().equals(uuid))
                return item;
        }

        return Contact.EMPTY;
    }

    public List<Contact> getByName (String name) {
        List<Contact> list = new ArrayList<Contact>();

        name = name.toLowerCase();

        // if contact's name contains any specified sequence
        // then place an item to result list
        for (Contact item : contacts) {
            if (item.getName().toLowerCase().contains(name))
                list.add(item);
        }

        return list;
    }

    public void add (Contact contact) {
        contacts.add(contact);
    }

    public void delete (String uuid) {
        Contact result = null;

        for (Contact item : contacts) {
            if (item.getUuid().equals(uuid))
                result = item;
        }

        if (result != null)
            contacts.remove(result);
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}
