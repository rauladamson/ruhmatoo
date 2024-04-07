package com.ut.pdf;

public class Oppeaine {

    private String text;

    public Oppeaine(String text) {
        this.text = text;
        
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.getText();
    }

}
