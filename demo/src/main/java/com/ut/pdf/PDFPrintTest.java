package com.ut.pdf;

import java.io.File;

public class PDFPrintTest {
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
