import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Point;

public class LivingEntity {
    public Point location;
    public boolean isDead;
    public int cloningRate;
    public int deathRate;
    public int mutationRate;
    public Color color;

    public LivingEntity(Point location, int cloning, int death, int mutation) {
        this.location = location;
        this.isDead = false;
        this.cloningRate = cloning;
        this.deathRate = death;
        this.mutationRate = mutation;
        this.color = new Color(
            Math.min(255, cloning / 4),
            Math.min(255, death / 4),
            Math.min(255, mutation / 4)
        );
    }

    public void update(List<LivingEntity> newEntities, List<Point> unusedSpace, Random rand) {
        if (rand.nextInt(1000) < deathRate) {
            isDead = true;
            unusedSpace.add(location);
            return;
        }

        if (rand.nextInt(1000) < cloningRate && !unusedSpace.isEmpty()) {
            Point newLoc = unusedSpace.remove(rand.nextInt(unusedSpace.size()));
            LivingEntity child = new LivingEntity(
                newLoc,
                clamp(cloningRate + rand.nextInt(mutationRate * 2 + 1) - mutationRate),
                clamp(deathRate + rand.nextInt(mutationRate * 2 + 1) - mutationRate),
                clamp(mutationRate + rand.nextInt(mutationRate * 2 + 1) - mutationRate)
            );
            newEntities.add(child);
        }
    }

    private int clamp(int val) {
        return Math.max(1, Math.min(1000, val));
    }
}
