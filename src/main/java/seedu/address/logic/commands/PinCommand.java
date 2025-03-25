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
 * Pins a person identified by the index number used in the last displayed person listing.
 * The pinned person will always appear at the top of the list.
 */
public class PinCommand extends Command {

    public static final String COMMAND_WORD = "pin";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Pins the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_PIN_PERSON_SUCCESS = "Pinned %1$d";

    private final Index index;

    private final Boolean pin;

    /**
     * Creates a PinCommand to pin the specified person at the given index.
     *
     * @param index The index of the person to be pinned.
     */
    public PinCommand(Index index) {
        requireAllNonNull(index);
        this.index = index;
        this.pin = true;
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
        EditPersonDescriptor newDescriptor = new EditPersonDescriptor();
        Pin newPin = new Pin(pin);
        newDescriptor.setPin(newPin);
        Person editedPerson = createEditedPerson(personToPin, newDescriptor);

        model.setPerson(personToPin, editedPerson);
        model.pinPerson(editedPerson);

        return new CommandResult(String.format(MESSAGE_PIN_PERSON_SUCCESS, index.getOneBased()));
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
