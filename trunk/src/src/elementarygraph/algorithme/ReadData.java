/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elementarygraph.algorithme;


import java.io.File;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 *
 * @author donnghi
 */
public class ReadData {

    public static String separe(String str) {
        String strReturn;
        int i;
        for (i = 0; i < str.length() - 1; i++) {
            if (str.charAt(i) == '=') {
                break;
            }
        }
        strReturn = str.substring(i + 2, str.length() - 1);

        return strReturn;
    }

    public static Vector getData(String argv) {
        Vector v = new Vector();
//        for (int i = 0; i < 100; i++) {
//            v.add(new Vector());
//        }
        try {
            File file = new File(argv);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
//            System.out.println("Root element " + doc.getDocumentElement().getNodeName());
            NodeList nodeLst = doc.getElementsByTagName("node");
//            System.out.println("Tong so nut trong cai graphe nay la : " + nodeLst.getLength());
//            System.out.println("-----------Khoi tao vector ----------------------");
//
//            System.out.println("----------------------khoi tao --------------------------");

            for (int s = 0; s < nodeLst.getLength(); s++) {
                v.add(new Vector());
//                System.out.println("----------------------------------------------");
                Node fstNode = nodeLst.item(s);
//                System.out.println("attributs 1 cua nut " + (s + 1) + " la: " + separe(fstNode.getAttributes().item(0).toString()));
//                System.out.println("attributs 2 cua nut " + (s + 1) + " la: " + separe(fstNode.getAttributes().item(1).toString()));
                ((Vector) v.get(s)).add(separe(fstNode.getAttributes().item(0).toString()));
                ((Vector) v.get(s)).add(separe(fstNode.getAttributes().item(1).toString()));
                if (fstNode.getChildNodes().getLength() != 0) {
//                    System.out.println("so nut con la " + (fstNode.getChildNodes().getLength() - 1) / 2);
                    for (int p = 0; p < (fstNode.getChildNodes().getLength() - 1) / 2; p++) {
//                        System.out.println("ten nut con thu " + (p + 1) + " la: " + fstNode.getChildNodes().item(p * 2 + 1).getNodeName());
//                        System.out.println("attributs cua nut con thu " + (p + 1) + " la: " + fstNode.getChildNodes().item(p * 2 + 1).getAttributes().item(0));
                        ((Vector) v.get(s)).add(separe(fstNode.getChildNodes().item(p * 2 + 1).getAttributes().item(0).toString()));
                    }
                } else {
//                    System.out.println("khong co nut con");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("thong bao loi: " + e.toString());
        }

        return v;

    }

}
