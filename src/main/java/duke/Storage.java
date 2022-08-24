package duke;

import java.io.*;
import java.util.ArrayList;

public class Storage {
    // Exceptions not handled
    private static final String FILENAME = "SaveData.txt";

    /**
     * Writes the ArrayList<Task> input to the specified save file
     * @param  array ArrayList<Task>
     */
    public void save(ArrayList<Task> array) {
        try {
            FileOutputStream writeData = new FileOutputStream(FILENAME);
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
            writeStream.writeObject(array);
            writeStream.flush();
            writeStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns an ArrayList<Task> retrieved from the specified save file
     * @return ArrayList<Task>
     */
    public ArrayList<Task> read() {
        try {
            FileInputStream readData = new FileInputStream(FILENAME);
            ObjectInputStream readStream = new ObjectInputStream(readData);
            ArrayList<Task> returnVal = (ArrayList<Task>) readStream.readObject();
            readStream.close();
            return returnVal;
        } catch (FileNotFoundException e) {
            return new ArrayList<Task>();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
