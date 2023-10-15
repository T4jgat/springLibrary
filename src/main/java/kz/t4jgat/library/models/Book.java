package kz.t4jgat.library.models;

public class Book {
    private int id;
    private String name;
    private String author_name;
    private int year;

    public Book() {}

    public Book(int id, String name, String author_name, int year) {
        this.id = id;
        this.name = name;
        this.author_name = author_name;
        this.year = year;
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

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
