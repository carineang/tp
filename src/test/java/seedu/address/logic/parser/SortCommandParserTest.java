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
    public void parse_validMultiplePrefixesTagName_success() throws ParseException {
        SortCommand command = parser.parse("t/ n/");
        assertTrue(Arrays.equals(new String[] {"t/", "n/"}, command.getSortPrefix()));
    }

    @Test
    public void parse_validMultiplePrefixesTagPhone_success() throws ParseException {
        SortCommand command = parser.parse("t/ p/");
        assertTrue(Arrays.equals(new String[] {"t/", "p/"}, command.getSortPrefix()));
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

    @Test
    public void parse_invalidFirstPrefix_throwsParseException() {
        String input = "n/ p/";
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE), exception.getMessage());
    }

    @Test
    public void parse_invalidSecondPrefix_throwsParseException() {
        String input = "t/ x/";
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE), exception.getMessage());
    }

    @Test
    public void parse_invalidNumberOfPrefixes_throwsParseException() {
        String input = "t/ n/ p/";
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });
        assertEquals("Invalid number of prefixes.", exception.getMessage());
    }

    @Test
    public void parse_extraSpacesBetweenPrefixes_throwsParseException() {
        String input = "t/  n/";
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE), exception.getMessage());
    }

    @Test
    public void parse_duplicateNamePrefixes_throwsParseException() {
        String input = "n/ n/";
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });
        assertEquals("Duplicate prefixes are not allowed.", exception.getMessage());
    }

    @Test
    public void parse_duplicateTagPrefixes_throwsParseException() {
        String input = "t/ t/";
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });
        assertEquals("Duplicate prefixes are not allowed.", exception.getMessage());
    }

    @Test
    public void parse_invalidTwoPrefix_throwsParseException() {
        String input = "a/ b/";
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE), exception.getMessage());
    }

    @Test
    public void parse_duplicateThreeTagPrefixes_throwsParseException() {
        String input = "t/ t/ t/";
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });
        assertEquals("Invalid number of prefixes.", exception.getMessage());
    }

}
