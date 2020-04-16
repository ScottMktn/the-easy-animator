import org.junit.Test;

import cs3500.animator.misc.Utils;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

/**
 * This class houses all of the tests for the utilities class.
 */
public class UtilsTest {
  @Test
  public void testValidReqNonNegative() {
    try {
      Utils.requireNonNegative(2, 1, 3);
      return;
    }
    catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testInvalidReqNonNegative() {
    try {
      Utils.requireNonNegative(2, -1, 3);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("The integer: -1.0 is less than zero", e.getMessage());
    }
    catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testValidReqNonZero() {
    try {
      Utils.requireNonZero(1, -1, 5);
      return;
    }
    catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testInvalidReqNonZero() {
    try {
      Utils.requireNonZero(1, 0, -5);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("The integer: 0.0 is zero", e.getMessage());
    }
    catch (Exception e) {
      fail();
    }
  }
}
