import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Main extends JFrame {
  private Stage stage = new Stage();
  private final Canvas canvas = new Canvas();
  private final JTextArea sidebar = new JTextArea();

  private boolean started = false;
  private boolean gameOver = false;
  private String winner = null;
  private int dogScore = 0;
  private int catScore = 0;

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      Main window = new Main();
      window.run();
    });
  }

  // ---------------- Canvas ----------------

  class Canvas extends JPanel {
    // Default mouse to grid center so any hover logic has a valid target pre-start
    Point mouse = new Point(10 + Cell.size * 10, 10 + Cell.size * 10);

    public Canvas() {
      setPreferredSize(new Dimension(820, 720));
      setFocusable(true);

      addMouseMotionListener(new MouseAdapter() {
        @Override public void mouseMoved(MouseEvent e) {
          mouse = e.getPoint();
          repaint();
        }
      });

      addMouseListener(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) {
          // Click anywhere to start
          if (!started) {
            started = true;
            dogScore = 0;
            catScore = 0;
            gameOver = false;
            winner = null;

            // Keep a valid mouse point (center the hover)
            canvas.mouse = new Point(10 + Cell.size * 10, 10 + Cell.size * 10);

            updateSidebar();

            // Prime the first draw: synthesize a mouse-move at current mouse to
            // trigger any hover-dependent geometry/painting in Stage/Grid.
            primeInitialDraw();

            // Focus the canvas for keyboard controls
            canvas.requestFocusInWindow();
          }
        }
        @Override public void mouseEntered(MouseEvent e) {
          requestFocusInWindow();
        }
      });
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      if (started) {
        stage.paint(g, mouse);
        if (gameOver) {
          // Draw winner image in center
          int centerX = 10 + Cell.size * 10;
          int centerY = 10 + Cell.size * 10;
          java.awt.Image img = null;
          if ("Dog".equals(winner)) {
            img = Dog.DOG_IMG;
          } else if ("Cat".equals(winner)) {
            img = Cat.CAT_IMG;
          }
          if (img != null) {
            int size = Cell.size * 2;
            g.drawImage(img, centerX - size/2, centerY - size/2, size, size, null);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
            g.drawString("Winner!", centerX - 50, centerY + size/2 + 40);
          }
        }
      } else {
        // Pre-game instructions
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        g.drawString("Click anywhere to start the game", 250, 300);
        g.drawString("Instructions:", 250, 320);
        g.drawString("- Use W S A D keys to move the Dog", 250, 340);
        g.drawString("- Use Arrow keys to move the Cat", 250, 360);
        g.drawString("- Collect items to score points", 250, 380);
        g.drawString("- First to 10 points wins!", 250, 400);
      }
    }
  }

  // ------------- Frame setup & controls -------------

  private Main() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    add(canvas, BorderLayout.CENTER);

    // Sidebar
    sidebar.setEditable(false);
    sidebar.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
    sidebar.setLineWrap(true);
    sidebar.setWrapStyleWord(true);
    JScrollPane sidebarScroll = new JScrollPane(sidebar);
    sidebarScroll.setPreferredSize(new Dimension(220, 720));
    sidebarScroll.setMinimumSize(new Dimension(220, 720));
    add(sidebarScroll, BorderLayout.EAST);

    updateSidebar();

    JButton restartButton = new JButton("Restart");
    restartButton.addActionListener(e -> {
    // Reset the game
    stage = new Stage(); // new grid, new actors, new items
    dogScore = 0;
    catScore = 0;
    started = false;
    gameOver = false;
    winner = null;
    updateSidebar();
    canvas.repaint();
    });

