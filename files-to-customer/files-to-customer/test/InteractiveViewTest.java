import org.junit.Before;
import org.junit.Test;

import cs3500.animator.misc.ErrorMessages;
import cs3500.animator.view.IInteractiveView;
import cs3500.animator.view.InteractiveVisualView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Houses all of the tests for the interactive visual view implementation.
 */
public class InteractiveViewTest {
  private int tempo;
  private IInteractiveView testInteractiveView;

  @Before
  public void setup() {
    this.tempo = 10;
    this.testInteractiveView = new InteractiveVisualView(this.tempo);
  }

  @Test
  public void testInvalidTempo() {
    try {
      new InteractiveVisualView(-1);
    } catch (IllegalArgumentException e) {
      assertEquals("The integer: " + -1.0 + " is less than zero", e.getMessage());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testNullParamInDisplay() {
    try {
      this.testInteractiveView.display(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid null parameter passed in",
              ErrorMessages.NullParameter.errorMessage());
    } catch (Exception e) {
      fail("" + tempo);
    }
  }

  @Test
  public void testNullParamInSetListener() {
    try {
      this.testInteractiveView.setListener(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid null parameter passed in",
              ErrorMessages.NullParameter.errorMessage());
    } catch (Exception e) {
      fail();
    }
  }
}













