import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public final class Fish extends Item implements Collectible {
  private static final Image FISH_IMG = new ImageIcon("fish.png").getImage().getScaledInstance(Cell.size, Cell.size, Image.SCALE_SMOOTH);
  public Fish() { super("Fish"); }
  @Override public void collect(Actor a) { a.inventory().add(this); }
  public void paint(Graphics g, int x, int y) {
    g.drawImage(FISH_IMG, x, y, Cell.size, Cell.size, null);
  }
}
