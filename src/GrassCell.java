import java.awt.Color;

public final class GrassCell extends TerrainCell {
  public GrassCell(char c, int r, int x, int y) { super(c, r, x, y, new Color(120, 190, 120)); }
  @Override public boolean canEnter(Actor a) { return true; }
}
