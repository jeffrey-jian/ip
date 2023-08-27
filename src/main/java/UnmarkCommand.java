public class UnmarkCommand extends Command {

    private int unmarkIndex;

    public UnmarkCommand(int unmarkIndex) {
        this.unmarkIndex = unmarkIndex;
    }

    @Override
    public void execute(TaskList items, Ui ui, Storage storage) throws DukeException {
        Task item = items.unmark(unmarkIndex);
        ui.unmarkItem(item.toString());
        storage.writeData(items.getItems());
    }
}
