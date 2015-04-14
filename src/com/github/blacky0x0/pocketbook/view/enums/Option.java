package com.github.blacky0x0.pocketbook.view.enums;

/**
 * User: blacky
 * Date: 13.04.15
 */
public enum Option {

    MAIN_SCREEN_ADD     ("Add a contact",            "1"),
    MAIN_SCREEN_PRINT   ("Print a contact list",     "2"),
    MAIN_SCREEN_SEARCH  ("Search a contact by name", "3"),
    MAIN_SCREEN_DELETE  ("Delete a contact",         "4"),
    MAIN_SCREEN_EXIT    ("Exit",                     "5"),

    DELETE_SCREEN_PRINT("Print a contact list", "p"),
    DELETE_SCREEN_EXIT("Return to the main menu", "q");


    private String msg;
    private String command;

    private Option(String msg, String command) {
        this.msg = msg;
        this.command = command;
    }

    public static String getMessage(Option option) {
        return option.msg;
    }

    public static String getCommand(Option option) {
        return option.command;
    }

}
