package cs3500.animator.model;

import java.awt.Color;
import java.util.ArrayList;

import cs3500.animator.misc.Position2D;
import cs3500.animator.shapes.IShape;

/**
 * Represents an Object with a Shape and EventInstructions that can be used for animating the shape
 * it contains.
 */
public interface IAnimatedShape {
  /**
   * Gets a copy of the local shape for this animated class.
   *
   * @return an IShape inside this class.
   */
  IShape getShape();

  /**
   * Gets a copy of the events this animation represents.
   *
   * @return a new ArrayList of EventInstructions.
   */
  ArrayList<EventInstructions> getEvents();


  /**
   * Gets a version of this shape at a given tick.
   *
   * @param tick the tick that we want the shape at. Must be > 0.
   * @throws IllegalArgumentException if the shape doesn't exist.
   * @returns a copy of the shape.
   */
  IShape getShapeAtTick(int tick);

  /**
   * Adds a copy of the event to the list of events.
   *
   * @param event EventInstruction that we want to add.
   * @throws IllegalArgumentException if the end time of the last added element is not the same as
   *                                  the start time of the new event.
   */
  void addEvent(EventInstructions event);


  /**
   * Places a keyframe at a given tick value (t) with the given parameters.
   *
   * @param t the tick at which we want to add the key frame.
   * @param p Position.
   * @param w Width.
   * @param h Height.
   * @param c Color.
   */
  void placeKeyFrame(int t, Position2D p, int w, int h, Color c);

  /**
   * Removes the KeyFrame at this index.
   * @param index that we want to remove the keyframe from this shape.
   */
  void removeKeyFrame(int index);

}
