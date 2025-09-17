import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Cell extends Rectangle {
  public static int size = 35;
  public final char col;
  public final int row;
  protected Color base;

  public Cell(char inCol, int inRow, int x, int y, Color color) {
    super(x, y, size, size);
    this.col = inCol;
    this.row = inRow;
    this.base = color;
  }

  public void paint(Graphics g, Point mouse) {
    // Always render the cell
    g.setColor(base);
    g.fillRect(x, y, size, size);
    g.setColor(Color.BLACK);
    g.drawRect(x, y, size, size);
    // Only darken if mouse is present and inside
    if (mouse != null && contains(mouse)) {
      g.setColor(new Color(0, 0, 0, 50)); // semi-transparent overlay
      g.fillRect(x, y, size, size);
    }
  }
}
