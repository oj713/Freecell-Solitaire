package cs3500.freecell;

import java.io.IOException;

/**
 * This is a class used to test that FreecellController appropriately catches IOExceptions.
 */
public class BogusAppendable implements Appendable {
  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException();
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException();
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException();
  }
}
