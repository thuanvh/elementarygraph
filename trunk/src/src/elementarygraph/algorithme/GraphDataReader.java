/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elementarygraph.algorithme;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
//import java.util.Vector;
import java.util.*;
//import java.util.Iterator;

/**
 *
 * @author chichuot
 */
public class GraphDataReader {

  /**
   * @param args the command line arguments
   */
  public static String tach(String argm) {
    String str = new String();
    for (int i = 0; i < argm.length(); i++) {
      if (argm.charAt(i) == '=') {
        str = argm.substring(i + 2, argm.length() - 1);
      }
    }
    return str;
  }

  public static Integer ConvertStringToInt(String aString) {
    int aInt = Integer.parseInt(aString);
    return aInt;
  }

  public static boolean ConvertStringToBoolean(String aString) {
    if (aString.endsWith("0")) {
      return false;
    }
    return true;
  }

  public static boolean isEgalMyNode(elementarygraph.algorithme.Node node1, elementarygraph.algorithme.Node node2) {
    if (node1.index == node2.index) {
      return true;
    }
    return false;
  }

  public static Vector<Vector<elementarygraph.algorithme.Node>> getInfos(String path) throws Exception {
//elementarygraph.algorithme.Node nodeLevel0= new elementarygraph.algorithme.Node();
//Vector<elementarygraph.algorithme.Node> nodeLevel1 = new Vector<elementarygraph.algorithme.Node>();
    List<elementarygraph.algorithme.Node> listMyNode = new ArrayList<elementarygraph.algorithme.Node>();
    Vector<Vector<elementarygraph.algorithme.Node>> nodeLevel2 = new Vector<Vector<elementarygraph.algorithme.Node>>();
//List<elementarygraph.algorithme.Node> myList = new ArrayList<elementarygraph.algorithme.Node>();

    try {
      File file = new File(path);
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(file);
      doc.getDocumentElement().normalize();
      System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
      NodeList nodeLst = doc.getElementsByTagName("node");
      System.out.println("Information of all nodes:");

      // bat dau tim cac du lieu doi voi tung node trong do thi
      for (int s = 0; s < nodeLst.getLength(); s++) {
        Node fstNode = nodeLst.item(s);
        elementarygraph.algorithme.Node nodeLevel0 = new elementarygraph.algorithme.Node();
        nodeLevel0.setTitle(tach(fstNode.getAttributes().item(1).toString()));
        nodeLevel0.setIndex(ConvertStringToInt(tach(fstNode.getAttributes().item(0).toString())));
        nodeLevel0.setTrace(ConvertStringToBoolean(tach(fstNode.getAttributes().item(2).toString())));
        listMyNode.add(nodeLevel0);

      }

      for (int s = 0; s < nodeLst.getLength(); s++) {

        // Truoc tien la tim cac node ke voi mot node
        Vector<elementarygraph.algorithme.Node> nodeLevel1 = new Vector<elementarygraph.algorithme.Node>();
        Node fstNode = nodeLst.item(s);
        nodeLevel1.add(listMyNode.get(s));

        if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

          Element fstElmnt = (Element) fstNode;
          NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("child");
          Element fstNmElmnt;
          NodeList fstNm;
          for (int i = 0; i < fstNmElmntLst.getLength(); i++) {
            fstNmElmnt = (Element) fstNmElmntLst.item(i);
            fstNm = fstNmElmnt.getChildNodes();

            for (int j = 0; j < listMyNode.size(); j++) {
              if (fstNm.item(0).getNodeValue().equals(listMyNode.get(j).index.toString())) {
                nodeLevel1.add(listMyNode.get(j));
              }
            }

          }

          nodeLevel2.add(nodeLevel1);

        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return nodeLevel2;
  }

  public static void main(String[] args) throws Exception {
    Vector<Vector<elementarygraph.algorithme.Node>> resultat = new Vector<Vector<elementarygraph.algorithme.Node>>();
    String path = new String("/home/thuan/sandbox/elementarygraph/src/src/elementarygraph/algorithme/Node.xml");
    resultat = getInfos(path);
    System.out.println("nay thi vector 2 chieu, mk");
    for (int i = 0; i < resultat.size(); i++) {
      System.out.println("");
      for (int j = 0; j < resultat.get(i).size(); j++) {
        System.out.print(" " + resultat.get(i).get(j).index + " " + resultat.get(i).get(j).title);
      }
    }
    System.out.println();
  }
}


