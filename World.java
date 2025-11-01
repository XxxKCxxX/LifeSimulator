import java.awt.*;
import java.util.*;

public class World {
    public final int WIDTH = 1000, HEIGHT = 1000;
    public List<Point> unusedSpace = new ArrayList<>();
    public List<LivingEntity> entities = new ArrayList<>();
    private Random rand = new Random();
    public int spawnRate = 10;

    public World() {
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                unusedSpace.add(new Point(x, y));
    }

    public void update() {
        List<LivingEntity> newList = new ArrayList<>(entities);
        for (LivingEntity e : entities) {
            e.update(newList, unusedSpace, rand);
        }

        // neue Entities hinzufügen, tote entfernen
        newList.removeIf(e -> e.isDead);
        entities = newList;

        // evtl. neue Entities spawnen
        if (!unusedSpace.isEmpty() && rand.nextInt(1000) < spawnRate) {
            Point p = unusedSpace.remove(rand.nextInt(unusedSpace.size()));
            entities.add(new LivingEntity(p, 50, 20, 10));
        }
    }
}
