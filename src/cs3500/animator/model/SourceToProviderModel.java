package cs3500.animator.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

import cs3500.animator.misc.Position2D;
import cs3500.animator.misc.Utils;
import cs3500.animator.provider.model.Motion;
import cs3500.animator.provider.model.Shapes;
import cs3500.animator.provider.model.SimpleAnimationImpl;
import cs3500.animator.provider.util.AnimationBuilder;
import cs3500.animator.provider.util.Canvas;
import cs3500.animator.provider.util.KeyFrame;
import cs3500.animator.shapes.Ellipse;
import cs3500.animator.shapes.IShape;
import cs3500.animator.shapes.Rectangle;

/**
 * Adapter object that converts our model into a model compatible with our customers editor view.
 * One instance of this object represents our customers SimpleAnimationImpl. Our model extends their
 * SimpleAnimationImpl class as opposed to our customer's ISimpleAnimation interface because their
 * editor view incorrectly takes in a class-type model. We emailed Professor Freifeld regarding this
 * issue and he said we should continue forward using the class type as opposed to the interface
 * type to adapt models.
 */
public class SourceToProviderModel extends SimpleAnimationImpl {
  private IAnimationModel srcModel;

  /**
   * Basic constructor that takes in a source model to use to convert into a SimpleAnimationImpl
   * type.
   *
   * @param srcModel the source model that will be converted.
   * @throws IllegalArgumentException if the srcModel is null
   */
  public SourceToProviderModel(IAnimationModel srcModel) {
    super(new ArrayList<>(), new ArrayList<>());
    Utils.requireNonNull(srcModel);
    this.srcModel = srcModel;
  }

  /**
   *************************** PUBLIC METHODS FROM CLASS. ***************************
   */

  /**
   * Converts the sourceType string to that of the provider.
   *
   * @param sourceType either 'rec' or 'ell'
   * @return the provider's Type. either 'rectangle' or 'ellipse'
   * @throws IllegalArgumentException if the sourceType is unsupported or if the sourceType is
   *                                  null.
   */
  private String convertShapeType(String sourceType) {
    Utils.requireNonNull(sourceType);
    switch (sourceType) {
      case "rec":
        return "rectangle";
      case "ell":
        return "ellipse";
      default:
        throw new IllegalArgumentException("Unsupported sourceType");
    }
  }

  /**
   * Creates key frames of the Model to be used with classes implementing IVIew.
   *
   * @param time ticks-per-second(?).
   * @return list of key frames to be used for IView implementing classes.
   */
  @Override
  public ArrayList<KeyFrame> makeAnimation(int time) {
    return this.srcModel.getShapesAt(time)
            .stream()
            .map(shape ->
                    new KeyFrame(
                            time,
                            shape.getHeight(),
                            shape.getWidth(),
                            (int) shape.getPosition().getX(),
                            (int) shape.getPosition().getY(),
                            new cs3500.animator.provider.util.Color(
                                    shape.getColor().getRed(),
                                    shape.getColor().getGreen(),
                                    shape.getColor().getBlue()
                            ),
                            this.convertShapeType(shape.asString()))
            )
            .collect(Collectors.toCollection(ArrayList::new));
  }

  /**
   * Gets the total end time for the Animation.
   *
   * @return the final time of the animation.
   */
  @Override
  public int getFinalTime() {
    return this.srcModel.getAllAnimatedShapes()
            .stream()
            .map(s -> s.getEvents().get(s.getEvents().size() - 1).getEndTick())
            .max(Integer::compare).get();
  }

  /**
   * Sets the Canvas sized to be used with the Model.
   *
   * @param c the Canvas size to be used when creating Model for animation.
   * @throws IllegalArgumentException if the canvas is null
   */
  public void setCanvas(Canvas c) {
    Utils.requireNonNull(c);
    Scanner s = new Scanner(c.getCanvasString());
    s.next();
    this.srcModel.setBounds(
            s.nextInt(),
            s.nextInt(),
            s.nextInt(),
            s.nextInt()
    );
  }

  /**
   *************************** PUBLIC METHODS FROM INTERFACE. ***************************
   */

  /**
   * Creates a shape object using the name and shape type.
   *
   * @param name the name of the shape
   * @param type the type of the shape
   * @return a well-constructed shape object
   * @throws IllegalArgumentException if the shape type is unsupported or if the name or type is
   *                                  null.
   */
  private IShape providerShapeFactory(String name, String type) {
    Utils.requireNonNull(name, type);
    switch (type) {
      case "rectangle":
        return new Rectangle(name,
                new Position2D(0, 0), 0, 0, Color.WHITE);
      case "ellipse":
        return new Ellipse(name,
                new Position2D(0, 0), 0, 0, Color.WHITE);
      default:
        throw new IllegalArgumentException("Unsupported shape type.");
    }
  }

