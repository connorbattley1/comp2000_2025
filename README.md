Connor Battley
46360085

This project evolves a simple 2D grid into a small game with **terrain**, **actors**, and **items**. The key goal was to use **inheritance**, **interfaces**, and **generics** in a way that improves the design
### Steps to compile/run

1. Open a terminal in the project root folder.  
2. Compile all source files:
   ```bash
   javac *.java
3. Run with
    ```bash
   java Main
Gameplay Instructions
	•	Start the game: Click anywhere on the grid.
	•	Dog controls: W = up, A = left, S = down, D = right.
	•	Cat controls: Arrow keys.
	•	Scoring:
	•	Dog: +2 for Bones, +1 for other items.
	•	Cat: +2 for Fish, +1 for other items.
	•	Winning: First to 10 points wins.
	•	Restart: Press the Restart button in the sidebar to play again.

## Inheritance – Why it helps here

### 1) `Actor` → `Cat` / `Dog`
- **What’s inherited:** position (`Cell`), painting scaffolding, an `Inventory<Item>`, and shared move helper logic.
- **Why it’s good:** common behavior (drawing, inventory) stays in `Actor`; species-specific rules live in `Cat`/`Dog` (e.g., dog avoids water, cat can swim). This avoids duplicated movement/painting code and keeps species rules local to each subclass.

### 2) `Cell` → `TerrainCell` → (`GrassCell`, `SandCell`, `WaterCell`)
- **What’s inherited:** basic cell geometry/painting (`Cell`), while `TerrainCell` adds the contract that a terrain is **traversable** (via `Traversable`).
- **Why it’s good:** all terrain types share base visuals and coordinates, and then specialize entry rules and movement costs. Adding a new terrain (e.g., `IceCell`) is a single new class, not a ripple of edits across the codebase.

**Rubric tie-in (Inheritance):** This is not just “used”—it organizes responsibilities so that extensions (new actors or terrain) require minimal changes elsewhere. That’s good OO design (Open/Closed Principle).

---

## Interfaces

### `Traversable`
```java
public interface Traversable {
  boolean canEnter(Actor a);
  default int moveCost(Actor a) { return 1; }
}
```

- **How it’s used:** `TerrainCell` implements `Traversable`, and actors consult `canEnter` to decide if movement is legal. Example:
  - `WaterCell.canEnter(actor)` returns `true` for `Cat`, `false` for `Dog`.
  - `SandCell.moveCost()` returns `2` (slower) for potential future pathfinding/turns.
- **Why it’s good:** rules are attached to **cells** (the environment), not hard-coded inside actors. This keeps actors simple, and adding a new terrain type does not require changing `Cat`/`Dog`.

### `Collectible`
- **How it’s used:** Items (`Bone`, `Fish`, `Seed`) implement `Collectible.collect(Actor)`. The `Stage` or `Actor` simply calls `collect()` when an actor steps onto an item cell.
- **Why it’s good:** items are pluggable; adding a new item effect does not require editing the actor classes.

**Rubric tie-in (Interfaces):** Interfaces provide clean seams between components (actors vs. terrain; actors vs. items), enabling extensibility without modification of existing classes.

---

## Generics – Type-safe, reusable inventories

### `Inventory<T extends Item>`
```java
public class Inventory<T extends Item> {
  private final java.util.List<T> items = new ArrayList<>();
  public void add(T t) { items.add(t); }
  public int size() { return items.size(); }
  public java.util.List<T> getItems() { return items; }
}
```

- **How it’s used:** Each `Actor` owns an `Inventory<Item>`. Because `Inventory` is generic, it can be reused as `Inventory<Bone>` for a dog-only mini-game, or `Inventory<Seed>` for a bird-only scenario—without rewriting containers.
- **Why it’s good:** Strong compile-time guarantees and reusable container logic that is **not** just using a raw `List`. The inventory becomes an API for future features (capacity limits, sorting, UI rendering hooks) with type safety.

**Rubric tie-in (Generics):** This is a **custom generic class** (beyond using `List<T>`). It directly improves type safety and code reuse, satisfying the “custom class” criterion.

---

