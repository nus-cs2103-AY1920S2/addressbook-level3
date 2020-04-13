package com.notably.commons;

/**
 * Manages useful information that is to be displayed to the user in the {@link com.notably.view.HelpWindow}.
 */
public class Help {
    private static String commandSummaryMarkdown = "### Command Summary"
            + "\n" + "### View help:\n  - help\n\n### Exit the program:\n  - exit\n\n### Create a new note:"
            + "\n  - new -t TITLE [-o]\n\n### Open an existing note:\n  - open [-t] [RelativePath/AbsolutePath]"
            + "TITLE\n\n### Edit an existing note:\n  - edit\n\n### Delete a note:\n  - delete [-t] "
            + "[RelativePath/AbsolutePath]TITLE\n\n### Find a note based on certain keywords: \n  - search"
            + " [-s] KEYWORD";

    public static String getCommandSummaryMarkdown() {
        return Help.commandSummaryMarkdown;
    }
}
