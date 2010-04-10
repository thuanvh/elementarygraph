/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elementarygraph.algorithme;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

/**
 *
 * @author donnghi
 */
public class Graph {

    public Vector<Vector<Node>> data = new Vector();
    Integer size;
    Vector<Node> trace = new Vector<Node>();
    public Queue<Vector<Node>> traceDFS = new LinkedList<Vector<Node>>();
    public Queue<Vector<Node>> traceBFS = new LinkedList<Vector<Node>>();
    Queue<Node> queue = new LinkedList<Node>();

    public Graph() {

//        for (int i = 0;i <= 6; i++) {
//            data.add(new Vector());
//        }
//
//        data.elementAt(1).add(new Node("1", 1));
//        data.elementAt(1).add(new Node("2", 2));
//        data.elementAt(1).add(new Node("3", 3));
//
//        data.elementAt(2).add(new Node("2", 2));
//        data.elementAt(2).add(new Node("1", 1));
//        data.elementAt(2).add(new Node("3", 3));
//        data.elementAt(2).add(new Node("4", 4));
//        data.elementAt(2).add(new Node("5", 5));
//
//        data.elementAt(3).add(new Node("3", 3));
//        data.elementAt(3).add(new Node("1", 1));
//        data.elementAt(3).add(new Node("2", 2));
//        data.elementAt(3).add(new Node("5", 5));
//
//        data.elementAt(4).add(new Node("4", 4));
//        data.elementAt(4).add(new Node("2", 2));
//        data.elementAt(4).add(new Node("5", 5));
//        data.elementAt(4).add(new Node("6", 6));
//
//        data.elementAt(5).add(new Node("5", 5));
//        data.elementAt(5).add(new Node("2", 2));
//        data.elementAt(5).add(new Node("3", 3));
//        data.elementAt(5).add(new Node("4", 4));
//        data.elementAt(5).add(new Node("6", 6));
//
//        data.elementAt(6).add(new Node("6", 6));
//        data.elementAt(6).add(new Node("4", 4));
//        data.elementAt(6).add(new Node("5", 5));
//
//        this.setSize(data.size() - 1);//Khong tinh phan tu dau tien, index = 0
    }

    public Vector<Node> addNode(Node node1, Node node2) {
        Vector<Node> temp = new Vector<Node>();
        temp.add(node1);
        temp.add(node2);
        return temp;
    }

    public void traceDfs( int vertex ) {

        Node node1 = data.elementAt(vertex).firstElement();
        node1.setTrace(true);

        for (int i = 1;i < data.elementAt(vertex).size(); i++) {
            Node node2 = data.elementAt(data.elementAt(vertex).elementAt(i).getIndex() ).firstElement();
            if ( node2.isTrace() == false ) {
                traceDFS.add( this.addNode( node1, node2 ) );
                traceDfs( node2.getIndex() );
            }
        }
    }

    public void dfs() {
        //Danh dau chua tham tat ca cac dinh
        traceDFS.clear();
        for (int i = 1; i < data.size(); i++) {
            data.elementAt(i).firstElement().setTrace(false);
        }

        for ( int i = 1;i < data.size(); i++) {
            if ( data.elementAt(i).firstElement().isTrace() == false ) {
                traceDfs( i );
            }
        }

    }

    public void bfs() {

        traceBFS.clear();
        for (int i = 1; i < data.size(); i++) {
            data.elementAt(i).firstElement().setTrace(false);
        }

        queue.add(data.elementAt( 1 ).firstElement());
        data.elementAt(1).firstElement().setTrace(true);

        while ( queue.isEmpty() == false ) {
            Node node1 = queue.poll(), node2;
            System.out.println("index:"+node1.getIndex());

            Vector<Node> listNode = data.elementAt(node1.getIndex());
            for (int i = 1;i < listNode.size(); i++) {
                node2 = data.elementAt( listNode.elementAt(i).getIndex() ).firstElement();
                if ( node2.isTrace() == false ) {
                    queue.add(node2);
                    traceBFS.add( this.addNode(node1, node2) );
                    node2.setTrace(true);
                }
            }
        }

    }

    public void test() {
        Vector<Node> temp;

        this.bfs();
        
        while ( traceBFS.isEmpty() == false ) {
            temp = traceBFS.poll();
            System.out.println("A partir de: " + temp.firstElement().getTitle() + " ---> " + temp.lastElement().getTitle() );
        }
    }

    public void setSize(Integer size) {
        this.size = size;
    }
    public Integer size() {
        return this.size;
    }

}
