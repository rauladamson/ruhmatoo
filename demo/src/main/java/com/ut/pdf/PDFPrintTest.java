package com.ut.pdf;

import java.io.File;
import java.util.HashMap;

public class PDFPrintTest {

    private HashMap<String, String[]> inputsMap;

    public PDFPrintTest(String dirPath) {
        this.inputsMap = PDFPrintTest.readDirFiles(System.getProperty("user.dir") + "/src/main/resources/pdfs");
        
    }
    public HashMap<String, String[]> getInputsMap() {
        return inputsMap;
    }

    public static HashMap<String, String[]> readDirFiles(String dirPath) {
        HashMap<String, String[]> inputsMap = new HashMap<>();
        PDFPrint pdfPrint = new PDFPrint();

        String startKeyword = "Maht";
        String endKeyword = "Õpikeskkond";
        
        System.out.println(dirPath);
        File[] files =  new File(dirPath).listFiles();
        if (files != null) {
            for (File file : files) {
                inputsMap.put(file.getName(), new String[]{file.getAbsolutePath()});
                pdfPrint.printLinesFromKeywordToKeyword(file.getAbsolutePath(), startKeyword, endKeyword);
            }
        } else {
            System.out.println("The directory is empty or does not exist.");
        }
        return inputsMap;
    }


    public static void main(String[] args) {
        PDFPrint pdfPrint = new PDFPrint();
        String startKeyword = "Maht";
        String endKeyword = "Õpikeskkond";

        File[] files =  new File(System.getProperty("user.dir") + "/demo/src/main/resources/pdfs").listFiles();
        if (files != null) {
            for (File file : files) {
                //System.out.println(file.getName());
                //System.out.println(file.getAbsolutePath());
                pdfPrint.printLinesFromKeywordToKeyword(file.getAbsolutePath(), startKeyword, endKeyword);
            }
        } else {
            System.out.println("The directory is empty or does not exist.");
        }

        // demo/src/main/resources/pdfs/ois1.pdf
        /*pdfPrint.printLinesFromKeywordToKeyword(System.getProperty("user.dir") + "/demo/src/main/resources" + "/pdfs/ois1.pdf", startKeyword, endKeyword);
        pdfPrint.printLinesFromKeywordToKeyword("/pdfs/ois2.pdf", startKeyword, endKeyword);
        pdfPrint.printLinesFromKeywordToKeyword("/pdfs/ois3.pdf", startKeyword, endKeyword);*/
    }
}
