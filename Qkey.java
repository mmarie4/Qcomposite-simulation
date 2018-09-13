public class Qkey {
  protected key[] keys;
  protected int q;

  // COnstructor
  public Qkey(int param_q){
    q = param_q;
    keys = new key[param_q];
  }

  // Constructor
  public Qkey(int param_q, key[] tab){
    q = param_q;
    keys = new key[param_q];
    setKeys(tab);
  }

  // Set the q keys
  public void setKeys(key[] tab){
    for(int i=0; i<this.q; i++){
      keys[i] = tab[i];
    }
  }

  // Return q
  public int getLength(){
    return q;
  }

}
