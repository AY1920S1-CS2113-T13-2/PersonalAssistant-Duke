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
import duke.command.UpdatePatientCommand;
import duke.command.UpdateTaskCommand;
import duke.command.UndoCommand;

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
            return new AddPatientCommand(parser.parseAddPatient());
        case "add task":
            return new AddStandardTaskCommand(parser.parseAddTask());
        case "assign deadline task":
            return new AssignTaskToPatientCommand(parser.parseAssignDeadlineTask());
        case "assign event task":
            return new AssignTaskToPatientCommand(parser.parseAssignEventTask());
        case "list patients":
            return new ListPatientsCommand();
        case "list tasks":
            return new ListTasksCommand();
        case "delete assigned task":
            return new DeletePatientTaskCommand(parser.parseDeleteAssignedTask());
        case "delete patient":
            return new DeletePatientCommand(parser.parseDeletePatient());
        case "delete task":
            return new DeleteTaskCommand(parser.parseDeleteTask());
        case "find patient":
            return new FindPatientCommand((parser.parseFindPatient()));
        case "find assigned tasks":
            return new FindPatientTaskCommand((parser.parseFindAssignedTasks()));
        case "update patient":
            return new UpdatePatientCommand(parser.parseUpdatePatient());
        case "update task":
            return new UpdateTaskCommand(parser.parseUpdateTask());
        case "duke":
            return new DukeCommand();
        case "bye":
            return new ExitCommand();
        case "undo" :
            return new UndoCommand();
        default:
            throw new DukeException("Could not understand user input.");
        }
    }
}
