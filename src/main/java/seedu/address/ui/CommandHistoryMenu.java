package seedu.address.ui;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Represents the context menu UI for the history of entered user commands/inputs.
 */
public class CommandHistoryMenu extends UiPart<Region> {

    private static final String FXML = "CommandHistoryMenu.fxml";

    private final ObservableList<String> pastCommands;
    private final CommandBoxInputSetter commandSetter;

    private int selection = 0;

    @FXML
    private ListView<String> commandHistoryList;

    /**
     * Creates a {@code CommandHistoryMenu}
     * with the given {@code commandHistory} and {@code commandSetter}.
     *
     * @param commandHistory The list of previously entered command inputs.
     * @param commandSetter The setter for the command box input.
     */
    public CommandHistoryMenu(ObservableList<String> commandHistory, CommandBoxInputSetter commandSetter) {
        super(FXML);
        this.commandSetter = commandSetter;
        commandHistoryList.setItems(commandHistory);
        pastCommands = commandHistory;
    }

    /**
     * Handles the Ctrl + Up key pressed event.
     * Cycles up the list of previously entered commands.
     */
    public void onMovementUp() {
        if (pastCommands.isEmpty()) {
            return;
        }
        selection = (selection + 1) % pastCommands.size();
        commandSetter.setCommandInput(pastCommands.get(selection));
    }

    /**
     * Handles the Ctrl + Down key pressed event.
     * Cycles down the list of previously entered commands.
     */
    public void onMovementDown() {
        if (pastCommands.isEmpty()) {
            return;
        }
        selection = (pastCommands.size() + selection - 1) % pastCommands.size();
        assert selection >= 0 && selection < pastCommands.size();
        commandSetter.setCommandInput(pastCommands.get(selection));
    }

    /**
     * Represents the setter for the command box input.
     */
    @FunctionalInterface
    public interface CommandBoxInputSetter {
        /**
         * Sets the text in the command box to the given value provided.
         *
         * @param text The new text to set the command box input to.
         */
        void setCommandInput(String text);
    };
}
