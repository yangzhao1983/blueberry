package zy;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by kaiser_zhao on 2018/7/28.
 */
public class UseCase {

    private static Set<String> backList = new HashSet<>();
    static {

        backList.add("black1");
//        backList.add("");
//        backList.add("");
//        backList.add("");
//        backList.add("");
//        backList.add("");
//        backList.add("");
//        backList.add("");
//        backList.add("");
    }

    // For use case
    final static String tagUseCase = "useCase";
    final static String tagName = "name";
    final static String tagTestType = "testType";

    // For testCase
    final static String tagTestStepUI = "testStepUI";

    UseCaseHeader usecaseHeader;
    String testName;
    String tag;

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {

        return tag;
    }

    public UseCaseHeader getUsecaseHeader() {
        return usecaseHeader;
    }


    public String getTestName() {
        return testName;
    }

    public void setUsecaseHeader(UseCaseHeader usecaseHeader) {
        this.usecaseHeader = usecaseHeader;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public UseCase() {

        usecaseHeader = new UseCaseHeader();
        testName = null;
    }

    /**
     * Use case header.
     * For now, only two items.
     */
    public class UseCaseHeader {

        String testName;

        public UseCaseHeader() {
            testName = "";
        }

        public void setTestName(String value) {
            this.testName = value;
        }

        public String getTestName() {
            return this.testName;
        }
    }

    public static Set<String> extractTestCases(String xmlFile, Map<String, XmlCase> xmlCaseMap) {
        Set<String> actionSet = new HashSet<String>();

        String name = xmlFile;

        Set<String> actions = new HashSet<String>();

        int lastSlashIndex = xmlFile.lastIndexOf("/");

        String dir = xmlFile.substring(0, lastSlashIndex);
        String tag = "";

        try {
            // Parse the file TODO: need schema?
            Document doc = parseXml(xmlFile);

            NodeList nList = doc.getElementsByTagName(tagUseCase);

            if (nList.getLength() != 0) {
                // get the name and description of the useCase

                for (int iN = 0; iN < nList.getLength(); iN++) {
                    Node nNode = nList.item(iN);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element rElement = (Element) nNode;

                        //System.out.println("Analyse test case : rElement.getTagName(): " + rElement.getTagName());
                        NodeList ucList = nNode.getChildNodes();

                        for (int j = 0; j < ucList.getLength(); j++) {
                            Node lsNode = ucList.item(j);
                            if (lsNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element elem = (Element) lsNode;
                                if (elem.getTagName().contains(tagTestType)) {
                                    tag = removeSpace(elem.getTextContent());
                                } else if (elem.getTagName().contains(tagTestStepUI)) {
                                    String action = TestStepUI.getAction(lsNode);
                                    String actionNoSpace = removeSpace(action);
                                //    if(!backList.contains(actionNoSpace)) {
                                        actions.add(actionNoSpace);
                                //    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(" Test not executed " + xmlFile);
            ex.printStackTrace();
        }
        //System.out.println("name+dir+tag =" + name + "+" + dir + "+" + tag);
        XmlCase xmlCase = new XmlCase(tag, name, dir);
        xmlCaseMap.put(name, xmlCase);
        return actions;
    }

    private static Document parseXml(String xmlFile) {

        Document doc = null;
        File fxmlFile = new File(xmlFile);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        final SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fxmlFile);
        } catch (ParserConfigurationException ex) {
            System.err.println("parseXml failed with Exception " + ex.getMessage());
        } catch (SAXException ex) {
            System.err.println("parseXml failed with Exception  " + ex.getMessage());

        } catch (IOException ex) {
            System.err.println("parseXml failed with Exception " + ex.getMessage());
        }
        return doc;
    }

    private static String removeSpace(String value) {

        String newValue;
        newValue = value.replaceAll("\\s", ""); // renvoie une copie du String
        newValue = newValue.trim();

        return newValue;
    }
}
