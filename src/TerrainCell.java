import java.awt.Color;

public abstract class TerrainCell extends Cell implements Traversable {
  public TerrainCell(char inCol, int inRow, int x, int y, Color color) {
    super(inCol, inRow, x, y, color);
  }
}
