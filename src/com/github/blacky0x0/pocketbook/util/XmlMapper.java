package com.github.blacky0x0.pocketbook.util;

import com.github.blacky0x0.pocketbook.exception.AppException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.Reader;
import java.io.Writer;

/**
 * User: blacky
 * Date: 12.04.15
 */
public class XmlMapper {
    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;

    public XmlMapper(Class... classesToBeBound) {
        try {
            JAXBContext ctx = JAXBContext.newInstance(classesToBeBound);

            marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            unmarshaller = ctx.createUnmarshaller();
        } catch (JAXBException e) {
            throw new AppException("Jaxb init failed", e);
        }
    }

    public <T> T unmarshall(Reader reader)  {
        try {
            return (T) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            throw new AppException("Jaxb unmarshall failed", e);
        }
    }

    public void marshall(Object instance, Writer writer) {
        try {
            marshaller.marshal(instance, writer);
        } catch (JAXBException e) {
            throw new AppException("Jaxb marshal failed", e);
        }
    }
}
