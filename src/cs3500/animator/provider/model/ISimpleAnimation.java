package cs3500.animator.provider.model;

import java.util.ArrayList;

import cs3500.animator.provider.util.AnimationBuilder;
import cs3500.animator.provider.util.Canvas;

/**
 * Allows the user to create numerous, uniquely named shapes and apply changes to those
 * shapes, if they desire, to be animated in their choice of Views.
 */
interface ISimpleAnimation {

  /**
   * Adds a new shape to the animation.
   *
   * @param name The unique name of the shape to be added.
   *             No shape with this name should already exist.
   * @param type The type of shape (e.g. "ellipse", "rectangle") to be added.
   *             The set of supported shapes is unspecified, but should
   *             include "ellipse" and "rectangle" as a minimum.
   */
  void declareShape(String name, String type);

  /**
   * Removes a previously created shape from the animation.
   *
   * @param name the unique name of a user-created shape.
   */
  void removeShape(String name);

  /**
   * Prints the values for each unique shape the user has created for an animation.
   *
   * @return the string representation for all shapes in the animation.
   */
  String getShapes();

  /**
   * Adds a transformation to the animation.
   *
   * @param name The name of the shape (added with {@link AnimationBuilder#declareShape})
   * @param t1   The start time of this transformation.
   * @param x1   The initial x-position of the shape.
   * @param y1   The initial y-position of the shape.
   * @param w1   The initial width of the shape.
   * @param h1   The initial height of the shape.
   * @param r1   The initial red color-value of the shape.
   * @param g1   The initial green color-value of the shape.
   * @param b1   The initial blue color-value of the shape.
   * @param t2   The end time of this transformation.
   * @param x2   The final x-position of the shape.
   * @param y2   The final y-position of the shape.
   * @param w2   The final width of the shape.
   * @param h2   The final height of the shape.
   * @param r2   The final red color-value of the shape.
   * @param g2   The final green color-value of the shape.
   * @param b2   The final blue color-value of the shape.
   */
  void addMotion(String name,
                 int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
                 int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2);

  /**
   * Removes a transformation from the animation.
   *
   * @param name The name of the shape (added with {@link AnimationBuilder#declareShape})
   * @param t1   The start time of this transformation.
   * @param x1   The initial x-position of the shape.
   * @param y1   The initial y-position of the shape.
   * @param w1   The initial width of the shape.
   * @param h1   The initial height of the shape.
   * @param r1   The initial red color-value of the shape.
   * @param g1   The initial green color-value of the shape.
   * @param b1   The initial blue color-value of the shape.
   * @param t2   The end time of this transformation.
   * @param x2   The final x-position of the shape.
   * @param y2   The final y-position of the shape.
   * @param w2   The final width of the shape.
   * @param h2   The final height of the shape.
   * @param r2   The final red color-value of the shape.
   * @param g2   The final green color-value of the shape.
   * @param b2   The final blue color-value of the shape.
   */
  void removeMotion(String name,
                    int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
                    int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2);

  /**
   * Prints the user-added motions for the animation.
   *
   * @param name the unique name for a user-created shape.
   * @return the string representation for the motions of a given shape.
   */
  String getMotionsForShape(String name);

  /**
   * Gets the animations for a model.
   *
   * @return the changes to be animated for a model.
   */
  ArrayList<Motion> getChanges();

  /**
   * Gets the canvas size of a model.
   *
   * @return the canvas for the model.
   */
  Canvas getCanvas();

  /**
   * Gets the shapes that have been added to the model.
   *
   * @return the list of shapes to be animated.
   */
  ArrayList<Shapes> getShapesList();
}

