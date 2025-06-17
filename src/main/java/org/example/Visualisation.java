package org.example;

import javax.swing.*;
import java.awt.*;

public class Visualisation extends JFrame implements Constants{
    private final JPanel drawing_panel = new JPanel()
    {
        @Override
        public void paint(Graphics g){
            super.paint(g);
            setPoints(g);
        }
    }
    ;
//    private static final int SIZE = 1000;
    private final Environment environment;
    public Visualisation(Environment environment) {
        super("It's alive!");
        this.environment = environment;
        setLayout(new BorderLayout());
        add(drawing_panel, BorderLayout.CENTER);
        setSize(SIZE, SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        drawing_panel.setVisible(true);
        setResizable(true);
        setVisible(true);
    }
    public void update() {
        environment.updateEntities();
        drawing_panel.repaint();
    }
    public void reproduce() {
        environment.reproduce();
        drawing_panel.repaint();
    }
    public void kill(int x, int y) {
        environment.kill(x, y);
        drawing_panel.repaint();
    }
    private void setPoints(Graphics g) {
        for (Entity entity: environment.getEntities()) {
            int x = entity.getX(); // x-coordinate of the center
            int y = SIZE - entity.getY(); // y-coordinate of the center
            int radius = 3; // radius of the circle
            // Draw the point
            g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
        }
    }
}
