import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class Projectile implements ActionListener{
    JLabel projectile;
    Timer t;
    ArrayList<Integer> projectileLocation = new ArrayList<Integer>();
    int xPos;
    int yPos;

    public Projectile(int x, int y, ImageIcon img){
        projectile = new JLabel();
        projectile.setBounds(x, 425, 25, 25);
        projectile.setIcon(img);
        xPos = x;
        yPos = y;
        projectileLocation.add(x);
        projectileLocation.add(y);
        t = new Timer(50, this);
        t.start();
    }

    public JLabel getLabel(){
        return projectile;
    }

    public int getX(){
        return (projectile.getX() + 25);
    }

    public int getY(){
        return (projectile.getY() + 25);
    }

    public void moveMissile(){
        projectile.setLocation(projectile.getX(), projectile.getY() - 10);
        projectileLocation.set(0, projectile.getX());
        projectileLocation.set(1, projectile.getY());
    }

    public ArrayList<Integer> getCoordinates(){
        return projectileLocation;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        moveMissile();
    }
}
