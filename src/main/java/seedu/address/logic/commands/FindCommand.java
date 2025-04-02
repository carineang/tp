package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Finds and lists all persons in the address book whose details match any of the specified keywords.
 * The search can be performed on different fields such as name, phone number, email, address, or tags.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all persons whose specified fields contain any of "
            + "the given keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [n/\"NAME\"] [p/\"PHONE\"] [e/\"EMAIL\"]"
            + " [a/\"ADDRESS\"] [t/\"TAG\"] \"KEYWORD\" [\"MORE_KEYWORDS\"]...\n"
            + "Use double quotation marks (\") around multi-word inputs or single keywords to ensure correct parsing.\n"
            + "Example: " + COMMAND_WORD + " n/\"Alice\" \"Bob\" p/\"98765432\" a/\"Bedok Central\" t/\"close friend\"";

    private final PersonContainsKeywordsPredicate predicate;

    public FindCommand(PersonContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
