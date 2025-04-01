package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class SortCommandParserTest {
    private SortCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new SortCommandParser();
    }

    @Test
    public void parse_validNamePrefix_success() throws ParseException {
        SortCommand command = parser.parse("n/");
        assertTrue(Arrays.equals(new String[] {"n/"}, command.getSortPrefix()));
    }

    @Test
    public void parse_validPhonePrefix_success() throws ParseException {
        SortCommand command = parser.parse("p/");
        assertTrue(Arrays.equals(new String[] {"p/"}, command.getSortPrefix()));
    }

    @Test
    public void parse_validEmailPrefix_success() throws ParseException {
        SortCommand command = parser.parse("e/");
        assertTrue(Arrays.equals(new String[] {"e/"}, command.getSortPrefix()));
    }

    @Test
    public void parse_validAddressPrefix_success() throws ParseException {
        SortCommand command = parser.parse("a/");
        assertTrue(Arrays.equals(new String[] {"a/"}, command.getSortPrefix()));
    }

    @Test
    public void parse_validTagPrefix_success() throws ParseException {
        SortCommand command = parser.parse("t/");
        assertTrue(Arrays.equals(new String[] {"t/"}, command.getSortPrefix()));
    }

    @Test
    public void parse_validMultiplePrefixes_success() throws ParseException {
        SortCommand command = parser.parse("t/ n/");
        assertTrue(Arrays.equals(new String[] {"t/", "n/"}, command.getSortPrefix()));
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parse("abc/");
        });
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE), exception.getMessage());
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parse("");
        });
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE), exception.getMessage());
    }
}
