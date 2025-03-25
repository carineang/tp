package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person(s) identified by the index number(s) used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)"
            + System.lineSeparator()
            + "Example: " + COMMAND_WORD + " 1"
            + System.lineSeparator()
            + "For deleting multiple persons at once:"
            + System.lineSeparator()
            + "1. (Spaced format) Parameters: m/INDEX1 INDEX2 INDEX3 ... INDEXN (Each INDEX must be a positive "
            + "integer.)"
            + System.lineSeparator()
            + " Example: " + COMMAND_WORD + " m/1 2 3 4 5"
            + System.lineSeparator()
            + "2. (Ranged format) Parameters: m/START_INDEX-END_INDEX (START_INDEX and END_INDEX "
            + "must be a positive integer.)"
            + System.lineSeparator()
            + " Example: " + COMMAND_WORD + " m/9-99";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person(s): "
            + System.lineSeparator()
            + "%1$s";

    /** Specifies the highest index to be the element at the start of {@code targetIndexes}. */
    private static final Integer HIGHEST_TARGET_INDEX_POSITION = 0;
    /**
     * This instance variable must obey the following constraints:
     * 1. Unique
     * 2. decreasing order (by 0-based index)
     * 3. Immutable
     * 4. Non-empty
     */
    private final List<Index> targetIndexes;

    public DeleteCommand(Index targetIndex) {
        this.targetIndexes = Collections.singletonList(targetIndex);
    }

    /**
     * Creates a delete command specifying multiple unique indexes to delete.
     *
     * @param targetIndexes The indexes of persons to be deleted.
     */
    public DeleteCommand(Set<Index> targetIndexes) {
        this.targetIndexes = List.copyOf(targetIndexes
                .stream()
                .sorted((index1, index2) -> index2.getZeroBased() - index1.getZeroBased())
                .toList());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        assert !targetIndexes.isEmpty();

        List<Person> lastShownList = model.getFilteredPersonList();
        // Delete operation fails (uncommited) if at least 1 index is out of range
        if (targetIndexes.get(HIGHEST_TARGET_INDEX_POSITION).getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        List<Person> deletedPersons = new ArrayList<>();
        for (Index targetIndex : targetIndexes) {
            Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deletePerson(personToDelete);

            deletedPersons.add(personToDelete);
        }

        Collections.reverse(deletedPersons);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(deletedPersons)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndexes.equals(otherDeleteCommand.targetIndexes);
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        for (Index targetIndex : targetIndexes) {
            builder.add("targetIndex", targetIndex);
        }
        return builder.toString();
    }
}
