package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class HelpCommandParserTest {
    private final HelpCommandParser parser = new HelpCommandParser();

    @Test
    public void parse_emptyArgs_returnsHelpCommand() throws ParseException {
        // Test when there are no arguments (empty string or whitespace)
        HelpCommand expectedHelpCommand = new HelpCommand();
        HelpCommand parsedCommand = parser.parse("");
        assertEquals(expectedHelpCommand.getClass(), parsedCommand.getClass());

        parsedCommand = parser.parse("   ");
        assertEquals(expectedHelpCommand.getClass(), parsedCommand.getClass());
    }

    @Test
    public void parse_validCommand_returnsHelpCommandWithCommandName() throws ParseException {
        String commandName = "find";
        HelpCommand expectedHelpCommand = new HelpCommand(commandName);

        HelpCommand parsedCommand = parser.parse(commandName);
        assertEquals(expectedHelpCommand.getClass(), parsedCommand.getClass());
        assertEquals(commandName, parsedCommand.getCommandName());
    }
}
