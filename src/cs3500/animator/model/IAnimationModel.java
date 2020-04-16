package cs3500.animator.model;

import java.awt.Color;
import java.util.ArrayList;

import cs3500.animator.misc.Position2D;

/**
 * Represents the model in the model view controller design pattern. Will deliver list of shapes at
 * a given time and any interactions in the animation. Contains 4 methods inside of it:
 * -placeAnimatedShape(IAnimatedShape animatedShape) -getShapesAt(int tick) -getAllAnimatedShapes()
 * -AddEventInstructionToShape(String name, EventInstructions e)
 * The animation model extends the ReadOnly interface which contains the two getter methods. The
 * animation model contains a list of IAnimatedShapes, which are objects that contain a shape and
 * its corresponding event instructions.
 */
public interface IAnimationModel extends ReadOnlyIAnimationModel {

  /**
   * Sets the list of animated shapes in the model to the passed in array list of animated shapes.
   * Checks to see if the passed in arraylist is null.
   * @throws IllegalArgumentException if the arrayList passed in is a null object
   */
  void setListOfAnimatedShapes(ArrayList<IAnimatedShape> animatedShapeArrayList);

  /**
   * Deletes the shape with the given id in the animation model.
   * @param id the string id of the shape in the model
   * @throws IllegalArgumentException if the passed in string is null
   */
  void deleteShape(String id);

  /**
   * Adds an animated shape to the list of shapes in this model.
   *
   * @param animatedShape the shape that we want to call the event instruction on.
   * @throws IllegalArgumentException if the shape exists, or object is null.
   */
  void placeAnimatedShape(IAnimatedShape animatedShape);

  /**
   * Adds the given event instructions to the appropriate shape based on its shape name.
   *
   * @param name the name of the shape
   * @param e    the event instructions that we want to add to the shape
   * @throws IllegalArgumentException if the name or the event instructions are null.
   */
  void addEventInstructionToShape(String name, EventInstructions e);

  /**
   * Specify the bounding box to be used for the animation.
   *
   * @param x      The leftmost x value
   * @param y      The topmost y value
   * @param width  The width of the bounding box
   * @param height The height of the bounding box
   */
  void setBounds(int x, int y, int width, int height);

  /**
   * Places a keyframe at a given tick value (t) with the given parameters.
   *
   * @param name the name of the shape we want to add the keyframe to.
   * @param t    the tick at which we want to add the key frame.
   * @param p    Position.
   * @param w    Width.
   * @param h    Height.
   * @param c    Color.
   * @throws IllegalArgumentException if the name, position, or color are null.
   */
  void placeKeyFrame(String name, int t, Position2D p, int w, int h, Color c);

  /**
   * Removes the keyFrame for the given shape name and that eventInstruction at a given keyFrame.
   * @param name the name of the shape we want to remove.
   * @param index the index of the keyFrame we want to remove.
   */
  void removeKeyFrame(String name, int index);

  /**
   * Sets the current layer of the model.
   * @param layer the new layer we want to set.
   * @throws IllegalArgumentException if the layer number doesn't exist.
   * @throws UnsupportedOperationException if the model doesn't implement layers.
   */
  void setCurrentLayer(int layer);

  /**
   * Adds a layer to the current number of layers in the model.
   * @throws UnsupportedOperationException if the model doesn't implement layers.
   */
  void addLayer();

  /**
   * Turns a layer to be enabled or disabled. Can be used to determine what layer we want
   * to display in the view (possibly).
   * @param layer the layer we want to toggle.
   * @param enabled the toggle state, true is enabled, false is disabled.
   */
  void setLayerState(int layer, boolean enabled);

  /**
   * Deletes the specified layer.
   * @param layer that we want to delete.
   */
  void deleteLayer(int layer);


  /**
   * Moves the currently selected layer up or down a given direction.
   * @param direction positive or minus direction we want to move the layer to.
   */
  void moveCurrentLayer(int direction);

  /**
   * Inherits from ReadOnly:
   *
   * ArrayList<IShape> getShapesAt(int tick);
   *
   * ArrayList<IAnimatedShape> getAllAnimatedShapes();
   */
}
