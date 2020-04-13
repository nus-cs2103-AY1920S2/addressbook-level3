package seedu.address.model.profile.course;

//@@author joycelynteo
/**
 * Accepted specialisations stored as enumerations.
 */
public enum AcceptedFocusArea {

    // UNDECIDED
    COMPUTER_SCIENCE_UNDECIDED(new CourseName("COMPUTER SCIENCE"), "UNDECIDED"),
    BUSINESS_ANALYTICS_UNDECIDED(new CourseName("BUSINESS ANALYTICS"), "UNDECIDED"),
    INFORMATION_SYSTEMS_UNDECIDED(new CourseName("INFORMATION SYSTEMS"), "UNDECIDED"),
    INFORMATION_SECURITY_UNDECIDED(new CourseName("INFORMATION SECURITY"), "UNDECIDED"),
    COMPUTER_ENGINEERING_UNDECIDED(new CourseName("COMPUTER ENGINEERING"), "UNDECIDED"),

    // COMPUTER SCIENCE
    ALGORITHMS_AND_THEORY(new CourseName("COMPUTER SCIENCE"), "ALGORITHMS AND THEORY"),
    ARTIFICIAL_INTELLIGENCE(new CourseName("COMPUTER SCIENCE"), "ARTIFICIAL INTELLIGENCE"),
    COMPUTER_GRAPHICS_AND_GAMES(new CourseName("COMPUTER SCIENCE"), "COMPUTER GRAPHICS AND GAMES"),
    COMPUTER_SECURITY(new CourseName("COMPUTER SCIENCE"), "COMPUTER SECURITY"),
    DATABASE_SYSTEMS(new CourseName("COMPUTER SCIENCE"), "DATABASE SYSTEMS"),
    MULTIMEDIA_INFORMATION_RETRIEVAL(new CourseName("COMPUTER SCIENCE"), "MULTIMEDIA INFORMATION RETRIEVAL"),
    NETWORKING_AND_DISTRIBUTED_SYSTEMS(new CourseName("COMPUTER SCIENCE"), "NETWORKING AND DISTRIBUTED SYSTEMS"),
    PARALLEL_COMPUTING(new CourseName("COMPUTER SCIENCE"), "PARALLEL COMPUTING"),
    PROGRAMMING_LANGUAGES(new CourseName("COMPUTER SCIENCE"), "PROGRAMMING LANGUAGES"),
    SOFTWARE_ENGINEERING(new CourseName("COMPUTER SCIENCE"), "SOFTWARE ENGINEERING"),

    // BUSINESS ANALYTICS
    FINANCIAL_ANALYTICS(new CourseName("BUSINESS ANALYTICS"), "FINANCIAL ANALYTICS"),
    MARKETING_ANALYTICS(new CourseName("BUSINESS ANALYTICS"), "MARKETING ANALYTICS"),

    // INFORMATION SYSTEMS
    FINANCIAL_TECHNOLOGY(new CourseName("INFORMATION SYSTEMS"), "FINANCIAL TECHNOLOGY"),
    DIGITAL_INNOVATION(new CourseName("INFORMATION SYSTEMS"), "DIGITAL INNOVATION"),
    ELECTRONIC_COMMERCE(new CourseName("INFORMATION SYSTEMS"), "ELECTRONIC COMMERCE"),

    // INFORMATION SECURITY HAS NONE

    // COMPUTER ENGINEERING
    COMMUNICATIONS_AND_NETWORKING(new CourseName("COMPUTER ENGINEERING"), "COMMUNICATIONS AND NETWORKING"),
    EMBEDDED_COMPUTING(new CourseName("COMPUTER ENGINEERING"), "EMBEDDED COMPUTING"),
    INTELLIGENT_SYSTEMS(new CourseName("COMPUTER ENGINEERING"), "INTELLIGENT_SYSTEMS"),
    INTERACTIVE_DIGITAL_MEDIA(new CourseName("COMPUTER ENGINEERING"), "INTERACTIVE_DIGITAL_MEDIA"),
    LARGE_SCALE_COMPUTING(new CourseName("COMPUTER ENGINEERING"), "LARGE-SCALE COMPUTING"),
    SYSTEM_ON_A_CHIP_DESIGN(new CourseName("COMPUTER ENGINEERING"), "SYSTEM-ON-A-CHIP DESIGN");

    private String name;
    private CourseName courseName;

    AcceptedFocusArea(CourseName courseName, String name) {
        this.courseName = courseName;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Returns true if enum contains given specialisation.
     */
    public static boolean isValid(CourseName courseName, String specialisationName) {
        for (AcceptedFocusArea c: AcceptedFocusArea.values()) {
            if (c.courseName.equals(courseName) && c.name.equals(specialisationName)) {
                return true;
            }
        }
        return false;
    }
}
