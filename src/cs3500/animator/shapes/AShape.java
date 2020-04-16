package cs3500.animator.shapes;


import java.awt.Color;
import java.util.Objects;

import cs3500.animator.misc.Position2D;
import cs3500.animator.misc.Utils;
import cs3500.animator.model.EventInstructions;

/**
 * An abstract class representing the different shapes we might have in the game. This class will
 * never be instantiated or passed anywhere. Graphical representation of shape and it's position:
 * <pre>
 * -------------x
 * |
 * |
 * |   shapes
 * |
 * |
 * y
 * </pre>
 */
public abstract class AShape implements IShape {
  private Position2D position; // INVARIANT: Position must be positive
  private int width; // INVARIANT: Position must be positive
  private int height; // INVARIANT: Position must be positive
  private Color color;
  private String name; // INVARIANT: Name must not be null or non empty
  private double angle;
  // INVARIANTS: Width and height are greater than or equal to zero

  /**
   * Constructor for a shape that takes in all fields.
   *
   * @param name     name of the object.
   * @param position the position of the shape
   * @param width    the width of the shape
   * @param height   the height of the shape
   * @param color    the color of the shape
   * @throws IllegalArgumentException if any of the parameters are invalid per the parameters above
   */
  AShape(String name, Position2D position, int width, int height, Color color) {
    Utils.requireNonNegative(width, height);
    if (Objects.isNull(name) || name.equals("")) {
      throw new IllegalArgumentException("Please pass in a valid name");
    }
    this.name = name;
    this.position = position;
    this.width = width;
    this.height = height;
    this.color = color;
  }


  /**
   * Constructor for a shape that takes in all fields.
   *
   * @param name     name of the object.
   * @param position the position of the shape
   * @param width    the width of the shape
   * @param height   the height of the shape
   * @param color    the color of the shape
   * @throws IllegalArgumentException if any of the parameters are invalid per the parameters above
   */
  AShape(String name, Position2D position, int width, int height, Color color, double angle) {
    Utils.requireNonNegative(width, height);
    if (Objects.isNull(name) || name.equals("")) {
      throw new IllegalArgumentException("Please pass in a valid name");
    }
    this.name = name;
    this.position = position;
    this.width = width;
    this.height = height;
    this.color = color;
    this.angle = angle;
  }

  /**
   * Executes the given event instructions on the shape. Makes all of the fields for the shape the
   * end fields of the event instructions passed in.
   *
   * @param e the event instructions
   */
  public void executeInstructions(EventInstructions e) {
    if (Objects.isNull(e)) {
      throw new IllegalArgumentException("Please pass in a non-null event instructions");
    }
    this.position = e.getEndPosn();
    this.width = e.getEndWidth();
    this.height = e.getEndHeight();
    this.color = e.getEndColor();
    this.angle = e.getEndAngle();
  }

  /**
   * The current position of the shape on an (x, y) grid.
   *
   * @return the current position
   */
  public Position2D getPosition() {
    return this.position;
  }

  /**
   * Gets the name of the shape.
   *
   * @return the name of the shape.
   */
  public String getName() {
    return this.name;
  }


  /**
   * Gets the width of the shape.
   *
   * @return the width of the shape
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Gets the height of the shape.
   *
   * @return the height of the shape
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Gets the color of the shape.
   *
   * @return the color of the shape
   */
  public Color getColor() {
    return this.color;
  }

  /**
   * Returns the name of the shape as a 3 letter string.
   *
   * @return a string representing the shape type
   */
  @Override
  public abstract String asString();

  @Override
  public double getAngle() {
    return this.angle;
  }
}
