package cs3500.animator.util;

import java.awt.Color;
import java.util.HashMap;

import cs3500.animator.misc.Position2D;
import cs3500.animator.model.AnimatedShape;
import cs3500.animator.model.EventInstructions;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.model.LayeredModelImpl;
import cs3500.animator.shapes.Ellipse;
import cs3500.animator.shapes.IShape;
import cs3500.animator.shapes.Rectangle;

/**
 * Represents a builder that can be used in the builder pattern. For the purpose of Assignment 6,
 * will be used to construct the AnimationModelBuilder. Contains methods that set the animation
 * model with the appropriate shapes and corresponding event instructions.
 */
public class AnimationModelBuilder implements AnimationBuilder<IAnimationModel> {
  private final String ELLIPSE = "ellipse";
  private final String RECTANGLE = "rectangle";

  private IAnimationModel model;
  private HashMap<String, String> declaredShapes;
  private HashMap<String, Integer> shapeToLayer;

  /**
   * Initializes local state variables for the Builder object.
   */
  public AnimationModelBuilder() {
    this.model = new LayeredModelImpl();
    this.declaredShapes = new HashMap<>();
    this.shapeToLayer = new HashMap<>();

  }

  @Override
  public IAnimationModel build() {
    return this.model;
  }

  @Override
  public AnimationBuilder<IAnimationModel> setBounds(int x, int y, int width, int height) {
    this.model.setBounds(x, y, width, height);
    return this;
  }

  /**
   * Builds an IShape based on the type of that shape.
   *
   * @param name The name of the shape.
   * @param type The type of IShape we want to build.
   * @param p    The position of that shape.
   * @param w    The width of that shape.
   * @param h    The height of that Shape.
   * @param c    the color of the shape.
   * @return an IShape based on the params put in.
   */
  private IShape iShapeBuilder(String name, String type, Position2D p,
                               int w, int h, Color c, int a) {
    switch (type) {
      case ELLIPSE:
        return new Ellipse(name, p, w, h, c, a);
      case RECTANGLE:
        return new Rectangle(name, p, w, h, c, a);
      default:
        throw new IllegalArgumentException("Name not contained");
    }

  }

  @Override
  public AnimationBuilder<IAnimationModel> declareShape(String name, String type) {
    this.declaredShapes.put(name, type);
    this.shapeToLayer.put(name, this.model.getCurrentLayer());
    return this;
  }

  @Override
  public AnimationBuilder<IAnimationModel> addMotion(
          String name, int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1, int a1,
          int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2, int a2) {
    this.model.setCurrentLayer(this.shapeToLayer.get(name));
    if (this.declaredShapes.containsKey(name)) {
      this.model.placeAnimatedShape(
              new AnimatedShape(
                      this.iShapeBuilder(name,
                              this.declaredShapes.get(name),
                              new Position2D(x1, y1), w1, h1,
                              new Color(r1, g1, b1), a1)));
      this.declaredShapes.remove(name);
    }
    this.model.addEventInstructionToShape(name,
            new EventInstructions(t1, t2, new Position2D(x2, y2), w2, h2,
                    new Color(r2, g2, b2), a2));

    return this;
  }

  @Override
  public AnimationBuilder<IAnimationModel> addKeyframe(String name, int t, int x, int y,
                                                       int w, int h, int r, int g, int b) {
    this.model.setCurrentLayer(this.shapeToLayer.get(name));
    this.model.placeKeyFrame(name, t, new Position2D(x, y), w, h, new Color(r, g, b));
    return null;
  }

  @Override
  public void setLayer(int layer) {
    if (layer >= this.model.layerCount()) {
      this.model.addLayer();
    }
    this.model.setCurrentLayer(layer);
  }
}
