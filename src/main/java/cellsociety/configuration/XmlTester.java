package cellsociety.configuration;

import java.util.List;
import java.util.Map.Entry;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * The XmlTester is a class for testing operations on objects of the XmlParser class, particularly
 * its readXml and writeXML methods.
 *
 * @author Judy He
 */
public class XmlTester {

  public static void main(String[] args) throws ParserConfigurationException, TransformerException {
    String path = "data/exception_tests/FireGridBoundsRandConfigTest.xml";

    XmlParser xmlParser = new XmlParser();
    xmlParser.readXml(path);

    System.out.println("Width: " + xmlParser.getWidth());
    System.out.println("Height: " + xmlParser.getHeight());
    for (Entry<String, Double> e : xmlParser.getParameters().entrySet()) {
      System.out.println(e.getKey() + ": " + e.getValue());
    }
    for (Entry<String, Integer> e : xmlParser.getRandomConfigurationTotalStates().entrySet()) {
      System.out.println(e.getKey() + ": " + e.getValue());
    }

    List<Integer> states = xmlParser.getStates();
    states.remove(states.size() - 1);
    states.add(1);

    xmlParser.createXml("testSavedXML", "exceptions_tests");

  }
}