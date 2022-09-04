package cn.com.zerobug.demo.excel.core;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.monitorjbl.xlsx.exceptions.ReadException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.tomcat.util.bcel.Const;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static cn.com.zerobug.demo.excel.core.ExcelConstant.*;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/8/25
 */
public class ExcelReader extends DefaultHandler {

    private ReaderHandler<?> readerHandler;
    private SharedStringsTable sharedStringsTable;

    public ExcelReader(ReaderHandler<?> readerHandler) {
        this.readerHandler = readerHandler;
    }

    public void read(File file, int sheetIndex) {
        this.read(FileUtil.getInputStream(file), sheetIndex);
    }

    public void read(InputStream inputStream, int sheetIndex) {
        try (OPCPackage opcPackage = OPCPackage.open(inputStream)) {
            this.doRead(opcPackage, sheetIndex);
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }

    private void doRead(OPCPackage opcPackage, int sheetIndex) throws OpenXML4JException, IOException, ParserConfigurationException, SAXException {
        XSSFReader xssfReader = new XSSFReader(opcPackage);
        XMLReader parser = this.getSheetParser(xssfReader.getSharedStringsTable());
        try (InputStream sheetIs = xssfReader.getSheet("rId" + sheetIndex)) {
            parser.parse(new InputSource(sheetIs));
        }
    }

    private XMLReader getSheetParser(SharedStringsTable sharedStringsTable) throws SAXException, ParserConfigurationException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        XMLReader parser = saxParser.getXMLReader();
        this.sharedStringsTable = sharedStringsTable;
        parser.setContentHandler(this);
        return parser;
    }

    /**
     * @param uri        The Namespace URI, or the empty string if the
     *                   element has no Namespace URI or if Namespace
     *                   processing is not being performed.
     * @param localName  The local name (without prefix), or the
     *                   empty string if Namespace processing is not being
     *                   performed.
     * @param qName      The qualified name (with prefix), or the
     *                   empty string if qualified names are not available.
     * @param attributes The attributes attached to the element.  If
     *                   there are no attributes, it shall be an empty
     *                   Attributes object.
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (C_TAG.equals(qName)) {
            String r = attributes.getValue(R_TAG);
            String cellType = attributes.getValue(T_TAG);
            String cellStyleStr = attributes.getValue(S_TAG);
        }
    }

    /**
     * @param ch     The characters.
     * @param start  The start position in the character array.
     * @param length The number of characters to use from the
     *               character array.
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
    }

    /**
     * @param uri       The Namespace URI, or the empty string if the
     *                  element has no Namespace URI or if Namespace
     *                  processing is not being performed.
     * @param localName The local name (without prefix), or the
     *                  empty string if Namespace processing is not being
     *                  performed.
     * @param qName     The qualified name (with prefix), or the
     *                  empty string if qualified names are not available.
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
    }
}
