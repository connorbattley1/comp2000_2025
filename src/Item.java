public abstract class Item {
  private final String name;
  protected Item(String name) { this.name = name; }
  public String name() { return name; }
}
