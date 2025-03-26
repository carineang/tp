package seedu.address.logic.commands;

import java.util.HashMap;
import java.util.Map;

import seedu.address.model.Model;


/**
 * Displays help information for the user.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Usage: help [COMMAND]\n"
            + "  - `help` : Displays a list of available commands.\n"
            + "  - `help <command>` : Shows detailed usage instructions for the specified command.\n"
            + "Example: help find";

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    private static final Map<String, String> COMMAND_HELP;

    static {
        COMMAND_HELP = new HashMap<>();
        COMMAND_HELP.put("add", "Usage: add n/NAME p/PHONE e/EMAIL a/ADDRESS [t/TAG]...\n"
                + "Adds a person to the address book.\nExample: "
                + "add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25");
        COMMAND_HELP.put("delete", "Usage: delete i/INDEX\n"
                + "Deletes the person(s) identified by the index number(s) used in the displayed person list.\n"
                + "Example: delete i/2-5");
        COMMAND_HELP.put("find", "Usage: find [n/\"NAME\"] [p/\"PHONE\"] [e/\"EMAIL\"] "
                + "[a/\"ADDRESS\"] [t/\"TAG\"] \"KEYWORD\" [\"MORE_KEYWORDS\"]...\n"
                + "Finds all persons whose specified fields contain any of the given keywords (case-insensitive).\n"
                + "Use double quotation marks (\") around each input\n"
                + "Example: find n/\"Alice\" \"Bob\" p/\"98765432\" a/\"Bedok Central\" t/\"close friend\"");
        COMMAND_HELP.put("sort", "Usage: sort [n/] [p/] [e/] [a/] [t/]\n"
                + "Sorts all persons by the specified attribute and displays them as a list.\n"
                + "Example: sort n/");
        COMMAND_HELP.put("list", "Usage: list\n"
                + "Lists all contacts in the address book.");
        COMMAND_HELP.put("exit", "Usage: exit\n"
                + "Exits the program.");
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
     * @param commandName The name of the command to show help for.
     */
    public HelpCommand(String commandName) {
        this.commandName = commandName.toLowerCase();
    }

    /**
     * Executes the HelpCommand to display help information to the user.
     * <p>
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
                return new CommandResult("Unknown command: " + commandName
                        + ". Use `help` to see available commands.");
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
