package com.github.blacky0x0.pocketbook.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * User: blacky
 * Date: 14.04.15
 */
public class PocketBookTest {

    private PocketBook pocketBook;
    private Contact C1, C2, C3;

    @Before
    public void before() {
        pocketBook = new PocketBook();

        C1 = new Contact("name 01", "111", "mail01@ya.ru");
        C2 = new Contact("name 02", "222", "mail02@ya.ru");
        C3 = new Contact("name 03", "333", "mail03@ya.ru");

        pocketBook.add(C1);
        pocketBook.add(C2);
        pocketBook.add(C3);
    }

    @Test
    public void testCreation() throws Exception {
        new PocketBook();
    }

    @Test
    public void testGet() throws Exception {
        Contact current = pocketBook.get(C1.getUuid());

        Assert.assertEquals(C1, current);
    }

    @Test
    public void testGetByName() throws Exception {
        List<Contact> list = pocketBook.getByName("name 01");

        Assert.assertEquals(Arrays.asList(C1), list);
    }

    @Test
    public void testGetAll() throws Exception {
        List<Contact> list = pocketBook.getContacts();

        Assert.assertEquals(Arrays.asList(C1, C2, C3), list);
    }


    @Test
    public void testAdd() throws Exception {
        Contact C4 = new Contact("name 04", "444", "mail04@ya.ru");

        boolean result = pocketBook.add(C4);
        List<Contact> list = pocketBook.getContacts();

        Assert.assertEquals(true, result);
        Assert.assertEquals(Arrays.asList(C1, C2, C3, C4), list);
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        boolean result = pocketBook.delete("dummy-uuid");
        List<Contact> list = pocketBook.getContacts();

        Assert.assertEquals(false, result);
        Assert.assertEquals(Arrays.asList(C1, C2, C3), list);
    }

    @Test
    public void testDelete() throws Exception {
        boolean result = pocketBook.delete(C1.getUuid());
        List<Contact> list = pocketBook.getContacts();

        Assert.assertEquals(true, result);
        Assert.assertEquals(Arrays.asList(C2, C3), list);
    }

    @Test
    public void testSize() throws Exception {
        Assert.assertEquals(3, pocketBook.getContacts().size());
    }
}
