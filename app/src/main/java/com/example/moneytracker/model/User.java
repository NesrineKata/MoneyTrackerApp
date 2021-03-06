package com.example.moneytracker.model;
public class User {
    private String email;
    private String password;
    private String name;
    private String profilePic;

    public User(String email, String password, String name,String profilePic) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.profilePic=profilePic;
    }
    public User(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }
    public User() {
    }
    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}