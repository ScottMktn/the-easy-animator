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
   * Gets the list of a shapes at a given tick from a layer.
   *
   * @param tick time tick at which we want to get the list of shapes at.
   * @return a copy of an ArrayList of shapes.
   */
  ArrayList<IShape> getShapesFromLayerAtTick(int layer, int tick);

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

  /**
   * The current layer that the model is set to.
   * Default is 0.
   * @return the current layer, previously set.
   * @throws UnsupportedOperationException if the model doesn't implement layers.
   */
  int getCurrentLayer();

  /**
   * How many layers does this model contain.
   * @return the number of layers in this model.
   * @throws UnsupportedOperationException if the model doesn't implement layers.
   */
  int layerCount();

  /**
   * Determines if the layer passed in is enabled or not.
   * @param layer the layer in question.
   * @return which lay is enabled or disabled.
   */
  boolean isEnabled(int layer);
}
