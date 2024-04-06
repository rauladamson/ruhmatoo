package com.ut.pdf;

public class PDFPrintTest {
    public static void main(String[] args) {
        PDFPrint pdfPrint = new PDFPrint();
        String startKeyword = "Maht";
        String endKeyword = "Õpikeskkond";
        pdfPrint.printLinesFromKeywordToKeyword("/pdfs/ois1.pdf", startKeyword, endKeyword);
        pdfPrint.printLinesFromKeywordToKeyword("/pdfs/ois2.pdf", startKeyword, endKeyword);
        pdfPrint.printLinesFromKeywordToKeyword("/pdfs/ois3.pdf", startKeyword, endKeyword);
    }
}
