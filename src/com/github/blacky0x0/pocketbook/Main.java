package com.github.blacky0x0.pocketbook;

import com.github.blacky0x0.pocketbook.storage.XmlStorage;
import com.github.blacky0x0.pocketbook.view.ConsoleView;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        XmlStorage storage;

        if (args.length != 0)
            storage = new XmlStorage(args[0]);
        else
            storage = new XmlStorage();

        ConsoleView view = new ConsoleView(storage);
        view.startMainScreen();

    }
}