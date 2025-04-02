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
        COMMAND_HELP.put(AddCommand.COMMAND_WORD, AddCommand.MESSAGE_USAGE);
        COMMAND_HELP.put(DeleteCommand.COMMAND_WORD, DeleteCommand.MESSAGE_USAGE);
        COMMAND_HELP.put(EditCommand.COMMAND_WORD, EditCommand.MESSAGE_USAGE);
        COMMAND_HELP.put(PinCommand.COMMAND_WORD, PinCommand.MESSAGE_USAGE);
        COMMAND_HELP.put(UnpinCommand.COMMAND_WORD, UnpinCommand.MESSAGE_USAGE);
        COMMAND_HELP.put(FindCommand.COMMAND_WORD, FindCommand.MESSAGE_USAGE);
        COMMAND_HELP.put(NoteCommand.COMMAND_WORD, NoteCommand.MESSAGE_USAGE);
        COMMAND_HELP.put(ViewNoteCommand.COMMAND_WORD, ViewNoteCommand.MESSAGE_USAGE);
        COMMAND_HELP.put(SortCommand.COMMAND_WORD, SortCommand.MESSAGE_USAGE);
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
