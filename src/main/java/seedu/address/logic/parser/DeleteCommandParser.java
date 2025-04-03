package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MASS_OPS;

import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    private final Logger logger = LogsCenter.getLogger(DeleteCommandParser.class);
    private final MassOpsIndexParser massOpsParser = new MassOpsIndexParser();

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MASS_OPS);
            // At most one such prefix is allowed
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_MASS_OPS);

            Optional<String> maybeMassOps = argMultimap.getValue(PREFIX_MASS_OPS);
            if (maybeMassOps.isEmpty()) {
                logger.info("Not a mass ops format.");
                Index index = ParserUtil.parseIndex(args);
                return new DeleteCommand(index);
            }

            String preamble = argMultimap.getPreamble();
            if (!preamble.isBlank()) {
                logger.info("Invalid preamble while parsing delete: " + preamble);
                throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
            }

            return new DeleteCommand(massOpsParser.parseIndexes(maybeMassOps.get()));
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            DeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}
