package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

public class ToDoCommand extends Command {

    private String name;

    public ToDoCommand(String name) {
        this.name = name;
    }

    @Override
    public void execute(TaskList items, Ui ui, Storage storage) throws DukeException {
        Task item = items.addToDo(name);
        ui.addItem(item.toString(), items.getCount());
        storage.writeData(items.getItems());
    }
}