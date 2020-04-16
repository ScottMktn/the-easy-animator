import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import cs3500.animator.model.EventInstructions;
import cs3500.animator.misc.Position2D;
import cs3500.animator.shapes.IShape;
import cs3500.animator.shapes.Triangle;
import cs3500.animator.shapes.Ellipse;
import cs3500.animator.shapes.Rectangle;

import static junit.framework.TestCase.assertEquals;


/**
 * This class houses all of the tests for the shapes package.
 */
public class ShapeTests {
  IShape rectangle1;
  IShape ellipse1;
  IShape triangle1;
  EventInstructions testEvent1;
  EventInstructions testEvent2;

  @Before
  public void initData() {
    rectangle1 = new Rectangle("r0", new Position2D(200, 200),
            50, 100, new Color(255, 0, 0));
    ellipse1 = new Ellipse("e0", new Position2D(440, 70),
            120, 60, new Color(0, 0, 255));
    triangle1 = new Triangle("t0", new Position2D(100, 100),
            50, 100, new Color(5, 40, 0));

    testEvent1 = new EventInstructions(
            10, 50, new Position2D(300, 300),
            50, 100, new Color(255, 0, 0));
    testEvent2 = new EventInstructions(
            51, 70, new Position2D(300, 300),
            25, 100, new Color(255, 0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullName() {
    new Rectangle(null, new Position2D(200, 200), 50, 100,
            new Color(255, 0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyName() {
    new Rectangle("", new Position2D(200, 200), 50, 100,
            new Color(255, 0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullEventInstructionsInExecute() {
    rectangle1.executeInstructions(null);
  }

  @Test
  public void testAsString() {
    assertEquals("rec", rectangle1.asString());
    assertEquals("ell", ellipse1.asString());
  }

  @Test
  public void testGetMethods() {
    assertEquals("r0", rectangle1.getName());
    assertEquals(new Position2D(200, 200), rectangle1.getPosition());
    assertEquals(50, rectangle1.getWidth());
    assertEquals(100, rectangle1.getHeight());
    assertEquals(new Color(255, 0, 0), rectangle1.getColor());
  }

  @Test
  public void testExecuteInstructions() {
    testGetMethods();

    rectangle1.executeInstructions(testEvent1);

    // Test for position change
    assertEquals(new Position2D(300, 300), rectangle1.getPosition());
    assertEquals(50, rectangle1.getWidth());
    assertEquals(100, rectangle1.getHeight());
    assertEquals(new Color(255, 0, 0), rectangle1.getColor());

    rectangle1.executeInstructions(testEvent2);

    // Test for dimension change
    assertEquals(new Position2D(300, 300), rectangle1.getPosition());
    assertEquals(25, rectangle1.getWidth());
    assertEquals(100, rectangle1.getHeight());
    assertEquals(new Color(255, 0, 0), rectangle1.getColor());
  }




}
