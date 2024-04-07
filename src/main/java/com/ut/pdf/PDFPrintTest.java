package com.ut.pdf;

public class PDFPrintTest {
    public static void main(String[] args) {
        PDFPrint pdfPrint = new PDFPrint();
        String startKeyword = "Maht";
        String endKeyword = "Õpikeskkond";
        pdfPrint.getTextFromKeywordToKeyword("/pdfs/ois1.pdf", startKeyword, endKeyword);
        pdfPrint.getTextFromKeywordToKeyword("/pdfs/ois2.pdf", startKeyword, endKeyword);
        String a = pdfPrint.getTextFromKeywordToKeyword("/pdfs/ois3.pdf", startKeyword, endKeyword);
    }
}