package com.github.blacky0x0.pocketbook.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * User: blacky
 * Date: 14.04.15
 */
public class ContactTest {

    @Test
    public void testCreation() throws Exception {
        new Contact();
        new Contact("contact name");
        new Contact("contact name", "8(800)2000600", "email@e.e");
    }

    @Test
    public void testProperties() throws Exception {
        String name = "contact name";
        String phone = "8(800)2000600";
        String email = "email@e.e";

        Contact contact01 = new Contact(name);
        Contact contact02 = new Contact(name, phone, email);

        Assert.assertEquals(name, contact01.getName());

        Assert.assertEquals(name,  contact02.getName());
        Assert.assertEquals(phone, contact02.getPhone());
        Assert.assertEquals(email, contact02.getEmail());
    }
}
