package seedu.expensela.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.junit.jupiter.api.Test;

public class ToggleViewTest {

    private final ToggleView toggleView = new ToggleView();

    @Test
    public void switchToggleView_switchCorrect() {
        boolean initialIsViewList = toggleView.getIsViewList();
        toggleView.switchIsViewList();
        assertEquals(toggleView.getIsViewList(), !initialIsViewList);
    }

    @Test
    public void setToggleView_setCorrect() {
        Random random = new Random();
        boolean newIsViewList = random.nextBoolean();
        toggleView.setIsViewList(newIsViewList);
        assertEquals(toggleView.getIsViewList(), newIsViewList);
    }
}
