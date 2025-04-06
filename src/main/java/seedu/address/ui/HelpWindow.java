package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NoteCommand;
import seedu.address.logic.commands.PinCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UnpinCommand;
import seedu.address.logic.commands.ViewNoteCommand;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2425s2-cs2103t-t17-1.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Here are the available commands:\n"
            + "1. '" + AddCommand.COMMAND_WORD + "': Add a person.\n"
            + "2. '" + ExitCommand.COMMAND_WORD + "': Exit the program.\n"
            + "3. '" + FindCommand.COMMAND_WORD + "': Find a person based on keyword.\n"
            + "4. '" + SortCommand.COMMAND_WORD + "': Sort the list of persons.\n"
            + "5. '" + ListCommand.COMMAND_WORD + "': List all persons.\n"
            + "6. '" + DeleteCommand.COMMAND_WORD + "': Delete a person.\n"
            + "7. '" + EditCommand.COMMAND_WORD + "': Edit a person\n"
            + "8. '" + PinCommand.COMMAND_WORD + "': Pins a person.\n"
            + "9. '" + UnpinCommand.COMMAND_WORD + "': Unpins a person.\n"
            + "10. '" + NoteCommand.COMMAND_WORD + "': Change note of a person.\n"
            + "11. '" + ViewNoteCommand.COMMAND_WORD + "': view note of a person\n"
            + "12. '" + UndoCommand.COMMAND_WORD + "': Undoes the last command\n"
            + "13. '" + RedoCommand.COMMAND_WORD + "': Redoes the effect of the last undo\n"
            + "14. '" + ClearCommand.COMMAND_WORD + "': Clears all client contacts in the address book.\n"
            + "Use `help <command>` for detailed information about a specific command.\n\n"
            + "Use `Ctrl + Up` and `Ctrl + Down` arrow keys to cycle up and down the command history.\n"
            + "MacOS users: Use `Ctrl + Opt + Up` and `Ctrl + Opt + Down` instead.\n\n"
            + "Refer to the full user guide: " + USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
    }

    /**
     * Creates a new HelpWindow with a custom help message.
     *
     * @param root Stage to use as the root of the HelpWindow.
     * @param message The custom message to be displayed in the help window.
     */
    public HelpWindow(Stage root, String message) {
        super(FXML, root);
        helpMessage.setText(message);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
