import org.junit.Before;
import org.junit.Test;

import cs3500.animator.misc.Position2D;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests any added functionality for Position2D.
 */
public class Position2DTest {
  Position2D pos4_6;
  Position2D pos10_6;
  Position2D pos50_76;

  @Before
  public void setup() {
    this.pos4_6 = new Position2D(4, 6);
    this.pos50_76 = new Position2D(50, 76);
    this.pos10_6 = new Position2D(10, 6);
  }

  @Test
  public void testCopy() {
    assertEquals(this.pos4_6, new Position2D(this.pos4_6));
  }

  @Test
  public void testHashCode() {
    assertEquals(this.pos4_6.hashCode(), new Position2D(this.pos4_6).hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("(4.000000, 6.000000)", this.pos4_6.toString());
  }

  @Test
  public void testEqualsOverride() {
    assertEquals(true, new Position2D(4, 6).equals(this.pos4_6));
  }

  @Test
  public void testEqualsSameObject() {
    assertEquals(true, this.pos4_6.equals(this.pos4_6));
  }

  @Test
  public void testEqualsNonPositionObject() {
    assertEquals(false, this.pos4_6.equals(1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRatioLessThanZero() {
    this.pos4_6.getPositionInBetween(pos50_76, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRatioGreaterThanOne() {
    this.pos4_6.getPositionInBetween(pos50_76, 2);
  }

  @Test
  public void testGetterMethods() {
    assertEquals(4.0, pos4_6.getX());
    assertEquals(6.0, pos4_6.getY());
  }


  @Test
  public void testGettingPositionInBetweenPoint5JustX() {
    assertEquals(7.0, pos4_6.getPositionInBetween(pos10_6, .5).getX(), .001);
  }

  @Test
  public void testGettingPositionInBetween1JustX() {
    assertEquals(10.0, pos4_6.getPositionInBetween(pos10_6, 1).getX(), .001);
  }

  @Test
  public void testGettingPositionInBetweenPoint5JustXCheckY() {
    assertEquals(6, pos4_6.getPositionInBetween(pos10_6, .5).getY(), .001);
  }

  @Test
  public void testGettingPositionInBetween1JustXCheckY() {
    assertEquals(6, pos4_6.getPositionInBetween(pos10_6, 1).getY(), .001);
  }


  @Test
  public void testGettingPositionInBetweenLargerNumbersWithXandY0Ratio1() {
    assertEquals(50, pos4_6.getPositionInBetween(pos50_76, 1).getX(), .001);
    assertEquals(76, pos4_6.getPositionInBetween(pos50_76, 1).getY(), .001);
  }

  @Test
  public void testGettingPositionInBetweenLargerNumbersWithXandY0Ratio0() {
    assertEquals(4, pos4_6.getPositionInBetween(pos50_76, 0).getX(), .001);
    assertEquals(6, pos4_6.getPositionInBetween(pos50_76, 0).getY(), .001);
  }

  @Test
  public void testGettingPositionInBetweenLargerNumbersWithXandYRatioPoint8() {
    assertEquals(42, pos10_6.getPositionInBetween(pos50_76, .8).getX(), .001);
  }

  @Test
  public void testGettingPositionInBetweenLargerNumbersWithXandYRatioPoint75() {
    assertEquals(40, pos10_6.getPositionInBetween(pos50_76, .75).getX(), .001);
  }
}
