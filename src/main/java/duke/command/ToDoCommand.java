package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * The `ToDoCommand` class represents a command to add a to-do task to the task list.
 */
public class ToDoCommand extends Command {

    private String name;

    /**
     * Constructs a new `ToDoCommand` with the specified name for the to-do task.
     *
     * @param name The name of the to-do task.
     */
    public ToDoCommand(String name) {
        assert(name != null && name.isEmpty());
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(TaskList items, Ui ui, Storage storage) throws DukeException {
        Task item = items.addToDo(name);
        storage.writeData(items);
        return ui.addItem(item.toString(), items.getCount());
    }
}
