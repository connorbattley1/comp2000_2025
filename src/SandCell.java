import java.awt.Color;

public final class SandCell extends TerrainCell {
  public SandCell(char c, int r, int x, int y) { super(c, r, x, y, new Color(220, 190, 120)); }
  @Override public boolean canEnter(Actor a) { return true; }
  @Override public int moveCost(Actor a) { return 2; }
}
