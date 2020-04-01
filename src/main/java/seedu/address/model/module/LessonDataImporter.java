package seedu.address.model.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import seedu.address.Main;
import seedu.address.commons.util.JsonUtil;


public class LessonDataImporter {

    private static List<Lesson> lessons = new ArrayList<>();

    public static List<Lesson> run(String modCode) {
        Lesson lesson;
        String fileName = "";
        try {
            fileName = "json/{0}.json".replace("{0}", modCode);
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            String text = new Scanner(Main.class.getClassLoader().getResourceAsStream(fileName),
                    "UTF-8").useDelimiter("\\A").next();
            int startIndex = text.indexOf("timetable") + 12;
            int endIndex = text.indexOf("examDate") - 3;
            String finalText = text.substring(startIndex, endIndex);

            char st = '[';
            char en = ']';
            finalText = st + finalText + en;
            System.out.println(finalText);
            Optional<JsonSerializableLesson[]> optionalLesson = JsonUtil.readJsonString(finalText,
                    JsonSerializableLesson[].class);
            if (optionalLesson.isPresent()) {
                JsonSerializableLesson[] lessonArray = optionalLesson.get();
                for (JsonSerializableLesson l : lessonArray) {
                    lesson = l.toModelType();
                    if (!lessons.contains(lesson)) {
                        lessons.add(lesson);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading file: " + fileName);
            System.err.println(e);
        }
        return lessons;
    }
}
