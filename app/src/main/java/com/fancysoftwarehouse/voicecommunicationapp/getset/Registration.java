package com.fancysoftwarehouse.voicecommunicationapp.getset;

public class Registration {

    private String email, password;

    public Registration() {
        //this constructor is required

    }

    public Registration(String email, String password) {
        this.email = email;
        this.password = password;
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
