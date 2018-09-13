import java.util.*;

public class Qcomposite {
  public static void main(String[] args){
    // Test arguments format
    if(args.length != 3){
      System.out.println("Please call the program like this : java Qcomposite <ring_size> <Q> <node_attacked>");
      System.exit(1);
    }

    // Variables used to calculate the average Resilience
    double res[] = new double[10];
    double average_res = 0.0;

    // We launch the program 10 times :
    for(int it = 0; it < res.length; it++){
      // Define scheme parameters
      int pool_size = 1000;
      int ring_size = Integer.parseInt(args[0]);
      int key_size = 128;
      int width = 1000;
      int height = 1000;
      int rayon = 50;
      int q = Integer.parseInt(args[1]);
      double average_degree = 6.0;
      int node_attacked = Integer.parseInt(args[2]);

      //System.out.println("-----------------------\n INITIALIZATION \n-----------------------");
      double density = getDensity(average_degree, rayon);
      int nb_nodes = (int)(density*width*height);
      //.out.println(nb_nodes);
      //System.out.println("density : " + density);
      //System.out.println("nb_nodes : " + nb_nodes);

      // Create nodes
      node[] sensors = new node[nb_nodes];
      for(int i=0; i<nb_nodes; i++){
        sensors[i] = new node(i, width, height);
      }
      //System.out.println(nb_nodes + " nodes created");

      // Create key pool
      key[] S = new key[pool_size];
      for(int a = 0; a<pool_size; a++){
        S[a] = new key(key_size);
      }
      //System.out.println("Key pool of " + pool_size + " keys created");

      // Distribute key to all nodes
      int key_distributed;
      key new_key = null;
      for(int j = 0; j<nb_nodes; j++){
        key_distributed = 0;
        while(key_distributed<ring_size){
          new_key = S[(int)(Math.random()*pool_size)];
          if( !sensors[j].ring.contains(new_key)){
            sensors[j].addKey(new_key);
            key_distributed++;
          }
        }
      }
      //System.out.println(ring_size + " key distributed to each node");

      // Fill the neighbors list for each nodes
      ArrayList<Link> possible_links = new ArrayList<Link>(); // just for Display
      fillNeighbors(sensors, rayon, possible_links);

      // Find connections with neighbors by testing others nodes
      ArrayList<Link> links = new ArrayList<Link>();
      fillConnections(sensors, q, links);

      // Display neighbors and Connections
      /*
      for(int d=0; d<nb_nodes;d++){
        sensors[d].displayNeighbors();
      }
      for(int e=0; e<nb_nodes;e++){
        sensors[e].displayConnectedNodes();
      }
      */

      // Calculate the connectivity : ratio nb_connections / nb_neighbors

      int nb_neighbors = 0;
      int nb_connexions = 0;
      for(int j=0; j<sensors.length; j++){
        nb_neighbors += sensors[j].neighbors.size();
        nb_connexions += sensors[j].connected_node.size();
      }
      nb_neighbors = nb_neighbors/2;
      nb_connexions = nb_connexions/2;
      double ratio = (double)(nb_connexions)/(double)(nb_neighbors);
      //res[it]=ratio;


      // Create attacker, fill its ring and attack the network
      Attacker attacker = new Attacker();
      attacker.stealKeys(node_attacked, sensors);
      attacker.attack(links);

      // Calculate resilience and println
      res[it]= resilience(links, attacker);

      // Display in a window
      if(it==res.length-1){
        new Display(sensors, links, possible_links);
      }
    }

    // Calculate the average resilience on the 10 launches
    average_res = (res[0]+res[1]+res[2]+res[3]+res[4]+res[5]+res[6]+res[7]+res[8]+res[9])/10.0;
    System.out.println(average_res);
  }


// =================================================== FUNCTIONS ===================================================


  // Count resilience : number of compromized links divided by number of links
  public static double resilience(ArrayList<Link> links, Attacker attacker){
    double res = 0.0;
    for(int i = 0; i<links.size(); i++){
      if(!links.get(i).getState()){
        res = res + 1;
      }
    }
    //System.out.println("Internal links : " + attacker.internalLinks);
    return (double)(res-attacker.internalLinks)/(double)(links.size()-attacker.internalLinks);
  }

  // Function fillNeighbors : set neighbors of each node thanks to the rayon and coordonates of each other nodes
  public static void fillNeighbors(node[] sensors, int rayon, ArrayList<Link> possible_links){
    double distance = 0;
    for(int i = 0; i<sensors.length; i++){
      for(int j = 0; j<sensors.length; j++){
        if(i != j){
          distance = sensors[i].dist(sensors[j]);
          if(distance <= rayon){
            sensors[i].addNeighbor(sensors[j]);
            possible_links.add(new Link(sensors[i], sensors[j], null));
          }
        }
      }
    }
  }

  // Function fillConnections : fill connected_node for each node
  public static void fillConnections(node[] sensors, int q, ArrayList<Link> links){
    key tab[] = new key[sensors.length];
    int res = 0;
    // Test neighbors of each node
    for(int b = 0; b<sensors.length; b++){
      res = 0;
      for(int c=0; c<sensors[b].neighbors.size(); c++){
        res = sensors[b].testNode(sensors[b].neighbors.get(c), tab);
        if(res >= q){
          Qkey qk = new Qkey(res, tab);
          links.add(new Link(sensors[b], sensors[b].neighbors.get(c), qk));
          sensors[b].addConnection(sensors[b].neighbors.get(c), qk);
        }
      }
    }
  }

  // Function getDensity : find density with the number_per_node needed and the rayon
  public static double getDensity(double number_per_node, int rayon){
    double density =  number_per_node/(Math.PI*rayon*rayon);
    return density;
  }

}
