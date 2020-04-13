package com.notably.commons;

import com.notably.view.HelpModalView;

/**
 * Manages useful information that is to be displayed to the user in the {@link HelpModalView}.
 */
public class Help {
    private static String commandSummaryMarkdown = "### Command Summary"
            + "\n" + "### View help:\n  - help\n\n### Exit the program:\n  - exit\n\n### Create a new note:"
            + "\n  - new -t TITLE [-o]\n\n### Open an existing note:\n  - open [-t] AbsolutePath/Relativepath"
            + "TITLE\n\n### Edit an existing note:\n  - edit\n\n### Delete a note:\n  - delete [-t] "
            + "AbsolutePath/Relativepath\n\n### Find a note based on certain keywords: \n  - search"
            + " [-s] KEYWORD";

    public static String getCommandSummaryMarkdown() {
        return Help.commandSummaryMarkdown;
    }
}
