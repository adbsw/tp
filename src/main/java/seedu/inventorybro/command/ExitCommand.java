package seedu.inventorybro.command;

import seedu.inventorybro.ItemList;

public class ExitCommand implements Command {
    @Override
    public void execute(ItemList items) {
        System.out.println("Bye! See you next time.");
        System.exit(0);
    }
}
