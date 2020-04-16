import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;

import cs3500.animator.misc.ErrorMessages;
import cs3500.animator.model.AnimatedShape;
import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.EventInstructions;
import cs3500.animator.model.IAnimatedShape;
import cs3500.animator.misc.Position2D;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.shapes.IShape;
import cs3500.animator.shapes.Rectangle;
import cs3500.animator.shapes.Triangle;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

/**
 * Houses all of the tests for the animation layerModel0 implementation.
 */
public class AnimationModelImplTest {
  IAnimatedShape as0;
  IAnimatedShape as1;
  EventInstructions testEvent0;
  IAnimationModel model;

  @Before
  public void setup() {
    as0 = new AnimatedShape(
            new Triangle("t0",
                    new Position2D(3, 3),
                    10,
                    4,
                    new Color(255)));
    as1 = new AnimatedShape(
            new Rectangle("r0",
                    new Position2D(3, 3),
                    10,
                    4,
                    new Color(255)));
    testEvent0 = new EventInstructions(
            10, 50, new Position2D(300, 300),
            50, 100, new Color(255, 0, 0));
    model = new AnimationModelImpl();
  }

  @Test
  public void testUnsupportedMethods0() {
    assertEquals(1, this.model.layerCount());
  }

  @Test
  public void testUnsupportedMethods1() {
    try {
      this.model.setCurrentLayer(1);
      fail();
    } catch (Exception e) {
      assertEquals("This model does not support layers", e.getMessage());
    }
  }

  @Test
  public void testUnsupportedMethods2() {
    try {
      this.model.setLayerState(0, true);
      fail();
    } catch (Exception e) {
      assertEquals("This model does not support layers", e.getMessage());
    }
  }

  @Test
  public void testUnsupportedMethods3() {
    assertEquals(0, this.model.getCurrentLayer());
  }

