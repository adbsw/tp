package seedu.inventorybro.command;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;
import seedu.inventorybro.Ui;

/**
 * Updates an existing item's description, quantity, or price individually.
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
     * Parses the edit command and updates the targeted item's name, quantity, or price.
     *
     * @param items The inventory item list to update.
     * @param ui    The UI to display messages.
     */
    @Override
    public void execute(ItemList items, Ui ui) {
        assert items != null : "ItemList should not be null";
        assert ui != null : "Ui should not be null";

        try {
            String[] words = input.split(" ", 2);
            String commandType = words[0].toLowerCase();

            if (words.length < 2) {
                throw new IllegalArgumentException(getFormatError(commandType));
            }

            if (commandType.equals("editname")) {
                editName(words[1], items, ui);
            } else if (commandType.equals("editquantity")) {
                editQuantity(words[1], items, ui);
            } else if (commandType.equals("editprice")) {
                editPrice(words[1], items, ui);
            } else {
                throw new IllegalArgumentException("Unknown edit command. "
                        + "Use: editName, editQuantity, or editPrice");
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Index must be a number.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void editName(String args, ItemList items, Ui ui) {
        String[] parts = args.split("d/", 2);
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid editName format. "
                    + "Use: editName INDEX d/NEW_NAME");
        }
        int index = Integer.parseInt(parts[0].trim()) - 1;
        validateIndex(index, items);
        String newName = parts[1].trim();
        if (newName.isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be empty.");
        }
        Item item = items.getItem(index);
        item.setDescription(newName);
        ui.showMessage("Item name updated: " + item);
    }

    private void editQuantity(String args, ItemList items, Ui ui) {
        String[] parts = args.split("q/", 2);
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid editQuantity format. "
                    + "Use: editQuantity INDEX q/NEW_QUANTITY");
        }
        int index = Integer.parseInt(parts[0].trim()) - 1;
        validateIndex(index, items);
        int newQuantity = Integer.parseInt(parts[1].trim());
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        Item item = items.getItem(index);
        item.setQuantity(newQuantity);
        ui.showMessage("Item quantity updated: " + item);
    }

    private void editPrice(String args, ItemList items, Ui ui) {
        String[] parts = args.split("p/", 2);
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid editPrice format. "
                    + "Use: editPrice INDEX p/NEW_PRICE");
        }
        int index = Integer.parseInt(parts[0].trim()) - 1;
        validateIndex(index, items);
        double newPrice = Double.parseDouble(parts[1].trim());
        if (newPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        Item item = items.getItem(index);
        item.setPrice(newPrice);
        ui.showMessage("Item price updated: " + item);
    }

    private void validateIndex(int index, ItemList items) {
        if (index < 0 || index >= items.size()) {
            throw new IllegalArgumentException("Invalid index.");
        }
    }

    private String getFormatError(String commandType) {
        switch (commandType) {
        case "editname":
            return "Invalid editName format. Use: editName INDEX d/NEW_NAME";
        case "editquantity":
            return "Invalid editQuantity format. Use: editQuantity INDEX q/NEW_QUANTITY";
        case "editprice":
            return "Invalid editPrice format. Use: editPrice INDEX p/NEW_PRICE";
        default:
            return "Use: editName, editQuantity, or editPrice";
        }
    }
}
//@@author
