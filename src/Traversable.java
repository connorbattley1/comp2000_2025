public interface Traversable {
  boolean canEnter(Actor a);
  default int moveCost(Actor a) { return 1; }
}
