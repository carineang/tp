package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.person.descriptors.EditPersonDescriptor.createEditedPerson;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Pin;
import seedu.address.model.person.descriptors.EditPersonDescriptor;

/**
 * Unpins a person identified by the index number used in the last displayed person listing if the person was
 * previously pinned.
 */
public class UnpinCommand extends Command {

    public static final String COMMAND_WORD = "unpin";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unpins the person identified by the index number used in the last person listing if they were pinned\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNPIN_PERSON_SUCCESS = "Unpinned %1$d";

    private final Index index;

    private final Boolean isPinned;

    /**
     * Creates an UnpinCommand to unpin the specified person at the given index.
     *
     * @param index The index of the person to be unpinned.
     */
    public UnpinCommand(Index index) {
        requireAllNonNull(index);
        this.index = index;
        this.isPinned = false;
    }

    /**
     * Executes the unpin command, removing the pin status from the person if they were previously pinned.
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

        Person personToUnpin = lastShownList.get(index.getZeroBased());
        EditPersonDescriptor newDescriptor = new EditPersonDescriptor();
        Pin unPin = new Pin(isPinned);
        newDescriptor.setPin(unPin);
        Person editedPerson = createEditedPerson(personToUnpin, newDescriptor);

        model.setPerson(personToUnpin, editedPerson);
        model.unpinPerson(editedPerson);

        return new CommandResult(String.format(MESSAGE_UNPIN_PERSON_SUCCESS, index.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnpinCommand)) {
            return false;
        }

        UnpinCommand e = (UnpinCommand) other;
        return index.equals(e.index);
    }
}