  /**
   * Adds a new shape to the animation.
   *
   * @param name The unique name of the shape to be added. No shape with this name should already
   *             exist.
   * @param type The type of shape (e.g. "ellipse", "rectangle") to be added. The set of supported
   *             shapes is unspecified, but should include "ellipse" and "rectangle" as a minimum.
   * @throws IllegalArgumentException if the name or type is null
   */
  @Override
  public void declareShape(String name, String type) {
    Utils.requireNonNull(name, type);
    this.srcModel.placeAnimatedShape(
            new AnimatedShape(this.providerShapeFactory(name, type))
    );
  }

  /**
   * Removes a previously created shape from the animation.
   *
   * @param name the unique name of a user-created shape.
   * @throws IllegalArgumentException if the name is null
   */
  public void removeShape(String name) {
    Utils.requireNonNull(name);
    this.srcModel.deleteShape(name);
  }

  /**
   * Prints the values for each unique shape the user has created for an animation.
   *
   * @return the string representation for all shapes in the animation.
   * @throws UnsupportedOperationException because this function is not used in their view.
   */
  @Override
  public String getShapes() {
    throw new UnsupportedOperationException("Unsupported function; getShapes");
  }

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
   * @throws UnsupportedOperationException because this function is not used in their view.
   */
  @Override
  public void addMotion(String name,
                        int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
                        int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2) {
    throw new UnsupportedOperationException("Unsupported function; getShapes");
  }

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
   * @throws UnsupportedOperationException because this function is not used in their view.
   */
  @Override
  public void removeMotion(String name,
                           int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
                           int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2) {
    throw new UnsupportedOperationException("Unsupported function: getShapes");
  }

  /**
   * Prints the user-added motions for the animation.
   *
   * @param name the unique name for a user-created shape.
   * @return the string representation for the motions of a given shape.
   * @throws UnsupportedOperationException because this function is not used in their view.
   */
  @Override
  public String getMotionsForShape(String name) {
    throw new UnsupportedOperationException("Unsupported function: getMotionsForShape");
  }

  /**
   * Gets the animations for a model.
   *
   * @return the changes to be animated for a model.
   */
  @Override
  public ArrayList<Motion> getChanges() {

    ArrayList<Motion> motions = new ArrayList<>();

    for (IAnimatedShape shape : this.srcModel.getAllAnimatedShapes()) {

      for (int i = 0; i < shape.getEvents().size(); i++) {
        EventInstructions e = shape.getEvents().get(i);

        boolean startEventIsPosition = i == 0;
        Position2D startPosn;
        int startHeight;
        int startWidth;
        Color startColor;
        if (startEventIsPosition) {
          startPosn = shape.getShape().getPosition();
          startHeight = shape.getShape().getHeight();
          startWidth = shape.getShape().getWidth();
          startColor = shape.getShape().getColor();
        } else {
          startPosn = shape.getEvents().get(i - 1).getEndPosn();
          startHeight = shape.getEvents().get(i - 1).getEndHeight();
          startWidth = shape.getEvents().get(i - 1).getEndWidth();
          startColor = shape.getEvents().get(i - 1).getEndColor();
        }

        motions.add(
                new Motion(
                        shape.getShape().getName(),
                        e.getStartTick(),
                        e.getEndTick(),
                        (int) startPosn.getX(),
                        (int) startPosn.getY(),
                        (int) e.getEndPosn().getX(),
                        (int) e.getEndPosn().getY(),
                        new cs3500.animator.provider.util.Color(
                                startColor.getRed(),
                                startColor.getGreen(),
                                startColor.getBlue()
                        ),
                        new cs3500.animator.provider.util.Color(
                                e.getEndColor().getRed(),
                                e.getEndColor().getGreen(),
                                e.getEndColor().getBlue()
                        ),
                        startWidth,
                        startHeight,
                        e.getEndWidth(),
                        e.getEndHeight())
        );

      }
    }

    return motions;
  }

  /**
   * Gets the canvas size of a model.
   *
   * @return the canvas for the model.
   */
  @Override
  public Canvas getCanvas() {
    java.awt.Rectangle r = this.srcModel.getBounds();
    return new cs3500.animator.provider.util.Canvas(r.x, r.y, r.width, r.height);
  }

  /**
   * Gets the shapes that have been added to the model.
   *
   * @return the list of shapes to be animated.
   * @throws UnsupportedOperationException because this function is not used in their view.
   */
  @Override
  public ArrayList<Shapes> getShapesList() {
    throw new UnsupportedOperationException("Unsupported function: getMotionsForShape");
  }
}

