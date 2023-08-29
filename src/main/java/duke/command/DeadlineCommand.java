package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

import java.time.LocalDateTime;

public class DeadlineCommand extends Command {

    private String name;
    private LocalDateTime by;

    public DeadlineCommand(String name, LocalDateTime by) {
        this.name = name;
        this.by = by;
    }

    @Override
    public void execute(TaskList items, Ui ui, Storage storage) throws DukeException {
        Task item = items.addDeadline(name, by);
        ui.addItem(item.toString(), items.getCount());
        storage.writeData(items.getItems());
    }
}