package cs3500.freecell.view;

import cs3500.freecell.model.FreecellModelState;

import java.io.IOException;

/**
 * Represents a textview of a game of Freecell.
 */
public class FreecellTextView implements FreecellView {
  private final FreecellModelState<?> model;
  private final Appendable destination;

  /**
   * Constructs a {@code FreecellTextView} object.
   * @param model is the freecell model being viewed.
   */
  public FreecellTextView(FreecellModelState<?> model) {
    this(model, System.out);
  }

  /**
   * Constructs a {@code FreecellTextView} object.
   * @param model is the freecell model being viewed
   * @param destination is the output location for the view.
   * @throws IllegalArgumentException if any argument is null.
   */
  public FreecellTextView(FreecellModelState<?> model, Appendable destination)
      throws IllegalArgumentException {
    if (model == null || destination == null) {
      throw new IllegalArgumentException("No arguments can be null");
    }
    this.model = model;
    this.destination = destination;
  }

  @Override
  public String toString() {
    String s = "";
    try {
      for (int i = 0; i < 4; i++) {
        s += "F" + Integer.toString(i + 1) + ":";
        for (int j = 0; j < model.getNumCardsInFoundationPile(i); j++) {
          s += " " + model.getFoundationCardAt(i, j).toString();
          if (j != model.getNumCardsInFoundationPile(i) - 1) {
            s += ",";
          }
        }
        s += "\n";
      }
      for (int i = 0; i < model.getNumOpenPiles(); i++) {
        s += "O" + Integer.toString(i + 1) + ":";
        if (model.getNumCardsInOpenPile(i) == 1) {
          s += " " + model.getOpenCardAt(i).toString();
        }
        s += "\n";
      }
      for (int i = 0; i < model.getNumCascadePiles(); i++) {
        s += "C" + Integer.toString(i + 1) + ":";
        for (int j = 0; j < model.getNumCardsInCascadePile(i); j++) {
          s += " " + model.getCascadeCardAt(i, j).toString();
          if (j != model.getNumCardsInCascadePile(i) - 1) {
            s += ",";
          }
        }
        if (i != model.getNumCascadePiles() - 1) {
          s += "\n";
        }
      }
    } catch (IllegalStateException e) {
      return "";
    }
    return s;
  }

  @Override
  public void renderBoard() throws IOException {
    try {
      this.destination.append(this.toString());
    } catch (Exception e) {
      throw new IOException();
    }
  }

  @Override
  public void renderMessage(String message) throws IOException {
    try {
      this.destination.append(message);
    } catch (Exception e) {
      throw new IOException();
    }
  }
}
