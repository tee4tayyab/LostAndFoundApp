package com.example.tayyab.lostandfoundapp.Event;

public class Event {
    private String decodedText;

    public Event(String decodedText) {
        this.decodedText = decodedText;
    }

    public String getDecodedText() {
        return decodedText;
    }

    public void setDecodedText(String decodedText) {
        this.decodedText = decodedText;
    }

    @Override
    public String toString() {
        return "Event{" +
                "decodedText='" + decodedText + '\'' +
                '}';
    }
}
