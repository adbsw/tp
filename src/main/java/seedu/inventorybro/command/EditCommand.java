package seedu.inventorybro.command;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;

/**
 * Updates an existing item's description, quantity, and price.
 */

//@@author vionyp
public class EditCommand implements Command {
    private final String input;

    /**
     * Creates an edit command from the raw user input.
     *
     * @param input The full edit command string.
     */
    public EditCommand(String input) {
        this.input = input;
    }

    /**
     * Parses the edit command input and updates the targeted item.
     *
     * @param items The inventory item list to update.
     */
    @Override
    public void execute(ItemList items, Ui ui) {
        try {
            String[] words = input.split(" ", 2);
            if (words.length < 2) {
                throw new IllegalArgumentException("Invalid edit format. "
                        + "Use: edit INDEX d/NEW_NAME q/NEW_QUANTITY p/NEW_PRICE");
            }

            String[] parts = words[1].split("d/", 2);
            if (parts.length < 2) {
                throw new IllegalArgumentException("Invalid edit format. "
                        + "Use: edit INDEX d/NEW_NAME q/NEW_QUANTITY p/NEW_PRICE");
            }

            int index = Integer.parseInt(parts[0].trim()) - 1;
            if (index < 0 || index >= items.size()) {
                throw new IllegalArgumentException("Invalid index.");
            }

            String[] descParts = parts[1].split("q/", 2);
            if (descParts.length < 2) {
                throw new IllegalArgumentException("Invalid edit format. "
                        + "Use: edit INDEX d/NEW_NAME q/NEW_QUANTITY p/NEW_PRICE");
            }

            String newName = descParts[0].trim();
            if (newName.isEmpty()) {
                throw new IllegalArgumentException("Item name cannot be empty.");
            }

            String[] quantityParts = descParts[1].split("p/", 2);
            if (quantityParts.length < 2) {
                throw new IllegalArgumentException("Invalid edit format. "
                        + "Use: edit INDEX d/NEW_NAME q/NEW_QUANTITY p/NEW_PRICE");
            }

            int newQuantity = Integer.parseInt(quantityParts[0].trim());
            if (newQuantity < 0) {
                throw new IllegalArgumentException("Quantity cannot be negative.");
            }

            double newPrice = Double.parseDouble(quantityParts[1].trim());
            if (newPrice < 0) {
                throw new IllegalArgumentException("Price cannot be negative.");
            }

            Item item = items.getItem(index);
            item.setDescription(newName);
            item.setQuantity(newQuantity);
            item.setPrice(newPrice);

            ui.showMessage("Item updated: " + item);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Index and quantity must be numbers, price must be a valid decimal.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
//@@author
