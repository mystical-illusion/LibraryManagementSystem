package models;

public interface Book {
    void getDetails(); // display book info

    boolean isAvailable(); // check availability

    void setAvailable(boolean status); // update status

    String getBookId();

}
