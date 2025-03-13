package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static final Pattern QUOTED_KEYWORD_PATTERN = Pattern.compile("\"([^\"]+)\"");

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        boolean searchName = argMultimap.getValue(PREFIX_NAME).isPresent();
        boolean searchPhone = argMultimap.getValue(PREFIX_PHONE).isPresent();
        boolean searchEmail = argMultimap.getValue(PREFIX_EMAIL).isPresent();
        boolean searchAddress = argMultimap.getValue(PREFIX_ADDRESS).isPresent();
        boolean searchTags = argMultimap.getValue(PREFIX_TAG).isPresent();

        List<String> keywords = new ArrayList<>();

        extractKeywords(argMultimap, PREFIX_NAME, searchName, keywords);
        extractKeywords(argMultimap, PREFIX_PHONE, searchPhone, keywords);
        extractKeywords(argMultimap, PREFIX_EMAIL, searchEmail, keywords);
        extractKeywords(argMultimap, PREFIX_ADDRESS, searchAddress, keywords);
        extractKeywords(argMultimap, PREFIX_TAG, searchTags, keywords);


        // Default to searching by name if no field is specified
        if (!searchName && !searchPhone && !searchEmail && !searchAddress && !searchTags) {
            searchName = true;
            String preamble = argMultimap.getPreamble().trim();
            if (!preamble.isEmpty()) {
                keywords = Arrays.asList(preamble.split("\\s+"));
            }
        }
        System.out.println("HELLLLOOOOOO:" + keywords);

        if (keywords.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new PersonContainsKeywordsPredicate(keywords, searchName,
                searchPhone, searchEmail, searchAddress, searchTags));
    }

    /**
     * Extracts keywords from the argument map
     * Differentiate between a full phrase (inside quotes) and multiple keywords.
     */
    private void extractKeywords(ArgumentMultimap argMultimap, Prefix prefix,
                                 boolean isPresent, List<String> keywords) {
        if (isPresent && !argMultimap.getValue(prefix).get().isEmpty()) {
            String input = (prefix == null) ? argMultimap.getPreamble() : argMultimap.getValue(prefix).get();
            Matcher matcher = QUOTED_KEYWORD_PATTERN.matcher(input);
            while (matcher.find()) {
                keywords.add(matcher.group(1));
            }
        }
    }
}
