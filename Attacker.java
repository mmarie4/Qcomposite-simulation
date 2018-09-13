import java.util.ArrayList;

public class Attacker {
  protected ArrayList<key> ring;
  protected int internalLinks; // number of links directly attached to the corrumpted nodes (we don't count them in the resilience calculation)

  public Attacker(){
    ring = new ArrayList<key>();
    internalLinks = 0;
  }

  // Attack the n first nodes of the network and steal their keys
  public void stealKeys(int n, node[] sensors){
    // Steal keys of each node
    for (int i = 0; i<n; i++){
      // Count amount of links
      this.internalLinks += sensors[n].getNumberOfLinks();
      // Test each key of the ring
      for (int j = 0; j<sensors[i].ring.size(); j++){
        if(!this.ring.contains(sensors[i].ring.get(j))){ // if the key is not in the attacker's ring
          this.ring.add(sensors[i].ring.get(j));
        }
      }
    }
  }

  // Attack the network and compromize as much links as possible
  public int attack(ArrayList<Link> links){
    int nb_links_compromized = 0;
    Qkey qk;
    for(int i=0; i<links.size(); i++){
      if(unlockLink(links.get(i).getQkey())){
        //System.out.println("J'attaque un lien avec " + links.get(i).getQkey().getLength() + " clés");
        links.get(i).compromize();
        nb_links_compromized++;
      }
    }
    return nb_links_compromized;
  }

  // Test if the attacker can unlock a Link by testing the Qkey
  public boolean unlockLink(Qkey qk){
    int contained = 0;
    for(int i = 0; i<qk.getLength(); i++){
      if(this.ring.contains(qk.keys[i])){
        contained++;
      }
    }
    if(contained>=qk.getLength()){
      return true;
    }else{
      return false;
    }
  }

}
