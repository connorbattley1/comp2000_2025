import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Dog extends Actor {
  public static final Image DOG_IMG = new ImageIcon("src/dog.png").getImage().getScaledInstance(Cell.size, Cell.size, Image.SCALE_SMOOTH);
  public Dog(Cell inLoc) { super(inLoc); }

  @Override
  public void updateDisplay() {
    display.clear();
    // No polygons needed
  }

  @Override
  public void paint(Graphics g) {
    g.drawImage(DOG_IMG, loc.x, loc.y, Cell.size, Cell.size, null);
  }

  public boolean canEnter(Cell c) {
    if (c instanceof Traversable t) {
      // Dogs cannot enter water cells; delegate to cell logic when possible
      if (c instanceof WaterCell) return false;
      return t.canEnter(this);
    }
    return false;
  }

  public boolean canMoveTo(Cell c) {
    int dx = Math.abs(c.col - loc.col);
    int dy = Math.abs(c.row - loc.row);
    return (dx + dy == 1) && canEnter(c);
  }
}
