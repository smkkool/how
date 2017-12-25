package model;

/**
 * Created by smkko on 12/17/2017.
 */

public class Phone {
    private int id;
    private String name;
    private double price;
    private String descreption;

    public Phone(int id, String name, double price, String descreption) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.descreption = descreption;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public String getDescreption() {
        return descreption;
    }

    public void setDescreption(String descreption) {
        this.descreption = descreption;
    }
}
