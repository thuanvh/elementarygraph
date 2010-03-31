package elementarygraph.gui;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author thuan
 */
import com.jgraph.components.labels.CellConstants;
import com.jgraph.components.labels.MultiLineVertexRenderer;
import elementarygraph.algorithme.*;
import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.JGraphLayout;
import com.jgraph.layout.graph.JGraphSimpleLayout;
import com.jgraph.layout.graph.JGraphSpringLayout;
import com.jgraph.layout.hierarchical.JGraphHierarchicalLayout;
import com.jgraph.layout.organic.JGraphFastOrganicLayout;
import com.jgraph.layout.organic.JGraphOrganicLayout;
import com.jgraph.layout.organic.JGraphSelfOrganizingOrganicLayout;
import com.jgraph.layout.tree.JGraphTreeLayout;
import org.jgraph.*;
import org.jgraph.event.GraphSelectionEvent;
import org.jgraph.event.GraphSelectionListener;
import org.jgraph.graph.*;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Map;
import java.util.Vector;

public class GraphDisplay extends JScrollPane implements GraphSelectionListener {

  //~ Static fields/initializers ---------------------------------------------
  private static final long serialVersionUID = 3256444702936019250L;
  private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
  private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);
  private static final int nodePaddingLeft = 100;
  private static final int nodePaddingTop = 70;
  //~ Instance fields --------------------------------------------------------
  public Vector<Node> nodeList;
  public Vector<Vector<Node>> graph;
  public JGraph graphView;
  //

  public void init(Vector<Vector<Node>> graph) {
    GraphModel model = new DefaultGraphModel();
    GraphLayoutCache view = new GraphLayoutCache(model,
            new DefaultCellViewFactory());
    graphView = new JGraph(model, view);
    graphView.setEditable(false);
    graphView.setConnectable(false);
    graphView.setDisconnectable(false);
    //graphView.set

    // Init graph model
    this.graph = graph;
    this.nodeList = new Vector<Node>();
    int i = 0, j = 0;
    for (i = 0; i < graph.size(); i++) {
      for (j = 0; j < graph.get(i).size(); j++) {
        if (!nodeList.contains(graph.get(i).get(j))) {
          nodeList.add(graph.get(i).get(j));
          //System.out.print(nodeList.get(i).getTaskName());
        }
      }
    }
    displayGraph(graphView);
    graphView.setPreferredSize(DEFAULT_SIZE);
    this.add(graphView);

  }

  private void displayGraph(JGraph g) {

    int i = 0;
    int j = 0;
    DefaultGraphCell[] nodeViewList = new DefaultGraphCell[nodeList.size()];
    for (i = 0; i < nodeList.size(); i++) {

      nodeViewList[i] = new DefaultGraphCell(nodeList.get(i).getTitle());
      nodeViewList[i].setUserObject(nodeList.get(i));
      GraphConstants.setBounds(nodeViewList[i].getAttributes(), new Rectangle2D.Double(20, 20, 40, 20));
      //GraphConstants.setBounds(nodeViewList[i].getAttributes(),new Rectangle2D);
      //CellConstants.setVertexShape(nodeViewList[i].getAttributes(), MultiLineVertexRenderer.SHAPE_CIRCLE);
      GraphConstants.setBorderColor(nodeViewList[i].getAttributes(), Color.black);
      //GraphConstants.setAutoSize(nodeViewList[i].getAttributes(),true);
      GraphConstants.setResize(nodeViewList[i].getAttributes(), true);

      GraphConstants.setBackground(nodeViewList[i].getAttributes(), Color.orange);
      GraphConstants.setOpaque(nodeViewList[i].getAttributes(), true);

    }
    Vector<DefaultGraphCell> edgeViewVector = new Vector<DefaultGraphCell>();
    for (i = 0; i < graph.size(); i++) {
      int srcNodePosition = nodeList.indexOf(graph.get(i).get(0));
      for (j = 1; j < graph.get(i).size(); j++) {
        int destNodePosition = nodeList.indexOf(graph.get(i).get(j));
        DefaultPort port = new DefaultPort();
        nodeViewList[srcNodePosition].add(port);
        DefaultPort portDest = new DefaultPort();
        nodeViewList[destNodePosition].add(portDest);

        DefaultEdge edge = new DefaultEdge();
        edge.setSource(port);
        edge.setTarget(portDest);


        int arrow = GraphConstants.ARROW_CLASSIC;
        GraphConstants.setLineEnd(edge.getAttributes(), arrow);
        GraphConstants.setEndFill(edge.getAttributes(), true);
        GraphConstants.setLineColor(edge.getAttributes(), Color.black);
        edgeViewVector.add(edge);
      }
    }

    g.getGraphLayoutCache().insert(nodeViewList);
    DefaultGraphCell[] edgeViewList = new DefaultGraphCell[edgeViewVector.size()];
    for (i = 0; i < edgeViewVector.size(); i++) {
      edgeViewList[i] = edgeViewVector.get(i);
    }

    g.getGraphLayoutCache().insert(edgeViewList);


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
    DefaultGraphCell root = null;
    for (i = 0; i < nodeListLevel.length; i++) {
      if (nodeListLevel[i] == 0) {
        root = nodeViewList[i];
      }
      if (maxLevel < nodeListLevel[i]) {
        maxLevel = nodeListLevel[i];
      }
    }

//        // Estimate position de vertex
//        int[] nodeListHeightLevel = new int[maxLevel + 1];
//        java.util.Random rand = new java.util.Random();
//        for (i = 0; i < nodeList.size(); i++) {
//            int x = nodeListLevel[i] * nodePaddingLeft + rand.nextInt((int) (nodePaddingLeft / 1.5));
//            nodeListHeightLevel[nodeListLevel[i]]++;
//            int y = nodeListHeightLevel[nodeListLevel[i]] * nodePaddingTop + rand.nextInt((int) (nodePaddingTop / 1.5));
//            //positionVertexAt(nodeList.get(i).getTaskName(), x, y);
//        }

    Object[] roots = new Object[]{root}; // replace getRoots with your own Object array of the cell tree roots. NOTE: these are the root cell(s) of the tree(s), not the roots of the graph model.

    JGraphFacade facade = new JGraphFacade(g, roots); // Pass the facade the JGraph instance
    //JGraphLayout layout = new JGraphTreeLayout(); // Create aninstance of the appropriate  layout
    //JGraphTreeLayout treelayout = (JGraphTreeLayout) layout;
    //JGraphLayout layout=new JGraphHierarchicalLayout();                 //*
    //JGraphLayout layout = new JGraphSimpleLayout(JGraphSimpleLayout.TYPE_CIRCLE); //*
    //JGraphLayout layout=new JGraphSpringLayout();
    JGraphLayout layout = new JGraphFastOrganicLayout();   //*
    //JGraphLayout layout=new JGraphOrganicLayout();
    //JGraphLayout layout=new JGraphSelfOrganizingOrganicLayout();
    //((JGraphTreeLayout) layout).setOrientation(SwingConstants.WEST);
    //treelayout.setAlignment(SwingConstants.CENTER);
    //treelayout.setLevelDistance(100);
    //treelayout.setNodeDistance(50);
    //layout.run(facade); // Run the layout on the facade.
    layout.run(facade);

    Map nested = facade.createNestedMap(true, true); // Obtain a map of the resulting attribute changes from the facade
    g.getGraphLayoutCache().edit(nested); // Apply the results to  the actual graph

    // position vertices nicely within JGraph component
        /*positionVertexAt(v1, 130, 40);
    positionVertexAt(v2, 60, 200);
    positionVertexAt(v3, 310, 230);
    positionVertexAt(v4, 380, 70);
     */
    // that's all there is to it!...
  }

  public static void main(String[] args) {
    GraphDisplay applet = new GraphDisplay();
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
    applet.displayCriticPath();
    //applet.setPreferredSize(new Dimension(500, 500));
    JFrame frame = new JFrame();
    //frame.getContentPane().add(applet);
    JPanel pane = new JPanel();
    pane.add(applet.graphView);
    //pane.setSize(500,500);
    frame.getContentPane().add(pane);
    frame.setTitle("Ordonnancement PERT Simulation");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setPreferredSize(new Dimension(500, 500));
    frame.pack();
    frame.setVisible(true);
  }

  private void adjustDisplaySettings(JGraph jg) {
    jg.setPreferredSize(DEFAULT_SIZE);

    Color c = DEFAULT_BG_COLOR;
    String colorStr = null;

    try {
      //colorStr = getParameter("bgcolor");
    } catch (Exception e) {
    }

    if (colorStr != null) {
      c = Color.decode(colorStr);
    }

    jg.setBackground(c);
  }


  /*
   * Display graph from list of adjacences
   */
  public void displayGraph() {
  }

  /**
   * Change color de criticPath
   */
  public Vector<Node> displayCriticPath() {
    Vector<Vector<Node>> path = generateCriticPath();
    int i = 0;
    int j = 0;
    Object[] edges = graphView.getGraphLayoutCache().getCells(false, false, false, true);
    for (i = 0; i < path.size(); i++) {
      for (j = 1; j < path.get(i).size(); j++) {
        //DefaultEdge e = g.getEdge(graph.get(i).get(0).getTaskName(), graph.get(i).get(j).getTaskName());
        for (int k = 0; k < edges.length; k++) {
          DefaultEdge e = (DefaultEdge) edges[k];
          DefaultPort ps = (DefaultPort) e.getSource();
          DefaultGraphCell s = (DefaultGraphCell) ps.getParent();

          DefaultPort pd = (DefaultPort) e.getTarget();
          DefaultGraphCell d = (DefaultGraphCell) pd.getParent();
          if (((Comparable<Node>) s.getUserObject()).compareTo(path.get(i).get(0)) == 0
                  && ((Comparable<Node>) d.getUserObject()).compareTo(path.get(i).get(j)) == 0) {

            GraphConstants.setLineColor(e.getAttributes(), Color.red);
            graphView.getGraphLayoutCache().editCell(e, e.getAttributes());
          }
        }

      }
    }
    graphView.refresh();
    return arrangeCriticPath(path);
  }

  private Vector<Node> arrangeCriticPath(Vector<Vector<Node>> path) {
    Vector<Node> arrangedPath = new Vector<Node>();
    if (path.size() > 0 && arrangedPath.size() == 0) {
      arrangedPath.add(0, path.get(0).get(0));
      arrangedPath.add(1, path.get(0).get(1));
      path.remove(0);

    }
//        while (path.size() != 0) {
//            for (int i = 0; i < path.size(); i++) {
//                for (int j = 1; j < path.get(i).size(); j++) {
//                    if (arrangedPath.get(0).compareTo(path.get(i).get(j)) == 0) {
//                        arrangedPath.insertElementAt(path.get(i).get(0), 0);
//                        path.remove(i);
//                        break;
//                    }
//                    if (arrangedPath.get(arrangedPath.size() - 1).compareTo(path.get(i).get(0)) == 0) {
//                        arrangedPath.add(path.get(i).get(j));
//                        path.remove(i);
//                        break;
//                    }
//
//                }
//            }
//        }
    return arrangedPath;
  }

  public Vector<Node> getAdjacenceNodes(Node node, boolean in) {
//        Vector<Node> nodeList = new Vector<Node>();
//        if (!in) {
//            //Sortie nodes
//            for (int i = 0; i < this.graph.size(); i++) {
//
//                if (graph.get(i).get(0).compareTo(node) != 0) {
//                    continue;
//                }
//                for (int j = 1; j < graph.get(i).size(); j++) {
//                    nodeList.add(graph.get(i).get(j));
//                }
//            }
//        } else {
//            // Input node
//            for (int i = 0; i < this.graph.size(); i++) {
//                for (int j = 1; j < graph.get(i).size(); j++) {
//                    if (graph.get(i).get(j).compareTo(node) == 0) {
//                        nodeList.add(graph.get(i).get(0));
//                        break;
//                    }
//                }
//            }
//        }
    return nodeList;
  }

  public void focusNode(Node node, FindingProcessState findingProcess) {
    Object[] vertices = graphView.getGraphLayoutCache().getCells(false, true, false, false);
    Color color = Color.orange;
    switch (findingProcess) {
      case EARLY_FINDING:
        color = Color.GREEN;
        break;
      case LATELY_FINDING:
        color = Color.YELLOW;
        break;
    }
    for (int i = 0; i < vertices.length; i++) {
      DefaultGraphCell vertex = (DefaultGraphCell) vertices[i];
      Node nodeAttached = (Node) vertex.getUserObject();
//            if (nodeAttached != null) {
//                if (nodeAttached.compareTo(node) == 0) {
//                    nodeAttached.setFindingState(findingProcess);
//                    GraphConstants.setBackground(vertex.getAttributes(), color);
//                    //GraphConstants.set
//                    graphView.getGraphLayoutCache().editCell(vertex, vertex.getAttributes());
//                    graphView.setSelectionCell(vertex);
//                    graphView.refresh();
//                    return;
//                }
//            }
    }

  }

  /**
   * @return
   */
  public Vector<Vector<Node>> generateCriticPath() {
    Vector<Vector<Node>> path = new Vector<Vector<Node>>();
    int i = 0;
    int j = 0;
    // calculate level of each node
    int[] nodeListLevel = new int[nodeList.size()];
//        for (i = 0; i < graph.size(); i++) {
//            if (graph.get(i).get(0).getSlack() != 0) {
//                continue;
//            }
//            Vector<Node> link = new Vector<Node>();
//            link.add(graph.get(i).get(0));
//            for (j = 1; j < graph.get(i).size(); j++) {
//                if (graph.get(i).get(j).getSlack() == 0) {
//                    link.add(graph.get(i).get(j));
//                }
//            }
//            if (link.size() > 1) {
//                path.add(link);
//            }
//        }
    return path;
  }

  public void valueChanged(GraphSelectionEvent e) {
  }
}
