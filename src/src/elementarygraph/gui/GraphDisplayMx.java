package elementarygraph.gui;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author thuan
 */
import elementarygraph.algorithme.*;

//import com.mxgraph.*;
import com.mxgraph.model.*;
//import com.mxgraph.event.GraphSelectionEvent;
//import com.mxgraph.event.GraphSelectionListener;
import com.mxgraph.view.*;
import com.mxgraph.layout.*;
import com.mxgraph.swing.mxGraphComponent;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Map;
import java.util.Vector;

public class GraphDisplayMx extends JScrollPane {

  //~ Static fields/initializers ---------------------------------------------
  private static final long serialVersionUID = 3256444702936019250L;
  private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
  private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);
  private static final int nodePaddingLeft = 100;
  private static final int nodePaddingTop = 70;
  //~ Instance fields --------------------------------------------------------
  public Vector<Node> nodeList;
  public Vector<Vector<Node>> graph;
  public mxGraphView graphView;
  public mxGraph graphMx;
  public Object parent;
  //

  public void init(Vector<Vector<Node>> graph) {
    //mxGraphModel model = new mxGraphModel();
    graphMx = new mxGraph();
    graphView = new mxGraphView(graphMx);
    parent = graphMx.getDefaultParent();
//    graphView.setEditable(false);
//    graphView.setConnectable(false);
//    graphView.setDisconnectable(false);
    //graphView.set

    // Init graph model
    this.graph = graph;
    this.nodeList = new Vector<Node>();
    int i = 0, j = 0;
    for (i = 0; i < graph.size(); i++) {
      for (j = 0; j < graph.get(i).size(); j++) {
        System.out.println(graph.get(i).get(j));
        if (!nodeList.contains(graph.get(i).get(j))) {
          nodeList.add(graph.get(i).get(j));
          System.out.print(graph.get(i).get(j));
        }
      }
    }
    displayGraph();

    mxGraphComponent graphcom = new mxGraphComponent(graphMx);
    this.add(graphcom);


  }

  private void displayGraph() {
    graphMx.getModel().beginUpdate();
    try {

      int i = 0;
      int j = 0;
      mxCell[] nodeViewList = new mxCell[nodeList.size()];
      for (i = 0; i < nodeList.size(); i++) {        
        mxCell cell = (mxCell)graphMx.insertVertex(parent, nodeList.get(i).getTitle(), nodeList.get(i), 50, 20, 80,30);
        cell.setStyle("ROUNDED;strokeColor=red;fillColor=green;width=40;height=50");
        nodeViewList[i] = (mxCell) cell;
      }
      Vector<mxCell> edgeViewVector = new Vector<mxCell>();
      for (i = 0; i < graph.size(); i++) {
        int srcNodePosition = nodeList.indexOf(graph.get(i).get(0));
        for (j = 1; j < graph.get(i).size(); j++) {
          int destNodePosition = nodeList.indexOf(graph.get(i).get(j));
          Object edge = graphMx.insertEdge(parent, null, "", nodeViewList[srcNodePosition], nodeViewList[destNodePosition]);
          edgeViewVector.add((mxCell) edge);
        }
      }

      // calculate level of each node
      int[] nodeListLevel = new int[nodeList.size()];
      for (i = 0; i < graph.size(); i++) {
        int srcNodePosition = nodeList.indexOf(graph.get(i).get(0));

        for (j = 1; j < graph.get(i).size(); j++) {
          int destNodePosition = nodeList.indexOf(graph.get(i).get(j));
          if (nodeListLevel[srcNodePosition] >= nodeListLevel[destNodePosition]) {
            nodeListLevel[destNodePosition] = nodeListLevel[srcNodePosition] + 1;
          }
        }
      }
      int maxLevel = 0;
      mxCell root = null;
      for (i = 0; i < nodeListLevel.length; i++) {
        if (nodeListLevel[i] == 0) {
          root = nodeViewList[i];
        }
        if (maxLevel < nodeListLevel[i]) {
          maxLevel = nodeListLevel[i];
        }
      }

     mxCircleLayout layout=new mxCircleLayout(graphMx);
     layout.execute(parent);
    } finally {
      graphMx.getModel().endUpdate();
    }
  }

  public static void main(String[] args) {
    GraphDisplayMx applet = new GraphDisplayMx();
    Node node1 = new Node("a", 1);
    Node node2 = new Node("b", 2);
    Node node3 = new Node("c", 3);
    Node node4 = new Node("d", 4);
    //node3.setSlack(5);
    Vector<Vector<Node>> graph = new Vector<Vector<Node>>();

    Vector<Node> link1 = new Vector<Node>();
    link1.add(node1);
    link1.add(node2);
    graph.add(link1);

    Vector<Node> link2 = new Vector<Node>();
    link2.add(node2);
    link2.add(node3);
    link2.add(node4);
    graph.add(link2);

    Vector<Node> link3 = new Vector<Node>();
    link3.add(node4);
    link3.add(node3);
    graph.add(link3);


    applet.init(graph);
//    applet.displayCriticPath();
    //applet.setPreferredSize(new Dimension(500, 500));
    JFrame frame = new JFrame();
    //frame.getContentPane().add(applet);
    JPanel pane = new JPanel();
    //pane.add(applet);
    //pane.setSize(500,500);
    /*mxGraph graph2 = new mxGraph();
    Object parent = graph2.getDefaultParent();

    graph2.getModel().beginUpdate();
    try
    {
    Object v1 = graph2.insertVertex(parent, null, "Hello", 20, 20, 80,
    30);
    Object v2 = graph2.insertVertex(parent, null, "World!", 240, 150,
    80, 30);
    graph2.insertEdge(parent, null, "Edge", v1, v2);
    }
    finally
    {
    graph2.getModel().endUpdate();
    }

    mxGraphComponent graphComponent = new mxGraphComponent(graph2);
    frame.getContentPane().add(graphComponent);*/
    mxGraphComponent graphComponent = new mxGraphComponent(applet.graphMx);
    frame.getContentPane().add(graphComponent);
    frame.setTitle("Ordonnancement PERT Simulation");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setPreferredSize(new Dimension(500, 500));
    frame.pack();
    frame.setVisible(true);
  }

  private void adjustDisplaySettings(mxGraph jg) {
    //jg.setPreferredSize(DEFAULT_SIZE);

    Color c = DEFAULT_BG_COLOR;
    String colorStr = null;

    try {
      //colorStr = getParameter("bgcolor");
    } catch (Exception e) {
    }

    if (colorStr != null) {
      c = Color.decode(colorStr);
    }

    //jg.setBackground(c);
  }
  mxCell recentEdge = null;

  public void focusEdge(Vector<Node> edge) {
    graphMx.getModel().beginUpdate();
    Color color = Color.red;
    Color oldcolor = Color.GREEN;

    if (recentEdge != null) {
      recentEdge.setStyle("fillColor=yellow");
    }

    Object[] edges = graphMx.getChildVertices(this);//graphView.getGraphLayoutCache().getCells(false, false, false, true);
    for (int k = 0; k < edges.length; k++) {
      mxCell e = (mxCell) edges[k];
      mxCell source = (mxCell) e.getSource();
      mxCell dest = (mxCell) e.getTarget();

      if (((Comparable<Node>) source.getValue()).compareTo(edge.get(0)) == 0
              && ((Comparable<Node>) dest.getValue()).compareTo(edge.get(1)) == 0) {
        recentEdge = e;
        recentEdge.setStyle("fillColor=green");
      }
    }
    graphMx.getModel().endUpdate();
  }
  mxCell recentCell = null;

  public void focusNode(Node node) {
    graphMx.getModel().beginUpdate();
    Object[] vertices = graphMx.getChildVertices(this);
    Color color = Color.orange;
    Color oldcolor = Color.GREEN;
    if (recentCell != null) {
      recentCell.setStyle("fillColor=yellow");
    }
    for (int i = 0; i < vertices.length; i++) {

      mxCell vertex = (mxCell) vertices[i];
      Node nodeAttached = (Node) vertex.getValue();
      if (nodeAttached != null) {
        if (nodeAttached.compareTo(node) == 0) {
          //nodeAttached.setFindingState(findingProcess);
          recentCell.setStyle("fillColor=green");
//          GraphConstants.setBackground(vertex.getAttributes(), color);
//          //GraphConstants.set
//          graphView.getGraphLayoutCache().editCell(vertex, vertex.getAttributes());
//          graphView.setSelectionCell(vertex);
//          graphView.refresh();
          return;
        }
      }
    }
    graphMx.getModel().endUpdate();
  }
//  public void valueChanged(GraphSelectionEvent e) {
//  }
}
