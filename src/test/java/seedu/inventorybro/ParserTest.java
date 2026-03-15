package seedu.inventorybro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ParserTest {

    @Test
    void parseAdd_validCommand_itemAdded() {
        ItemList items = new ItemList();

        Parser.parseAdd("addItem d/Apple q/10", items);

        Item item = items.getItems().get(0);

        assertEquals("Apple", item.getDescription());
        assertEquals(10, item.getQuantity());
    }

    @Test
    void parseAdd_multiWordName() {
        ItemList items = new ItemList();

        Parser.parseAdd("addItem d/Green Apple q/25", items);

        Item item = items.getItems().get(0);

        assertEquals("Green Apple", item.getDescription());
        assertEquals(25, item.getQuantity());
    }

    @Test
    void parseAdd_invalidFormat_throwsException() {
        ItemList items = new ItemList();

        assertThrows(
            IllegalArgumentException.class,
            () -> Parser.parseAdd("addItem Apple 10", items)
        );
    }
}
