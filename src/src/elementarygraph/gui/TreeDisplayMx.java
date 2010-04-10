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
import com.mxgraph.util.mxConstants;
import javax.swing.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Map;
import java.util.Vector;

public class TreeDisplayMx {

  //~ Static fields/initializers ---------------------------------------------
  private static final long serialVersionUID = 3256444702936019250L;
  private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
  private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);
  private static final int nodePaddingLeft = 100;
  private static final int nodePaddingTop = 70;
  //~ Instance fields --------------------------------------------------------
  public Vector<Node> nodeList;
  public Vector<Vector<Node>> graph;
  public mxGraphComponent graphcom;
  public mxGraphView graphView;
  public mxGraph graphMx;
  public Object parent;
  //

  public void init(Vector<Vector<Node>> graph) {

    graphMx = new mxGraph();
    parent = graphMx.getDefaultParent();

    graphView = new mxGraphView(graphMx);

    // Init graph model
    this.graph = graph;
    this.nodeList = new Vector<Node>();
    graphMx.setAllowDanglingEdges(false);
    graphMx.setCellsBendable(false);
    graphMx.setCellsDisconnectable(false);
    //graphMx.setCellsMovable(false);
    graphMx.setCellsResizable(false);
    graphMx.setCellsSelectable(false);

    mxCompactTreeLayout treelayout = new mxCompactTreeLayout(graphMx);
    treelayout.setHorizontal(false);
    treelayout.setMoveTree(true);
    treelayout.execute(parent);
    graphcom = new mxGraphComponent(graphMx);
    graphcom.setCenterPage(false);
    graphcom.setAutoExtend(false);
    graphcom.setDragEnabled(false);
    graphcom.setBackground(Color.GRAY);
  }

  public static void main(String[] args) {
    TreeDisplayMx applet = new TreeDisplayMx();
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
    //applet.displayCriticPath();
    //applet.setPreferredSize(new Dimension(500, 500));
    JFrame frame = new JFrame();
    //frame.getContentPane().add(applet);
    JPanel pane = new JPanel();
    //pane.add(applet.graphView);
    //pane.setSize(500,500);
    frame.getContentPane().add(pane);
    frame.setTitle(" Simulation");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setPreferredSize(new Dimension(500, 500));
    frame.pack();
    frame.setVisible(true);
  }
  mxCell recentNode = null;
  mxCell recentEdge = null;
  mxCell rootNode = null;

  public void recoverRecentEdge() {
    if (recentNode != null && recentEdge != null) {
      //recentNode.setStyle("fillColor=yellow");
      graphMx.setCellStyles(mxConstants.STYLE_FILLCOLOR, "green", new Object[]{recentNode});
      //recentEdge.setStyle("lineColor=yellow");
      graphMx.setCellStyles(mxConstants.STYLE_FILLCOLOR, "green", new Object[]{recentEdge});
    }

  }

  public void addEdge(Vector<Node> edge) {
    graphMx.getModel().beginUpdate();
    try {
      Object[] vertices = graphMx.getChildVertices(parent);
      Color color = Color.orange;
      Color oldcolor = Color.GREEN;

      Node source = edge.get(0);
      Node dest = edge.get(1);
      if (vertices.length == 0) {
        mxCell cell = (mxCell) graphMx.insertVertex(parent, source.getTitle(), source, 50, 20, 80, 30);
        //cell.setStyle("ROUNDED;strokeColor=red;fillColor=green;width=40;height=50");
        //mxCell cell = new mxCell();
        //cell.setVertex(true);//createNode(source);
        //graphMx.addCell(cell, this);
        graphMx.setCellStyles(mxConstants.STYLE_FILLCOLOR, "green", new Object[]{cell});
        //graphView.getGraphLayoutCache().insert(cell);
        rootNode = cell;
      }

      recoverRecentEdge();

      vertices = graphMx.getChildVertices(parent);
      //System.out.println("size of vertices:" + vertices.length);
      for (int i = 0; i < vertices.length; i++) {
        mxCell vertex = (mxCell) vertices[i];
        Node nodeAttached = (Node) vertex.getValue();
        if (nodeAttached != null) {
          if (nodeAttached.compareTo(source) == 0) {

            mxCell destCell = (mxCell) graphMx.insertVertex(parent, dest.getTitle(), dest, 50, 20, 80, 30);
            //destCell.setStyle("ROUNDED;strokeColor=red;fillColor=green;width=40;height=50");


            mxCell newedge = (mxCell) graphMx.insertEdge(parent, null, "", vertex, destCell);
            //destCell.setStyle("lineColor=red");

            graphMx.setCellStyles(mxConstants.STYLE_FILLCOLOR, "yellow", new Object[]{destCell});
            //recentEdge.setStyle("lineColor=yellow");
            graphMx.setCellStyles(mxConstants.STYLE_FILLCOLOR, "yellow", new Object[]{newedge});
            recentEdge = newedge;
            recentNode = destCell;
            //GraphConstants.set
            break;
          }
        }
      }

      mxCompactTreeLayout treelayout = new mxCompactTreeLayout(graphMx);
      treelayout.setHorizontal(true);
      //treelayout.setInvert(false);
      treelayout.setMoveTree(true);
      treelayout.execute(parent);

    } finally {
      graphMx.getModel().endUpdate();
    }
    return;
  }
}
