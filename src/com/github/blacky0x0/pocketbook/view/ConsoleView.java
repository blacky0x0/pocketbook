package com.github.blacky0x0.pocketbook.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.github.blacky0x0.pocketbook.exception.AppException;
import com.github.blacky0x0.pocketbook.model.Contact;
import com.github.blacky0x0.pocketbook.storage.IStorage;
import com.github.blacky0x0.pocketbook.view.enums.Message;
import com.github.blacky0x0.pocketbook.view.enums.Option;
import jline.console.ConsoleReader;

public class ConsoleView {

	protected IStorage storage;

    protected ConsoleReader reader;
    protected PrintWriter out;
    protected String userInput;

	// The column headings
	public static final String ID = "ID";
    public static final String NAME = "Name";
    public static final String PHONE = "Phone";
    public static final String EMAIL = "E-mail";

    // Formatter for columns
    public static final String COLUMN_FORMAT = "|%-36s|%-16s|%-16s|%-20s|";
	
	public ConsoleView(IStorage storage) {
		this.storage = storage;
        try
        {
            reader = new ConsoleReader();
            out = new PrintWriter(reader.getOutput());
            out.flush();
        }
        catch (IOException e)
        {
            throw new AppException("Can't init console", e);
        }
    }
	
	/**
	 * The main entry point to interactive mode 
	 */
	public void startMainScreen() throws IOException {
        clearScreen();

        final String addCommand = Option.getCommand(Option.MAIN_SCREEN_ADD);
        final String printCommand = Option.getCommand(Option.MAIN_SCREEN_PRINT);
        final String searchCommand = Option.getCommand(Option.MAIN_SCREEN_SEARCH);
        final String deleteCommand = Option.getCommand(Option.MAIN_SCREEN_DELETE);
        final String exitCommand = Option.getCommand(Option.MAIN_SCREEN_EXIT);

		do {

            printMainScreen();
            out.flush();
            userInput = reader.readLine().trim();
            out.flush();

            // Adding a new contact
            if (addCommand.equalsIgnoreCase(userInput)) {
                clearScreen();
                addNewContact();
                continue;
            }

            // Print a contact list
            if (printCommand.equalsIgnoreCase(userInput)) {
                clearScreen();
                printPocketBook();
                continue;
            }

            // Search for a contact
            if (searchCommand.equalsIgnoreCase(userInput)) {
                clearScreen();
                searchByName();
                continue;
            }

            // Delete a contact
            if (deleteCommand.equalsIgnoreCase(userInput)) {
                clearScreen();
                deleteContact();
            }


		} while (!exitCommand.equalsIgnoreCase(userInput));

	}
	
	/**
	 * Displays the text of the main screen
	 */
	private void printMainScreen() {
        out.print(Message.MAIN_SCREEN);

        out.flush();
	}

	/**
	 * Enter the sub menu to delete a contact.
	 */
	private void deleteContact() throws IOException {
        // Get commands for do-while cycle
        String printCommand = Option.getCommand(Option.DELETE_SCREEN_PRINT);
        String deleteCommand = Option.getCommand(Option.DELETE_SCREEN_EXIT);

		do {
            // 1. Displays the text of the 'delete' sub menu screen
            out.print(Message.SUB_SCREEN_DELETE);
            out.flush();
			// 2. Get a contact id or command
            userInput = reader.readLine().trim();
			clearScreen();

			// 3. Make a decision
			if (userInput.equalsIgnoreCase(printCommand))
				printPocketBook();
			
			if (!(userInput.equalsIgnoreCase(printCommand))
					& !(userInput.equalsIgnoreCase(deleteCommand))) {

                if (storage.delete(userInput))
                    out.println(Message.ENTRY_DELETED);
                else
                    out.println(Message.ENTRY_NOT_DELETED);
			}

            out.flush();
		} while (!userInput.equalsIgnoreCase(deleteCommand));
	}
	
	/**
	 * Enter the sub menu to add a contact.
	 */
	private void addNewContact() throws IOException {
        String name;
        String phone;
        String email;

        // Displays the text of the 'add' sub menu screen
        out.println(Message.ADD_TITLE);
        out.flush();

		do {
			out.print(Message.ENTER_NAME);
            out.flush();
            name = reader.readLine().trim();
		} while (name.equals(""));

		out.print(Message.ENTER_PHONE);
        out.flush();
        phone = reader.readLine().trim();

		out.print(Message.ENTER_EMAIL);
        out.flush();
        email = reader.readLine().trim();

		Contact contact = new Contact();
		contact.setName(name);
		contact.setPhone(phone);
		contact.setEmail(email);

		out.println();

        if (storage.add(contact))
			System.out.println(Message.ENTRY_ADDED);
		else
			System.out.println(Message.ENTRY_NOT_ADDED);

		out.println();
		out.println();
        out.flush();
	}
	
	/**
	 * Displays the specified contact list.
	 * @param contactList
	 */
	private void printContactList(List<Contact> contactList) {
		for (Contact c : contactList) {
			out.format(COLUMN_FORMAT,
                    c.getUuid(), c.getName(), c.getPhone(), c.getEmail());
			out.println();
		}
		out.println();
        out.flush();
	}
	
	/**
	 * Displays all contacts from the pocket book.
	 */
	private void printPocketBook() throws IOException {
		printColumnHeadings();

		if (storage.getAll().isEmpty()) {
			out.println(Message.POCKETBOOK_EMPTY);
			out.println();
			out.println();
            out.flush();
			return;
		}

		printContactList(storage.getAll());
        out.flush();
	}

	/**
	 * Displays all found contacts matching the search expression 
	 * in the pocket book. 
	 */
	private void searchByName() throws IOException {

		// Displays the text of the 'search' sub menu screen
        out.println(Message.SEARCH_TITLE);
        out.print(Message.ENTER_NAME);
        out.flush();

        userInput = reader.readLine().trim();
        out.flush();
		printColumnHeadings();

        if (storage.getAll().isEmpty()) {
            out.println(Message.POCKETBOOK_EMPTY);
            out.println();
            out.println();
            out.flush();
            return;
        }

		printContactList(storage.getByName(userInput));

		out.println();
        out.flush();
	}

	/**
	 * Headings for the contacts table
	 */
	private void printColumnHeadings() {
		out.format(COLUMN_FORMAT, ID, NAME, PHONE, EMAIL);
		out.println();
        out.flush();
	}

	private void clearScreen() throws IOException {
        reader.clearScreen();
        out.flush();
	}

}
