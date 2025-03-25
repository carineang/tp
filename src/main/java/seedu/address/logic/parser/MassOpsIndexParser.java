package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.StringUtil.isNonZeroUnsignedInteger;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Provides utility methods for parsing multiple 1-based indexes in command arguments.
 */
public class MassOpsIndexParser {

    public static final int MAX_OPERATIONS_RANGE = 100;
    public static final String MESSAGE_OPS_RANGE_TOO_LARGE = "Index range is too large!";
    public static final String MESSAGE_INVALID_MASS_OPS_FORMAT = "Mass ops index format is invalid.";
    public static final String MESSAGE_INVALID_INDEX_RANGE = "Invalid index range specified!"
            + " Start index should be less than or equal to end index.";
    public static final String MESSAGE_INVALID_INDEX = "Invalid index specified. "
            + "Index should be a non-zero integer."
            + System.lineSeparator()
            + "Valid index formats: 1, 2, 3, ..., " + Integer.MAX_VALUE;


    // These two patterns should match mutually exclusively (both cannot be true at the same time).
    private static final Pattern SPACED_INDEX_PATTERN = Pattern.compile(
            "(?<indexes>\\d+(?:\\s+\\d+)*)");
    private static final Pattern RANGE_INDEX_PATTERN = Pattern.compile(
            "(?<startIndex>\\d+)\\s*-\\s*(?<endIndex>\\d+)"
    );

    /**
     * Parses the given {@code String} of arguments in the context of the MassOpsIndexParser
     * and returns a Set of Index objects for execution.
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
            throw new ParseException(MESSAGE_INVALID_MASS_OPS_FORMAT);
        }
        if (isSpacedMatch) {
            assert !isRangedMatch;
            return parseSpacedIndexes(spacedMatcher);
        }
        return parseRangedIndexes(rangedMatcher);

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

        if (oneBasedStartIndex > oneBasedEndIndex) {
            throw new ParseException(MESSAGE_INVALID_INDEX_RANGE);
        }

        if ((long) oneBasedEndIndex - (long) oneBasedStartIndex + 1 > MAX_OPERATIONS_RANGE) {
            throw new ParseException(MESSAGE_OPS_RANGE_TOO_LARGE);
        }

        Set<Index> indexes = new HashSet<>();
        for (int i = oneBasedStartIndex; i <= oneBasedEndIndex; i++) {
            indexes.add(Index.fromOneBased(i));
        }

        return indexes;
    }

    private Set<Index> parseSpacedIndexes(Matcher spacedMatcher) throws ParseException {
        requireNonNull(spacedMatcher);
        assert SPACED_INDEX_PATTERN.equals(spacedMatcher.pattern());

        String rawIndexes = spacedMatcher.group("indexes").trim();
        Collection<String> oneBasedIndexStrings = Arrays.asList(rawIndexes.split("\\s+"));
        return ParserUtil.parseIndexes(oneBasedIndexStrings);
    }
}
