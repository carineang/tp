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

    private int commandSelectionIndex = 0;

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
    public void handleMovementUp() {
        if (pastCommands.isEmpty()) {
            return;
        }
        commandSelectionIndex = (commandSelectionIndex + 1) % pastCommands.size();
        setInputToSelection();
    }

    /**
     * Handles the Ctrl + Down key pressed event.
     * Cycles down the list of previously entered commands.
     */
    public void handleMovementDown() {
        if (pastCommands.isEmpty()) {
            return;
        }
        commandSelectionIndex = (pastCommands.size() + commandSelectionIndex - 1) % pastCommands.size();
        assert commandSelectionIndex >= 0 && commandSelectionIndex < pastCommands.size();
        setInputToSelection();
    }

    /**
     * Resets the selected input to the default index value.
     */
    public void resetSelection() {
        commandSelectionIndex = 0;
    }

    private void setInputToSelection() {
        commandSetter.setCommandInput(pastCommands.get(commandSelectionIndex));
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
