package com.github.blacky0x0.pocketbook.view.enums;

/**
 * User: blacky
 * Date: 13.04.15
 */
public enum Message {
    ;

    // Messages for user
    public static final String POCKETBOOK_EMPTY =   "\n\n---===Pocket book is empty===---";
    public static final String NOTHING_FOUND =      "\n\n---===Nothing was found===---";
    public static final String NOTHING_TO_PRINT =   "\n\n---===Nothing to print===---";
    public static final String ENTRY_ADDED =        "\n\n---===The entry was added===---";
    public static final String ENTRY_NOT_ADDED =    "\n\n---===The entry wasn't added===---";
    public static final String ENTRY_DELETED =      "\n\n---===The entry was deleted===---";
    public static final String ENTRY_NOT_DELETED =  "\n\n---===The entry wasn't deleted===---";

    public static final String SEARCH_TITLE = "\n\n###Search for a contact###\n";

    public static final String ADD_TITLE = "\n\n###Add a contact###\n";
    public static final String ENTER_NAME = "Input a contact name & press Enter: ";
    public static final String ENTER_PHONE = "Input a phone number & press Enter: ";
    public static final String ENTER_EMAIL = "Input an e-mail address & press Enter: ";

    public static final String MAIN_SCREEN =
            "\n\n###Pocket book###\n"
                    + "("
                    + Option.getCommand(Option.MAIN_SCREEN_ADD) + ") "
                    + Option.getMessage(Option.MAIN_SCREEN_ADD) + "\n"
                    + "("
                    + Option.getCommand(Option.MAIN_SCREEN_PRINT) + ") "
                    + Option.getMessage(Option.MAIN_SCREEN_PRINT) + "\n"
                    + "("
                    + Option.getCommand(Option.MAIN_SCREEN_SEARCH) + ") "
                    + Option.getMessage(Option.MAIN_SCREEN_SEARCH) + "\n"
                    + "("
                    + Option.getCommand(Option.MAIN_SCREEN_DELETE) + ") "
                    + Option.getMessage(Option.MAIN_SCREEN_DELETE) + "\n"
                    + "("
                    + Option.getCommand(Option.MAIN_SCREEN_EXIT) + ") "
                    + Option.getMessage(Option.MAIN_SCREEN_EXIT) + "\n"
                    + "Enter a number and press Enter: ";

    public static final String SUB_SCREEN_DELETE =
            "\n\n###Delete a contact###\n"
                    + "("
                    + Option.getCommand(Option.DELETE_SCREEN_PRINT) + ") "
                    + Option.getMessage(Option.DELETE_SCREEN_PRINT) + "\n"
                    + "("
                    + Option.getCommand(Option.DELETE_SCREEN_EXIT) + ") "
                    + Option.getMessage(Option.DELETE_SCREEN_EXIT) + "\n"
                    + "Enter the contact id to delete or the command & press Enter: ";

}
