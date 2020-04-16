package cs3500.animator.shapes;

import java.awt.Color;

import cs3500.animator.misc.Position2D;

/**
 * This class represents a Triangle shape. One object of this represents one singular Triangle.
 */
public class Triangle extends AShape {

  /**
   * The constructor to make triangles based on the given parameters.
   *
   * @param name     the name of the triangle.
   * @param position the position of the triangle
   * @param width    the width of the triangle
   * @param height   the height of the triangle
   * @param color    the color of the triangle
   */
  public Triangle(String name, Position2D position, int width, int height, Color color) {
    super(name, position, width, height, color);
  }

  /**
   * Copy constructor, duplicates the Triangle object passed in.
   *
   * @param triangle object we want to copy.
   */
  public Triangle(Triangle triangle) {
    this(
            triangle.getName(),
            new Position2D(triangle.getPosition()),
            triangle.getWidth(),
            triangle.getHeight(),
            new Color(triangle.getColor().getRGB())
    );
  }

  /**
   * Returns the name of the shape as a 3 letter string.
   *
   * @return a string representing the shape type
   */
  @Override
  public String asString() {
    return "tri";
  }

  @Override
  public IShape copy() {
    return new Triangle(this);
  }
}