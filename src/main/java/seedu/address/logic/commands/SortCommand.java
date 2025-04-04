package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_SORT_SUCCESSFUL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.logging.Logger;

import seedu.address.model.Model;

/**
 * Represents SortCommand to sort the address book based on specified attribute and update the model
 * to reflect the new sorting order.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sort all persons by the attribute specified "
            + "and displays them as a list with index numbers.\n"
            + "Acceptable prefixes: "
            + "[" + PREFIX_NAME + "] "
            + "[" + PREFIX_PHONE + "] "
            + "[" + PREFIX_EMAIL + "] "
            + "[" + PREFIX_ADDRESS + "] "
            + "[" + PREFIX_TAG + "] \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME;
    private static final Logger logger = Logger.getLogger(SortCommand.class.getName());
    private final String[] prefixes;

    /**
     * Constructs a SortCommand to sort the address book based on specified prefix.
     *
     * @param prefixes The prefixes indicating the attribute to sort by.
     */
    public SortCommand(String... prefixes) {
        if (prefixes == null || prefixes.length == 0) {
            throw new IllegalArgumentException("Prefixes cannot be null or empty");
        }
        this.prefixes = prefixes;
    }

    /**
     * Executes sort command by updating the filtered list of persons in the model
     * and sorting them based on the given prefix.
     *
     * @param model The model containing the address book data and the logic for sorting the address book.
     * @return The result of executing the sort command, containing a success message.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateSortedPersonList(prefixes);
        model.updateSortedFilteredPersonList(prefixes);

        model.commitAddressBook();

        logger.info("SortCommand execution completed. List sorted by: " + prefixes);

        return new CommandResult(MESSAGE_SORT_SUCCESSFUL);
    }

    /**
     * Returns the prefixes that are used to sort the address book.
     *
     * @return The array of prefixes specifying the attributes which the address book is sorted.
     */
    public String[] getSortPrefix() {
        return prefixes;
    }
}
