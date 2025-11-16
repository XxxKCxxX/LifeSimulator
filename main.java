
import javax.swing.JFrame;
import javax.swing.Timer;


public class main {
    static int count = 0;
    public static void main(String[] args) {
        World world = new World();
        Renderer renderer = new Renderer(world);

        JFrame frame = new JFrame("Life Simulator (Java)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(renderer);
        frame.pack();
        frame.setVisible(true);

        

        new Timer(1, e -> {
            world.updateDeath();
            renderer.repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            world.updateCloning();
            renderer.repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            count++;
            System.out.printf("Entities: %d | Free: %d%n | Generation: %d%n",
                world.entities.size(), world.unusedSpace.size(), count);
        }).start();
    }
}
