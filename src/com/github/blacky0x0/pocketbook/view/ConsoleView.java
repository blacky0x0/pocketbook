package com.github.blacky0x0.pocketbook.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.github.blacky0x0.pocketbook.exception.AppException;
import com.github.blacky0x0.pocketbook.model.Contact;
import com.github.blacky0x0.pocketbook.storage.IStorage;
import jline.console.ConsoleReader;

// TODO: pick out messages to external file | enum

public class ConsoleView {

	private IStorage storage;

    private ConsoleReader reader;
    private PrintWriter out;
	private String userInput;

	// Messages to the user
	static final String POCKETBOOK_EMPTY = "---===Pocket book is empty===---";
	static final String NOTHING_FOUND = "---===Nothing was found===---";
	static final String NOTHING_TO_PRINT = "---===Nothing to print===---";
	static final String ENTRY_ADDED = "---===The entry was added===---";
	static final String ENTRY_NOT_ADDED = "---===The entry wasn't added===---";
	static final String ENTRY_DELETED = "---===The entry was deleted===---";
	static final String ENTRY_NOT_DELETED = "---===The entry wasn't deleted===---";

	// The column headings
	static final String ID = "ID";
	static final String NAME = "Name";
	static final String PHONE = "Phone";
	static final String EMAIL = "E-mail";

	// The Main Screen
	static final String MAIN_SCREEN_TITLE = "###Pocket book###";
	static final String MAIN_SCREEN_ADD = "Add a contact";
	static final String MAIN_SCREEN_PRINT = "Print a contact list";
	static final String MAIN_SCREEN_SEARCH = "Search a contact by name";
	static final String MAIN_SCREEN_DELETE = "Delete a contact";
	static final String MAIN_SCREEN_EXIT = "Exit";
	static final String MAIN_SCREEN_PRESS_ENTER = "Enter a number & press Enter: ";

	// The Sub Menus
	static final String SUBMENU_DELETE_TITLE = "###Delete a contact###";
	static final String SUBMENU_DELETE_PRINT = "Print a contact list";
	static final String SUBMENU_DELETE_EXIT = "Return to the main menu";
	static final String SUBMENU_DELETE_PRESS_ENTER = 
			"Enter the contact id to delete or the command & press Enter: ";
	static final String SUBMENU_DELETE_PRINT_CHAR = "p";
	static final String SUBMENU_DELETE_QUIT_CHAR = "q";

	static final String SUBMENU_SEARCH_TITLE = "###Search for a contact###";
	static final String SUBMENU_SEARCH_ENTER_NAME = "Input a contact name & press Enter: ";

