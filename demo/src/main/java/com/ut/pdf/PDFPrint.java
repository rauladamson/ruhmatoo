package com.ut.pdf;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PDFPrint {
    /**
     *
     * @param pdfPath pdf faili asukoht failisüsteemis
     * @param startKeyword Sõne millest algab ridade väljastamine (k.a ka sõne ise)
     * @param endKeyword Sõne millega lõppeb väljastamine (see jääb välja)
     */
    public void printLinesFromKeywordToKeyword(String pdfPath, String startKeyword, String endKeyword) {

        try (InputStream pdfStream = new FileInputStream(pdfPath)) {
        
            System.out.println("-".repeat(5));
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
                            System.out.println(text);
                            
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
    }


    /**
     * Meetod on peamiselt testimiseks, et kuidas koodis pdf fail välja näeb ¯\_(ツ)_/¯
     * @param pdfPath pdf faili asukoht failisüsteemis.
     */
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