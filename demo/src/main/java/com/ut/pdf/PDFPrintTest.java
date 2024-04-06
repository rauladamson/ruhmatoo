package com.ut.pdf;

public class PDFPrintTest {
    public static void main(String[] args) {
        PDFPrint pdfPrint = new PDFPrint();
        String startKeyword = "Maht";
        String endKeyword = "Õpikeskkond";

        System.out.println(System.getProperty("user.dir")); // kontrolli kausta, kus oled

        // demo/src/main/resources/pdfs/ois1.pdf
        pdfPrint.printLinesFromKeywordToKeyword("/demo/src/main/resources/pdfs/ois1.pdf", startKeyword, endKeyword);
        pdfPrint.printLinesFromKeywordToKeyword("/demo/src/main/resources/pdfs/ois2.pdf", startKeyword, endKeyword);
        pdfPrint.printLinesFromKeywordToKeyword("/demo/src/main/resources/pdfs/ois3.pdf", startKeyword, endKeyword);
    }
}
