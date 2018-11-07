package com.fancysoftwarehouse.voicecommunicationapp.getset;

public class Chat2 {
String message,user ;

    public Chat2(String message, String user) {
        this.message = message;
        this.user = user;
    }

    public Chat2() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
