package seedu.nova.commons.core;

/**
 * Container for help display messages.
 */
public class HelpMessages {

    //============== The help message for the home page ==================
    public static final String HELP_HOME = "Help: Home Mode \n"
            + "=================== \n"
            + "Nav to addressbook: "
            + "nav ab \n\n"
            + "Nav to schedule: "
            + "nav schedule \n\n"
            + "Nav to ProgressTracker: "
            + "nav progresstracker \n\n"
            + "Nav to Planner: "
            + "nav planner \n\n";

    //============== The help message for the address book page ==================
    public static final String HELP_ADDRESS_BOOK = "Help: Address Book Mode \n"
            + "=================== \n"
            + "Add contact: \n"
            + "\u2022 add n\\[name] p\\[phone num] e\\[email addr] c\\[classmate/teammate] \n"
            + "List contacts: \n"
            + "\u2022 list \n"
            + "List classmate/teammate: \n"
            + "\u2022 list c\\[classmate/teammate] \n"
            + "Find contact: \n"
            + "find n\\[name] \n"
            + "To undo, type undo \n"
            + "To redo, type redo \n"
            + "=================== \n"
            + "NOTE: The following commands must be used after using list (including list category) and find command"
            + "\n\n"
            + "Edit contact: (After using list/find) \n"
            + "\u2022 edit i\\[index] n\\[name] p\\[phone num] e\\[email addr] c\\[classmate/teammate] \n"
            + "Delete contact: (After using list/find) \n"
            + "\u2022 delete i\\[index]\n"
            + "Add remark: (After using list/find) \n"
            + "\u2022 remark i\\[index] r\\[remark]\n"
            + "Remove remark: (After using list/find) \n"
            + "\u2022 remark i\\[index] r\\ \n"
            + "\u2022 remark i\\[index]";


    //============== The help message for the schedule page ==================
    public static final String HELP_SCHEDULE = "Help: Schedule Mode \n"
            + "=================== \n"
            + "Add consultation: \n"
            + "consultation d\\[description] v\\[venue] t\\[YYYY-MM-DD] [Start time (HH:MM)] [End time (HH:MM)] \n"
            + "Add meeting: \n"
            + "meeting d\\[description] v\\[venue] t\\[YYYY-MM-DD] [Start time (HH:MM)] [End time (HH:MM)] \n"
            + "Add study session: \n"
            + "study d\\[description] v\\[venue] t\\[YYYY-MM-DD] [Start time (HH:MM)] [End time (HH:MM)] \n"
            + "Add lesson: \n"
            + "lesson d\\[description] v\\[venue] t\\[day] [Start time (HH:MM)] [End time (HH:MM)] \n"
            + "Add note to event: \n"
            + "note d\\[description] t\\[YYYY-MM-DD] i\\[index] \n"
            + "Delete event: \n"
            + "delete t\\[YYYY-MM-DD] i\\[index] \n"
            + "View schedule on date: \n"
            + "view t\\[date] \n"
            + "View schedule on week: \n"
            + "view week i\\[week #]";

    //============== The help message for the study planner page ==================
    public static final String HELP_STUDY_PLANNER = "";

    //============== The help message for the progress tracker page ==================
    public static final String HELP_PROGRESS_TRACKER = "Help: ProgressTracker Mode \n"
            + "=================== \n"
            + "List tasks: \n"
            + "list p\\[project] w\\[week] \n\n"
            + "Add task: \n"
            + "add p\\[project] w\\[week] d\\[task description] \n\n"
            + "Delete task: \n"
            + "delete p\\[project] w\\[week] t\\[task] \n\n"
            + "Edit task: \n"
            + "edit p\\[project] w\\[week] t\\[task] d\\[task description] \n\n"
            + "Set task as done: \n"
            + "done p\\[project] w\\[week] t\\[task] \n\n"
            + "Add note to task \n"
            + "addNote p\\[project] w\\[week] t\\[task] d\\[note] \n\n"
            + "Delete note from task \n"
            + "deleteNote p\\[project] w\\[week] t\\[task] \n\n"
            + "Edit note in task \n"
            + "editNote p\\[project] w\\[week] t\\[task] d\\[note] \n\n";


}
