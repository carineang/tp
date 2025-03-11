package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.person.descriptors.EditPersonDescriptor.createEditedPerson;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.descriptors.EditPersonDescriptor;

/**
 * Changes a note of a {@code Person} identified using it's displayed index from the address book.
 * By default, a {@code Person} always has a note even if they don't enter any, it is just an empty String.
 */
public class NoteCommand extends Command {

    public static final String COMMAND_WORD = "note";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the note of the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "[" + PREFIX_NOTE + "Note] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NOTE + "Does not like to be called";

    public static final String MESSAGE_NOTE_PERSON_SUCCESS = "Changed Note: %1$s";

    private final Index targetIndex;
    private final String noteString;

    /**
     * @param targetIndex of the person in the filtered person list to change notes
     * @param noteString new value of the note
     */
    public NoteCommand(Index targetIndex, String noteString) {
        requireAllNonNull(targetIndex, noteString);

        this.targetIndex = targetIndex;
        this.noteString = noteString;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToNote = lastShownList.get(targetIndex.getZeroBased());
        EditPersonDescriptor newDescriptor = new EditPersonDescriptor();
        Note newNote = new Note(noteString);
        newDescriptor.setNote(newNote);
        Person editedPerson = createEditedPerson(personToNote, newDescriptor);

        model.setPerson(personToNote, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_NOTE_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NoteCommand)) {
            return false;
        }

        NoteCommand otherNoteCommand = (NoteCommand) other;
        return targetIndex.equals(otherNoteCommand.targetIndex)
                && noteString.equals(otherNoteCommand.noteString);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("noteString", noteString)
                .toString();
    }
}
