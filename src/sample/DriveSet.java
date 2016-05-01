package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by JOHNY on 30.04.2016.
 */
public class DriveSet {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    private int id;
    private String date;
    private int number;

    private SimpleIntegerProperty idProperty;
    private SimpleIntegerProperty numberProperty;
    private SimpleStringProperty dateProperty;

    public DriveSet(int id, String date, int number) {
        this.id = id;
        this.date = date;
        this.number = number;
        this.idProperty = new SimpleIntegerProperty(this.id);
        this.dateProperty = new SimpleStringProperty(this.date);
        this.numberProperty = new SimpleIntegerProperty((this.number));
    }


    public StringProperty dateProperty() {
        return dateProperty;
    }

    public IntegerProperty numberProperty() {
        return numberProperty;
    }

    public IntegerProperty idProperty() {
        return idProperty;
    }



}
