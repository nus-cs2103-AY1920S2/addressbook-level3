package seedu.address.searcher;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * WIP
 */
public class ModPlanClass {
    private static final ArrayList<String> allMajors = new ArrayList<>(
            Arrays.asList("Chinese Language", "Chinese Studies", "Japanese Studies", "Malay Studies",
                    "South Asian Studies", "Southeast Asian Studies", "English Language", "English Literature",
                    "History", "Philosophy", "Theater Studies", "Communications and New Media", "Economics",
                    "Geography", "Political Science", "Psychology", "Social Work", "Sociology",
                    "Environmental Studies in Geography", "Global Studies", "Business Administration(Accountancy)",
                    "Business Administration", "Business Analytics", "Computer Science", "Information Systems",
                    "Information Security", "Computer Engineering", "Dentistry", "Architecture", "Industrial Design",
                    "Landscape Architecture", "Project and Facilities Management", "Real Estate",
                    "Biomedical Engineering", "Chemical Engineering", "Civil Engineering", "Engineering Science",
                    "Environmental Engineering", "Electrical Engineering", "Industrial and Systems Engineering",
                    "Material Science Engineering", "Mechanical Engineering", "Computer Engineering",
                    "Undergraduate Law Programme", "Graduate LL.B. Programme", "Medicine", "Nursing", "Music",
                    "Applied Mathematics", "Chemistry", "Computational Biology", "Data Science and Analytics",
                    "Environmental Studies in Biology", "Food Science and Technology", "Life Sciences", "Mathematics",
                    "Pharmacy", "Pharmaceutical Science", "Physics", "Quantitative Finance", "Statistics"));

    private static ArrayList<String> statisticsReq = new ArrayList<>(Arrays.asList("ST1131 /OR ST1232", "MA1101R",
            "MA1102R", "CS1010E /OR CS1010S /OR CS1010X", "ST2131 /OR MA2216", "ST2132", "ST2137",
            "MA2311 /OR MA2104 /OR MA2108S", "ST3131", "ST3236 /OR MA3238", "ST4199", "ST4231", "ST4233"));

    private static final ArrayList<String> computerScienceReq = new ArrayList<>(Arrays.asList("CS1101S", "CS1231S",
            "CS2030S", "CS2040S", "CS2100", "CS2103T", "CS2106", "CS3230", "IS1103", "CS2101", "ES2660", "MA1521",
            "MA1101R", "ST2334"));

    private static final ArrayList<String> informationSystemsReq = new ArrayList<>(Arrays.asList("CS1010J", "CS1231",
            "IS1103", "CS2030", "CS2040", "CS2102", "CS2105", "IS2101", "IS2102", "IS2103", "IS3103", "IS3106",
            "IS4100", "IS4103", "MA1301", "MA1312", "ST2334"));

    private static final ArrayList<String> computerEngineeringReq = new ArrayList<>(Arrays.asList("CS1010", "CS2040C",
            "CS2113T", "CG2271", "CG2027", "EE2026", "CG2028", "EE4204", "CG2023", "CG1111", "CG1112", "CG4002",
            "CS1231", "MA1511", "MA1512", "MA1508E", "ST2334", "CS2101", "EG2401A"));

    private static final ArrayList<String> businessAnalyticsReq = new ArrayList<>(Arrays.asList("BT1101", "CS1010S",
            "EC1301", "IS1103", "MKT1705X", "BT2101", "BT2102", "CS2030", "CS2040", "IS2101", "ST2334",
            "BT3102", "BT3103", "IS3103", "BT4103"));

    private static final ArrayList<String> informationSecurityReq = new ArrayList<>(Arrays.asList("CS1010", "CS1231S",
            "CS2040C", "CS2100", "CS2102", "CS2113T", "CS2105", "CS2106", "IS3103", "CS2107", "CS3235", "IS4231",
            "IS1103", "CS2101", "MA1101R", "MA1521", "ST2334"));

    public static ArrayList<String> getAllMajors() {
        return allMajors;
    }

    public static ArrayList<String> getComputerScienceReq() {
        return computerScienceReq;
    }

    public static ArrayList<String> getInformationSystemsReq() {
        return informationSystemsReq;
    }

    public static ArrayList<String> getComputerEngineeringReq() {
        return computerEngineeringReq;
    }

    public static ArrayList<String> getBusinessAnalyticsReq() {
        return businessAnalyticsReq;
    }

    public static ArrayList<String> getInformationSecurityReq() {
        return informationSystemsReq;
    }

    /**
     * Takes in a major and a mod code
     *
     * @param major (e.g Computer Science or Information Systems)
     * @param mod   (any mod code e.g CS1101S)
     * @return true if mod (mod code) is a compulsory core mod of Major
     */
    public static boolean checkIfCoreMod(String major, String mod) {
        switch (major.toUpperCase()) {
        case "COMPUTER SCIENCE":
            for (String curr : computerScienceReq) {
                if (curr.equals(mod)) {
                    return true;
                }
            }
            return false;

        case "INFORMATION SYSTEMS":
            for (String curr : informationSystemsReq) {
                if (curr.equals(mod)) {
                    return true;
                }
            }
            return false;

        case "COMPUTER ENGINEERING":
            for (String curr : computerEngineeringReq) {
                if (curr.equals(mod)) {
                    return true;
                }
            }
            return false;
        case "BUSINESS ANALYTICS":
            for (String curr : businessAnalyticsReq) {
                if (curr.equals(mod)) {
                    return true;
                }
            }
            return false;
        case "INFORMATION SECURITY":
            for (String curr : informationSecurityReq) {
                if (curr.equals(mod)) {
                    return true;
                }
            }
            return false;
        default:
            return false;
        }
    }
}
