package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
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
        validatePrefixes(prefixes);
        checkForPrefixesLength(prefixes);
        checkForDuplicatePrefixes(prefixes);
        checkIfTagFirstPrefix(prefixes);
        checkForMultipleSpaces(args);
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

    /**
     * Checks whether the provided prefixes contain any duplicates.
     * If duplicates are found, a ParseException is thrown.
     *
     * @param prefixes The array of prefixes to check for duplicates.
     * @throws ParseException If duplicate prefixes are found.
     */
    private void checkForDuplicatePrefixes(String[] prefixes) throws ParseException {
        Set<String> prefixSet = new HashSet<>(Arrays.asList(prefixes));
        if (prefixes.length != prefixSet.size()) {
            throw new ParseException("Duplicate prefixes are not allowed.");
        }
    }

    /**
     * Checks if the first prefix is a tag. Throws a ParseException if it is not.
     *
     * @param prefixes The array of prefixes to check.
     * @throws ParseException If the first prefix is not "t/".
     */
    private void checkIfTagFirstPrefix(String[] prefixes) throws ParseException {
        if (!prefixes[0].equals("t/")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Checks if the input or prefixes contains consecutive spaces.
     * Throws a ParseException if multiple spaces are found.
     *
     * @param args The input string to check for multiple spaces.
     * @throws ParseException If multiple consecutive spaces are found in the string.
     */
    private void checkForMultipleSpaces(String args) throws ParseException {
        if (args.contains("  ")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Validates all prefixes in the given array. Throws a ParseException if any of the prefixes are invalid.
     *
     * @param prefixes The array of prefixes to validate.
     * @throws ParseException If any of the prefixes are invalid.
     */
    private void validatePrefixes(String[] prefixes) throws ParseException {
        for (String prefix : prefixes) {
            if (!isValidPrefix(prefix)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
            }
        }
    }

    /**
     * Checks if the input contains exactly two prefixes and if the second prefix is valid.
     * Throws a ParseException if the second prefix is invalid or if the number of prefixes is incorrect.
     *
     * @param prefixes The array of prefixes to check.
     * @throws ParseException If the length of prefixes is not 2 or if the second prefix is invalid.
     */
    private void checkForPrefixesLength(String[] prefixes) throws ParseException {
        if (prefixes.length > 2) {
            throw new ParseException("Invalid number of prefixes.");
        }
    }

}
