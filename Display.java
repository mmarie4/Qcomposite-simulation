import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Display extends JFrame {
  JPanel panel;
  node[] sensors;
  ArrayList<Link> links;
  ArrayList<Link> possible_links;

  public Display(node[] s, ArrayList<Link> l, ArrayList<Link> p_l){
    super("Representation of the sensor network");
    sensors = s;
    links = l;
    possible_links = p_l;

    setLayout(null);
    panel = new JPanel();
    panel.setBounds(0, 0, 1000, 1000);
    setContentPane(panel);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1000, 1000);
    setResizable(false);
    setVisible(true);
    }

    public void paint(Graphics g){
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, 1000, 1000);
      // Draw possible links
      g.setColor(Color.WHITE);
      for(int k=0; k<possible_links.size(); k++){
        g.drawLine(possible_links.get(k).A.x, possible_links.get(k).A.y, possible_links.get(k).B.x, possible_links.get(k).B.y);
      }
      // Draw links
      for(int j = 0; j < links.size(); j++){
        if(links.get(j).getState()){
          g.setColor(Color.GREEN);
          g.drawLine(links.get(j).A.x, links.get(j).A.y, links.get(j).B.x, links.get(j).B.y);
        }else{
          g.setColor(Color.RED);
          g.drawLine(links.get(j).A.x, links.get(j).A.y, links.get(j).B.x, links.get(j).B.y);
        }
      }
      // Draw nodes
      g.setColor(Color.GREEN);
      for(int i = 0; i<sensors.length; i++){
          g.drawLine(sensors[i].x, sensors[i].y, sensors[i].x, sensors[i].y);
      }
    }
}
