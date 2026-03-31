package seedu.inventorybro.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;

/**
 * Execution tests for {@link FilterCommand}.
 */
class FilterCommandTest {
    private final Ui ui = new Ui();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    /**
     * Redirects standard output to a buffer before each test.
     */
    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Restores standard output after each test.
     */
    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    private ItemList buildItems() {
        ItemList items = new ItemList();
        items.addItem(new Item("Apple", 10));
        items.addItem(new Item("Banana", 5));
        items.addItem(new Item("Cherry", 20));
        return items;
    }

    /**
     * Verifies that filtering by description equality returns only the exact match.
     */
    @Test
    void execute_descriptionEquals_returnsExactMatch() {
        new FilterCommand("filterItem description = 'Apple'").execute(buildItems(), ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. Apple (Quantity: 10)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    /**
     * Verifies that filtering by description less-than returns lexicographically smaller items.
     */
    @Test
    void execute_descriptionLessThan_returnsLexicographicallySmaller() {
        new FilterCommand("filterItem description < 'Banana'").execute(buildItems(), ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. Apple (Quantity: 10)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    /**
     * Verifies that filtering by description greater-than returns lexicographically larger items.
     */
    @Test
    void execute_descriptionGreaterThan_returnsLexicographicallyLarger() {
        new FilterCommand("filterItem description > 'Banana'").execute(buildItems(), ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. Cherry (Quantity: 20)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    /**
     * Verifies that filtering by description with a multi-word quoted value works correctly.
     */
    @Test
    void execute_descriptionMultiWordValue_returnsMatch() {
        ItemList items = new ItemList();
        items.addItem(new Item("Green Apple", 15));
        items.addItem(new Item("Apple", 10));

        new FilterCommand("filterItem description = 'Green Apple'").execute(items, ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. Green Apple (Quantity: 15)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    /**
     * Verifies that filtering by quantity equality returns only items with the exact quantity.
     */
    @Test
    void execute_quantityEquals_returnsExactMatch() {
        new FilterCommand("filterItem quantity = 10").execute(buildItems(), ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. Apple (Quantity: 10)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    /**
     * Verifies that filtering by quantity less-than returns items with a smaller quantity.
     */
    @Test
    void execute_quantityLessThan_returnsItemsBelow() {
        new FilterCommand("filterItem quantity < 10").execute(buildItems(), ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. Banana (Quantity: 5)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    /**
     * Verifies that filtering by quantity greater-than returns items with a larger quantity.
     */
    @Test
    void execute_quantityGreaterThan_returnsItemsAbove() {
        new FilterCommand("filterItem quantity > 10").execute(buildItems(), ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. Cherry (Quantity: 20)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    /**
     * Verifies that when no items match, a "no match" message is displayed.
     */
    @Test
    void execute_noMatchingItems_showsNoMatchMessage() {
        new FilterCommand("filterItem description = 'Durian'").execute(buildItems(), ui);

        String expected = "No items match the given filter." + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    /**
     * Verifies that multiple items can be returned when more than one satisfies the predicate.
     */
    @Test
    void execute_quantityGreaterThan_returnsMultipleMatches() {
        new FilterCommand("filterItem quantity > 4").execute(buildItems(), ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. Apple (Quantity: 10)" + System.lineSeparator()
                + "2. Banana (Quantity: 5)" + System.lineSeparator()
                + "3. Cherry (Quantity: 20)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    // ── AND combinations ──────────────────────────────────────────────────────

    /**
     * Verifies that AND narrows results to only items satisfying both predicates.
     */
    @Test
    void execute_andFilter_itemsMustMatchBoth() {
        new FilterCommand("filterItem quantity > 5 AND quantity < 15").execute(buildItems(), ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. Apple (Quantity: 10)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    /**
     * Verifies that AND with no items matching both predicates returns the no-match message.
     */
    @Test
    void execute_andFilterNoMatch_showsNoMatchMessage() {
        new FilterCommand("filterItem description = 'Apple' AND quantity > 50")
                .execute(buildItems(), ui);

        String expected = "No items match the given filter." + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    // ── OR combinations ───────────────────────────────────────────────────────

    /**
     * Verifies that OR returns items that satisfy either predicate.
     */
    @Test
    void execute_orFilter_itemsMatchEither() {
        new FilterCommand("filterItem description = 'Apple' OR description = 'Cherry'")
                .execute(buildItems(), ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. Apple (Quantity: 10)" + System.lineSeparator()
                + "2. Cherry (Quantity: 20)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    // ── Mixed AND / OR (AND binds tighter than OR) ────────────────────────────

    /**
     * Verifies A AND B OR C grouping: items matching (A AND B) or C are returned.
     * Input: quantity > 5 AND quantity < 15 OR description = 'Cherry'
     * Expected groups: [(quantity>5 AND quantity<15), (description='Cherry')]
     * Apple satisfies group 1 (10 > 5 and 10 < 15); Cherry satisfies group 2.
     */
    @Test
    void execute_andThenOr_correctGrouping() {
        new FilterCommand("filterItem quantity > 5 AND quantity < 15 OR description = 'Cherry'")
                .execute(buildItems(), ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. Apple (Quantity: 10)" + System.lineSeparator()
                + "2. Cherry (Quantity: 20)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }

    /**
     * Verifies A OR B AND C grouping: items matching A or (B AND C) are returned.
     * Input: description = 'Banana' OR quantity > 5 AND quantity < 15
     * Expected groups: [(description='Banana'), (quantity>5 AND quantity<15)]
     * Banana satisfies group 1; Apple satisfies group 2.
     */
    @Test
    void execute_orThenAnd_correctGrouping() {
        new FilterCommand("filterItem description = 'Banana' OR quantity > 5 AND quantity < 15")
                .execute(buildItems(), ui);

        String expected = "Here are the filtered items:" + System.lineSeparator()
                + "1. Apple (Quantity: 10)" + System.lineSeparator()
                + "2. Banana (Quantity: 5)" + System.lineSeparator();
        assertEquals(expected, outContent.toString());
    }
}
