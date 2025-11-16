import java.util.List;
import java.util.ArrayList;
import java.awt.Point;
import java.util.Random;


public class World {
    public final int WIDTH = 1000, HEIGHT = 1000;
    public List<Point> unusedSpace = new ArrayList<>();
    public List<LivingEntity> entities = new ArrayList<>();
    private Random rand = new Random();
    public int spawnRate = 50;

    public World() {
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                unusedSpace.add(new Point(x, y));
    }

    public void updateDeath() {
        List<LivingEntity> newList = new ArrayList<>(entities);

        for (LivingEntity e : entities) {
            e.updateDeath(newList, unusedSpace, rand);
        }

        // neue Entities hinzufügen, tote entfernen
        newList.removeIf(e -> e.isDead);
        entities = newList;
    }

    public void updateCloning() {
        List<LivingEntity> newList = new ArrayList<>(entities);

        for (LivingEntity e : entities) {
            e.updateCloning(newList, unusedSpace, rand);
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

    public void countEntities() {
        // group entities by their "color" (supports a getColor() method or a public "color" field)
        java.util.Map<Object, java.util.List<LivingEntity>> groups = new java.util.HashMap<>();
        for (LivingEntity e : entities) {
            Object colorKey = null;
            try {
            java.lang.reflect.Method gm = e.getClass().getMethod("getColor");
            colorKey = gm.invoke(e);
            } catch (Exception ex1) {
            try {
                java.lang.reflect.Field cf = e.getClass().getField("color");
                colorKey = cf.get(e);
            } catch (Exception ex2) {
                // cannot find color, skip this entity
                continue;
            }
            }
            groups.computeIfAbsent(colorKey, k -> new java.util.ArrayList<>()).add(e);
        }

        // sort color groups by size (descending) and take top 3
        java.util.List<java.util.Map.Entry<Object, java.util.List<LivingEntity>>> entries =
            new java.util.ArrayList<>(groups.entrySet());
        java.util.Collections.sort(entries, (a, b) -> Integer.compare(b.getValue().size(), a.getValue().size()));
        int topN = Math.min(3, entries.size());

        // For each of the top 3 colors: count occurrences and aggregate numeric fields multiplied by 3.92
        for (int i = 0; i < topN; i++) {
            Object color = entries.get(i).getKey();
            java.util.List<LivingEntity> list = entries.get(i).getValue();
            int count = list.size();

            java.util.Map<String, Double> aggregated = new java.util.HashMap<>();
            try {
            java.lang.reflect.Field[] fields = list.get(0).getClass().getDeclaredFields();
            for (java.lang.reflect.Field f : fields) {
                f.setAccessible(true);
                Class<?> t = f.getType();
                boolean isNumeric = t == int.class || t == long.class || t == double.class || t == float.class
                    || t == short.class || t == byte.class
                    || Number.class.isAssignableFrom(t);
                if (!isNumeric) continue;

                double sum = 0.0;
                for (LivingEntity e : list) {
                Object v = f.get(e);
                if (v instanceof Number) sum += ((Number) v).doubleValue();
                }
                // multiply aggregated value by 3.92
                aggregated.put(f.getName(), sum * 3.92);
            }
            } catch (Exception ex) {
            // reflection error: skip property aggregation
            }

            // output: rank, color, count and aggregated properties (properties are sums * 3.92)
            System.out.println("Rank " + (i + 1) + " - color=" + color + " count=" + count + " props*3.92=" + aggregated);
        }
    }
}
