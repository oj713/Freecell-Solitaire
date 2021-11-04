package cs3500.freecell.model;

import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw02.card.ICard;

/**
 * Class used to construct a {@code FreecellModel<ICard>} object.
 *   Provides enumeration for different Freecell types and code
 *   to construct a model.
 */
public class FreecellModelCreator {
  /**
   * Represents the different types of models for a game of Freecell.
   * SINGLEMOVE refers to a {@code SimpleFreecellModel} where only single card moves are allowed.
   * MULTIMOVE refers to a {@code MultiCardMoveFreecellModel} where multicard moves are permitted.
   */
  public enum GameType { SINGLEMOVE, MULTIMOVE }

  /**
   * Constructs a {@code FreecellModel<ICard>} object based on the given game type.
   * @param type the type of the freecell model to be created
   * @return a new model of freecell
   * @throws IllegalArgumentException if the given type is null.
   */
  public static FreecellModel<ICard> create(GameType type) throws IllegalArgumentException {
    if (type == null) {
      throw new IllegalArgumentException("Cannot create game from null type.");
    }
    if (type.equals(GameType.SINGLEMOVE)) {
      return new SimpleFreecellModel();
    } else {
      return new MultiMoveFreecellModel();
    }
  }
}
