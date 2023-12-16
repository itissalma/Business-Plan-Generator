package models;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// filehandler to read and write objects that implement identifiable interface to json file
public class FileHandler<T extends identifiable> {
    private final String FILE_PATH;

    public FileHandler(String filePath) {
        this.FILE_PATH = filePath;
    }

    private void writeObjectsToJson(List<T> objectList) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            writer.println("[");
            for (T obj : objectList) {
                // convert object to json string
                String jsonEntry = convertObjectToJson(obj);
                writer.println(jsonEntry + (objectList.indexOf(obj) == objectList.size() - 1 ? "" : ","));
            }
            writer.println("]");
            System.out.println("Objects written to JSON file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing objects to JSON file.");
        }
    }

    private List<T> readObjectsFromJson() {
        // create file if not exists
        File file = new File(FILE_PATH);
        try {
            if (file.createNewFile()) {
                System.out.println("JSON file created successfully.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error creating JSON file.");
        }

        List<T> objectList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            String jsonString = jsonContent.toString().trim();
            // Remove leading and trailing square brackets to parse individual objects
            if (jsonString.startsWith("[")) jsonString = jsonString.substring(1);
            if (jsonString.endsWith("]")) jsonString = jsonString.substring(0, jsonString.length() - 1);

            if (jsonString.isEmpty()) {
                System.out.println("No objects found in JSON file.");
                return objectList;
            }
            // Split by "}," to separate individual user entries
            String[] jsonObjects = jsonString.split("\\},");

            for (String json : jsonObjects) {
                // convert json string to object if not empty
                if (json.isEmpty()) {
                    continue;
                }
                T obj = convertJsonToObject(json);
                objectList.add(obj);
            }

            System.out.println("Objects read from JSON file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error reading objects from JSON file.");
        }

        return objectList;
    }

    protected String convertObjectToJson(T obj) {
        // Implement in subclasses
        return null;
    }

    protected T convertJsonToObject(String json) {
        // Implement in subclasses
        return null;
    }

    public List<T> getObjects() {
        return readObjectsFromJson();
    }

    public void addObject(T obj) {
        List<T> objectList = getObjects();
        objectList.add(obj);
        obj.setId(obj.getLastId() + 1);
        obj.incrementLastId();
        writeObjectsToJson(objectList);
    }

    public void updateObject(T obj) {
        List<T> objectList = getObjects();
        int index = objectList.indexOf(obj);
        if (index != -1) {
            objectList.set(index, obj);
            writeObjectsToJson(objectList);
        } else {
            System.err.println("Object not found for updating.");
        }
    }

    public boolean deleteObject(T obj) {
        List<T> objectList = getObjects();
        // remove object from list by id
        // return true if object was removed, false otherwise
        boolean removed = objectList.removeIf(o -> o.getId() == obj.getId());
        if (removed) {
            writeObjectsToJson(objectList);
        } else {
            System.err.println("Object not found for deleting.");
        }
        return removed;
    }
}