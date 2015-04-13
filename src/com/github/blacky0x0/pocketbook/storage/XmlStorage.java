package com.github.blacky0x0.pocketbook.storage;

import com.github.blacky0x0.pocketbook.exception.AppException;
import com.github.blacky0x0.pocketbook.model.Contact;
import com.github.blacky0x0.pocketbook.model.PocketBook;
import com.github.blacky0x0.pocketbook.util.XmlMapper;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

public class XmlStorage implements IStorage {

    private final File file;

    private Writer writer;
    private Reader reader;

    private XmlMapper xmlMapper;
    private PocketBook pocketBook;


    public static final transient String DEFAULT_NAME = "../pocket_book.xml";

    public XmlStorage() {
        this (DEFAULT_NAME);
    }

    public XmlStorage(String fileName) {
        this.file = new File(fileName);
        xmlMapper = new XmlMapper(PocketBook.class);

        try
        {
            // Read & unmarshall a specified file
            InputStream is = new FileInputStream(file);
            reader = new InputStreamReader(is, Charset.forName("utf8"));
            pocketBook = xmlMapper.unmarshall(reader);
        }
        catch (FileNotFoundException e)
        {
            throw new AppException("Can't read a file: ".concat(file.toString()), e);
        }
    }

    public File getFile() {
        return file;
    }

    @Override
    public Contact get(String uuid) {
        return pocketBook.get(uuid);
    }

    @Override
    public List<Contact> getByName(String name) {
        return pocketBook.getByName(name);
    }

    @Override
    public List<Contact> getAll() {
        return pocketBook.getContacts();
    }

    @Override
    public boolean add(Contact contact) {
        pocketBook.add(contact);

        try
        {
            OutputStream os = new FileOutputStream(file, false);
            writer = new OutputStreamWriter(os, Charset.forName("utf8"));

            xmlMapper.marshall(pocketBook, writer);
            return true;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(String uuid) {

        if (pocketBook.delete(uuid))
        {
            try
            {
                OutputStream os = new FileOutputStream(file, false);
                writer = new OutputStreamWriter(os, Charset.forName("utf8"));

                xmlMapper.marshall(pocketBook, writer);
                return true;
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }

        return false;
    }
}