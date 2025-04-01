package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments to create a SortCommand and parsing logic identifies the sorting prefix.
 *
 */
public class SortCommandParser implements Parser<SortCommand> {
    private static final Logger logger = Logger.getLogger(SortCommandParser.class.getName());

    /**
     * Parses the given prefix string and returns a SortCommand.
     *
     * @param args The arguments string provided by the user.
     * @return SortCommand object based on the specified sorting attribute.
     * @throws ParseException Prefix is invalid or the arguments are not recognized.
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        String[] prefixes = trimmedArgs.split("\\s+");
        if (prefixes.length == 1) {
            if (isValidPrefix(prefixes[0])) {
                return new SortCommand(prefixes);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
            }
        }
        if (!prefixes[0].equals("t/")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        if (prefixes.length == 2) {
            if (!isValidPrefix(prefixes[1])) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
            }
        } else {
            throw new ParseException("Invalid number of prefixes.");
        }
        for (String prefix : prefixes) {
            if (!isValidPrefix(prefix)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
            }
        }
        if (args.contains("  ")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        logger.info("Sorting by multiple attributes: " + String.join(", ", prefixes));
        return new SortCommand(prefixes);
    }

    /**
     * Validates if a given prefix is a recognized sorting prefix.
     *
     * @param prefix The prefix string to validate.
     * @return True if the prefix is valid, false otherwise.
     */
    private boolean isValidPrefix(String prefix) {
        return prefix.equals("n/") || prefix.equals("p/") || prefix.equals("e/") || prefix.equals("a/")
                || prefix.equals("t/");
    }
}
