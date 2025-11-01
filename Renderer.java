import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Renderer extends JPanel {
    private final World world;

    public Renderer(World world) {
        this.world = world;
        setPreferredSize(new Dimension(world.WIDTH, world.HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, world.WIDTH, world.HEIGHT);

        for (LivingEntity e : world.entities) {
            if (!e.isDead)
                g.setColor(e.color);
            else
                g.setColor(Color.BLACK);
            g.fillRect(e.location.x, e.location.y, 1, 1);
        }
    }
}
