/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elementarygraph.algorithme;

/**
 *
 * @author donnghi
 */
public class Node implements Comparable<Node> {

  protected String title;
  protected Integer index;
  protected boolean trace;

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getIndex() {
    return this.index;
  }

  public void setIndex(Integer value) {
    this.index = value;
  }

  public boolean isTrace() {
    return this.trace;
  }

  public void setTrace(boolean bool) {
    this.trace = bool;
  }

  public Node() {
    this.title = new String();
    this.setTrace(false);
  }

  public Node(String title) {
    this.setTitle(title);
    this.setTrace(false);
  }

  public Node(String title, boolean bool) {
    this.setTitle(title);
    this.setTrace(bool);
  }

  public Node(String title, Integer value) {
    this.setTitle(title);
    this.setTrace(false);
    this.setIndex(value);
  }

  @Override
  public String toString() {
    //return getTaskName() + "[" + getDuration() + "," + getEarlyStart() + ","+ getEarlyFinish()+"]";
    return getTitle();
  }

  public int compareTo(Node o) {
    return this.getTitle().compareTo(o.getTitle());
  }
}
