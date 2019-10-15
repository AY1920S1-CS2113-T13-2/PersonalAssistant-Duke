package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.PatientManager;
import duke.relation.EventPatientTask;
import duke.relation.StandardPatientTask;
import duke.storage.PatientStorage;
import duke.storage.PatientTaskStorage;
import duke.storage.TaskStorage;
import duke.relation.PatientTask;
import duke.relation.PatientTaskList;
import duke.task.TaskManager;

public class AssignTaskToPatientCommand extends Command {

    private String command;
    private String[] taskAssignmentInfo;
    private PatientTask newPatientTask;

    public AssignTaskToPatientCommand(String[] taskAssignmentInfo) throws DukeException {
        super();
        this.taskAssignmentInfo = taskAssignmentInfo;
        this.newPatientTask = finalPatientTask(taskAssignmentInfo);
    }

    @Override
    public void execute(PatientTaskList patientTaskList, TaskManager tasksList, PatientManager patientList, Ui ui, PatientTaskStorage patientTaskStorage, TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException {
        if (patientList.isExist(newPatientTask.getPatientId()) && tasksList.isExist(newPatientTask.getTaskID()))
        {
            patientTaskList.addPatientTask(newPatientTask);
            patientTaskStorage.save(patientTaskList.fullPatientTaskList());
            ui.patientTaskAssigned(newPatientTask, patientList.getPatient(newPatientTask.getPatientId()).getName(), tasksList.getTask(newPatientTask.getTaskID()).getDescription());
        }
        else
        {
            throw new DukeException("Either the patient or the task does not exist in our data record");
        }

    }

    public PatientTask finalPatientTask(String[] assignmentInfo) throws DukeException {
        try {
            if (assignmentInfo[0].equals("S")) {

                String type = assignmentInfo[0];
                int patientId = Integer.parseInt(assignmentInfo[1]);
                int taskId = Integer.parseInt(assignmentInfo[2]);
                String deadline = assignmentInfo[3];

                StandardPatientTask standardPatientTask = new StandardPatientTask(patientId, taskId, deadline, type);
                return standardPatientTask;
            } else if (assignmentInfo[0].equals("E")) {

                String type = assignmentInfo[0];
                int patientId = Integer.parseInt(assignmentInfo[1]);
                int taskId = Integer.parseInt(assignmentInfo[2]);
                String startTime = assignmentInfo[3];
                String endTime = assignmentInfo[4];

                EventPatientTask eventPatientTask = new EventPatientTask(patientId, taskId, startTime, endTime, type);
                return eventPatientTask;
            } else {
                throw new DukeException("Parsing failed! Please ensure the format you have entered is correct!");
            }
        } catch (Exception e) {
            throw new DukeException("Unable to parse your task assignment. Please check your command's format!");
        }
    }
    @Override
    public boolean isExit() {
        return false;
    }
}