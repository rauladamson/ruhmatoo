package com.ut.pdf;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PDFPrint {

    public String getTextFromKeywordToKeyword(String pdfPath, String startKeyword, String endKeyword) {
        StringBuilder textBuilder = new StringBuilder();

        try (InputStream pdfStream = getClass().getResourceAsStream(pdfPath)) {
            if (pdfStream == null) {
                throw new IOException("PDF file not found at path: " + pdfPath);
            }

            RandomAccessRead randomAccessRead = RandomAccessReadBuffer.createBufferFromStream(pdfStream);

            try (PDDocument document = Loader.loadPDF(randomAccessRead)) {
                PDFTextStripper pdfStripper = new PDFTextStripper() {
                    private boolean printing = false;

                    @Override
                    protected void writeString(String text, List<TextPosition> textPositions) {
                        if (text.contains(startKeyword)) {
                            printing = true;
                        }

                        if (printing && !text.contains(endKeyword)) {
                            textBuilder.append(text).append("\n");
                        }

                        if (text.contains(endKeyword)) {
                            printing = false;
                        }
                    }
                };
                pdfStripper.setSortByPosition(false);
                pdfStripper.getText(document);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return textBuilder.toString();
    }

    public void printEntirePDF(String pdfPath) {
        try (InputStream pdfStream = getClass().getResourceAsStream(pdfPath)) {
            if (pdfStream == null) {
                throw new IOException("PDF file not found at path: " + pdfPath);
            }

            RandomAccessRead randomAccessRead = RandomAccessReadBuffer.createBufferFromStream(pdfStream);

            try (PDDocument document = Loader.loadPDF(randomAccessRead)) {
                PDFTextStripper pdfStripper = new PDFTextStripper();
                String text = pdfStripper.getText(document);
                System.out.println("Text in PDF:\n---------------------------------");
                System.out.println(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}