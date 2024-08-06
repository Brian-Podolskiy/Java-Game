import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.math.*;
import java.util.ArrayList;

public class Game implements ActionListener{
  public JFrame frame;
  JLabel label;
  JLabel scoreLabel;
  JLabel gameOverLabel;
  JLabel streakLabel;
  JLabel livesLabel;
  Action leftAction;
  Action rightAction;
  Action shootProjectile;
  Timer timer;
  Timer timeStep;
  int delay = 5000;
  int score = 0;
  int streak = 0;
  int extraLivesStreak = 0;
  int maxEnemies = 5;
  int currentEnemyCount = 0;
  int lives = 3;
  int enemySpeed;
  int projectileX;
  int projectileY;
  ImageIcon missile = new ImageIcon("C:\\Users\\brian\\source\\repos\\JavaActivities\\gameProject\\n" + 
        "ewGame\\src\\missile.png"); // code for implementation in VScode
  ImageIcon img = new ImageIcon("C:\\Users\\brian\\source\\repos\\JavaActivities\\gameProject\\n" + 
        "ewGame\\src\\spaceship.png");
  ImageIcon enemy = new ImageIcon("C:\\Users\\brian\\source\\repos\\JavaActivities\\gameProject\\n" + 
        "ewGame\\src\\enemy.png");

  /*ImageIcon img = new ImageIcon("src/main/java/spaceship.png");
  ImageIcon enemy = new ImageIcon("src/main/java/enemy.png");
  ImageIcon missile = new ImageIcon("src/main/java/missile.png");*/
  
  Enemy enemy1;
  Projectile p;
  ArrayList<Enemy> allEnemies = new ArrayList<Enemy>();
  ArrayList<Projectile> allProjectiles = new ArrayList<Projectile>();
  ArrayList<EnemyProjectile> allEnemyProjectiles = new ArrayList<EnemyProjectile>();
  int timesCalled = 0;

  Game(){
    frame = new JFrame("Game");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setBackground(Color.BLACK);
    //frame.pack();
    frame.setSize(500, 500);
    frame.setLayout(null);

    label = new JLabel();
    label.setBackground(Color.RED);
    label.setBounds(250, 425, 50, 50);
    label.setIcon(img);

    scoreLabel = new JLabel();
    scoreLabel.setForeground(Color.RED);
    scoreLabel.setText("Score: " + score);
    scoreLabel.setBounds(400, 300, 100, 100);

    streakLabel = new JLabel();
    streakLabel.setForeground(Color.RED);
    streakLabel.setText("Current Streak: " + streak);
    streakLabel.setBounds(350, 350, 150, 100);

    livesLabel = new JLabel();
    livesLabel.setForeground(Color.RED);
    livesLabel.setText("Lives: " + lives);
    livesLabel.setBounds(300, 300, 100, 100);

    gameOverLabel = new JLabel();
    gameOverLabel.setBounds(0, 0, 500, 500);
    gameOverLabel.setFont(new Font("MV Boli", Font.BOLD, 25));
    gameOverLabel.setForeground(Color.RED);
    gameOverLabel.setText("GAME OVER");
    timer = new Timer(50, this);
    timer.start();

    leftAction = new LeftAction();
    rightAction = new RightAction();
    shootProjectile = new ShootProjectile();

    label.getInputMap().put(KeyStroke.getKeyStroke('a'), "leftAction");
    label.getActionMap().put("leftAction", leftAction);

    label.getInputMap().put(KeyStroke.getKeyStroke('d'), "rightAction");
    label.getActionMap().put("rightAction", rightAction);
    
    label.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "shootProjectile");
    label.getActionMap().put("shootProjectile", shootProjectile);

