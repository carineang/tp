package seedu.address.ui.controller;

import java.util.function.Supplier;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Represents the controller for the command history menu ui component.
 */
public class CommandHistoryMenuController {
    static final int DEFAULT_SELECTION_INDEX = 0;

    final ObservableList<String> pastCommands;
    final CommandBoxInputSetter commandSetter;

    private int commandSelectionIndex = DEFAULT_SELECTION_INDEX;

    /**
     * Creates a {@code CommandHistoryMenuController}
     * with the given {@code commandHistory} and {@code commandSetter}.
     *
     * @param commandHistory The list of previously entered command inputs.
     * @param commandSetter The setter for the command box input.
     */
    public CommandHistoryMenuController(
            ObservableList<String> commandHistory, CommandBoxInputSetter commandSetter) {
        this.commandSetter = commandSetter;
        pastCommands = commandHistory;
    }

    /**
     * Moves the command history selection up.
     */
    public void moveUp() {
        if (pastCommands.isEmpty()) {
            return;
        }
        commandSelectionIndex = (commandSelectionIndex + 1) % pastCommands.size();
        setInputToSelection();
    }

    /**
     * Moves the command history selection down.
     */
    public void moveDown() {
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
        commandSelectionIndex = DEFAULT_SELECTION_INDEX;
    }

    private void setInputToSelection() {
        commandSetter.setCommandInput(pastCommands.get(commandSelectionIndex));
    }

    int getCommandSelectionIndex() {
        return commandSelectionIndex;
    }

    void setCommandSelectionIndex(int commandSelectionIndex) {
        this.commandSelectionIndex = commandSelectionIndex;
    }

    /**
     * Constructs and returns an event handler for the command history menu of
     * a given {@code CommandHistoryActionHandler}.
     *
     * @param handlerSupplier The supplier of the command history menu handler callbacks to use on a key press event.
     * @return An event handler for the command history menu.
     */
    public static EventHandler<KeyEvent> bakeEventHandler(Supplier<CommandHistoryActionHandler> handlerSupplier) {
        return event -> {
            boolean isPageUpAndCtrlDown = event.getCode() == KeyCode.UP && event.isControlDown();
            boolean isPageDownAndCtrlDown = event.getCode() == KeyCode.DOWN && event.isControlDown();
            boolean isEnterPressed = event.getCode() == KeyCode.ENTER;

            if (isPageUpAndCtrlDown) {
                handlerSupplier.get().handleMovementUp();
                event.consume();
                return;
            }
            if (isPageDownAndCtrlDown) {
                handlerSupplier.get().handleMovementDown();
                event.consume();
                return;
            }
            if (isEnterPressed) {
                handlerSupplier.get().handleEnterPressed();
            }
        };
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
    }
}
