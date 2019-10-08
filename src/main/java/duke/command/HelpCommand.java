package duke.command;

import duke.core.DukeException;
import duke.patient.PatientList;
import duke.storage.PatientStorage;
import duke.storage.Storage;
import duke.storage.TaskStorage;
import duke.task.TaskList;
import duke.core.Ui;

public class HelpCommand extends Command {

    @Override
    public void execute(TaskList tasks, PatientList patientList, Ui ui, TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException {
        ui.showHelpCommand();

    }

    @Override
    public boolean isExit() {
        return false;
    }
}
