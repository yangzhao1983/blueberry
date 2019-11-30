package zy;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by kaiser_zhao on 2018/7/28.
 */
public class TestStepUI {

    private final static String tagAction = "action";

    public static String getAction(Node node) {

        String action = "";
        String textContent = "";
        TestStepUI testStepUI = new TestStepUI();

        NodeList tcList = node.getChildNodes();

        for (int j = 0; j < tcList.getLength(); j++) {
            Node lsNode = tcList.item(j);

            if (lsNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) lsNode;
                textContent = elem.getTextContent();
                if (elem.getTagName().equalsIgnoreCase(tagAction)) {
                    action = textContent;

                }
            }

        }
        return action;
    }
}
