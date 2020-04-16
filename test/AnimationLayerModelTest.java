import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import cs3500.animator.misc.Position2D;
import cs3500.animator.model.AnimatedShape;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.model.LayeredModelImpl;
import cs3500.animator.shapes.Rectangle;
import cs3500.animator.shapes.Triangle;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

/**
 * Tests the LayerModel.
 */
public class AnimationLayerModelTest {
  IAnimationModel layerModel0;
  IAnimationModel layerModel1;


  @Before
  public void setup() {
    layerModel0 = new LayeredModelImpl();
    layerModel1 = new LayeredModelImpl();
    layerModel1.addLayer();

  }

  @Test
  public void initLayerIs0() {
    assertEquals(0, this.layerModel0.getCurrentLayer());
    assertEquals(0, this.layerModel1.getCurrentLayer());
  }

  @Test
  public void layerCount() {
    assertEquals(1, this.layerModel0.layerCount());
    assertEquals(2, this.layerModel1.layerCount());
  }

  @Test
  public void testEnabledSetting() {
    assertEquals(true, this.layerModel1.isEnabled(0));
    this.layerModel1.setLayerState(0, true);
    assertEquals(true, this.layerModel1.isEnabled(0));
  }

  @Test
  public void testDeletingALayer0() {
    assertEquals(2, this.layerModel1.layerCount());
    this.layerModel1.setCurrentLayer(0);
    assertEquals(0, this.layerModel1.getAllAnimatedShapes().size());

    this.layerModel1.placeAnimatedShape(new AnimatedShape(
            new Rectangle("r0",
                    new Position2D(3, 3),
                    10,
                    4,
                    new Color(255))));

    assertEquals(1, this.layerModel1.getAllAnimatedShapes().size());

    this.layerModel1.deleteLayer(0);


    assertEquals(0, this.layerModel1.getCurrentLayer());
    assertEquals(1, this.layerModel1.layerCount());
    assertEquals(0, this.layerModel1.getCurrentLayer());

    assertEquals(0, this.layerModel1.getAllAnimatedShapes().size());
  }

  @Test
  public void testDeletingALayerCheckingWhileOnADifferntLayer() {
    assertEquals(2, this.layerModel1.layerCount());
    this.layerModel1.setCurrentLayer(0);
    assertEquals(0, this.layerModel1.getAllAnimatedShapes().size());

    this.layerModel1.placeAnimatedShape(new AnimatedShape(
            new Rectangle("r0",
                    new Position2D(3, 3),
                    10,
                    4,
                    new Color(255))));

    assertEquals(1, this.layerModel1.getAllAnimatedShapes().size());

    this.layerModel1.setCurrentLayer(1);

    this.layerModel1.deleteLayer(0);


    assertEquals(0, this.layerModel1.getCurrentLayer());
    assertEquals(1, this.layerModel1.layerCount());
    assertEquals(0, this.layerModel1.getCurrentLayer());

    assertEquals(0, this.layerModel1.getAllAnimatedShapes().size());
  }

  @Test
  public void testMoveLayer() {
    assertEquals(2, this.layerModel1.layerCount());
    this.layerModel1.setCurrentLayer(0);
    assertEquals(0, this.layerModel1.getAllAnimatedShapes().size());

    this.layerModel1.placeAnimatedShape(new AnimatedShape(
            new Rectangle("r0",
                    new Position2D(3, 3),
                    10,
                    4,
                    new Color(255))));

    assertEquals(1, this.layerModel1.getAllAnimatedShapes().size());

    this.layerModel1.setCurrentLayer(0);
    this.layerModel1.moveCurrentLayer(1);

    assertEquals(2, this.layerModel1.layerCount());

    assertEquals(0, this.layerModel1.getAllAnimatedShapes().size());

    this.layerModel1.setCurrentLayer(1);
    assertEquals(1, this.layerModel1.getAllAnimatedShapes().size());


  }

  @Test
  public void testMoveLayerInvalid() {
    this.layerModel1.setCurrentLayer(0);
    try {
      this.layerModel1.moveCurrentLayer(-1);
    } catch (Exception e) {
      assertEquals("invalid direction", e.getMessage());
    }
  }

  @Test
  public void checkThatSettingTheCurrentLayerOutOfBoundsThrowsException() {
    try {
      this.layerModel0.setCurrentLayer(1);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid Layer", e.getMessage());
    }
  }

  @Test
  public void addShapesToOneLayer() {

    AnimatedShape as0 = new AnimatedShape(
            new Triangle("t0",
                    new Position2D(3, 3),
                    10,
                    4,
                    new Color(255)));
    AnimatedShape as1 = new AnimatedShape(
            new Rectangle("r0",
                    new Position2D(3, 3),
                    10,
                    4,
                    new Color(255)));
    this.layerModel1.placeAnimatedShape(as0);
    this.layerModel1.setCurrentLayer(1);
    this.layerModel1.placeAnimatedShape(as1);
    this.layerModel1.setCurrentLayer(0);
    assertEquals("t0",
            this.layerModel1.getAllAnimatedShapes().get(0).getShape().getName());

  }

  @Test
  public void addShapesToOneLayer1() {

    AnimatedShape as0 = new AnimatedShape(
            new Triangle("t0",
                    new Position2D(3, 3),
                    10,
                    4,
                    new Color(255)));
    AnimatedShape as1 = new AnimatedShape(
            new Rectangle("r0",
                    new Position2D(3, 3),
                    10,
                    4,
                    new Color(255)));
    this.layerModel1.placeAnimatedShape(as0);
    this.layerModel1.setCurrentLayer(1);
    this.layerModel1.placeAnimatedShape(as1);
    this.layerModel1.setCurrentLayer(1);
    assertEquals("r0",
            this.layerModel1.getAllAnimatedShapes().get(0).getShape().getName());

  }
}
