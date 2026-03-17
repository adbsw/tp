package seedu.inventorybro.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.inventorybro.Item;
import seedu.inventorybro.ItemList;

public class AddCommand implements Command {
    private static final Pattern ADD_COMMAND_PATTERN = Pattern.compile("^addItem d/(.*?) q/(\\d+)$");

    private final String input;

    public AddCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(ItemList items) {
        Matcher matcher = ADD_COMMAND_PATTERN.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(
                    "Invalid addItem format! Use: addItem d/NAME q/INITIAL_QUANTITY"
            );
        }

        String name = matcher.group(1);
        int quantity = Integer.parseInt(matcher.group(2));
        items.addItem(new Item(name, quantity));
    }
}
