package cs3500.animator.shapes;

import java.awt.Color;

import cs3500.animator.misc.Position2D;

/**
 * This class represents an ellipse shape. One object of this represents one singular rectangle.
 */
public class Ellipse extends AShape {

  /**
   * The constructor to make ellipses based on the given parameters.
   *
   * @param name     name of the object
   * @param position the position of the ellipse
   * @param width    the width of the ellipse
   * @param height   the height of the ellipse
   * @param color    the color of the ellipse
   */
  public Ellipse(String name, Position2D position, int width, int height, Color color) {
    super(name, position, width, height, color);
  }


  /**
   * The constructor to make ellipses based on the given parameters.
   *
   * @param name     name of the object
   * @param position the position of the ellipse
   * @param width    the width of the ellipse
   * @param height   the height of the ellipse
   * @param color    the color of the ellipse
   * @param angle    rotation angle of the shape
   */
  public Ellipse(String name, Position2D position,
                 int width, int height, Color color, double angle) {
    super(name, position, width, height, color, angle);
  }

  /**
   * Copy constructor, duplicates the object passed in.
   *
   * @param ellipse object we want to copy.
   */
  public Ellipse(Ellipse ellipse) {
    this(
            ellipse.getName(),
            new Position2D(ellipse.getPosition()),
            ellipse.getWidth(),
            ellipse.getHeight(),
            new Color(ellipse.getColor().getRGB()),
            ellipse.getAngle()
    );
  }

  /**
   * Returns the name of the shape as a 3 letter string.
   *
   * @return a string representing the shape type
   */
  @Override
  public String asString() {
    return "ell";
  }


  @Override
  public IShape copy() {
    return new Ellipse(this);
  }


}
