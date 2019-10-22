package duke.core;

import duke.command.AddPatientCommand;
import duke.command.AddStandardTaskCommand;
import duke.command.AssignTaskToPatientCommand;
import duke.command.Command;
import duke.command.DeletePatientCommand;
import duke.command.DeletePatientTaskCommand;
import duke.command.DeleteTaskCommand;
import duke.command.ExitCommand;
import duke.command.FindPatientCommand;
import duke.command.FindPatientTaskCommand;
import duke.command.ListPatientsCommand;
import duke.command.ListTasksCommand;
import duke.command.UpdatePatientCommand;
import duke.command.UpdateTaskCommand;

/**
 * Represents a Parser that parses user input into a specific
 * type of Command.
 */
public class CommandManager {

    /**
     * Parses a Task from a string array.
     *
     * @param userInput The string array to be parsed.
     * @return The Command received from user.
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
            AddPatientCommand addPatientCommand = new AddPatientCommand(parser.parseAddPatient());
            return addPatientCommand;
        case "add task":
            AddStandardTaskCommand addStandardTaskCommand = new AddStandardTaskCommand(parser.parseAddTask());
            return addStandardTaskCommand;
        case "assign standard task":
            return new AssignTaskToPatientCommand(parser.parseAssignStandardTask());
        case "assign event task":
            return new AssignTaskToPatientCommand(parser.parseAssignEventTask());
        case "list patients":
            return new ListPatientsCommand();
        case "list tasks":
            return new ListTasksCommand();
        case "delete patient task":
            return new DeletePatientTaskCommand(parser.parseDeletePatientTask());
        case "delete patient":
            return new DeletePatientCommand(parser.parseDeletePatient());
        case "delete task":
            return new DeleteTaskCommand(parser.parseDeleteTask());
        case "find patient":
            return new FindPatientCommand((parser.parseFindPatient()));
        case "find patient tasks":
            return new FindPatientTaskCommand((parser.parseFindPatientTask()));
        case "update patient":
            return new UpdatePatientCommand(parser.parseUpdatePatient());
        case "update task":
            return new UpdateTaskCommand(parser.parseUpdateTask());
        case "bye":
            ExitCommand exitCommand = new ExitCommand();
            return exitCommand;
        default:
            throw new DukeException("Could not understand user input");
        }
    }
}
