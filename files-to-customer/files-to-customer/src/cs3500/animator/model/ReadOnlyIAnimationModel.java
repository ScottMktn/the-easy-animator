package cs3500.animator.model;

import java.util.ArrayList;

import cs3500.animator.shapes.IShape;

/**
 * Represents an Animation Model that can only be read from and no writing back to the data
 * structure.
 */
public interface ReadOnlyIAnimationModel {

  /**
   * Gets the list of a shapes at a given tick.
   *
   * @param tick time tick at which we want to get the list of shapes at.
   * @return a copy of an ArrayList of shapes.
   */
  ArrayList<IShape> getShapesAt(int tick);

  /**
   * Gets all the animated shapes from the model. Copies a each element in the list.
   *
   * @return an ArrayList of IAnimatedShape.
   */
  ArrayList<IAnimatedShape> getAllAnimatedShapes();

  /**
   * Gets the specified the bounding box to be used for the animation.
   *
   * @return a rectangle object that represents the bounds.
   */
  java.awt.Rectangle getBounds();
}
