package seedu.inventorybro.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;

class TransactCommandTest {

    @Test
    void execute_validSale_quantityDecreased() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        new TransactCommand("transact 1 q/-5").execute(items);

        assertEquals(45, items.getItem(0).getQuantity());
    }

    @Test
    void execute_validRestock_quantityIncreased() {
        ItemList items = new ItemList();
        items.addItem(new Item("Sprite Bottle", 30));

        new TransactCommand("transact 1 q/10").execute(items);

        assertEquals(40, items.getItem(0).getQuantity());
    }

    @Test
    void execute_exactDepletion_quantityBecomesZero() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        new TransactCommand("transact 1 q/-50").execute(items);

        assertEquals(0, items.getItem(0).getQuantity());
    }

    @Test
    void execute_quantityBelowZero_quantityUnchanged() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        new TransactCommand("transact 1 q/-999").execute(items);

        assertEquals(50, items.getItem(0).getQuantity());
    }

    @Test
    void execute_invalidIndex_quantityUnchanged() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        new TransactCommand("transact 99 q/10").execute(items);

        assertEquals(50, items.getItem(0).getQuantity());
    }

    @Test
    void execute_invalidInputFormats_quantityUnchanged() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        new TransactCommand("transact 1 10").execute(items);
        assertEquals(50, items.getItem(0).getQuantity());

        new TransactCommand("transact abc q/10").execute(items);
        assertEquals(50, items.getItem(0).getQuantity());

        new TransactCommand("transact 1 q/-").execute(items);
        assertEquals(50, items.getItem(0).getQuantity());

        new TransactCommand("transact 1 q/abc").execute(items);
        assertEquals(50, items.getItem(0).getQuantity());
    }

    @Test
    void execute_zeroChange_quantityUnchanged() {
        ItemList items = new ItemList();
        items.addItem(new Item("Coke Can", 50));

        new TransactCommand("transact 1 q/0").execute(items);

        assertEquals(50, items.getItem(0).getQuantity());
    }
}
