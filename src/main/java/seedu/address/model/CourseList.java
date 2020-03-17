package seedu.address.model;

import java.util.ArrayList;

import seedu.address.model.profile.course.Course;

/**
 * Creates a new CourseList object which contains Module objects.
 */
public class CourseList {

    private ArrayList<Course> courseList = new ArrayList<>();

    public CourseList() {}

    /**
     * Adds a course to the course list.
     */
    public void addCourse(Course course) {
        courseList.add(course);
    }

    /**
     * Returns true if a module with the same fields as {@code module} exists in the module list.
     */
    /*
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return moduleList.contains(module);
    }
*/
    /**
     * Returns true if a module with the module code {@code moduleCode} exists in the module list.
     */
    /*
    public boolean hasModuleWithModuleCode(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        return moduleCodes.contains(moduleCode);
    }
     */

    /**
     * Returns the module with module code {@code moduleCode} in the module list, if it exists.
     * @throws NoSuchElementException No module in the module list contains {@code moduleCode}.
     */
    /*
    public Module getModuleWithModuleCode(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        if (!hasModuleWithModuleCode(moduleCode)) {
            throw new NoSuchElementException("Module with module code " + moduleCode.toString() + " does not exist");
        }
        for (Module mod: moduleList) {
            if (mod.getModuleCode().equals(moduleCode)) {
                return mod;
            }
        }
        // Code should not reach this line
        assert false;
        return null;
    }

     */
}
