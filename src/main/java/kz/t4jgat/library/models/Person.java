package kz.t4jgat.library.models;

import jakarta.validation.constraints.*;

public class Person {
    private int id;
    @NotEmpty(message = "Имя не может быть пустым")
    private String name;
    @NotNull(message = "Дата рождения обязательна!")
//    @Pattern(regexp = "0[1-9]|1[012]", message = "Wrong format!")
    @Min(value = 1900, message = "Такие люди больше не живут!")
    @Max(value = 2023, message = "Такой год ещё не наступил!")
    private int birth_year;

    public Person() {
    }

    public Person(int id, String name, int birth_year) {
        this.id = id;
        this.name = name;
        this.birth_year = birth_year;
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

    public int getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(int birth_year) {
        this.birth_year = birth_year;
    }
}
