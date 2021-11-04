package cs3500.freecell.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the various functions of the IPileInfo interface.
 */
public class IPileInfoTest {
  IPileInfo cascade1 = new SimplePileInfo(PileType.CASCADE, 1);
  IPileInfo foundation4 = new SimplePileInfo(PileType.FOUNDATION, 4);

  @Test
  public void getPileTypeWorks() {
    assertEquals(cascade1.getPileType(), PileType.CASCADE);
    assertEquals(foundation4.getPileType(), PileType.FOUNDATION);
  }

  @Test
  public void getPileIndexWorks() {
    assertEquals(cascade1.getPileIndex(), 1);
    assertEquals(foundation4.getPileIndex(), 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorBreaksForNullPileType() {
    IPileInfo nullPileType = new SimplePileInfo(null, 4);
  }
}