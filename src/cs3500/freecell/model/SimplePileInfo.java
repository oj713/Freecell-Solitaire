package cs3500.freecell.model;

/**
 * This class models a simple implementation of the IPileInfo interface, and uses
 * PileType and int to represent the information for a a pile.
 */
public class SimplePileInfo implements IPileInfo {
  private final PileType pileType;
  private final int pileIndex;

  /**
   * Constructs a {@code SimplePileInfo} object.
   * @param pileType the PileType of the pile.
   * @param pileIndex the index of the pile.
   * @throws IllegalArgumentException if pileType is null.
   */
  public SimplePileInfo(PileType pileType, int pileIndex) {
    if (pileType == null) {
      throw new IllegalArgumentException("Cannot have null PileType.");
    }
    this.pileType = pileType;
    this.pileIndex = pileIndex;
  }

  @Override
  public PileType getPileType() {
    return this.pileType;
  }

  @Override
  public int getPileIndex() {
    return this.pileIndex;
  }
}
