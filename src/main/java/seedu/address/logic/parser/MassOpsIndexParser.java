package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.StringUtil.isNonZeroUnsignedInteger;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Provides utility methods for parsing multiple 1-based indexes in command arguments,
 * which are values prefixed with {@link CliSyntax#PREFIX_MASS_OPS}
 * and formatted according to either {@link MassOpsIndexParser#SPACED_INDEX_PATTERN}
 * or {@link MassOpsIndexParser#RANGE_INDEX_PATTERN} patterns.
 */
public class MassOpsIndexParser {

    static final int MAX_OPERATIONS_SIZE = 100;
    static final String MESSAGE_RANGE_INDEX_CONSTRAINT = "Start index should be less than or equal to end index.";
    static final String MESSAGE_RANGE_CONSTRAINT =
            "Number of specified indices should not exceed " + MAX_OPERATIONS_SIZE
            + " indexes.";
    static final String MESSAGE_MASS_OPS_CONSTRAINTS = "Constraints:"
            + System.lineSeparator()
            + "1. Index should be a non-zero integer."
            + System.lineSeparator()
            + " Valid index formats: 1, 2, 3, ..., " + Integer.MAX_VALUE
            + System.lineSeparator()
            + "2. " + MESSAGE_RANGE_INDEX_CONSTRAINT
            + System.lineSeparator()
            + "3. " + MESSAGE_RANGE_CONSTRAINT;

    private static final Logger logger = LogsCenter.getLogger(MassOpsIndexParser.class);

    public static final String MESSAGE_RANGE_SIZE_EXCEEDED = "Number of specified indexes exceeded limit."
            + System.lineSeparator() + MESSAGE_MASS_OPS_CONSTRAINTS;

    public static final String MESSAGE_INVALID_INDEX = "Invalid mass-ops indexes specified."
            + System.lineSeparator() + MESSAGE_MASS_OPS_CONSTRAINTS;

    public static final String MESSAGE_INVALID_RANGE_ORDER = "Start index should not be greater than end index."
            + System.lineSeparator() + MESSAGE_MASS_OPS_CONSTRAINTS;

    // These two patterns should match mutually exclusively (both cannot be true at the same time).
    private static final Pattern SPACED_INDEX_PATTERN = Pattern.compile(
            "(?<indexes>\\d+(?:\\s+\\d+)*)");
    private static final Pattern RANGE_INDEX_PATTERN = Pattern.compile(
            "(?<startIndex>\\d+)\\s*-\\s*(?<endIndex>\\d+)"
    );



    /**
     * Parses the given string argument according to the spaced and ranged parsing formats. <br/>
     * Space format e.g.: "1 2 3 4 5", Range format: "1-200". <br/>
     * Returns a Set of Index objects for command execution.
     * Leading and trailing spaces are ignored in the matching.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public Set<Index> parseIndexes(String args) throws ParseException {
        requireNonNull(args);

        final Matcher spacedMatcher = SPACED_INDEX_PATTERN.matcher(args.trim());
        final Matcher rangedMatcher = RANGE_INDEX_PATTERN.matcher(args.trim());

        final boolean isSpacedMatch = spacedMatcher.matches();
        final boolean isRangedMatch = rangedMatcher.matches();

        if (!isSpacedMatch && !isRangedMatch) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        if (isSpacedMatch) {
            assert !isRangedMatch;
            return parseSpacedIndexes(spacedMatcher);
        }

        return parseRangedIndexes(rangedMatcher);
    }

    private void validateRangedIndex(int oneBasedStartIndex, int oneBasedEndIndex) throws ParseException {
        if (oneBasedStartIndex > oneBasedEndIndex) {
            throw new ParseException(MESSAGE_INVALID_RANGE_ORDER);
        }
        long numOperations = (long) oneBasedEndIndex - (long) oneBasedStartIndex + 1;
        logger.info("Number of operations: " + numOperations);
        if (numOperations > MAX_OPERATIONS_SIZE) {
            throw new ParseException(MESSAGE_RANGE_SIZE_EXCEEDED);
        }
    }

    private Set<Index> parseRangedIndexes(Matcher rangedMatcher) throws ParseException {
        requireNonNull(rangedMatcher);
        assert RANGE_INDEX_PATTERN.equals(rangedMatcher.pattern());

        String startIndexString = rangedMatcher.group("startIndex").trim();
        String endIndexString = rangedMatcher.group("endIndex").trim();

        final boolean isValidStartIndex = isNonZeroUnsignedInteger(startIndexString);
        final boolean isValidEndIndex = isNonZeroUnsignedInteger(endIndexString);

        if (!isValidStartIndex || !isValidEndIndex) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }

        final int oneBasedStartIndex = Integer.parseInt(startIndexString);
        final int oneBasedEndIndex = Integer.parseInt(endIndexString);
        logger.info("oneBasedStartIndex: " + oneBasedStartIndex + ", oneBasedEndIndex: " + oneBasedEndIndex);

        validateRangedIndex(oneBasedStartIndex, oneBasedEndIndex);

        Set<Index> indexes = new HashSet<>();

        for (int i = oneBasedStartIndex; i <= oneBasedEndIndex; i++) {
            logger.info("zero based: " + (i - 1));
            indexes.add(Index.fromOneBased(i));

            if (i == Integer.MAX_VALUE) {
                // Prevents overflow when i is incremented
                break;
            }
        }

        return indexes;
    }

    private Set<Index> parseSpacedIndexes(Matcher spacedMatcher) throws ParseException {
        requireNonNull(spacedMatcher);
        assert SPACED_INDEX_PATTERN.equals(spacedMatcher.pattern());

        String rawIndexes = spacedMatcher.group("indexes").trim();
        Collection<String> oneBasedIndexStrings = Arrays.asList(rawIndexes.split("\\s+"));

        Set<Index> indexes;
        try {
            indexes = ParserUtil.parseIndexes(oneBasedIndexStrings);
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_INDEX, pe);
        }

        if (indexes.size() > MAX_OPERATIONS_SIZE) {
            throw new ParseException(MESSAGE_RANGE_SIZE_EXCEEDED);
        } else {
            return indexes;
        }
    }
}
