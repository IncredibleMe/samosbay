package com.example.jack.samost;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user")
public class User {
    @DatabaseField(generatedId  = true)
    private int id;
    @DatabaseField
    private String username;
    @DatabaseField
    private String fname;
    @DatabaseField
    private String lname;
    @DatabaseField
    private int rating;
    @DatabaseField
    private String email;
    @DatabaseField
    private String password;

    public User() {
    }

    public User(int id, String username, String fname, String lname, int rating, String email, String password) {
        this.id = id;
        this.username = username;
        this.fname = fname;
        this.lname = lname;
        this.rating = rating;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
