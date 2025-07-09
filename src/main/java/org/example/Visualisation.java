package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Visualisation extends JFrame implements Constants{
    private final JPanel drawingPanel = new JPanel()
    {
        @Override
        public void paint(Graphics g){
            super.paint(g);
            setPoints(g);
        }
    }
    ;
    private final Environment environment;
    private static boolean runLoop = false;
    private final long[] SLOWDOWN_FACTOR = new long[] {1, 3, 5, 10, 33, 50, 100};
    private static int index = 0;
    private static final JButton pauseButton = new JButton();
    private static final JButton toggleObstacleButton = new JButton();
    private static final JButton speedButton = new JButton();
    private static int i = 1;
    private static int generation = 1;
    private static boolean checkForObstacle = false;

    public Visualisation(Environment environment) {
        super("It's alive!");
        this.environment = environment;
        setLayout(new BorderLayout());
        add(drawingPanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.EAST);
        int correctionTerm = ENLARGEMENT_FACTOR * 8;
        int BUTTON_PANEL_SIZE = 40;
        setSize(SCREEN_SIZE + BUTTON_PANEL_SIZE + ENLARGEMENT_FACTOR * 3, SCREEN_SIZE + correctionTerm);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        drawingPanel.setVisible(true);
        drawingPanel.setSize(SCREEN_SIZE, SCREEN_SIZE);
        buttonPanel.setSize(BUTTON_PANEL_SIZE, SCREEN_SIZE + correctionTerm);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        pauseButton.addActionListener(new ButtonListener());
        toggleObstacleButton.addActionListener(new ButtonListener());
        speedButton.addActionListener(new ButtonListener());
        buttonPanel.add(pauseButton);
        buttonPanel.add(toggleObstacleButton);
        buttonPanel.add(speedButton);
        setResizable(true);
        setVisible(true);
    }
    public void update() {
        environment.updateEntities();
        drawingPanel.repaint();
    }
    public void reproduce() {
        environment.reproduce();
        drawingPanel.repaint();
    }
    public void kill(int leftX, int rightY, int rightX, int upY) {
        environment.kill(leftX, rightY, rightX, upY);
        drawingPanel.repaint();
    }
    private void setPoints(Graphics g) {
        Entity[] world = environment.getWorld();
        for (int i = 0; i < world.length; i++) {
            if (world[i] != null) {
                int x = ENLARGEMENT_FACTOR * (i % (WORLD_SIZE)); // x-coordinate of the center
                int y = ENLARGEMENT_FACTOR * ((WORLD_SIZE) - i / (WORLD_SIZE)); // y-coordinate of the center
                int width = ENLARGEMENT_FACTOR; // radius of the circle
                // Draw the entity
                g.setColor(new Color(world[i].hashCode()));
                g.fillRect(x, y + width, width, width);
//                g.fillOval(x - width, y + width, 2 * width, 2 * width);
            }
        }
    }
    private void startLoop() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (runLoop) {
                    try {
                        Thread.sleep(TIME_STEP * SLOWDOWN_FACTOR[index]);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            update();
                            if (i == KILL_TIME - 1) {
                                kill(KILL_X_LEFT, KILL_Y_DOWN, KILL_X_RIGHT, KILL_Y_UP);
                            } else if (i == 0) {
                                reproduce();
                                System.out.println("generation " + generation);
                                generation++;
                            }
                            i = (i + 1) % KILL_TIME;
                        }
                    });
                }
            }
        }).start();
    }

    class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == pauseButton) {
                runLoop = !runLoop;
                if (runLoop) {
                    startLoop();
                }
            }
            else if (e.getSource() == toggleObstacleButton) {
                if (checkForObstacle) {
                    environment.setObstacle();
                }
                else {
                    environment.removeObstacle();
                }
                checkForObstacle = !checkForObstacle;
            }
            else if (e.getSource() == speedButton) {
                index = (index + 1) % SLOWDOWN_FACTOR.length;
            }
        }
    }
    private void visualiseGenomes() {

    }
}
