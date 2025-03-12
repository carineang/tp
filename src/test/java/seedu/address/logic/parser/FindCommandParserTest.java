package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        List<String> keywords = Arrays.asList("Alice", "Bob");
        PersonContainsKeywordsPredicate expectedPredicate =
                new PersonContainsKeywordsPredicate(keywords, true, false, false, false, false);
        FindCommand expectedFindCommand = new FindCommand(expectedPredicate);
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_searchByPhone_returnsFindCommand() {
        List<String> keywords = Arrays.asList("12345678");
        PersonContainsKeywordsPredicate expectedPredicate =
                new PersonContainsKeywordsPredicate(keywords, false, true, false, false, false);
        FindCommand expectedFindCommand = new FindCommand(expectedPredicate);
        assertParseSuccess(parser, "find p/\"12345678\"", expectedFindCommand);
    }

    @Test
    public void parse_searchByEmail_returnsFindCommand() {
        List<String> keywords = Arrays.asList("alice@example.com");
        PersonContainsKeywordsPredicate expectedPredicate =
                new PersonContainsKeywordsPredicate(keywords, false, false, true, false, false);
        FindCommand expectedFindCommand = new FindCommand(expectedPredicate);
        assertParseSuccess(parser, "find e/\"alice@example.com\"", expectedFindCommand);
    }

    @Test
    public void parse_multipleFields_returnsFindCommand() {
        List<String> keywords = Arrays.asList("Alice", "12345678");
        PersonContainsKeywordsPredicate expectedPredicate =
                new PersonContainsKeywordsPredicate(keywords, true, true, false, false, false);
        FindCommand expectedFindCommand = new FindCommand(expectedPredicate);
        assertParseSuccess(parser, "find n/\"Alice\" p/\"12345678\"", expectedFindCommand);
    }
}
