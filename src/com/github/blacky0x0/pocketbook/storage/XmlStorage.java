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

    private XmlMapper xmlMapper;
    private PocketBook pocketBook;


    public static final transient String DEFAULT_NAME = "pocket_book.xml";

    public XmlStorage() {
        this (DEFAULT_NAME, false);
    }

    public XmlStorage(String fileName, boolean isNeedToCreateFile) {
        this(new File(fileName), isNeedToCreateFile);
    }

    public XmlStorage(File file, boolean isNeedToCreateFile) {
        this.file = file;

        InputStream is = null;
        Reader reader = null;
        xmlMapper = new XmlMapper(PocketBook.class);

        if (isNeedToCreateFile)
        {
            pocketBook = new PocketBook();
            return;
        }

        try
        {
            // Read & unmarshall a specified file
            is = new FileInputStream(file);
            reader = new InputStreamReader(is, Charset.forName("utf8"));
            pocketBook = xmlMapper.unmarshall(reader);
        }
        catch (FileNotFoundException e)
        {
            throw new AppException("Can't read a file: ".concat(file.toString()), e);
        }
        finally
        {
            if (reader != null)
                try { reader.close(); }
                catch (IOException e) { e.printStackTrace(); }
            if (is != null)
                try { is.close(); }
                catch (IOException e) { e.printStackTrace(); }
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