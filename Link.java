public class Link {
  protected node A;
  protected node B;
  protected Qkey qkey;
  private boolean safe;

  public Link(node nA, node nB, Qkey k) {
    A = nA;
    B = nB;
    qkey = k;
    safe = true;
  }

  // Return true if the link is safe, false if it's compromised
  public boolean getState(){
    return this.safe;
  }

  // Compromise the link (safe = false)
  public void compromize(){
    this.safe = false;
  }

  // Return the Qkey
  public Qkey getQkey(){
    return this.qkey;
  }

}
