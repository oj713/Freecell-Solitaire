package cs3500.freecell.model;

import cs3500.freecell.model.pile.ICascadePile;
import cs3500.freecell.model.pile.MultiMoveCascadePile;

import java.util.ArrayList;

/**
 * Represents a model of the game Freecell as lists of each pile in the game, the card within them,
 * and whether or not the game has been started. Allows for multiple cards to be
 * moved at a time.
 */
public class MultiMoveFreecellModel extends SimpleFreecellModel {

  public MultiMoveFreecellModel() {
    super();
  }

  @Override
  protected ArrayList<ICascadePile> generateCascadePiles(int numCascadePiles) {
    ArrayList<ICascadePile> newCascadePiles = new ArrayList<>(numCascadePiles);

    for (int i = 0; i < numCascadePiles; i++) {
      newCascadePiles.add(new MultiMoveCascadePile());
    }
    return newCascadePiles;
  }

  @Override
  protected int getMaxCardsCanMove() {
    int freeOpenPiles = 0;
    for (int i = 0; i < this.getNumOpenPiles(); i++) {
      if (this.getNumCardsInOpenPile(i) == 0) {
        freeOpenPiles++;
      }
    }
    int freeCascadePiles = 0;
    for (int i = 0; i < this.getNumCascadePiles(); i++) {
      if (this.getNumCardsInCascadePile(i) == 0) {
        freeCascadePiles++;
      }
    }
    if (freeCascadePiles > 4) {
      return 20;
    }
    int maxCards = (freeOpenPiles + 1) * (int) Math.pow(2, freeCascadePiles);
    return maxCards;
  }
}
