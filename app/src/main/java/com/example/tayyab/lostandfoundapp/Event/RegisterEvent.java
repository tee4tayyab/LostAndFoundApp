package com.example.tayyab.lostandfoundapp.Event;

public class RegisterEvent {
    private int RegisterID;

    public RegisterEvent(int registerID) {
        RegisterID = registerID;
    }

    public int getRegisterID() {
        return RegisterID;
    }

    public void setRegisterID(int registerID) {
        RegisterID = registerID;
    }

}
