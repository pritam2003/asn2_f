package pritam.asn2_f.models;


import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment
    private int uid;

    private String name;
    private int weight;
    private int height;
    private String hair;
    private double gpa;
    private String colour;
    public Student() {
    }
    public Student(String name, int weight, int height, String hair, double gpa, String colour) {
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.hair = hair;
        this.gpa = gpa;
        this.colour = colour;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public String getHair() {
        return hair;
    }
    public void setHair(String hair) {
        this.hair = hair;
    }
    public double getGpa() {
        return gpa;
    }
    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
    public String getColour() {
        return colour;
    }
    public void setColour(String colour) {
        this.colour = colour;
    }
}