  @Test
  public void testRemovingKeyFrameFromShapeThatDoesNotExist() {
    try {
      model.removeKeyFrame("hello", 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Shape doesn't exist in model", e.getMessage());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testRemovingKeyFrameFromShapeInvalidIndex() {
    model.placeAnimatedShape(as1);
    try {
      model.removeKeyFrame("r0", -1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The integer: -1.0 is less than zero", e.getMessage());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testRemovingKeyFrameFromShapeInvalidIndex1() {
    as1.addEvent(testEvent0);
    model.placeAnimatedShape(as1);
    try {
      model.removeKeyFrame("r0", 2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("KeyEvent Doesn't exist at this time", e.getMessage());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testRemovingKeyFrameFromShapeFromFirstFrame() {

    as1.addEvent(testEvent0);
    model.placeAnimatedShape(as1);

    this.model.removeKeyFrame("r0", 0);
    IShape s = this.model.getAllAnimatedShapes().get(0).getShape();

    assertEquals(s.getPosition(), new Position2D(300, 300));
  }

  @Test
  public void testRemovingKeyFrameFromShapeFromSecondFrame() {

    as1.addEvent(testEvent0);
    model.placeAnimatedShape(as1);

    this.model.removeKeyFrame("r0", 1);
    IShape s = this.model.getAllAnimatedShapes().get(0).getShape();

    assertEquals(this.model.getAllAnimatedShapes().get(0).getEvents().size(), 1);

    assertEquals(s.getPosition(), new Position2D(3, 3));
  }

  @Test
  public void testNullParamInCopyConstructor() {
    try {
      new AnimationModelImpl(this.model);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid null parameter passed in",
              ErrorMessages.NullParameter.errorMessage());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testNullNameInPlaceKeyFrame() {
    try {
      this.model.placeKeyFrame(null, 0, new Position2D(0, 0),
              0, 0, new Color(255, 0, 0));
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid null parameter passed in",
              ErrorMessages.NullParameter.errorMessage());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testNullPositionInPlaceKeyFrame() {
    try {
      this.model.placeKeyFrame("Hello", 0, null,
              0, 0, new Color(255, 0, 0));
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid null parameter passed in",
              ErrorMessages.NullParameter.errorMessage());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testNullColorInPlaceKeyFrame() {
    try {
      this.model.placeKeyFrame("Hello", 0, new Position2D(0, 0),
              0, 0, null);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid null parameter passed in",
              ErrorMessages.NullParameter.errorMessage());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testSetArrayList() {
    this.model.placeAnimatedShape(as0);
    ArrayList<IAnimatedShape> testArrayList = new ArrayList<>();
    testArrayList.add(as1);
    assertEquals(as0.getShape().getName(),
            this.model.getAllAnimatedShapes().get(0).getShape().getName());
    this.model.setListOfAnimatedShapes(testArrayList);
    assertEquals(as1.getShape().getName(),
            this.model.getAllAnimatedShapes().get(0).getShape().getName());
  }

  @Test
  public void testNullSetArrayList() {
    try {
      this.model.setListOfAnimatedShapes(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid null parameter passed in",
              ErrorMessages.NullParameter.errorMessage());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testNullDeleteShape() {
    try {
      this.model.deleteShape(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid null parameter passed in",
              ErrorMessages.NullParameter.errorMessage());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testRemoveShape() {
    assertEquals(0, model.getAllAnimatedShapes().size());
    model.placeAnimatedShape(as0);
    assertEquals(1, model.getAllAnimatedShapes().size());
    model.deleteShape("r0");
    assertEquals(1, model.getAllAnimatedShapes().size());
    model.deleteShape("t0");
    assertEquals(0, model.getAllAnimatedShapes().size());
  }

  @Test
  public void testAddEventInstructionToShape() {
    model.placeAnimatedShape(as1);
    assertEquals(0, as1.getEvents().size());
    model.addEventInstructionToShape("r0", testEvent0);
    assertEquals(1, as1.getEvents().size());
    assertEquals(testEvent0.getStartTick(), as1.getEvents().get(0).getStartTick());
    assertEquals(testEvent0.getEndTick(), as1.getEvents().get(0).getEndTick());
    assertEquals(testEvent0.getEndHeight(), as1.getEvents().get(0).getEndHeight());
    assertEquals(testEvent0.getEndWidth(), as1.getEvents().get(0).getEndWidth());
    assertEquals(testEvent0.getEndPosn(), as1.getEvents().get(0).getEndPosn());
    assertEquals(testEvent0.getEndColor(), as1.getEvents().get(0).getEndColor());
  }

  @Test
  public void testAddEventInstructionToWrongShape() {
    model.placeAnimatedShape(as1);
    assertEquals(0, as1.getEvents().size());
    model.addEventInstructionToShape("t0", testEvent0);
    assertEquals(0, as1.getEvents().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullAnimatedShapeInPlaceAnimatedShape() {
    model.placeAnimatedShape(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullShapeInAddEventInstructionToShape() {
    model.addEventInstructionToShape(null, testEvent0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullEventInstructionsInAddEventInstructionToShape() {
    model.addEventInstructionToShape("r0", null);
  }

  @Test
  public void testSetAndGetBounds() {
    AnimationModelImpl test = new AnimationModelImpl();
    test.setBounds(0, 0, 1000, 1000);
    java.awt.Rectangle rectangle = new java.awt.Rectangle(0, 0, 1000, 1000);
    assertEquals(rectangle, test.getBounds());
  }

  @Test
  public void testConstructNoArguments() {
    AnimationModelImpl simple = new AnimationModelImpl();
    assertEquals(0, simple.getShapesAt(70).size());
  }

  @Test
  public void testConstructNoArgumentsAddedSameEventError() {
    AnimationModelImpl simple = new AnimationModelImpl();
    simple.placeAnimatedShape(as0);
    try {
      simple.placeAnimatedShape(new AnimatedShape(as0));
      fail();
    } catch (Exception e) {
      assertEquals("Shape already exists in this model!", e.getMessage());
    }
  }

  @Test
  public void testConstructAddASingleShape() {
    AnimationModelImpl simple = new AnimationModelImpl();
    as0.addEvent(testEvent0);
    simple.placeAnimatedShape(as0);

    assertEquals("t0", simple.getShapesAt(20).get(0).getName());
    assertEquals(1, simple.getShapesAt(20).size());
  }

  @Test
  public void testConstructAddTwoShapes() {
    AnimationModelImpl simple = new AnimationModelImpl();

    as0.addEvent(testEvent0);
    simple.placeAnimatedShape(as0);


    assertEquals("t0", simple.getShapesAt(20).get(0).getName());
    assertEquals(1, simple.getShapesAt(20).size());

    as1.addEvent(testEvent0);
    simple.placeAnimatedShape(as1);

    assertEquals("t0", simple.getShapesAt(20).get(0).getName());
    assertEquals("r0", simple.getShapesAt(20).get(1).getName());
    assertEquals(2, simple.getShapesAt(20).size());
  }


  @Test
  public void testConstructNoArgumentsAddingTriangle() {
    AnimationModelImpl simple = new AnimationModelImpl();
    assertEquals(0, simple.getShapesAt(70).size());

    as0.addEvent(testEvent0);
    simple.placeAnimatedShape(as0);
    assertEquals(0, simple.getShapesAt(9).size());
    assertEquals(1, simple.getShapesAt(10).size());
    assertEquals(1, simple.getShapesAt(11).size());
    assertEquals(1, simple.getShapesAt(49).size());
    assertEquals(1, simple.getShapesAt(50).size());
    assertEquals(0, simple.getShapesAt(51).size());

    assertEquals(as0.getShape().getName(), simple.getShapesAt(35).get(0).getName());
  }

  @Test
  public void testConstructNoArgumentsAddingTriangleAndRectangle() {
    AnimationModelImpl simple = new AnimationModelImpl();
    assertEquals(0, simple.getShapesAt(70).size());
    AnimatedShape as0 = new AnimatedShape(
            new Triangle("t0",
                    new Position2D(3, 3),
                    10,
                    4,
                    new Color(255)));
    EventInstructions testEvent0 = new EventInstructions(
            10, 50, new Position2D(300, 300),
            50, 100, new Color(255, 0, 0));

    as0.addEvent(testEvent0);
    simple.placeAnimatedShape(as0);
    assertEquals(0, simple.getShapesAt(9).size());
    assertEquals(1, simple.getShapesAt(10).size());
    assertEquals(1, simple.getShapesAt(11).size());
    assertEquals(1, simple.getShapesAt(49).size());
    assertEquals(1, simple.getShapesAt(50).size());
    assertEquals(0, simple.getShapesAt(51).size());

    assertEquals(as0.getShape().getName(), simple.getShapesAt(35).get(0).getName());
  }

}
