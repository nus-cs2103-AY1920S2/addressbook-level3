package seedu.planner.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.planner.commons.exceptions.IllegalValueException;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.module.ModuleCode;
import seedu.planner.model.programmes.specialisations.GenericSpecialisation;
import seedu.planner.model.programmes.specialisations.cs.AlgorithmsAndTheorySpecialisation;
import seedu.planner.model.programmes.specialisations.cs.ArtificialIntelligenceSpecialisation;
import seedu.planner.model.programmes.specialisations.cs.ComputerGraphicsAndGamesSpecialisation;
import seedu.planner.model.programmes.specialisations.cs.ComputerSecuritySpecialisation;
import seedu.planner.model.programmes.specialisations.cs.DatabaseSystemsSpecialisation;
import seedu.planner.model.programmes.specialisations.cs.MultimediaInformationRetrievalSpecialisation;
import seedu.planner.model.programmes.specialisations.cs.NetworkingAndDistributedSystemsSpecialisation;
import seedu.planner.model.programmes.specialisations.cs.ParallelComputingSpecialisation;
import seedu.planner.model.programmes.specialisations.cs.ProgrammingLanguagesSpecialisation;
import seedu.planner.model.programmes.specialisations.cs.SoftwareEngineeringSpecialisation;
import seedu.planner.model.programmes.specialisations.is.DigitalInnovationSpecialisation;
import seedu.planner.model.programmes.specialisations.is.ElectronicCommerceSpecialisation;
import seedu.planner.model.programmes.specialisations.is.FinancialTechnologySpecialisation;
import seedu.planner.model.student.Major;
import seedu.planner.model.student.Name;
import seedu.planner.model.student.Student;
import seedu.planner.model.student.TimeTableMap;
import seedu.planner.model.util.ModuleUtil;


/**
 * Jackson-friendly version of {@link Student}.
 */
class JsonAdaptedStudent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Student's %s field is missing!";
    public static final String INVALID_SPECIALISATION = "Invalid Specialisation: %s";

    public final JsonAdaptedTimeTableMap timeTableMap;
    public final List<JsonAdaptedModuleCode> exemptedModules;
    private final String name;
    private final String major;
    private final String specialisation;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedStudent(@JsonProperty("name") String name, @JsonProperty("major") String major,
                              @JsonProperty("specialisation") String specialisation,
                              @JsonProperty("timeTableMap") JsonAdaptedTimeTableMap timeTableMap,
                              @JsonProperty("exemptedModules") List<JsonAdaptedModuleCode> exemptedModules) {
        this.name = name;
        this.major = major;
        this.specialisation = specialisation;
        this.timeTableMap = timeTableMap;
        this.exemptedModules = exemptedModules;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedStudent(Student source) {
        name = source.getName().fullName;
        major = source.getMajor().toString();
        if (source.getSpecialisation() == null) {
            specialisation = null;
        } else {
            specialisation = source.getSpecialisation().toString();
        }
        timeTableMap = new JsonAdaptedTimeTableMap(source.getTimeTableMap());
        exemptedModules = new ArrayList<>();
        exemptedModules.addAll(source.getExemptedModules().stream()
                .map(JsonAdaptedModuleCode::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Student} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted student.
     */
    public Student toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (major == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Major.class.getSimpleName()));
        }
        if (!Major.isValidMajor(major)) {
            throw new IllegalValueException(Major.MESSAGE_CONSTRAINTS);
        }
        final Major modelMajor = new Major(major);

        if (timeTableMap == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TimeTableMap.class.getSimpleName()));
        }
        final TimeTableMap modelTimeTableMap = timeTableMap.toModelType();

        final List<ModuleCode> modelExemptedModules = new ArrayList<>();
        if (exemptedModules != null) {
            for (JsonAdaptedModuleCode moduleCode : exemptedModules) {
                ModuleCode modelCode = moduleCode.toModelType();

                // Ignore module codes not present in the module database
                if (!ModuleUtil.hasModuleWithCode(modelCode)) {
                    continue;
                }

                // Ignore duplicate module codes
                if (modelExemptedModules.contains(modelCode)) {
                    continue;
                }

                modelExemptedModules.add(modelCode);
            }
        }
        GenericSpecialisation modelSpecialisation = null;
        if (specialisation != null) {
            switch (specialisation) {
            case AlgorithmsAndTheorySpecialisation.NAME:
                modelSpecialisation = new AlgorithmsAndTheorySpecialisation();
                break;
            case ArtificialIntelligenceSpecialisation.NAME:
                modelSpecialisation = new ArtificialIntelligenceSpecialisation();
                break;
            case ComputerGraphicsAndGamesSpecialisation.NAME:
                modelSpecialisation = new ComputerGraphicsAndGamesSpecialisation();
                break;
            case ComputerSecuritySpecialisation.NAME:
                modelSpecialisation = new ComputerSecuritySpecialisation();
                break;
            case DatabaseSystemsSpecialisation.NAME:
                modelSpecialisation = new DatabaseSystemsSpecialisation();
                break;
            case MultimediaInformationRetrievalSpecialisation.NAME:
                modelSpecialisation = new MultimediaInformationRetrievalSpecialisation();
                break;
            case NetworkingAndDistributedSystemsSpecialisation.NAME:
                modelSpecialisation = new NetworkingAndDistributedSystemsSpecialisation();
                break;
            case ParallelComputingSpecialisation.NAME:
                modelSpecialisation = new ParallelComputingSpecialisation();
                break;
            case ProgrammingLanguagesSpecialisation.NAME:
                modelSpecialisation = new ProgrammingLanguagesSpecialisation();
                break;
            case SoftwareEngineeringSpecialisation.NAME:
                modelSpecialisation = new SoftwareEngineeringSpecialisation();
                break;
            case DigitalInnovationSpecialisation.NAME:
                modelSpecialisation = new DigitalInnovationSpecialisation();
                break;
            case ElectronicCommerceSpecialisation.NAME:
                modelSpecialisation = new ElectronicCommerceSpecialisation();
                break;
            case FinancialTechnologySpecialisation.NAME:
                modelSpecialisation = new FinancialTechnologySpecialisation();
                break;
            default:
                throw new ParseException(String.format(INVALID_SPECIALISATION, specialisation));
            }
        }

        return new Student(modelName, modelMajor, modelTimeTableMap, modelExemptedModules, modelSpecialisation);
    }

}
