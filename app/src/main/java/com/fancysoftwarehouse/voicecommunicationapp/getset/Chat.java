package com.fancysoftwarehouse.voicecommunicationapp.getset;

public class Chat {
    String message,user,date;


    public String getDate() {
        return date;
    }

    public void setTime(String Date) {
        this.date = date;
    }

    public Chat(String message, String user, String date) {
        this.message = message;
        this.user = user;
        this.date = date;

    }

    public Chat() {
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
