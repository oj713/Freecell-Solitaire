package cs3500.freecell.model.pile.pileInfo;

/**
 * Represents identifying information for a pile in a game of Freecell.
 */
public interface IPileInfo {
  /**
   * Returns the pile type for a pile in a game of Freecell.
   * @return the pileType of this pile.
   */
  public PileType getPileType();

  /**
   * Returns the index of this pile in a game of Freecell.
   * @return the index of this pile.
   */
  public int getPileIndex();
}
