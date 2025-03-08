package seedu.address.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents the data model for the collection of previously entered user inputs.
 * Only the {@link #MAX_HISTORY_SIZE} most recently entered inputs are considered.
 */
public class InputHistory {
    private static final int MAX_HISTORY_SIZE = 20;
    private final ObservableList<String> pastCommands;

    /**
     * Constructs an empty {@code InputHistory} object.
     */
    public InputHistory() {
        // Initialise an empty command history on load
        pastCommands = FXCollections.observableArrayList();
    }

    /**
     * Adds a new command input to the history.
     * If the history is full, the oldest command input is removed.
     *
     * @param commandInput The command input to be added.
     */
    public void addInput(String commandInput) {
        if (pastCommands.size() == MAX_HISTORY_SIZE) {
            pastCommands.remove(0);
        }
        pastCommands.add(commandInput);
    }

    /**
     * Returns the list of previously entered commands.
     */
    public ObservableList<String> getPastCommands() {
        return pastCommands;
    }
}
