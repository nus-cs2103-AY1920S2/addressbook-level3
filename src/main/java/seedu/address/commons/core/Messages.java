package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String UNREACHABLE_STATEMENT_REACHED = "Able to reach unreachable statement.";
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command!";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_INDEX = "Invalid index: The index provided is out "
            + "of bound for this list!";
    public static final String MESSAGE_ITEMS_LISTED = "%1$d %2$s listed!";

    public static final String MESSAGE_INVALID_REDIT_ITEM_INDEX = "Invalid index provided for %s item: %s";

    public static final String HELP_COMMAND_SUMMARY = getHelpCommandSummary();
    public static final String HELP_START = getHelpStart();

    public static final String getHelpCommandSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Command Summary\n")
                .append("===========================================================================\n")
                .append("GENERAL COMMANDS:\n")
                .append("===========================================================================\n")
                .append("Help: help OPTION\n")
                .append("Update user profile: me [dp/ FILE_PATH] [n/ NAME] [d/ DESCRIPTION] [p/ PHONE_NUMBER] "
                        + "[e/ EMAIL] [g/ GITHUB] [u/ UNIVERSITY] [m/ MAJOR] [f/ FROM] [t/ TO] "
                        + "[c/ CURRENT_CAP MAX_CAP]\n")
                .append("List items of a specific type: list i/ TYPE\n")
                .append("View an item: view INDEX i/ TYPE\n")
                .append("Find items containing keywords: find KEYWORD [MORE_KEYWORDS]… i/ TYPE\n")
                .append("Delete an item: delete INDEX i/ TYPE\n")
                .append("Sort items of a specific type: sort i/ TYPE order/ SORT_WORD [reverse/ TRUE_OR_FALSE]\n")
                .append("Undo an action: undo\n")
                .append("Redo an action: redo\n")
                .append("Clear all data: clear\n")
                .append("Exit: exit\n")
                .append("\n")
                .append("===========================================================================\n")
                .append("ITEM-SPECIFIC COMMANDS:\n")
                .append("===========================================================================\n")
                .append("Add an internship: "
                        + "add i/ int n/ COMPANY_NAME r/ ROLE f/ FROM t/ TO d/ DESCRIPTION [#/ TAG]…\n")
                .append("Add a project: "
                        + "add i/ proj n/ PROJECT_NAME t/ TIME w/ WEBSITE d/ DESCRIPTION [#/ TAG]…\n")
                .append("Add a skill: add i/ ski n/ SKILL_NAME l/ LEVEL [#/ TAG]…\n")
                .append("Add a resume: add i/ res n/ RESUME_NAME [#/ TAG]…\n")
                .append("Add a note: add i/ note n/ NOTE_NAME t/ TIME [#/ TAG]…\n\n")
                .append("Edit an internship: edit "
                        + "INDEX i/ int [n/ COMPANY_NAME] [r/ ROLE] [f/ FROM] [t/ TO] [d/ DESCRIPTION] [#/ TAG]…\n")
                .append("Edit a project: "
                        + "edit INDEX i/ proj [n/ PROJECT_NAME] [t/ TIME] [w/ WEBSITE] [d/ DESCRIPTION] [#/ TAG]…\n")
                .append("Edit a skill: edit INDEX i/ ski [n/ SKILL_NAME] [l/ LEVEL] [#/ TAG]…\n")
                .append("Edit a resume: edit INDEX i/ res [n/ RESUME_NAME] [#/ TAG]…\n")
                .append("Edit a note: edit INDEX i/ note [n/ NOTE_NAME] [t/ TIME] [#/ TAG]…\n\n")
                .append("Mark a note as done: done NOTE_INDEX\n")
                .append("\n")
                .append("===========================================================================\n")
                .append("RESUME COMMANDS:\n")
                .append("===========================================================================\n")
                .append("Edit a resume: redit RESUME_INDEX TYPE/ [ITEM_ID…] [MORE_TYPE/ [ITEM_ID…]]…\n")
                .append("Pull items of specific tags to a resume: tagpull RESUME_INDEX [#/ TAG]…\n")
                .append("Preview resume: rpreview RESUME_INDEX\n")
                .append("Generate a resume: rgen RESUME_INDEX [n/ FILE_NAME]\n");
        return summary.toString();
    }

    public static final String getHelpStart() {
        StringBuilder summary = new StringBuilder();
        summary.append("Getting Started\n")
                .append("Welcome to ResuMe!\nYou have summoned me - your trusty Assistant, probably because you are "
                        + "lost and do not know where to start.\nBut don't worry, because that is the whole point of "
                        + "my existence!\n\n")
                .append("===========================================================================\n")
                .append("I. User Profile\n")
                .append("===========================================================================\n")
                .append("First, let's kick-start your quest to creating the perfect resume by ensuring that "
                        + "the resume is truly YOURS!\nKey in this command while replacing the details with your own "
                        + "personal information:\n")
                .append("   --> me n/ YOUR_NAME p/ YOUR_PHONE e/ YOUR_EMAIL g/ YOUR_GITHUB\n\n")
                .append("See the changes yet? That was easy, isn't it? "
                        + "And I promise you, that is as difficult as it can get when using ResuMe!\n")
                .append("Similarly, you can edit your university, major, start and end time, as well as your current "
                        + "CAP with u/, m/, f/, t/ and c/ respectively. Take some time to play around with this "
                        + "feature, before we move on to the next step.\n\n")
                .append("(Hot tip: try dp/ FILE_PATH to update the profile picture to a picture of your choice!)\n\n")
                .append("===========================================================================\n")
                .append("II. Your Experiences\n")
                .append("===========================================================================\n")
                .append("Now, it's time to add in your first experience! An experience could be an internship that "
                        + "you had, a project that you worked on, or any relevant skill that you wish to include in "
                        + "your resume.\n\n")
                .append("Try typing in this example command to add an internship experience at Google. Of course, "
                        + "you can use that template for all of your internships, just need to change the fields with "
                        + "your own internship details.\n")
                .append("   --> add i/ int n/ Gooogle r/ Frontend Web Engineer f/ 06-2020 t/ 12-2020 "
                        + "d/ I did work, made money. #/ frontend\n\n")
                .append("Next, try out these two example commands to add a project and a skill:\n")
                .append("   --> add i/ proj n/ Duke t/ 06-2020 w/ abc.github.io "
                        + "d/ For a little module named CS2103T. #/ java #/ tech\n")
                .append("   --> add i/ ski n/ Git and Github l/ INTERMEDIATE #/ VCS #/ tech\n\n")
                .append("Nobody is perfect, and it is okay to make mistakes sometimes. Notice the extra \"o\" in "
                        + "the Google internship you just added? You can easily fix that, either by editing or "
                        + "deleting the item. But first, let's switch to view the list of all internships!\n")
                .append("   --> list i/ int\n")
                .append("   --> edit 1 i/ int n/ Google   or  delete 1 i/ int\n\n")
                .append("Now, if you chose to delete Google in the previous step, and then regret instantly because "
                        + "working at Google is the dream of your life, simply use the undo command:\n")
                .append("   --> undo\n")
                .append("And voila! Gooogle is back! (You still need to edit that name tho)\n\n")
                .append("You can also add a note similar to how you add an item, that will serve as a mental reminder "
                        + "for unfinished task that you need to complete. Once you have completed the task, just mark "
                        + "it as done with the done command, or simply delete it using delete command.\n\n")
                .append("Some additional commands that you might want to try out are view, find, sort and redo. "
                        + "Simply key in the command word and follow the example in the prompt message given! "
                        + "You are smart and will probably be able to master these in no time!\n\n")
                .append("===========================================================================\n")
                .append("III. YOUR RESUME\n")
                .append("===========================================================================\n")
                .append("Finally, it's time to make your resume! Add a new Resume with details of your choice, "
                        + "following this example command:\n")
                .append("   --> add i/ res n/ My Software Engineering Resume #/ frontend #/ SE\n\n")
                .append("Next, you could add any number of internships, projects or skills into this resume. Let's "
                        + "assume that you want to add the first internship, project and skill in each list. After "
                        + "executing this command, your resume will contain 3 items!\n")
                .append("   --> redit 1 int/ 1 proj/ 1 ski/ 1\n")
                .append("(Another tip: previous commands like edit, delete, list, find, undo/redo will work with "
                        + "resume as well!)\n\n")
                .append("Once your resume is ready, let's go ahead and export it! But first, let's preview it to "
                        + "ensure that there is no error:\n")
                .append("   --> rpreview 1\n")
                .append("If there are errors, well, you know the drills! Just go back to previous steps and edit.\n\n"
                        + "Otherwise, we can now generate the .pdf file (the moment we've all been waiting for):\n")
                .append("   --> rgen 1 n/ My Resume\n")
                .append("(Final tip: the output file name is totally optional. By default, the output "
                        + "file will have the same name as your resume).\n\n")
                .append("And there you have it - your first resume made with ResuMe! What's left to do, is to send "
                        + "that masterpiece to Google, and (only if you cannot resist) start crafting yet another one!")
                .append("\n");
        return summary.toString();
    }
}
