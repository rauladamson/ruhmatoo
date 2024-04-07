package com.ut.pdf;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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

    public static void main(String[] args) {


        try (InputStream pdfStream = new FileInputStream("message.txt")) {

            System.out.println(pdfStream.readAllBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
