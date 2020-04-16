package cs3500.animator.shapes;

import java.awt.Color;

import cs3500.animator.misc.Position2D;

/**
 * This class represents a rectangle shape. One object of this represents one singular rectangle.
 */
public class Rectangle extends AShape {

  /**
   * The constructor to make rectangles with the given parameters.
   *
   * @param position the position of the rectangle
   * @param width    the width of the rectangle
   * @param height   the height of the rectangle
   * @param color    the color of the rectangle
   */
  public Rectangle(String name, Position2D position, int width, int height, Color color) {
    super(name, position, width, height, color);
  }


  /**
   * The constructor to make rectangles with the given parameters.
   *
   * @param position the position of the rectangle
   * @param width    the width of the rectangle
   * @param height   the height of the rectangle
   * @param color    the color of the rectangle
   * @param angle    rotation angle of the shape
   */
  public Rectangle(String name, Position2D position, int width,
                   int height, Color color, double angle) {
    super(name, position, width, height, color, angle);
  }

  /**
   * Copy constructor, duplicates the object passed in.
   */
  public Rectangle(Rectangle rectangle) {
    this(
            rectangle.getName(),
            new Position2D(rectangle.getPosition()),
            rectangle.getWidth(),
            rectangle.getHeight(),
            new Color(rectangle.getColor().getRGB()),
            rectangle.getAngle()
    );
  }

  /**
   * Returns the name of the shape as a 3 letter string.
   *
   * @return a string representing the shape type
   */
  @Override
  public String asString() {
    return "rec";
  }

  /**
   * Gets a copy of this rectangle.
   *
   * @return new rectangle
   */
  @Override
  public IShape copy() {
    return new Rectangle(this);
  }
}
