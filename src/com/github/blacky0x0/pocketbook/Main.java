package com.github.blacky0x0.pocketbook;

import com.github.blacky0x0.pocketbook.storage.XmlStorage;
import com.github.blacky0x0.pocketbook.view.ConsoleView;

import java.io.*;

import jline.console.ConsoleReader;

public class Main {

    public static final String DEFAULT_NAME = "pocket_book.xml";
    public static String USER_DIR = System.getProperties().getProperty("user.dir");
    public static String USER_HOME = System.getProperties().getProperty("user.home");

    public static final String USAGE_MESSAGE =
            "=======================\n"
                    + "Examples of usage:\n"
                    + "1. Running an application without arguments "
                    + "is the same as the following command:\n"
                    + "java com.github.blacky0x0.pocketbook.Main "
                    + DEFAULT_NAME + "\n"
                    + "2. Running an application with one argument is looks like:\n"
                    + "java com.github.blacky0x0.pocketbook.Main file_to_read_and_write.xml\n"
                    + "=======================";

    public static File searchFile(String fileName, PrintWriter out) {
        File current_file = new File(fileName);
        File file_in_user_dir = new File(USER_DIR + "/" + fileName);
        File file_in_user_home = new File(USER_HOME + "/" + fileName);

        if (isFileValid(current_file, out))
            return current_file;

        if (isFileValid(file_in_user_dir, out))
            return file_in_user_dir;

        if (isFileValid(file_in_user_home, out))
            return file_in_user_home;

        return null;
    }

    public static boolean isFileValid(File file, PrintWriter out) {
        // TODO: validate found xml file with xsd file
        if (file.isFile())
        {
            out.println("Found next file: ");
            out.println(file.getAbsolutePath());
            out.println("Loading...");
            out.flush();
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        ConsoleReader reader = new ConsoleReader();
        PrintWriter out = new PrintWriter(reader.getOutput());

        String userInput;
        XmlStorage storage = null;

        // Show an usage message if arguments not valid
        if(args.length > 1
                || (args.length == 1 && "help".equals(args[0]))
                || (args.length == 1 && "-help".equals(args[0])))
        {
            System.out.println(USAGE_MESSAGE);
            return;
        }

        // try to load received file; if it's not valid -> exception
        if (args.length == 1)
            storage = new XmlStorage(args[0], false);

        // Check default directories for a xml file
        if (args.length == 0)
        {
            File foundFile = searchFile(DEFAULT_NAME, out);

            if (foundFile != null) {
                storage = new XmlStorage(foundFile, false);
            }
            else
            {
                // ask to create a new file
                out.println("\n\nNo pocketbook file was found.");
                out.print("Do you want to create a new file? [y/n]: ");
                out.flush();
                userInput = reader.readLine().trim();

                if ("y".equalsIgnoreCase(userInput))
                {
                    out.print("Input a new file name or press enter to keep a 'pocket_book.xml' value: ");
                    out.flush();
                    userInput = reader.readLine().trim();

                    if ("".equalsIgnoreCase(userInput))
                        storage = new XmlStorage(DEFAULT_NAME, true);
                    else
                        storage = new XmlStorage(userInput, true);
                }
                else
                    return;
            }
        }

        try { reader.shutdown(); }
        catch (Exception e) { e.printStackTrace(); }

        ConsoleView view = new ConsoleView(storage);
        view.startMainScreen();

    }
}