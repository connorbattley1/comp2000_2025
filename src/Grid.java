import java.awt.Point;
import java.util.Optional;
import java.util.Random;

public class Grid {
  public Cell[][] cells = new Cell[20][20]; // make sure this is public or has an accessor

  public Grid() {
    Random rnd = new Random(42);
    for (int col = 0; col < cells.length; col++) {
      for (int row = 0; row < cells[col].length; row++) {
        char label = colToLabel(col);
        int x = 10 + Cell.size * col;
        int y = 10 + Cell.size * row;
        int r = rnd.nextInt(100);
        // ~60% grass, 25% sand, 15% water
        if (r < 60) {
          cells[col][row] = new GrassCell(label, row, x, y);
        } else if (r < 85) {
          cells[col][row] = new SandCell(label, row, x, y);
        } else {
          cells[col][row] = new WaterCell(label, row, x, y);
        }
      }
    }
  }

  // Helper:
  private char colToLabel(int c) { return (char)('A' + c); }

  private int labelToCol(char c) { return c - 'A'; }

  public Optional<Cell> cellAtColRow(int c, int r) {
    if (c >= 0 && c < cells.length && r >= 0 && r < cells[c].length)
      return Optional.of(cells[c][r]);
    return Optional.empty();
  }

  public Optional<Cell> cellAtColRow(char c, int r) {
    return cellAtColRow(labelToCol(c), r);
  }

  public Optional<Cell> cellAtPoint(Point p) {
    if (p == null) return Optional.empty();
      for (Cell[] cell : cells) {
        for (Cell cell1 : cell) {
            if (cell1.contains(p)) {
                return Optional.of(cell1);
            }
        }
      }
    return Optional.empty();
  }

  public void paint(java.awt.Graphics g, Point mouse) {
      for (Cell[] cell : cells) {
          for (Cell cell1 : cell) {
              cell1.paint(g, mouse);
          }
      }
  }
}