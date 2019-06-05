package com.iText;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.attach.impl.DefaultTagWorkerFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.font.FontProvider;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author 徐其伟
 * @Description:
 * @date 19-3-11 下午2:52
 */
public class PdfService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PdfService.class);
    public static final String RESOURCE_PREFIX_INDEX = "/data/test";
    public static final String FONT_NAME = "msyh.ttf";
    private static final String FONT_CLASS_PATH = "font/msyh.ttf";

    public void copyFont(String dirStr) {
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(FONT_CLASS_PATH);
            File dir = new File(dirStr);
            FileUtils.forceMkdir(dir);
            FileOutputStream fout = new FileOutputStream(dirStr + "msyh.ttf");
            if (inputStream != null && fout != null) {
                org.apache.commons.io.IOUtils.copy(inputStream, fout);
            }
        } catch (Exception e) {
            LOGGER.error("failed to copy font: ", e);
            e.printStackTrace();
        }
    }

    public void createPdfFromHtml(String pdfFileName, InputStream htmlInputStream, String resourcePrefix) {
        PdfDocument pdfDoc = null;
        try {
            FileOutputStream outputStream = new FileOutputStream(resourcePrefix + pdfFileName);
            WriterProperties writerProperties = new WriterProperties();
            writerProperties.addXmpMetadata();
            PdfWriter pdfWriter = new PdfWriter(outputStream, writerProperties);
            pdfDoc = createPdfDoc(pdfWriter);
            ConverterProperties props = createConverterProperties(resourcePrefix);
            HtmlConverter.convertToPdf(htmlInputStream, pdfDoc, props);
        } catch (Exception e) {
            LOGGER.error("failed to create pdf from html exception: ", e);
            e.printStackTrace();
        } finally {
            pdfDoc.close();
//            IOUtils.closeQuietly(pdfDoc);
        }

    }
    private PdfDocument createPdfDoc(PdfWriter pdfWriter) {
        PdfDocument pdfDoc;
        pdfDoc = new PdfDocument(pdfWriter);
        pdfDoc.getCatalog().setLang(new PdfString("zh-CN"));
        pdfDoc.setTagged();
        pdfDoc.getCatalog().setViewerPreferences(new PdfViewerPreferences().setDisplayDocTitle(true));
        return pdfDoc;
    }
    private ConverterProperties createConverterProperties(String resourcePrefix) {
        ConverterProperties props = new ConverterProperties();
        props.setFontProvider(createFontProvider(resourcePrefix));
        props.setBaseUri(resourcePrefix);
        props.setCharset("UTF-8");
        DefaultTagWorkerFactory tagWorkerFactory = new DefaultTagWorkerFactory();
        props.setTagWorkerFactory(tagWorkerFactory);
        return props;
    }

    private FontProvider createFontProvider(String resourcePrefix) {
        FontProvider fp = new FontProvider();
        fp.addStandardPdfFonts();
        fp.addDirectory(resourcePrefix);
        return fp;
    }
}