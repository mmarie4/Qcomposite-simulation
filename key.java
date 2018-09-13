import java.util.*;

public class key{
  protected char[] k;

  public key(int size){
    k = new char[size];
    createRandomKey();
  }

  // Fill the char tab with random characters
  public void createRandomKey(){
    int c = 0;
    for (int i = 0; i<k.length; i++){
      c = (int)(Math.random()*74) + 48;
      this.k[i]=(char)(c);
    }
  }

  // Display the key
  public String toString(){
    String str = "";
    for(int i = 0; i<k.length; i++){
      str = str + k[i];
    }
    return str;
  }
}
