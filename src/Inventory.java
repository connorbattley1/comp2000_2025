import java.util.*;

public class Inventory<T extends Item> {
  private final java.util.List<T> items = new ArrayList<>();
  public void add(T t) { items.add(t); }
  public int size() { return items.size(); }
  public String toString() { return items.toString(); }
  public java.util.List<T> getItems() { return items; }
}
