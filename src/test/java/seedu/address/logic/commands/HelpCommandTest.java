package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_helpNoCommand_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpWithValidCommand_success() {
        // Create the HelpCommand for a specific command (e.g., "find")
        HelpCommand helpCommand = new HelpCommand("find");

        String expectedFeedback = "Usage: find [n/\"NAME\"] [p/\"PHONE\"] [e/\"EMAIL\"] "
                + "[a/\"ADDRESS\"] [t/\"TAG\"] \"KEYWORD\" [\"MORE_KEYWORDS\"]...\n"
                + "Finds all persons whose specified fields contain any of the given keywords (case-insensitive).\n"
                + "Use double quotation marks (\") around each input\n"
                + "Example: find n/\"Alice\" \"Bob\" p/\"98765432\" a/\"Bedok Central\" t/\"close friend\"";

        CommandResult result = helpCommand.execute(model);
        assertTrue(result.getFeedbackToUser().contains(expectedFeedback));
    }

    @Test
    public void execute_helpWithInvalidCommand_showsUnknownCommandMessage() {
        HelpCommand helpCommand = new HelpCommand("nonexistent");

        String expectedFeedback = "Unknown command! Use `help` to see available commands.";

        CommandResult result = helpCommand.execute(model);
        assertTrue(result.getFeedbackToUser().contains(expectedFeedback));
    }
}
