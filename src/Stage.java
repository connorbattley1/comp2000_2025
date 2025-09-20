import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Stage {
  Grid grid;
  List<Actor> actors;
  List<Cell> itemCells = new ArrayList<>();
  List<Item> items = new ArrayList<>();

  public Stage() {
    grid = new Grid();
    actors = new ArrayList<>();

    // Random spawn for Cat (any traversable cell) and Dog (non-water traversable)
    java.util.Random spawnRnd = new java.util.Random();
    java.util.List<Cell> traversable = new java.util.ArrayList<>();
    java.util.List<Cell> nonWater = new java.util.ArrayList<>();

    for (int i = 0; i < grid.cells.length; i++) {
      for (int j = 0; j < grid.cells[i].length; j++) {
        Cell c = grid.cells[i][j];
        if (c instanceof Traversable) { // null = any actor
        Traversable t = (Traversable)c;
        if (t.canEnter(null)) {
                    traversable.add(c);
                    if (!(c instanceof WaterCell)) nonWater.add(c);
        }
        }
      }
    }

    // Ensure nonWater is not empty; fallback to traversable if needed
    Cell catCell = traversable.get(spawnRnd.nextInt(traversable.size()));
    Cell dogCell = nonWater.isEmpty() ? traversable.get(spawnRnd.nextInt(traversable.size()))
                                      : nonWater.get(spawnRnd.nextInt(nonWater.size()));

    // Avoid overlap
    int guard = 0;
    while (dogCell == catCell && guard++ < 1000) {
      dogCell = nonWater.isEmpty() ? traversable.get(spawnRnd.nextInt(traversable.size()))
                                   : nonWater.get(spawnRnd.nextInt(nonWater.size()));
    }

    actors.add(new Cat(catCell));
    actors.add(new Dog(dogCell));
    // Place a random number of each item, total = ITEM_TOTAL
    final int ITEM_TOTAL = 24;
    Random rnd = new Random(20);
    int boneCount = rnd.nextInt(ITEM_TOTAL + 1);
    int fishCount = rnd.nextInt(ITEM_TOTAL - boneCount + 1);
    int seedCount = ITEM_TOTAL - boneCount - fishCount;
    List<Item> itemPool = new ArrayList<>();
    for (int i = 0; i < boneCount; i++) itemPool.add(new Bone());
    for (int i = 0; i < fishCount; i++) itemPool.add(new Fish());
    for (int i = 0; i < seedCount; i++) itemPool.add(new Seed());
    // Shuffle itemPool
    java.util.Collections.shuffle(itemPool, rnd);
    int placed = 0;
    while (placed < ITEM_TOTAL) {
      int x = rnd.nextInt(20), y = rnd.nextInt(20);
      Cell c = grid.cellAtColRow(x, y).get();
      if (!(c instanceof WaterCell) && !itemCells.contains(c)) {
        itemCells.add(c);
        items.add(itemPool.get(placed));
        placed++;
      }
    }
  }

  public void click(Point p) {
    // Removed character selection logic
  }

  public void paint(Graphics g, Point mouse) {
    grid.paint(g, mouse);
    // draw items
    for (int i=0;i<itemCells.size();i++) {
      Cell c = itemCells.get(i);
      Item item = items.get(i);
      if (item instanceof Bone) {
        ((Bone)item).paint(g, c.x, c.y);
      } else if (item instanceof Fish) {
        ((Fish)item).paint(g, c.x, c.y);
      } else if (item instanceof Seed) {
        ((Seed)item).paint(g, c.x, c.y);
      }
    }
    for(Actor a: actors) a.paint(g);
  }
}

