import java.util.*;

public class node {
  protected int id;
  protected ArrayList<key> ring;
  // node at index i of neighbors shares Qkey at index i of links with the current node
  protected ArrayList<node> neighbors;
  protected ArrayList<node> connected_node;
  protected ArrayList<Qkey> links;
  protected int x, y;

  public node(int i, int w, int h){
    id = i;
    ring = new ArrayList<key>();
    neighbors = new ArrayList<node>();
    connected_node = new ArrayList<node>();
    links = new ArrayList<Qkey>();
    setCoordonates(w, h);
  }

  // Get the ratio number of connections / number of neighbors
  public double getRatio(){
    if(neighbors.isEmpty()){
      return 0;
    }else{
      return (double)(connected_node.size())/(double)(neighbors.size());
    }
  }

  // Add a connection
  public void addConnection(node n, Qkey qk){
    connected_node.add(n);
    links.add(qk);
  }

  // Calculate distance with an other node
  public double dist(node n){
    return Math.sqrt(Math.pow(Math.abs(n.x - this.x), 2) + Math.pow(Math.abs(n.y - this.y), 2));
  }

  // Generate random coordonates
  public void setCoordonates(int width, int height){
    this.x = (int)(Math.random()*width);
    this.y = (int)(Math.random()*height);
  }

  // Add a key to the ring
  public void addKey(key new_key){
    ring.add(new_key);
  }

  // Add a neighbor to the to the list
  public void addNeighbor(node n){
    neighbors.add(n);
  }

  // Test a node and return the number of shared key, and fill a tab with sk shared keys
  public int testNode(node n, key[] tab){
    int sk = 0;
    for(int i = 0; i < this.ring.size(); i++){
      if (n.ring.contains(this.ring.get(i))){
         tab[sk] = this.ring.get(i);
         sk++;
      }
    }
    return sk;
  }



  // Return id and ring
  public String toString(){
    String str = "Node " + this.id + " at coordonates (" + this.x + ", " + this.y + ")";
    str += " - Key ring :";
    for(int i=0; i<ring.size(); i++){
      str = str + "\n" + ring.get(i);
    }
    return str;
  }

  // Display neighbors
  public void displayNeighbors(){
    System.out.println("Neighbors of node "+id+" : ");
    for(int i = 0; i<neighbors.size(); i++){
      System.out.println(neighbors.get(i));
    }
    System.out.println("");
  }

  // Display connected nodes
  public void displayConnectedNodes(){
    System.out.println("Connections of node "+id+" : ");
    for(int i = 0; i<connected_node.size(); i++){
      System.out.println(connected_node.get(i));
    }
    System.out.println("");
  }

  // Return number of links
  public int getNumberOfLinks(){
    return this.connected_node.size();
  }
}
