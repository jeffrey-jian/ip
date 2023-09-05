package duke.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import duke.command.ByeCommand;
import duke.command.Command;
import duke.command.DeadlineCommand;
import duke.command.DeleteCommand;
import duke.command.EventCommand;
import duke.command.FindCommand;
import duke.command.ListCommand;
import duke.command.MarkCommand;
import duke.command.ToDoCommand;
import duke.command.UnmarkCommand;
import duke.exception.DukeException;

/**
 * The `Parser` class is responsible for parsing user input commands into executable commands.
 */
public class Parser {

    public static final DateTimeFormatter DATETIME_INPUT_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");

    /**
     * Parses a user input command and returns the corresponding `Command` object.
     *
     * @param command The user input command to be parsed.
     * @return A `Command` object representing the parsed command.
     * @throws DukeException If the command cannot be parsed or contains invalid arguments.
     */
    public static Command parse(String command) throws DukeException {
        String[] commandArr = command.split(" ", 2);
        String keyword = commandArr[0];
        String description;
        switch (keyword) {
        case "bye":
            return new ByeCommand();
        case "list":
            return new ListCommand();
        case "find":
            if (commandArr.length < 2) {
                throw new DukeException("\"OOPS!!! Please include the keyword you would like to search.");
            }
            String searchWord = commandArr[1].trim();
            return new FindCommand(searchWord);
        case "mark":
            if (commandArr.length < 2) {
                throw new DukeException("OOPS!!! Please include the task number you would like to mark.");
            }
            try {
                int markNumber = Integer.parseInt(commandArr[1].trim());
                return new MarkCommand(markNumber);
            } catch (NumberFormatException e) {
                throw new DukeException("OOPS!! Please indicate a number for the task.");
            }
        case "unmark":
            if (commandArr.length < 2) {
                throw new DukeException("OOPS!!! Please include the task number you would like to unmark.");
            }
            try {
                int unmarkNumber = Integer.parseInt(commandArr[1].trim());
                return new UnmarkCommand(unmarkNumber);
            } catch (NumberFormatException e) {
                throw new DukeException("OOPS!! Please indicate a number for the task.");
            }
        case "delete":
            if (commandArr.length < 2) {
                throw new DukeException("OOPS!!! Please include the task number you would like to delete.");
            }
            try {
                int deleteNumber = Integer.parseInt(commandArr[1].trim());
                return new DeleteCommand(deleteNumber);
            } catch (NumberFormatException e) {
                throw new DukeException("OOPS!! Please indicate a number for the task.");
            }
        case "todo":
            if (commandArr.length < 2) {
                throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
            }
            description = commandArr[1];
            return new ToDoCommand(description);
        case "deadline":
            if (commandArr.length < 2) {
                throw new DukeException("OOPS!!! The description of a deadline cannot be empty.");
            }
            description = commandArr[1];
            String deadlineName = "";
            String deadlineBy = "";
            String[] deadlineDescription = description.split("/");
            for (String str : deadlineDescription) {
                if (str.startsWith("by")) {
                    deadlineBy = str.split(" ", 2)[1].trim();
                } else {
                    deadlineName = str.trim();
                }
            }
            if (deadlineName.equals("")) {
                throw new DukeException("OOPS!! Please include the name of the deadline.");
            }
            if (deadlineBy.equals("")) {
                throw new DukeException("OOPS!! Please include when the deadline is by.");
            }
            try {
                LocalDateTime byParsed = LocalDateTime.parse(deadlineBy, DATETIME_INPUT_FORMAT);
                return new DeadlineCommand(deadlineName, byParsed);
            } catch (DateTimeParseException e) {
                throw new DukeException(DukeException.WRONG_DATETIME_MESSAGE);
            }
        case "event":
            if (commandArr.length < 2) {
                throw new DukeException("OOPS!!! The description of a deadline cannot be empty.");
            }
            description = commandArr[1];
            String eventName = "";
            String eventFrom = "";
            String eventTo = "";
            String[] eventDescription = description.split("/");
            for (String str : eventDescription) {
                if (str.startsWith("from")) {
                    eventFrom = str.split(" ", 2)[1].trim();
                } else if (str.startsWith("to")) {
                    eventTo = str.split(" ", 2)[1].trim();
                } else {
                    eventName = str.trim();
                }
            }
            if (eventName.equals("")) {
                throw new DukeException("OOPS!! Please include the name of the event.");
            }
            if (eventFrom.equals("")) {
                throw new DukeException("OOPS!! Please include when the event is from.");
            }
            if (eventTo.equals("")) {
                throw new DukeException("OOPS!! Please include when the event is till.");
            }
            try {
                LocalDateTime fromParsed = LocalDateTime.parse(eventFrom, DATETIME_INPUT_FORMAT);
                LocalDateTime toParsed = LocalDateTime.parse(eventTo, DATETIME_INPUT_FORMAT);
                return new EventCommand(eventName, fromParsed, toParsed);
            } catch (DateTimeParseException e) {
                throw new DukeException(DukeException.WRONG_DATETIME_MESSAGE);
            }
        default:
            throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
