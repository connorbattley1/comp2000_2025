import java.awt.Color;

public final class WaterCell extends TerrainCell {
  public WaterCell(char c, int r, int x, int y) { super(c, r, x, y, new Color(120, 160, 230)); }

  @Override
  public boolean canEnter(Actor actor) {
    // Cats can enter water; dogs and others cannot. When actor is null (e.g., spawn scan), treat as traversable for cats only.
    return actor == null || actor instanceof Cat;
  }
}
