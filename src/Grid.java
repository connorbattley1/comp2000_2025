import java.awt.Graphics;
import java.awt.Point;
import java.util.Optional;
import java.util.Random;

public class Grid {
  Cell[][] cells = new Cell[20][20];
  
  public Grid() {
    Random rnd = new Random(42);
    for(int i=0; i<cells.length; i++) {
      for(int j=0; j<cells[i].length; j++) {
        char label = colToLabel(i);
        int x = 10 + Cell.size * i;
        int y = 10 + Cell.size * j;
        int r = rnd.nextInt(100);
        if (r < 70) cells[i][j] = new GrassCell(label, j, x, y);
        else if (r < 88) cells[i][j] = new SandCell(label, j, x, y);
        else cells[i][j] = new WaterCell(label, j, x, y);
      }
    }
  }

  public char colToLabel(int col) { return (char) (col + Character.valueOf('A')); }
  public int labelToCol(char col) { return (int) (col - Character.valueOf('A')); }

  public void paint(Graphics g, Point mouse) {
    for (int i=0;i<cells.length;i++) {
      for(int j=0;j<cells[i].length;j++) {
        cells[i][j].paint(g, mouse);
      }
    }
  }

  public Optional<Cell> cellAtColRow(int c, int r) {
    if(c >= 0 && c < cells.length && r >=0 && r < cells[c].length) return Optional.of(cells[c][r]);
    return Optional.empty();
  }
  public Optional<Cell> cellAtColRow(char c, int r) { return cellAtColRow(labelToCol(c), r); }

  public Optional<Cell> cellAtPoint(Point p) {
    if (p == null) return Optional.empty();
    for(int i=0; i < cells.length; i++) for(int j=0; j < cells[i].length; j++) if(cells[i][j].contains(p)) return Optional.of(cells[i][j]);
    return Optional.empty();
  }
}
