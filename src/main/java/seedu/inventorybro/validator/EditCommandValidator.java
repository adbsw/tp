package seedu.inventorybro.validator;

import seedu.inventorybro.ItemList;

/**
 * Validates the raw input string for the editItem command.
 */
public class EditCommandValidator implements Validator {
    private final String input;

    public EditCommandValidator(String input) {
        this.input = input;
    }

    @Override
    public void validate(ItemList items) {
        try {
            String[] words = input.split(" ", 2);
            if (words.length < 2) {
                throw new IllegalArgumentException("Invalid edit format. "
                        + "Use: edit INDEX d/NEW_NAME q/NEW_QUANTITY");
            }

            String[] parts = words[1].split("d/", 2);
            int index = Integer.parseInt(parts[0].trim()) - 1;
            if (index < 0 || index >= items.size()) {
                throw new IllegalArgumentException("Invalid index.");
            }

            String[] descParts = parts[1].split("q/", 2);
            Integer.parseInt(descParts[1].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Index and quantity must be numbers.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
