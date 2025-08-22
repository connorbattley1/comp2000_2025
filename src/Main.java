import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame {
    public static void main(String[] args) throws Exception {
        Main window = new Main();
        window.run();
    }

    // ====== Task 4: Grid + Cell identity ===================================
    class Grid {
        private final int rows, cols, cellSize, offsetX, offsetY;
        private final Cell[][] cells;

        Grid(int rows, int cols, int cellSize, int offsetX, int offsetY) {
            this.rows = rows;
            this.cols = cols;
            this.cellSize = cellSize;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.cells = new Cell[rows][cols];

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    int x = offsetX + c * cellSize;
                    int y = offsetY + r * cellSize;
                    cells[r][c] = new Cell(r, c, x, y, cellSize);
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
        private final int row, col, x, y, size;

        Cell(int row, int col, int x, int y, int size) {
            this.row = row;
            this.col = col;
            this.x = x;
            this.y = y;
            this.size = size;
        }

        private boolean contains(Point p) {
            return p != null && p.x >= x && p.x < x + size && p.y >= y && p.y < y + size;
        }

        void paint(Graphics g, Point mouse) {
            // ====== Task 5: highlight hovered cell ===========================
            if (contains(mouse)) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(x, y, size, size);
            }
            g.setColor(Color.BLACK);
            g.drawRect(x, y, size, size);
        }
    }

    // ====== Canvas (built on your original) =================================
    class Canvas extends JPanel {
        private final Grid grid;

        public Canvas() {
            setPreferredSize(new Dimension(720, 720));
            // Task 3: 20x20 cells, 35px each, starting at (10,10) -> 700x700 area
            grid = new Grid(20, 20, 35, 10, 10);

            // Repaint when mouse moves so highlight updates
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    repaint();
                }
                @Override
                public void mouseDragged(MouseEvent e) {
                    repaint();
                }
            });
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            // Keep your original border
            g.setColor(Color.BLACK);
            g.drawRect(10, 10, 700, 700);

            // Draw the grid; pass current mouse position for highlighting
            Point mouse = getMousePosition(); // may be null if mouse outside
            grid.paint(g, mouse);
        }
    }

    private final Canvas canvas;

    private Main() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas = new Canvas();
        this.setContentPane(canvas);
        this.pack();
        this.setVisible(true);
    }

    public void run() {
        // Keep your loop, but throttle to ~60 FPS to avoid 100% CPU
        while (true) {
            canvas.repaint();
            try { Thread.sleep(16); } catch (InterruptedException ignored) {}
        }
    }
}