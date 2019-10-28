//@@lmtaek

package duke.core;

import duke.command.AddPatientCommand;
import duke.command.AddStandardTaskCommand;
import duke.command.AssignTaskToPatientCommand;
import duke.command.Command;
import duke.command.DeletePatientCommand;
import duke.command.DeletePatientTaskCommand;
import duke.command.DeleteTaskCommand;
import duke.command.DukeCommand;
import duke.command.ExitCommand;
import duke.command.FindPatientCommand;
import duke.command.FindPatientTaskCommand;
import duke.command.ListPatientsCommand;
import duke.command.ListTasksCommand;
import duke.command.UndoCommand;
import duke.command.UpdatePatientCommand;
import duke.command.UpdateTaskCommand;

/**
 * Represents a Parser that parses user input into a specific
 * type of Command.
 */
public class CommandManager {

    /**
     * Decides which command to execute based on keywords available in the user's input.
     *
     * @param userInput The user's input.
     * @return The command dictated by the user.
     */
    public static Command manageCommand(String userInput) throws DukeException {
        userInput = userInput.trim();
        String possibleCommand = TypoCorrector.commandCorrection(userInput);
        if (!possibleCommand.equals(userInput)) {
            if (Ui.getUi().confirmTypoCorrection(possibleCommand, userInput)) {
                userInput = possibleCommand;
            }
        }
        String[] command = userInput.toLowerCase().split(":");
        String keyWord = command[0].trim();

        Parser parser = new Parser(userInput);
        switch (keyWord) {
        case "add patient":
            try {
                return new AddPatientCommand(parser.parseUserInput());
            } catch (Exception e) {
                throw new DukeException("Please follow the  "
                        + "`add patient :<patient name> :<NRIC> :<patient room> :<patient_remark>` "
                        + "format.");
            }
        case "add task":
            try {
                return new AddStandardTaskCommand(parser.parseUserInput()[0]);
            } catch (Exception e) {
                throw new DukeException("Please follow the `add task :<task description>` format.");
            }
        case "assign deadline task":
            try {
                return new AssignTaskToPatientCommand(parser.parseUserInput());
            } catch (Exception e) {
                throw new DukeException("Please follow the "
                        + "`assign deadline task :<patient name> or #<patient id> :#<task id> or <task name> "
                        + ":<dd/MM/YYYY HHmm>` format.");
            }
        case "assign period task":
            try {
                return new AssignTaskToPatientCommand(parser.parseUserInput());
            } catch (Exception e) {
                throw new DukeException("Please follow the "
                        + "`assign event task :<patient name> or #<patient id> :#<task ID> or <task name> "
                        + ":<dd/MM/YYYY HHmm> to <dd/MM/YYYY HHmm>` format.");
            }
        case "list patients":
            return new ListPatientsCommand();
        case "list tasks":
            return new ListTasksCommand();
        case "delete assigned task":
            try {
                return new DeletePatientTaskCommand(parser.parseUserInput());
            } catch (Exception e) {
                throw new DukeException("Please follow the `delete assigned task :<patient name> or #<patient id>"
                        + " :<task name> or #<task id>` or "
                        + " `delete patient task :%<unique assigned task id>` format.");
            }
        case "delete patient":
            try {
                return new DeletePatientCommand(parser.parseUserInput()[0]);
            } catch (Exception e) {
                throw new DukeException("Please follow the `delete patient :<patient name> or #<patient id>` format");
            }
        case "delete task":
            try {
                return new DeleteTaskCommand(parser.parseUserInput()[0]);
            } catch (Exception e) {
                throw new DukeException("Please follow the `delete task :<task name> or #<task id>` format");
            }
        case "find patient":
            try {
                return new FindPatientCommand(parser.parseUserInput()[0]);
            } catch (Exception e) {
                throw new DukeException("Please use the `find patient :<patient name> or #<patient id>` format.");
            }
        case "find assigned tasks":
            try {
                return new FindPatientTaskCommand(parser.parseUserInput()[0]);
            } catch (Exception e) {
                throw new DukeException("Please use the `find assigned tasks "
                        + ":<patient name> or #<patient id>` format.");
            }
        case "update patient":
            try {
                return new UpdatePatientCommand(parser.parseUserInput());
            } catch (Exception e) {
                throw new DukeException("Please use the `update patient :<patient name> or #<patient id>"
                        + ":<edited info type> :<updated info>` format.");
            }
        case "update task":
            try {
                return new UpdateTaskCommand(parser.parseUserInput());
            } catch (Exception e) {
                throw new DukeException("Please use the `update task :<task name> or #<task id>"
                        + " :<updated description>` format.");
            }
        case "duke":
            return new DukeCommand();
        case "bye":
            return new ExitCommand();
        case "undo":
            return new UndoCommand();
        default:
            throw new DukeException("Could not understand user input.");
        }
    }
}
