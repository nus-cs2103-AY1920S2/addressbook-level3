package seedu.address.storage;

import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.CourseList;

//@@author chanckben
public class JsonCourseListStorageTest {
    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonCourseListStorageTest");

    @Test
    public void readCourseList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readCourseList(null));
    }

    private java.util.Optional<CourseList> readCourseList(String filePath) throws Exception {
        return new JsonCourseListStorage(Paths.get(filePath).toString())
                .readCourseList(addToTestDataPathIfNotNull(filePath).toString());
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_throwsDataConversionException() throws Exception {
        assertThrows(DataConversionException.class, () -> readCourseList("NonExistentFile.json"));
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readCourseList("notJsonFormatCourseList.json"));
    }

    @Test
    public void readCourseList_invalidCourseCourseList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readCourseList("invalidCourseCourseList.json"));
    }

    @Test
    public void readCourseList_invalidAndValidCourseCourseList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () ->
                readCourseList("invalidAndValidCourseCourseList.json"));
    }
}