// Add the button below the sidebar
JPanel sidePanel = new JPanel(new BorderLayout());
sidePanel.add(sidebarScroll, BorderLayout.CENTER);
sidePanel.add(restartButton, BorderLayout.SOUTH);
add(sidePanel, BorderLayout.EAST);


    setExtendedState(JFrame.MAXIMIZED_BOTH); // maximize window to avoid cutoff
    pack();
    setVisible(true);
    canvas.requestFocus();
    canvas.repaint(); // initial render

    // Keep canvas focused when the window is activated
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      @Override public void windowActivated(java.awt.event.WindowEvent e) {
        canvas.requestFocus();
      }
    });

    // Keyboard controls: Dog = WSAD, Cat = Arrows
    canvas.addKeyListener(new java.awt.event.KeyAdapter() {
      @Override public void keyPressed(java.awt.event.KeyEvent e) {
        if (!started || gameOver) return;

        Actor actor = null;
        int newCol = -1, newRow = -1;

        // WSAD for Dog
        if (e.getKeyCode() == java.awt.event.KeyEvent.VK_W ||
            e.getKeyCode() == java.awt.event.KeyEvent.VK_S ||
            e.getKeyCode() == java.awt.event.KeyEvent.VK_A ||
            e.getKeyCode() == java.awt.event.KeyEvent.VK_D) {

          actor = stage.actors.get(1); // Dog
          char colChar = actor.cell().col;
          int row = actor.cell().row;
          int col = stage.grid.labelToCol(colChar);
          newCol = col; newRow = row;

          switch (e.getKeyCode()) {
            case java.awt.event.KeyEvent.VK_W -> newRow = row - 1;
            case java.awt.event.KeyEvent.VK_S -> newRow = row + 1;
            case java.awt.event.KeyEvent.VK_A -> newCol = col - 1;
            case java.awt.event.KeyEvent.VK_D -> newCol = col + 1;
          }
        }
        // Arrow keys for Cat
        else if (e.getKeyCode() == java.awt.event.KeyEvent.VK_UP ||
                 e.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN ||
                 e.getKeyCode() == java.awt.event.KeyEvent.VK_LEFT ||
                 e.getKeyCode() == java.awt.event.KeyEvent.VK_RIGHT) {

          actor = stage.actors.get(0); // Cat
          char colChar = actor.cell().col;
          int row = actor.cell().row;
          int col = stage.grid.labelToCol(colChar);
          newCol = col; newRow = row;

          switch (e.getKeyCode()) {
            case java.awt.event.KeyEvent.VK_UP -> newRow = row - 1;
            case java.awt.event.KeyEvent.VK_DOWN -> newRow = row + 1;
            case java.awt.event.KeyEvent.VK_LEFT -> newCol = col - 1;
            case java.awt.event.KeyEvent.VK_RIGHT -> newCol = col + 1;
          }
        }

        // Apply move if valid
        if (actor != null && newCol >= 0 && newCol < 20 && newRow >= 0 && newRow < 20) {
          char newColChar = stage.grid.colToLabel(newCol);
          java.util.Optional<Cell> destOpt = stage.grid.cellAtColRow(newColChar, newRow);
          if (destOpt.isPresent()) {
            Cell dest = destOpt.get();

            boolean canMove =
              (actor instanceof Dog && ((Dog) actor).canMoveTo(dest)) ||
              (actor instanceof Cat && ((Cat) actor).canMoveTo(dest));

            if (canMove) {
              actor.setCell(dest);

              // Pickup item if present
              for (int i = 0; i < stage.itemCells.size(); i++) {
                if (stage.itemCells.get(i) == dest && stage.items.get(i) instanceof Collectible) {
                  Item item = stage.items.get(i);
                  ((Collectible) item).collect(actor);

                  // Scoring
                  if (actor instanceof Dog) {
                    if (item instanceof Bone) dogScore += 2; else dogScore += 1;
                  } else if (actor instanceof Cat) {
                    if (item instanceof Fish) catScore += 2; else catScore += 1;
                  }

                  stage.itemCells.remove(i);
                  stage.items.remove(i);
                  break;
                }
              }

              // Check win
              if (dogScore >= 10) { gameOver = true; winner = "Dog"; }
              else if (catScore >= 10) { gameOver = true; winner = "Cat"; }

              updateSidebar();
              canvas.repaint();
            }
          }
        }
      }
    });

    // OPTIONAL: Gentle repaint loop so the very first frame is guaranteed
    // to paint even if no input occurs yet. Low CPU overhead (~30 FPS).
    new javax.swing.Timer(33, e -> canvas.repaint()).start();
  }

  // Synthesizes a mouse move at the current mouse position to trigger any
  // hover-dependent logic in Stage/Grid on the very first frame.
  private void primeInitialDraw() {
    Point p = (canvas.mouse != null)
      ? canvas.mouse
      : new Point(10 + Cell.size * 10, 10 + Cell.size * 10);

    MouseEvent fakeMove = new MouseEvent(
        canvas, MouseEvent.MOUSE_MOVED,
        System.currentTimeMillis(), 0,
        p.x, p.y, 0, false
    );

    for (MouseMotionListener l : canvas.getMouseMotionListeners()) {
      l.mouseMoved(fakeMove);
    }

    canvas.repaint();
  }

  private void updateSidebar() {
    StringBuilder sb = new StringBuilder();
    sb.append("Points:\n");
    sb.append("Cat: ").append(catScore).append("\n");
    sb.append("Dog: ").append(dogScore).append("\n");
    if (gameOver) {
      sb.append("Game Over! Winner: ").append(winner).append("\n");
    } else if (!started) {
      sb.append("\nClick anywhere on the grid to start.\n");
    }
    sidebar.setText(sb.toString());
  }

  public void run() {
    setVisible(true);
  }
}
