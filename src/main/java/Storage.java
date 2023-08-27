import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Storage {

    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws DukeException {
        ArrayList<Task> items = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineArr = line.split("\\|");

                String taskType = lineArr[0];
                boolean isDone = Integer.parseInt(lineArr[1]) == 1 ? true : false;
                String name = lineArr[2];
                switch (taskType) {
                case "T":
                    items.add(new ToDo(name, isDone));
                    break;
                case "D":
                    String by = lineArr[3];
                    items.add(new Deadline(name, LocalDateTime.parse(by), isDone));
                    break;
                case "E":
                    String from = lineArr[3];
                    String to = lineArr[4];
                    items.add(new Event(name, LocalDateTime.parse(from), LocalDateTime.parse(to), isDone));
                    break;
                default:
                    continue;
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new DukeException("OOPS!!! I am unable to read your duke.txt data file. Exiting....");
        }
        return items;
    }

    public void writeData(ArrayList<Task> items) throws DukeException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("ip/src/data/duke.txt"));
            for (Task t : items) {
                writer.write(t.toDataString());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            throw new DukeException("OOPS!!! I am unable to write to your duke.txt data file. Exiting....");
        }
    }
}