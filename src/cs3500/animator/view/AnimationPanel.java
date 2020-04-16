package cs3500.animator.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Objects;

import javax.swing.JPanel;

import cs3500.animator.misc.Utils;
import cs3500.animator.model.IAnimatedShape;
import cs3500.animator.model.ReadOnlyIAnimationModel;
import cs3500.animator.shapes.IShape;

/**
 * Represents the window inside of the animation that will display the shapes in the
 * IAnimationModel. This class extends the JPanel class, and contains additional fields inside of it
 * such as a read only IAnimationModel and the current tick of the animation. This class contains
 * two methods:
 * <p>-increment() : increments the curTick field by 1</p>
 * <p>-paintComponent(Graphics g) : Override the paint component method in JPanel. This method
 * actually renders the shapes according to their type and characteristics.</p>
 */
public class AnimationPanel extends JPanel {


  private static final String ELLIPSE = "ell";
  private static final String RECTANGLE = "rec";
  private final int REFRESH_MAX_TICK_AT = 100;
  private final double DEGREE_TO_RADIAN = Math.PI / 180;
  private ReadOnlyIAnimationModel model;
  private int curTick;
  private int maxTick;
  private Looping isLooping;


  /**
   * Constructor for an animation panel. Consumes a model and initializes the private fields.
   *
   * @param model the model we want to consume for the animation panel
   */
  public AnimationPanel(ReadOnlyIAnimationModel model) {
    super();
    Utils.requireNonNull(model);
    this.model = model;
    this.curTick = 0;
    this.maxTick = this.calcMaxTick();
    this.isLooping = Looping.ENABLED;
    this.setBackground(Color.WHITE);
  }

  /**
   * Either enables or disables looping depending on the current state of the animation.
   */
  public void setLooping(boolean b) {
    this.isLooping = b ? this.isLooping = Looping.ENABLED : Looping.DISABLED;
    this.maxTick = this.calcMaxTick();
  }

  /**
   * Calculates the max tick of the animation by going through the list of animated shapes.
   *
   * @return an integer representing the last tick in the animation.
   */
  private int calcMaxTick() {
    int x = 0;
    for (IAnimatedShape shape : model.getAllAnimatedShapes()) {
      int endTick = shape.getEvents().get(shape.getEvents().size() - 1).getEndTick();
      x = (endTick > x) ? endTick : x;
    }
    return x;
  }

  /**
   * Maximum tick value calculated by examining the model.
   *
   * @return max tick value.
   */
  public int getMaxTick() {
    return this.maxTick;
  }

  /**
   * Retrieves the current tick of the panel.
   *
   * @return an integer representing the tick
   */
  public int getCurTick() {
    return this.curTick;
  }

  /**
   * Sets the current tick to the passed in tick.
   *
   * @param tick the tick we want the animation to be
   */
  public void setCurTick(int tick) {
    this.curTick = tick;
  }

  /**
   * Increments the current tick by 1. If the next tick is greater than the max tick and looping is
   * enabled, start the animation over again. If looping is disabled, end the animation.
   */
  public void increment() {
    if (this.curTick + 1 > this.maxTick && isLooping == Looping.ENABLED) {
      this.setCurTick(0);
    } else {
      this.curTick++;
    }
    // on every 300th tick lets update our max tick for Looping purposes
    if (this.isLooping == Looping.ENABLED && this.curTick % REFRESH_MAX_TICK_AT == 0) {
      this.maxTick = this.calcMaxTick();
    }
  }

  /**
   * Handler for the actual rendering of shapes in the panel.
   *
   * @param g the graphics
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(Objects.requireNonNull(g));
    Graphics2D g2d = (Graphics2D) g;

    for (int i = 0; i < this.model.layerCount(); i++) {
      if (this.model.isEnabled(i)) {
        // copy of shapes
        ArrayList<IShape> shapes = this.model.getShapesFromLayerAtTick(i, this.curTick);
        for (IShape shape : shapes) {
          g2d.setColor(shape.getColor());
          g2d.rotate(shape.getAngle() * DEGREE_TO_RADIAN,
                  shape.getPosition().getX() + shape.getWidth() / 2,
                  shape.getPosition().getY() + shape.getHeight() / 2);

          switch (shape.asString()) {
            case ELLIPSE:
              g2d.fillOval((int) shape.getPosition().getX(),
                      (int) shape.getPosition().getY(),
                      shape.getWidth(),
                      shape.getHeight());
              break;
            case RECTANGLE:
              g2d.fillRect((int) shape.getPosition().getX(),
                      (int) shape.getPosition().getY(),
                      shape.getWidth(),
                      shape.getHeight());
              break;
            default:
              throw new IllegalArgumentException("Does not support given Shape!");
          }
        }
      }
    }
  }

  /**
   * Represents whether looping is enabled or disabled.
   */
  private enum Looping {
    DISABLED,
    ENABLED
  }
}
