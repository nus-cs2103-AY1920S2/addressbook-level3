package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class FrequencyListTest {

    private ArrayList<String> strings1;
    private ArrayList<String> strings2;

    /**
     * Fills the test ArrayLists with arbitrary strings.
     */
    public void fillStringList() {
        strings1 = new ArrayList<>();
        strings1.add("abc");
        strings1.add("def");
        strings1.add("ghi");
        strings2 = new ArrayList<>();
        strings2.add("abc");
        strings2.add("def");
        strings2.add("ghi");
        strings2.add("ghi");
    }

    @Test
    public void generate() {
        fillStringList();
        FrequencyList list1 = new FrequencyList();
        FrequencyList list2 = new FrequencyList();
        list1.generate(strings1);
        list2.generate(strings1);
        assertTrue(list1.equals(list2));
    }

    @Test
    public void add() {
        fillStringList();
        FrequencyList list1 = new FrequencyList();
        FrequencyList list2 = new FrequencyList();
        list1.generate(strings1);
        list2.generate(strings2);
        list1.add("ghi");
        assertTrue(list1.equals(list2));
    }
}
