package duke;

import duke.exceptions.*;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Creates a TaskList object from a reference input ArrayList<Task>
     * @param input ArrayList<Task>
     */
    public TaskList(ArrayList<Task> input) {
        this.tasks = input;
    }

    /**
     * Commands Ui class to print out the ArrayList<Task> in pretty UI
     */
    public void list() {
        //move Ui calls to Parser instead. Function is redundant.
        Ui.listPrint(tasks);
    }

    /**
     * Adds a Task item to the ArrayList<Task> accounting for the Type and Item
     * @param type Type of Task
     * @param item Additional Arguments for specified Task
     * @throws DukeException if inputs are missing or dates are incorrect
     */
    public void listAdd(String type, String item) throws DukeException {
        // atodo creates an empty task if no input after command (unresolved)
        // move Ui calls to Parser instead.
        Task currTask;
        String[] args;
        switch(type) {
            case "todo":
                currTask = new Todo(item);
                tasks.add(currTask);
                Ui.addTask("todo", currTask, tasks.size());
                Storage.save(tasks);
                break;
            case "deadline":
                args = item.split("/by ");
                try{
                    currTask = new Deadline(args[0], args[1]);
                    tasks.add(currTask);
                    Ui.addTask("deadline", currTask, tasks.size());
                    Storage.save(tasks);
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new DukeMissingInputException(type);
                } catch (DateTimeParseException e) {
                    throw new DukeUnknownDateException(type);
                }
                break;
            case "event":
                args = item.split("/at ");
                try{
                    currTask = new Event(args[0], args[1]);
                    tasks.add(currTask);
                    Ui.addTask("event", currTask, tasks.size());
                    Storage.save(tasks);
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new DukeMissingInputException(type);
                } catch (DateTimeParseException e) {
                    throw new DukeUnknownDateException(type);
                }
                break;
        }
    }

    /**
     * Deletes the Task item from the ArrayList<Task> at specified index (1-indexed)
     * @param indexString String representation of 1-indexed index
     * @throws DukeException if argument is of wrong format or OOB error
     */
    public void listDelete(String indexString) throws DukeException {
        int index = 0;
        try {
            index = Integer.parseInt(indexString) - 1;
        } catch (NumberFormatException e) {
            throw new DukeWrongInputException("delete");
        }
        if (index >= tasks.size() || index < 0) {
            throw new DukeListOOBException(index + 1);
        }
        Task currTask = tasks.remove(index);
        Ui.deleteTask(currTask, tasks.size());
        Storage.save(tasks);
    }

    /**
     * Toggles the Task item completion from ArrayList<Task> at specified index (1-indexed)
     * @param indexString String representation of 1-indexed index
     * @throws DukeException if argument is of wrong format or OOB error
     */
    public void listToggle(String indexString) throws DukeException{
        int index = 0;
        try {
            index = Integer.parseInt(indexString) - 1;
        } catch (NumberFormatException e) {
            throw new DukeWrongInputException("mark");
        }
        if (index >= tasks.size() || index < 0) {
            throw new DukeListOOBException(index + 1);
        }
        Task currTask = tasks.get(index);
        currTask.completeToggle();
        Ui.toggleTask(currTask);
        Storage.save(tasks);
    }

    /**
     * Calls Ui to print tasks containing regex in pretty UI
     * @param regex
     */
    public void find(String regex) {
        Ui.find(tasks, regex);
    }
}
