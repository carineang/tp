package seedu.address.ui.controller;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import seedu.address.commons.core.LogsCenter;

/**
 * Represents the controller for the command history menu ui component.
 */
public final class CommandHistoryMenuController {
    static final int DEFAULT_SELECTION_INDEX = 0;
    private static final Logger logger = LogsCenter.getLogger(CommandHistoryMenuController.class);

    final ObservableList<String> pastCommands;
    final CommandBoxInputSetter commandSetter;


    private Optional<Integer> commandSelectionIndex;

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
        // initially, none are selected
        commandSelectionIndex = Optional.empty();
    }

    /**
     * Moves the command history selection down.
     */
    public void moveDown() {
        if (pastCommands.isEmpty()) {
            return;
        }
        commandSelectionIndex = commandSelectionIndex
                .map(previousSelectionIndex -> (previousSelectionIndex + 1) % pastCommands.size())
                .or(() -> Optional.of(DEFAULT_SELECTION_INDEX));
        setInputToSelection();
    }

    /**
     * Moves the command history selection up.
     */
    public void moveUp() {
        if (pastCommands.isEmpty()) {
            return;
        }
        commandSelectionIndex = commandSelectionIndex
                .map(previousSelectionIndex
                        -> (pastCommands.size() + previousSelectionIndex - 1) % pastCommands.size())
                .or(() -> Optional.of(DEFAULT_SELECTION_INDEX));
        setInputToSelection();
    }

    public void setSelection(int commandSelectionIndex) {
        logger.log(Level.INFO, "Got selection index: " + commandSelectionIndex);
        if (commandSelectionIndex < 0 || commandSelectionIndex >= pastCommands.size()) {
            return;
        }
        this.commandSelectionIndex = Optional.of(commandSelectionIndex);
        setInputToSelection();
    }

    public void clearSelection() {
        commandSelectionIndex = Optional.empty();
    }

    private void setInputToSelection() {
        commandSelectionIndex.ifPresent(index -> {
            commandSetter.setCommandInput(pastCommands.get(index));
        });
    }

    public Optional<Integer> getCommandSelectionIndex() {
        return commandSelectionIndex;
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
            boolean isEscPressed = event.getCode() == KeyCode.ESCAPE;

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
            if (isEnterPressed || isEscPressed) {
                handlerSupplier.get().handleCloseAction();
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
