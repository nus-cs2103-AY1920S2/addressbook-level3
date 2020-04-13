package seedu.expensela.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.expensela.ui.CategoryLabelMaker.getColouredCategoryLabel;

import org.junit.jupiter.api.Test;

class CategoryLabelMakerTest {

    @Test
    public static void equals() {
        assertEquals(getColouredCategoryLabel("FOOD"), getColouredCategoryLabel("FOOD"));
        assertEquals(getColouredCategoryLabel("food"), getColouredCategoryLabel("FOOD"));
        assertEquals(getColouredCategoryLabel("Category: FOOD"),
                getColouredCategoryLabel("FOOD"));
        assertEquals(getColouredCategoryLabel("Category: FOOD"),
                getColouredCategoryLabel("food"));
        assertNotEquals(getColouredCategoryLabel("Category: FOOD"),
                getColouredCategoryLabel("2020-01"));
    }

}
