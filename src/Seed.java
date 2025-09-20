import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public final class Seed extends Item implements Collectible {
  private static final Image SEED_IMG = new ImageIcon("seed.png").getImage().getScaledInstance(Cell.size, Cell.size, Image.SCALE_SMOOTH);
  public Seed() { super("Seed"); }
  @Override public void collect(Actor a) { a.inventory().add(this); }
  public void paint(Graphics g, int x, int y) {
    g.drawImage(SEED_IMG, x, y, Cell.size, Cell.size, null);
  }
}
