package seedu.inventorybro.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;

/**
 * Execution tests for {@link EditCommand}.
 */
//@@author vionyp
class EditCommandTest {
    private final Ui ui = new Ui();

    /**
     * Verifies that edit updates name, quantity, and price correctly.
     */
    @Test
    void execute_validEdit_updatesAllFields() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));

        new EditCommand("editItem 1 d/Orange q/20 p/1.50").execute(items, ui);

        assertEquals("Orange", items.getItem(0).getDescription());
        assertEquals(20, items.getItem(0).getQuantity());
        assertEquals(1.50, items.getItem(0).getPrice());
    }

    /**
     * Verifies that editing one item does not modify the others.
     */
    @Test
    void execute_doesNotAffectOtherItems() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));
        items.addItem(new Item("Banana", 5));

        new EditCommand("editItem 1 d/Orange q/20 p/1.50").execute(items, ui);

        assertEquals("Banana", items.getItem(1).getDescription());
        assertEquals(5, items.getItem(1).getQuantity());
    }

    /**
     * Verifies that editing the second item works correctly.
     */
    @Test
    void execute_secondItem_updatesCorrectly() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));
        items.addItem(new Item("Banana", 5));

        new EditCommand("editItem 2 d/Mango q/15 p/2.00").execute(items, ui);

        assertEquals("Mango", items.getItem(1).getDescription());
        assertEquals(15, items.getItem(1).getQuantity());
        assertEquals(2.00, items.getItem(1).getPrice());
    }

    /**
     * Verifies that an invalid index throws an exception.
     */
    @Test
    void execute_invalidIndex_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));

        assertThrows(IllegalArgumentException.class,
                () -> new EditCommand("editItem 99 d/Ghost q/0 p/0.00").execute(items, ui));
    }

    /**
     * Verifies that a negative quantity throws an exception.
     */
    @Test
    void execute_negativeQuantity_throwsException() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));

        assertThrows(IllegalArgumentException.class,
                () -> new EditCommand("editItem 1 d/Apple q/-1 p/1.00").execute(items, ui));
    }
}
//@@author
