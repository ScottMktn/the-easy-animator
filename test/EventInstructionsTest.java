import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import cs3500.animator.model.EventInstructions;
import cs3500.animator.misc.Position2D;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

/**
 * This class contains all of the tests for the event instructions class.
 */
public class EventInstructionsTest {
  EventInstructions testEvent1;

  @Before
  public void initData() {
    testEvent1 = new EventInstructions(
            10, 50, new Position2D(300, 300),
            50, 100, new Color(255, 0, 0));
  }

  @Test
  public void testEndTickLessThanStartTick() {
    try {
      new EventInstructions(10, 9, new Position2D(300, 300),
              50, 100, new Color(255, 0, 0));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The end tick must be greater than the start tick.", e.getMessage());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testCopyConstructor() {
    EventInstructions copy = new EventInstructions(testEvent1);
    assertEquals(copy.getStartTick(), testEvent1.getStartTick());
    assertEquals(copy.getEndTick(), testEvent1.getEndTick());
    assertEquals(copy.getEndPosn(), testEvent1.getEndPosn());
    assertEquals(copy.getEndWidth(), testEvent1.getEndWidth());
    assertEquals(copy.getEndHeight(), testEvent1.getEndHeight());
    assertEquals(copy.getEndColor().getRGB(), testEvent1.getEndColor().getRGB());
  }

  @Test
  public void testGetMethods() {
    assertEquals(10, testEvent1.getStartTick());
    assertEquals(50, testEvent1.getEndTick());
    assertEquals(new Position2D(300, 300), testEvent1.getEndPosn());
    assertEquals(50, testEvent1.getEndWidth());
    assertEquals(100, testEvent1.getEndHeight());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidStartTime() {
    new EventInstructions(
            -10, 50, new Position2D(300, 300),
            50, 100, new Color(255, 0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidEndTime() {
    new EventInstructions(
            10, -50, new Position2D(300, 300),
            50, 100, new Color(255, 0, 0));
  }

}
