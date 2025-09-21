import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;


public abstract class Actor {
  protected Color color;
  protected Cell loc;
  protected final Inventory<Item> bag = new Inventory<>();

  public Actor(Cell inLoc) {
    this.loc = inLoc;
    // Subclasses should call updateDisplay() after construction if needed
  }

  public Inventory<Item> inventory() { return bag; }
  public Cell cell() { return loc; }
  public void setCell(Cell c) { this.loc = c; updateDisplay(); }

  public abstract void updateDisplay();

  public void paint(Graphics g) {
    for(Polygon p: display) {
      g.setColor(color);
      g.fillPolygon(p);
      g.setColor(Color.GRAY);
      g.drawPolygon(p);
    }
  }
}
