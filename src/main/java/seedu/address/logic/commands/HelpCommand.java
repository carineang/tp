package seedu.address.logic.commands;

import java.util.HashMap;
import java.util.Map;

import seedu.address.model.Model;


/**
 * Displays help information for the user.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String COMMAND_NOT_FOUND = "Unknown command! Use `help` to see available commands.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Usage: help [COMMAND]\n"
            + "  - `help` : Displays a list of available commands.\n"
            + "  - `help <command>` : Shows detailed usage instructions for the specified command.\n"
            + "Example: help find";

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    private static final Map<String, String> COMMAND_HELP;

    static {
        COMMAND_HELP = new HashMap<>();
        COMMAND_HELP.put(AddCommand.COMMAND_WORD, "Usage: add n/NAME p/PHONE e/EMAIL a/ADDRESS [t/TAG]...\n"
                + "Adds a person to the address book.\nExample: "
                + "add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25");
        COMMAND_HELP.put(DeleteCommand.COMMAND_WORD, "Usage: delete [i/]INDEX\n"
                + "Deletes the person(s) identified by the index number(s) used in the displayed person list.\n"
                + "Parameters: INDEX (must be a positive integer)\n"
                + "Example: delete 1\n"
                + "For deleting multiple persons at once:\n"
                + "1. (Spaced format) Parameters: i/INDEX1 INDEX2 INDEX3 ... INDEXN "
                + "(Each INDEX must be a positive integer.)\n"
                + "Example: delete i/1 2 3 4 5\n"
                + "2. (Ranged format) Parameters: i/START_INDEX-END_INDEX "
                + "(START_INDEX and END_INDEX must be a positive integer.)\n"
                + "Example: delete i/9-99");
        COMMAND_HELP.put(EditCommand.COMMAND_WORD, "Usage: edit INDEX "
                + "[n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…\n"
                + "Edits the information of person identified by the index number\n"
                + "Example: edit 1 p/91234567 e/johndoe@example.com");
        COMMAND_HELP.put(PinCommand.COMMAND_WORD, "Usage: pin INDEX\n"
                + "Pins the person identified in the index number.\n"
                + "Example: pin 1");
        COMMAND_HELP.put(UnpinCommand.COMMAND_WORD, "Usage: unpin INDEX\n"
                + "Unpins the person identified by the index number used "
                + "in the last person listing if they were pinned.\n"
                + "Example: unpin 1");
        COMMAND_HELP.put(FindCommand.COMMAND_WORD, "Usage: find [n/\"NAME\"] [p/\"PHONE\"] [e/\"EMAIL\"] "
                + "[a/\"ADDRESS\"] [t/\"TAG\"] \"KEYWORD\" [\"MORE_KEYWORDS\"]...\n"
                + "Finds all persons whose specified fields contain any of the given keywords (case-insensitive).\n"
                + "Use double quotation marks (\") around each input\n"
                + "Example: find n/\"Alice\" \"Bob\" p/\"98765432\" a/\"Bedok Central\" t/\"close friend\"");
        COMMAND_HELP.put(NoteCommand.COMMAND_WORD, "Usage: note INDEX nt/NOTE\n"
                + "Changes the note of the person identified by the index number used in the displayed person list.\n"
                + "Example: note 1 nt/Does not like to be called");
        COMMAND_HELP.put(ViewNoteCommand.COMMAND_WORD, "Usage: viewnote INDEX\n"
                + "Displays the note of the person identified by the index number used in the displayed person list.\n"
                + "Example: viewnote 2");
        COMMAND_HELP.put(SortCommand.COMMAND_WORD, "Usage: sort [n/] [p/] [e/] [a/] [t/]\n"
                + "Sorts all persons by the specified attribute and displays them as a list.\n"
                + "Example: sort n/");
        COMMAND_HELP.put(ListCommand.COMMAND_WORD, "Usage: list\n"
                + "Lists all contacts in the address book.");
        COMMAND_HELP.put(ExitCommand.COMMAND_WORD, "Usage: exit\n"
                + "Exits the program.");
        COMMAND_HELP.put(UndoCommand.COMMAND_WORD, "Usage: undo\n"
                + "Undoes the last command\n");
        COMMAND_HELP.put(RedoCommand.COMMAND_WORD, "Usage: redo\n"
                + "Redoes the effect of last undo\n");
    }

    private final String commandName;

    /**
     * Creates a HelpCommand with no arguments (shows all commands).
     */
    public HelpCommand() {
        this.commandName = null;
    }

    /**
     * Creates a HelpCommand for a specific command.
     *
     * @param commandName The name of the command to show help for.
     */
    public HelpCommand(String commandName) {
        this.commandName = commandName.toLowerCase();
    }

    /**
     * Executes the HelpCommand to display help information to the user.
     *
     * If no specific command is provided, it displays a general help message with a list of available commands.
     * If a specific command is provided, it shows detailed help information for that command.
     * If the command is not recognized, it informs the user that the command is unknown and suggests using `help`
     * to see all available commands.
     *
     * @param model The model object which contains the data of the application.
     * @return A CommandResult containing the help message to be shown to the user.
     *         The message will either be the list of commands, the details for a specific command,
     *         or an error message for an unknown command.
     */
    public CommandResult execute(Model model) {
        if (commandName == null) {
            return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        } else {
            String helpMessage = COMMAND_HELP.get(commandName);
            if (helpMessage != null) {
                return new CommandResult(helpMessage);
            } else {
                return new CommandResult(COMMAND_NOT_FOUND);
            }
        }
    }

    /**
     * Returns the command name for this HelpCommand.
     * If no command name was specified, it returns null.
     *
     * @return The command name as a String.
     */
    public String getCommandName() {
        return commandName;
    }
}
