import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class EnemyProjectile implements ActionListener{
  JLabel eProjectile;
  //ImageIcon img = new ImageIcon("src/main/java/enemymissile.png");
  
  ImageIcon img = new ImageIcon(Game.class.getResource("/enemymissile.png"));

  Timer t;
  ArrayList<Integer> eProjectileLocation = new ArrayList<Integer>();
  
  
  public EnemyProjectile(int x, int y){
    eProjectile = new JLabel();
    eProjectile.setBounds(x, y, 25, 25);
    eProjectile.setIcon(img);
    eProjectileLocation.add(x);
    eProjectileLocation.add(y);
    //System.out.println("spawned");
    t = new Timer(100, this);
    t.start();
  }

  public JLabel getLabel(){
    return eProjectile;
  }

  public int getX(){
    return (eProjectile.getX() + 25);
  }

  public int getY(){
    return (eProjectile.getY() + 25);
  }

  public void moveMissile(){
    eProjectile.setLocation(eProjectile.getX(), eProjectile.getY() + 10);
    eProjectileLocation.set(0, eProjectile.getX());
    eProjectileLocation.set(1, eProjectile.getY());
    //System.out.println("move");
  }

  public ArrayList<Integer> getCoordinates(){
      return eProjectileLocation;
  }

  @Override
  public void actionPerformed(ActionEvent e){
    moveMissile();
  }
  
}
