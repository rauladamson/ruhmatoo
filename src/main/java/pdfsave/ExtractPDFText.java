package pdfsave;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import org.apache.pdfbox.Loader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ExtractPDFText {
    public static void main(String[] args) {
        File pdfFile = new File("src/main/resources/pdfs/ois1.pdf");
        File outputFile = new File("src/main/resources/pdfsaves/output1.bin");

        try (PDDocument document = Loader.loadPDF(pdfFile);

             OutputStream outputStream = new FileOutputStream(outputFile)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            byte[] bytes = text.getBytes();
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}