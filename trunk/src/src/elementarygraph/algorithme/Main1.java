/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elementarygraph.algorithme;

import java.util.Queue;
import java.awt.*;
import java.io.File;
import javax.swing.*;
import java.util.Vector;

/**
 *
 * @author donnghi
 */
public class Main1 {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    try {
      // TODO code application logic here
//    Graph graph = new Graph();
//    graph.test();

      Graph graphAlgo = new Graph();

      Vector<Vector<Node>> gr = GraphDataReader.getInfos("/home/thuan/sandbox/elementarygraph/src/data/Node.xml");//(Vector<Vector<Node>>)graphAlgo.data.clone();//oral.matrixNode;

//      if (gr.size() > 0) {
//        gr.remove(0);
//      }

      //graphAlgo.data = (Vector<Vector<Node>>) gr.clone();
      Vector<Node> a = new Vector<Node>();
      a.add(new Node());
      gr.add(0,a);

      graphAlgo.data = gr;
      graphAlgo.setSize(gr.size() - 1);

      for (int i = 0; i < gr.size(); i++) {
        System.out.println("");
        for (int j = 0; j < gr.get(i).size(); j++) {
          System.out.print(" " + gr.get(i).get(j).index + " " + gr.get(i).get(j).title);

          System.out.println();
        }
      }
      graphAlgo.test();
      
    } catch (Exception e) {
      System.out.println(e.toString());
      e.printStackTrace();
    }
  }
}