	static final String SUBMENU_ADD_TITLE = "###Add a contact###";
	static final String SUBMENU_ADD_ENTER_NAME = "Input a contact name & press Enter: ";
	static final String SUBMENU_ADD_ENTER_PHONE = "Input a phone number & press Enter: ";
	static final String SUBMENU_ADD_ENTER_EMAIL = "Input an e-mail address & press Enter: ";

	
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
        reader.clearScreen();
		printMainScreen();
        out.flush();
        userInput = reader.readLine().trim();
        out.flush();
		while (!userInput.equals("5")) {
			
			if(userInput.length() != 0) { 
				switch (userInput.charAt(0)) {
				case '1':
					// Adding a new contact
					addNewContact();
					break;
	
				case '2':
					// Print a contact list
					printPocketBook();
					break;
	
				case '3':
					// Search for a contact
					clearScreen();
					searchByName();
					break;
	
				case '4':
					// Delete a contact
					deleteContact();
					break;
					
				default:
                    reader.clearScreen();
					break;
				}
			}
			
			printMainScreen();
            out.flush();
            userInput = reader.readLine().trim();
            out.flush();
		}

	}
	
	/**
	 * Displays the text of the main screen
	 */
	private void printMainScreen() {
        out.println(MAIN_SCREEN_TITLE);

        out.print("(1) ");
        out.println(MAIN_SCREEN_ADD);

        out.print("(2) ");
        out.println(MAIN_SCREEN_PRINT);

        out.print("(3) ");
        out.println(MAIN_SCREEN_SEARCH);

        out.print("(4) ");
        out.println(MAIN_SCREEN_DELETE);

        out.print("(5) ");
        out.println(MAIN_SCREEN_EXIT);
        out.println();

        out.print(MAIN_SCREEN_PRESS_ENTER);
        out.flush();
	}

	/**
	 * Enter the sub menu to delete a contact.
	 */
	private void deleteContact() throws IOException {
		clearScreen();
		do {
			// 1. Print a submenu
			printDeleteSubMenuScreen();
			// 2. Get a contact id or command
            out.flush();
            userInput = reader.readLine().trim();
			// 3. Clear the screen
			clearScreen();
            out.flush();
			// 4. Make a decision
			if (userInput.equalsIgnoreCase(SUBMENU_DELETE_PRINT_CHAR)) {
				// Print a contact list
				printPocketBook();
                out.flush();
			}
			
			if (!(userInput.equalsIgnoreCase(SUBMENU_DELETE_PRINT_CHAR))
					& !(userInput.equalsIgnoreCase(SUBMENU_DELETE_QUIT_CHAR))) {
                    storage.delete(userInput);
                out.println(ENTRY_DELETED);

//				if (storage.delete(userInput)) {
//					System.out.println(ENTRY_DELETED);
//				} else {
//					System.out.println(ENTRY_NOT_DELETED);
//				}
				out.println();
				out.println();
                out.flush();
			}

		} while (!userInput.equalsIgnoreCase(SUBMENU_DELETE_QUIT_CHAR));
	}

	/**
	 * Displays the text of the 'search' sub menu screen 
	 */
	private void printSearchSubMenuScreen() {
		out.println(SUBMENU_SEARCH_TITLE);
		out.println();
		out.print(SUBMENU_SEARCH_ENTER_NAME);
        out.flush();
	}
	
	/**
	 * Displays the text of the 'delete' sub menu screen 
	 */
	private void printDeleteSubMenuScreen() {
		out.println(SUBMENU_DELETE_TITLE);

		out.print("(");
		out.print(SUBMENU_DELETE_PRINT_CHAR);
		out.print(") ");
		out.println(SUBMENU_DELETE_PRINT);

		out.print("(");
		out.print(SUBMENU_DELETE_QUIT_CHAR);
		out.print(") ");
		out.println(SUBMENU_DELETE_EXIT);

		out.println();
		out.print(SUBMENU_DELETE_PRESS_ENTER);
        out.flush();
	}

	/**
	 * Displays the text of the 'add' sub menu screen 
	 */
	private void printAddContactSubMenuScreen() {
		out.println(SUBMENU_ADD_TITLE);
		out.println();
        out.flush();
	}
	
	/**
	 * Enter the sub menu to add a contact.
	 */
	private void addNewContact() throws IOException {
		clearScreen();
		printAddContactSubMenuScreen();
		String name;
		String phone;
		String email;
		do {
			out.print(SUBMENU_ADD_ENTER_NAME);
            out.flush();
            name = reader.readLine().trim();
		} while (name.equals(""));

		out.print(SUBMENU_ADD_ENTER_PHONE);
        out.flush();
        phone = reader.readLine().trim();

		out.print(SUBMENU_ADD_ENTER_EMAIL);
        out.flush();
        email = reader.readLine().trim();

		Contact contact = new Contact();
		contact.setName(name);
		contact.setPhone(phone);
		contact.setEmail(email);

		out.println();

        storage.add(contact);
        out.println(ENTRY_ADDED);

//        if (storage.add(contact))
//			System.out.println(ENTRY_ADDED);
//		else
//			System.out.println(ENTRY_NOT_ADDED);

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
			out.format("|%-36s|%-16s|%-16s|%-20s|",
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
		clearScreen();
		printColumnHeadings();

//		if (storage.isPocketBookEmpty()) {
//			System.out.println(POCKETBOOK_EMPTY);
//			System.out.println();
//			System.out.println();
//			return;
//		}

		printContactList(storage.getAll());
        out.flush();
	}

	/**
	 * Displays all found contacts matching the search expression 
	 * in the pocket book. 
	 */
	private void searchByName() throws IOException {
		clearScreen();
		printSearchSubMenuScreen();

        out.flush();
        userInput = reader.readLine().trim();
        out.flush();
		printColumnHeadings();

//		if (storage.isPocketBookEmpty()) {
//			System.out.println(POCKETBOOK_EMPTY);
//			System.out.println();
//			System.out.println();
//			return;
//		}

		printContactList(storage.getByName(userInput));

		out.println();
        out.flush();
	}

	/**
	 * Headings for the contacts table
	 */
	private void printColumnHeadings() {
		out.format("|%-36s|%-16s|%-16s|%-20s|", ID, NAME, PHONE, EMAIL);
		out.println();
        out.flush();
	}

	private void clearScreen() throws IOException {
//		System.out.println(((char) 27) + "[2J"); // ANSI clear screen...
//		System.out.flush();
        reader.clearScreen();
        out.flush();
	}

}
