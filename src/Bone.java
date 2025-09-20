import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public final class Bone extends Item implements Collectible {
  private static final Image BONE_IMG = new ImageIcon("bone.png").getImage().getScaledInstance(Cell.size, Cell.size, Image.SCALE_SMOOTH);
  public Bone() { super("Bone"); }
  @Override public void collect(Actor a) { a.inventory().add(this); }
  public void paint(Graphics g, int x, int y) {
    g.drawImage(BONE_IMG, x, y, Cell.size, Cell.size, null);
  }
}
