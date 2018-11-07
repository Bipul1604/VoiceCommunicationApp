package com.fancysoftwarehouse.voicecommunicationapp.getset;

public class insert {

    private String date,id;
    private String message;

    public insert() {
        //this constructor is required  
    }


    public insert(String date, String id, String message) {
        this.date = date;
        this.id = id;
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
 