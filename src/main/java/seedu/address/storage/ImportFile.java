package seedu.address.storage;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PATH;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.group.Group;
import seedu.address.model.person.ActivityList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PlaceList;
import seedu.address.model.person.Time;
import seedu.address.model.person.TimeList;
import seedu.address.model.tag.Tag;

/**
 * Converts CSV files to be added to current Life.
 */
public class ImportFile {

    /**
     * Reads a CSV File and returns a list of person to be added to the current CoderLifeInsights.
     *
     * @param fileName path of CSV file to be imported.
     * @return List of person to be imported.
     * @throws CommandException if person in CSV file does not conform to format.
     */
    public List<Person> importCsv(String fileName) throws CommandException {
        try {
            File csvFile = new File(fileName);
            CsvMapper mapper = new CsvMapper();
            CsvSchema schema = CsvSchema.emptySchema().withHeader();
            MappingIterator<Map<String, String>> it =
                    mapper.readerFor(Map.class).with(schema).readValues(csvFile);
            List<Person> people = new ArrayList<>();
            while (it.hasNext()) {
                Map<String, String> onePerson = it.next();
                String oneName = onePerson.get("name");
                String onePhone = onePerson.get("phone");
                String oneEmail = onePerson.get("email");
                String oneAddress = onePerson.get("address");
                String oneTagged = onePerson.get("tagged").strip();
                String[] tags = oneTagged.split(";");
                String oneTime = onePerson.get("time");
                String onePlaceList = onePerson.get("places");
                String[] places = onePlaceList.split(";");
                String oneActivityList = onePerson.get("activities");
                String[] activities = oneActivityList.split(";");
                String oneTimeList = onePerson.get("times");
                String[] times = oneTimeList.split(";");

                Set<Tag> tag;
                if (oneTagged.isEmpty()) {
                    tag = ParserUtil.parseTags(new ArrayList<>());
                } else {
                    tag = ParserUtil.parseTags(Arrays.asList(tags));
                }
                Name name = ParserUtil.parseName(oneName);
                Phone phone = ParserUtil.parsePhone(onePhone);
                Email email = ParserUtil.parseEmail(oneEmail);
                Address address = ParserUtil.parseAddress(oneAddress);
                Time time = ParserUtil.parseTime(oneTime);
                PlaceList placeList;
                if (onePlaceList.isEmpty()) {
                    placeList = new PlaceList(new ArrayList<String>());
                } else {
                    List<String> xs = new ArrayList<>();
                    for (String s : places) {
                        xs.add(s);
                    }
                    placeList = new PlaceList(xs);
                }
                ActivityList activityList;
                if (oneActivityList.isEmpty()) {
                    activityList = new ActivityList(new ArrayList<String>());
                } else {
                    List<String> xs = new ArrayList<>();
                    for (String s : activities) {
                        xs.add(s);
                    }
                    activityList = new ActivityList(xs);
                }
                TimeList timeList;
                if (oneTimeList.isEmpty()) {
                    timeList = new TimeList(new ArrayList<String>());
                } else {
                    List<String> xs = new ArrayList<>();
                    for (String s : times) {
                        xs.add(s);
                    }
                    timeList = new TimeList(xs);
                }

                Person person = new Person(name, phone, email, address, tag, time,
                        placeList, activityList, timeList);
                people.add(person);
            }
            return people;
        } catch (IOException | ParseException ioe) {
            throw new CommandException(String.format(MESSAGE_INVALID_PATH));
        }
    }

