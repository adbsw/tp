package seedu.inventorybro.validator;

import seedu.inventorybro.ItemList;

/**
 * Contract for all input validators.
 * Implementations throw {@link IllegalArgumentException} when the input is invalid.
 */
public interface Validator {
    void validate(ItemList items);
}
