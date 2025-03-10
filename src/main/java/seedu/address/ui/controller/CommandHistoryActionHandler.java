package seedu.address.ui.controller;

/**
 * Represents the handler for key actions that can be performed on the command history menu.
 */
public interface CommandHistoryActionHandler {
    /**
     * Handles the Ctrl + Up key pressed event.
     * Cycles up the list of previously entered commands.
     */
    void handleMovementUp();

    /**
     * Handles the Ctrl + Down key pressed event.
     * Cycles down the list of previously entered commands.
     */
    void handleMovementDown();

    /**
     * Resets the selected input to the default index value.
     */
    void handleEnterPressed();
}
