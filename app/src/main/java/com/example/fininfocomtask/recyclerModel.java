package com.example.fininfocomtask;

public class recyclerModel {
    private String Email;
    private String Number;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public recyclerModel(String email, String number) {
        Email = email;
        Number = number;
    }
}
