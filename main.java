import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.Timer;


public class main {
    public static void main(String[] args) {
        World world = new World();
        Renderer renderer = new Renderer(world);

        JFrame frame = new JFrame("Life Simulator (Java)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(renderer);
        frame.pack();
        frame.setVisible(true);

        new Timer(50, e -> {
            world.update();
            renderer.repaint();
            System.out.printf("Entities: %d | Free: %d%n",
                world.entities.size(), world.unusedSpace.size());
        }).start();
    }
}