    frame.add(livesLabel);
    frame.add(label);
    frame.add(scoreLabel);
    frame.add(streakLabel);
    frame.setVisible(true);
  }

  public class LeftAction extends AbstractAction{

    @Override
    public void actionPerformed(ActionEvent e){
      if (label.getX() > 0){
        label.setLocation(label.getX() - 10, label.getY());
      }
    }
  }

  public class RightAction extends AbstractAction{

    @Override
    public void actionPerformed(ActionEvent e){
      if (label.getX() <= 500 - label.getSize().getWidth()){
        label.setLocation(label.getX() + 10, label.getY());
      }
    }
  }

  public class ShootProjectile extends AbstractAction{
    Timer coolDown = new Timer(1000, this);
    int charge = 5;
    @Override
    public void actionPerformed(ActionEvent e){
      Projectile p = new Projectile(label.getX(), label.getY(), missile);
      frame.add(p.getLabel());
      allProjectiles.add(p);
    }
  }

  public ArrayList<Enemy> getAllEnemyCoords(){
    return allEnemies;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    timesCalled++;
    if (timesCalled == 100){
      Enemy enemy1 = new Enemy(0,0,enemy,frame, allEnemyProjectiles);
      frame.add(enemy1.getLabel());
      allEnemies.add(enemy1);
      timesCalled = 0;
    }
    for (int i = 0; i < allProjectiles.size(); i++){
      if (allProjectiles.get(i).getY() <= 0){
        Projectile r = allProjectiles.get(i);
        frame.remove(r.getLabel());
        allProjectiles.remove(i);
        frame.repaint();
      }
      else{
        projectileX = allProjectiles.get(i).getX();
        projectileY = allProjectiles.get(i).getY();
        for (int j = 0; j < allEnemies.size(); j++){
          if(Math.sqrt(Math.pow((allEnemies.get(j).getX() - projectileX), 2) + Math.pow((allEnemies.get(j).getY() - projectileY), 2)) <= 15){
            Projectile r = allProjectiles.get(i);
            Enemy e1 = allEnemies.get(j);
            e1.setActive();
            frame.remove(r.getLabel());
            frame.remove(e1.getLabel());
            allProjectiles.remove(i);
            allEnemies.remove(j);
            frame.repaint();
            score += 100;
            streak++;
            if (streak % 2 == 0 && delay >= 100){
              delay -= 100;
              System.out.println(delay);
            }
            if (streak % 5 == 0){
              lives++;
            }
            scoreLabel.setText("Score: " + score);
            livesLabel.setText("Lives: " + lives);
            streakLabel.setText("Current Streak: " + streak);
          }
        }
      }
    }

    for (int k = 0; k < allEnemyProjectiles.size(); k++){
      if (allEnemyProjectiles.get(k).getY() >= 450){
        EnemyProjectile ep = allEnemyProjectiles.get(k);
        frame.remove(ep.getLabel());
        allEnemyProjectiles.remove(k);
        frame.repaint();
      }
      else if (Math.sqrt(Math.pow(((label.getX() + 25) - allEnemyProjectiles.get(k).getX()), 2) + Math.pow(((label.getY() + 25) - allEnemyProjectiles.get(k).getY()), 2)) <= 20){
        EnemyProjectile ep = allEnemyProjectiles.get(k);
        frame.remove(ep.getLabel());
        allEnemyProjectiles.remove(k);
        frame.repaint();

        if (lives > 0){
          lives--;
          livesLabel.setText("Lives: " + lives);
          streak = 0;
          streakLabel.setText("Streak: " + streak);
          score -= 100;
          scoreLabel.setText("Score: " + score);
          frame.repaint();
        }
        else{
          GameOver();
        }
        
      }
    }

    for (int j = 0; j < allEnemies.size(); j++){
      if ((allEnemies.get(j).getY()) >= 425){
        if (lives > 1){
          lives--;
          System.out.println("Lives left: " + lives);
          Enemy e1 = allEnemies.get(j);
          frame.remove(e1.getLabel());
          allEnemies.remove(j);
          livesLabel.setText("Lives: " + lives);
          streak = 0;
          streakLabel.setText("Streak: " + streak);
          score -= 300;
          scoreLabel.setText("Score: " + score);
          frame.repaint();
        }
        else{
          GameOver();
        }
      }
    }
  }

  public void GameOver(){
    timer.stop();
    for (int i = 0; i < allProjectiles.size(); i++){
      Projectile r = allProjectiles.get(i);
      frame.remove(r.getLabel());
      allProjectiles.remove(i);
      frame.repaint();
    }
    for (int j = 0; j < allEnemies.size(); j++){
      Enemy e1 = allEnemies.get(j);
      frame.remove(e1.getLabel());
      allEnemies.remove(j);
      frame.repaint();
    }
    frame.remove(label);
    frame.add(gameOverLabel);
  }
}