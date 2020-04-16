import org.junit.Before;
import org.junit.Test;

import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.view.AnimationPanel;
import cs3500.animator.view.IAnimationView;
import cs3500.animator.view.VisualView;

import static org.junit.Assert.assertEquals;

/**
 * Holds the tests for the Animation Panel and the Visual View.
 */
public class PanelAndViewTest {
  IAnimationModel model;
  AnimationPanel panel;
  IAnimationView visualView;

  @Before
  public void setup() {
    model = new AnimationModelImpl();
    panel = new AnimationPanel(model);
    visualView = new VisualView(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullParamForPanelConstructor() {
    new AnimationPanel(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullParamForViewConstructor() {
    new VisualView(1).display(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTempoForViewConstructor() {
    new VisualView(-1);
  }

  @Test
  public void testSetCurTick() {
    assertEquals(0, panel.getCurTick());
    panel.setCurTick(10);
    assertEquals(10, panel.getCurTick());
  }
}
