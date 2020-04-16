package cs3500.animator.shapes;

import java.awt.Color;

import cs3500.animator.misc.Position2D;
import cs3500.animator.model.EventInstructions;

/**
 * Represents a single shape. One instance of this object represents one shape. Shapes can be
 * mutated based on executing a given Event Instructions on them.
 */
public interface IShape {

  /**
   * The current position of the shape on an (x, y) grid.
   *
   * @return the current position
   */
  Position2D getPosition();

  /**
   * Gets the width of the shape.
   *
   * @return the width of the shape
   */
  int getWidth();

  /**
   * Gets the name of the shape.
   *
   * @return the name of the shape.
   */
  String getName();

  /**
   * Gets the height of the shape.
   *
   * @return the height of the shape
   */
  int getHeight();

  /**
   * Gets the color of the shape.
   *
   * @return the color of the shape
   */
  Color getColor();

  /**
   * Executes the given event instructions on the shape.
   *
   * @param e the event instructions
   */
  void executeInstructions(EventInstructions e);

  /**
   * Returns the name of the shape as a string.
   *
   * @return a string representing the shape type
   */
  String asString();

  /**
   * Gets a copy of this IShape.
   *
   * @return new IShape.
   */
  IShape copy();

  /**
   * Gets the angle that the shape is rotated.
   * @return a double representing the angle of rotation from the origin.
   */
  double getAngle();

}
