package com.example.FirsRestApp.util;

/**
 * этот класс будет отправлятся json-ном вместо ошибки
 */
public class PersonErrorResponse {
    private String massage;
    private long timestamp;

    public PersonErrorResponse(String massage, long timestamp) {
        this.massage = massage;
        this.timestamp = timestamp;
    }

    public String getMassage() {return massage;}
    public void setMassage(String massage) {this.massage = massage;}
    public long getTimestamp() {return timestamp;}
    public void setTimestamp(long timestamp) {this.timestamp = timestamp;}
}
