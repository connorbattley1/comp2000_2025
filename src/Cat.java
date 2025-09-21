import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Cat extends Actor {
  public static final Image CAT_IMG = new ImageIcon("src/cat.png").getImage().getScaledInstance(Cell.size, Cell.size, Image.SCALE_SMOOTH);

  public Cat(Cell inLoc) { super(inLoc); }

  @Override
  public void updateDisplay() {

  }

  @Override
  public void paint(Graphics g) {
    g.drawImage(CAT_IMG, loc.x, loc.y, Cell.size, Cell.size, null);
  }

  public boolean canEnter(Cell c) {
    if (c instanceof Traversable) {
      Traversable t = (Traversable)c;
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
