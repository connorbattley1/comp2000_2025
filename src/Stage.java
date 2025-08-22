import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

// Actor superclass
abstract class Actor {
    protected Cell cell;
    
    public Actor(Cell cell) {
        this.cell = cell;
    }
    
    public abstract void paint(Graphics g);
    
    public Cell getCell() {
        return cell;
    }
}

// Cat subclass - draws blue
class Cat extends Actor {
    public Cat(Cell cell) {
        super(cell);
    }
    
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(cell.x, cell.y, cell.width, cell.height);
        g.setColor(Color.BLACK);
        g.drawRect(cell.x, cell.y, cell.width, cell.height);
    }
}

// Dog subclass - draws yellow
class Dog extends Actor {
    public Dog(Cell cell) {
        super(cell);
    }
    
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(cell.x, cell.y, cell.width, cell.height);
        g.setColor(Color.BLACK);
        g.drawRect(cell.x, cell.y, cell.width, cell.height);
    }
}

// Bird subclass - draws green
class Bird extends Actor {
    public Bird(Cell cell) {
        super(cell);
    }
    
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(cell.x, cell.y, cell.width, cell.height);
        g.setColor(Color.BLACK);
        g.drawRect(cell.x, cell.y, cell.width, cell.height);
    }
}

// Stage class that contains Grid and Actors
public class Stage {
    private final Grid grid;
    private final ArrayList<Actor> actors;
    
    public Stage() {
        grid = new Grid();
        actors = new ArrayList<>();
        
        // Create actors at simple positions
        actors.add(new Cat(new Cell(185, 185)));    // [5,5]
        actors.add(new Dog(new Cell(360, 360)));    // [10,10]
        actors.add(new Bird(new Cell(535, 535)));   // [15,15]
    }
    
    public void paint(Graphics g, Point mousePos) {
        // First paint the grid
        grid.paint(g, mousePos);
        
        // Then paint all the actors on top
        for (Actor actor : actors) {
            actor.paint(g);
        }
    }
}
