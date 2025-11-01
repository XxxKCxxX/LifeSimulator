
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

        new Timer(1, e -> {
            world.update();
            renderer.repaint();
            System.out.printf("Entities: %d | Free: %d%n",
                world.entities.size(), world.unusedSpace.size());
        }).start();
    }
}
