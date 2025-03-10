package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.ui.controller.CommandHistoryActionHandler;
import seedu.address.ui.controller.CommandHistoryMenuController;
import seedu.address.ui.controller.CommandHistoryMenuController.CommandBoxInputSetter;

/**
 * Represents the context menu UI for the history of entered user commands/inputs.
 */
public class CommandHistoryMenu extends UiPart<Region> implements CommandHistoryActionHandler {

    private static final String FXML = "CommandHistoryMenu.fxml";

    /** Handles the action logic of the command history menu */
    private final CommandHistoryMenuController controller;

    @FXML
    private ListView<String> commandHistoryList;

    /**
     * Creates a {@code CommandHistoryMenu}
     * with the given {@code commandHistory} and {@code commandSetter}.
     *
     * @param commandHistory The list of previously entered command inputs.
     * @param commandSetter The setter for the command box input.
     */
    public CommandHistoryMenu(ObservableList<String> commandHistory,
                              CommandBoxInputSetter commandSetter) {
        super(FXML);
        commandHistoryList.setItems(commandHistory);
        controller = new CommandHistoryMenuController(commandHistory, commandSetter);
    }


    @Override
    public void handleMovementUp() {
        controller.moveUp();
    }

    @Override
    public void handleMovementDown() {
        controller.moveDown();
    }

    @Override
    public void handleEnterPressed() {
        controller.resetSelection();
    }

}
