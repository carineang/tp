package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Pins a person identified by the index number used in the last displayed person listing.
 * The pinned person will always appear at the top of the list.
 */
public class PinCommand extends Command {

    public static final String COMMAND_WORD = "pin";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Pins the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Pin command not implemented yet";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d";

    private final Index index;

    /**
     * Creates a PinCommand to pin the specified person at the given index.
     *
     * @param index The index of the person to be pinned.
     */
    public PinCommand(Index index) {
        requireAllNonNull(index);
        this.index = index;
    }

    /**
     * Executes the pin command, moving the selected person to the top of the list.
     *
     * @param model The model containing the list of persons.
     * @return A CommandResult indicating the success of the command.
     * @throws CommandException If the index is out of bounds.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToPin = lastShownList.get(index.getZeroBased());
        model.pinPerson(personToPin);
        throw new CommandException(
                String.format(MESSAGE_ARGUMENTS, index.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PinCommand)) {
            return false;
        }

        PinCommand e = (PinCommand) other;
        return index.equals(e.index);
    }
}
