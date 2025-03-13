package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NOTE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NOTE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.NoteCommand;

public class NoteCommandParserTest {
    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE);

    private NoteCommandParser parser = new NoteCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NOTE_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NOTE_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 monkaS", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 l/ brick", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_blankNote_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + NOTE_DESC_AMY;

        NoteCommand expectedCommand = new NoteCommand(targetIndex, VALID_NOTE_AMY);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_nonBlankNote_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + NOTE_DESC_BOB;

        NoteCommand expectedCommand = new NoteCommand(targetIndex, VALID_NOTE_BOB);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
