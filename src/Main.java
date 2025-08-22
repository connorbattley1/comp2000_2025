import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame {
    public static void main(String[] args) throws Exception {
      Main window = new Main();
      window.run();
    }

    // --- Task 4: simple Grid + Cell ---------------------------------------
    class Grid {
      final int rows = 20, cols = 20, size = 35, offX = 10, offY = 10;
      final Cell[][] cells = new Cell[rows][cols];

      Grid() {
        for (int r = 0; r < rows; r++) {
          for (int c = 0; c < cols; c++) {
            int x = offX + c * size, y = offY + r * size;
            cells[r][c] = new Cell(x, y, size);
          }
        }
      }

      void paint(Graphics g, Point mouse) {
        for (int r = 0; r < rows; r++) {
          for (int c = 0; c < cols; c++) {
            cells[r][c].paint(g, mouse);
          }
        }
      }
    }

    class Cell {
      final int x, y, s;
      Cell(int x, int y, int s) { this.x = x; this.y = y; this.s = s; }

      boolean contains(Point p) {
        return p != null && p.x >= x && p.x < x + s && p.y >= y && p.y < y + s;
      }

      // Task 5: highlight hovered cell (grey fill), then draw border
      void paint(Graphics g, Point mouse) {
        if (contains(mouse)) {
          g.setColor(Color.LIGHT_GRAY);
          g.fillRect(x, y, s, s);
        }
        g.setColor(Color.BLACK);
        g.drawRect(x, y, s, s);
      }
    }

    // --- Canvas, drawing the grid -------------------------------
    class Canvas extends JPanel {
      final Grid grid = new Grid();
      public Canvas() { setPreferredSize(new Dimension(720, 720)); }

      @Override
      public void paint(Graphics g) {
        super.paint(g);
        // Task 3: optional outer border to match your original
        g.setColor(Color.BLACK);
        g.drawRect(10, 10, 700, 700);

        // Draw grid; pass current mouse position for highlighting
        grid.paint(g, getMousePosition()); // may be null
      }
    }

    private Main() {
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setContentPane(new Canvas());
      this.pack();
      this.setVisible(true);
    }

    public void run() {
      while(true) {
        repaint();
      }
    }
}