    /**
     * Reads a CSV File and returns a list of events to be added to the current CoderLifeInsights.
     *
     * @param fileName path of CSV file to be imported.
     * @return List of groups to be imported.
     * @throws CommandException if event in CSV file does not conform to format.
     */
    public List<Event> importEventCsv(String fileName) throws CommandException {
        try {
            File csvFile = new File(fileName);
            CsvMapper mapper = new CsvMapper();
            CsvSchema schema = CsvSchema.emptySchema().withHeader();
            MappingIterator<Map<String, String>> it =
                    mapper.readerFor(Map.class).with(schema).readValues(csvFile);

            List<Event> events = new ArrayList<>();
            while (it.hasNext()) {
                Map<String, String> oneEvent = it.next();
                String eventId = oneEvent.get("eventId");
                String oneActivity = oneEvent.get("activity");
                String onePlace = oneEvent.get("place");
                String oneWithPerson = oneEvent.get("withPerson");
                String oneWithGroup = oneEvent.get("withGroup");
                String oneTime = oneEvent.get("time");

                Time time = ParserUtil.parseTime(oneTime);
                int withPerson = Integer.valueOf(oneWithPerson);
                int withGroup = Integer.valueOf(oneWithGroup);
                Event event = new Event(oneActivity, onePlace, time);
                event.setEventId(Integer.valueOf(eventId));
                event.setWithPerson(withPerson);
                event.setWithGroup(withGroup);
                events.add(event);
            }
            return events;
        } catch (IOException ioe) {
            throw new CommandException(String.format(MESSAGE_INVALID_PATH));
        }
    }

    /**
     * Reads a CSV File and returns a list of groups to be added to the current CoderLifeInsights.
     *
     * @param fileName path of CSV file to be imported.
     * @return List of events to be imported.
     * @throws CommandException if group in CSV file does not conform to format.
     */
    public List<Group> importGroupCsv(String fileName) throws CommandException {
        try {
            File csvFile = new File(fileName);
            CsvMapper mapper = new CsvMapper();
            CsvSchema schema = CsvSchema.emptySchema().withHeader();
            MappingIterator<Map<String, String>> it =
                    mapper.readerFor(Map.class).with(schema).readValues(csvFile);

            List<Group> groups = new ArrayList<>();
            while (it.hasNext()) {
                Map<String, String> oneGroup = it.next();
                String oneName = oneGroup.get("name");
                String oneGroupId = oneGroup.get("groupId");
                String oneTimeSpent = oneGroup.get("timeSpent");
                String oneMemberIds = oneGroup.get("memberIDs").strip();
                String[] memberIds = oneMemberIds.split(";");
                String oneEventIds = oneGroup.get("eventIDs").strip();
                String[] eventIds = oneEventIds.split(";");
                String onePlaceList = oneGroup.get("places");
                String[] places = onePlaceList.split(";");
                String oneActivityList = oneGroup.get("activities");
                String[] activities = oneActivityList.split(";");
                String oneTimeList = oneGroup.get("times");
                String[] times = oneTimeList.split(";");

                ArrayList<Integer> members = new ArrayList<>();
                if (!oneMemberIds.isEmpty()) {
                    for (int i = 0; i < memberIds.length; i++) {
                        members.add(Integer.parseInt(memberIds[i]));
                    }
                }
                ArrayList<Integer> events = new ArrayList<>();
                if (!oneEventIds.isEmpty()) {
                    for (int i = 0; i < eventIds.length; i++) {
                        events.add(Integer.parseInt(eventIds[i]));
                    }
                }
                Name name = ParserUtil.parseName(oneName);
                Time time = ParserUtil.parseTime(oneTimeSpent);
                PlaceList placeList;
                if (onePlaceList.isEmpty()) {
                    placeList = new PlaceList(new ArrayList<String>());
                } else {
                    List<String> xs = new ArrayList<>();
                    for (String s : places) {
                        xs.add(s);
                    }
                    placeList = new PlaceList(xs);
                }
                ActivityList activityList;
                if (oneActivityList.isEmpty()) {
                    activityList = new ActivityList(new ArrayList<String>());
                } else {
                    List<String> xs = new ArrayList<>();
                    for (String s : activities) {
                        xs.add(s);
                    }
                    activityList = new ActivityList(xs);
                }

                TimeList timeList;
                if (oneTimeList.isEmpty()) {
                    timeList = new TimeList(new ArrayList<String>());
                } else {
                    List<String> xs = new ArrayList<>();
                    for (String s : times) {
                        xs.add(s);
                    }
                    timeList = new TimeList(xs);
                }

                Group group = new Group(name, placeList, activityList, timeList);
                group.setTimeSpent(time);
                group.setMemberIDs(members);
                group.setEventIDs(events);
                groups.add(group);
            }
            return groups;
        } catch (IOException | ParseException ioe) {
            throw new CommandException(String.format(MESSAGE_INVALID_PATH));
        }
    }
}
