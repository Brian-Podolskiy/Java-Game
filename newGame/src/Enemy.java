import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class Enemy implements ActionListener{
  JLabel eLabel;
  JFrame temp;
  boolean isActive = true;
  Timer t;
  int direction = 1;
  int timesCalled = 0;
  ArrayList<Integer> enemyLocation = new ArrayList<Integer>(2);
  ArrayList<EnemyProjectile> projectile;
  public Enemy(int x, int y, ImageIcon i, JFrame frame, ArrayList<EnemyProjectile> eProj){
    eLabel = new JLabel();
    eLabel.setBounds(x, y, 50, 50);
    eLabel.setIcon(i);
    enemyLocation.add(0);
    enemyLocation.add(0);
    temp = frame;
    projectile = eProj;
    t = new Timer(50, this);
    t.start();
  }
  
  public JLabel getLabel(){
    return eLabel;
  }

  public int getX(){
    return (eLabel.getX() + 25);
  }

  public int getY(){
    return (eLabel.getY() + 25);
  }

  public ArrayList<Integer> getCoordinates(){
    return enemyLocation;
  }

  public void moveEnemy(){
    if (eLabel.getX() <= 450 && eLabel.getX() >= 0){
      eLabel.setLocation(eLabel.getX()+ (10 * direction), eLabel.getY());
    }
    else{
      eLabel.setLocation(eLabel.getX(), eLabel.getY() + 15);
      if (eLabel.getX() >= 450){
        eLabel.setLocation(440, eLabel.getY());
        direction *= -1;
      }
      else if (eLabel.getX() <= 0){
        eLabel.setLocation(10, eLabel.getY());
        direction *= -1;
      }
    }
    enemyLocation.set(0, eLabel.getX());
    enemyLocation.set(1, eLabel.getY());
  }

  public void setActive(){
    isActive = false;
  }

  @Override
  public void actionPerformed(ActionEvent e){
    moveEnemy();
    timesCalled++;
    if (timesCalled == 15 && isActive == true){
      EnemyProjectile p = new EnemyProjectile(eLabel.getX(), eLabel.getY());
      temp.add(p.getLabel());
      projectile.add(p);
      timesCalled = 0;
    }
  }
}