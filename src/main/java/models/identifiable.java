package models;

public interface identifiable {
    int getId();
    int getLastId();
    void setId(int id);
    void incrementLastId();
}
