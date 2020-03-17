package seedu.address.logic.commands.commandAssign;

public class AssignCommandFactory {
    public static AssignCommandBase getCommand(AssignDescriptor assignDescriptor) {
        if (AssignTeacherToCourseCommand.isValidDescriptor(assignDescriptor)) {
            return new AssignTeacherToCourseCommand(assignDescriptor);
        }
        return new AssignCommandBase();
    }
}
