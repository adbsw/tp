package seedu.inventorybro.command;

import seedu.inventorybro.ItemList;

public interface Command {
    void execute(ItemList items);
}
