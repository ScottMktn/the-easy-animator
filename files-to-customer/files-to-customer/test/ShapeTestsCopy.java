import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;

import cs3500.animator.misc.Position2D;
import cs3500.animator.shapes.Ellipse;
import cs3500.animator.shapes.IShape;
import cs3500.animator.shapes.Rectangle;
import cs3500.animator.shapes.Triangle;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests the shape copy constructors and methods.
 */
@RunWith(Parameterized.class)
public class ShapeTestsCopy {

  private static void loadConstructorsCopyForData(ArrayList<Object[]> constructors) {

    Triangle triangleOriginal0 = new Triangle("t0", new Position2D(100, 100),
            50, 100, new Color(5, 40, 0));
    IShape triangleCopy1 = new Triangle(triangleOriginal0);

    constructors.add(new Object[]{triangleOriginal0, triangleCopy1});

    Ellipse ellipseOriginal0 = new Ellipse("e0", new Position2D(100, 100),
            50, 100, new Color(5, 40, 0));
    IShape ellipseCopy1 = new Ellipse(ellipseOriginal0);

    constructors.add(new Object[]{triangleOriginal0, triangleCopy1});

    Rectangle rectangleOriginal0 = new Rectangle("r0", new Position2D(100, 100),
            50, 100, new Color(5, 40, 0));
    IShape rectangleCopy1 = new Rectangle(rectangleOriginal0);
    constructors.add(new Object[]{rectangleOriginal0, rectangleCopy1});
  }

  private static void loadCopiedShapesForData(ArrayList<Object[]> constructors) {

    Triangle triangleOriginal0 = new Triangle("t0", new Position2D(100, 100),
            50, 100, new Color(5, 40, 0));
    IShape triangleCopy1 = triangleOriginal0.copy();

    constructors.add(new Object[]{triangleOriginal0, triangleCopy1});

    Ellipse ellipseOriginal0 = new Ellipse("e0", new Position2D(100, 100),
            50, 100, new Color(5, 40, 0));
    IShape ellipseCopy1 = ellipseOriginal0.copy();

    constructors.add(new Object[]{triangleOriginal0, triangleCopy1});

    Rectangle rectangleOriginal0 = new Rectangle("r0", new Position2D(100, 100),
            50, 100, new Color(5, 40, 0));
    IShape rectangleCopy1 = rectangleOriginal0.copy();
    constructors.add(new Object[]{rectangleOriginal0, rectangleCopy1});
  }

  /**
   * Initializes all the data params for each of the tests.
   */
  @Parameterized.Parameters
  public static Collection data() {
    ArrayList<Object[]> constructors = new ArrayList<Object[]>();
    loadConstructorsCopyForData(constructors);
    loadCopiedShapesForData(constructors);
    return constructors;
  }

  IShape original;
  IShape copied;

  public ShapeTestsCopy(IShape original, IShape copied) {
    this.original = original;
    this.copied = copied;
  }

  @Test
  public void testCopiedAsString() {
    assertEquals(this.original.asString(), this.copied.asString());
  }

  @Test
  public void testCopiedName() {
    assertEquals(this.original.getName(), this.copied.getName());
  }

  @Test
  public void testCopiedPosition() {
    assertEquals(this.original.getPosition(), this.copied.getPosition());
  }


  @Test
  public void testCopiedColorRGB() {
    assertEquals(this.original.getColor().getRGB(), this.copied.getColor().getRGB());
  }

  @Test
  public void testCopiedColorString() {
    assertEquals(this.original.getColor().toString(), this.copied.getColor().toString());
  }

  @Test
  public void testCopiedWidth() {
    assertEquals(this.original.getWidth(), this.copied.getWidth());
  }

  @Test
  public void testCopiedHeight() {
    assertEquals(this.original.getHeight(), this.copied.getHeight());
  }
}
