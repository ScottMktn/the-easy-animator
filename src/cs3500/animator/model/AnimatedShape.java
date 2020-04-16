package cs3500.animator.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Objects;

import cs3500.animator.misc.ErrorMessages;
import cs3500.animator.misc.Position2D;
import cs3500.animator.misc.Utils;
import cs3500.animator.shapes.IShape;

/**
 * Represents an animated shape object. Contains an IShape as well as the shapes corresponding
 * EventInstructions. There instructions are what will be called on the IShape using the shapes
 * executeInstructions method. This class is final as it is immutable.
 */
public final class AnimatedShape implements IAnimatedShape {
  private final IShape shape;
  private final ArrayList<EventInstructions> events;

  /**
   * Constructs an animated shape object.
   *
   * @param shape  shape that we want to animate.
   * @param events list of initial events.
   */
  public AnimatedShape(IShape shape, ArrayList<EventInstructions> events) {
    this.shape = Objects.requireNonNull(shape).copy();
    this.events = new ArrayList<EventInstructions>();
    events.stream().forEach(e -> this.addEvent(e));
  }

  /**
   * Constructs an animated shape object, with no events.
   *
   * @param shape shape that we want to animate.
   */
  public AnimatedShape(IShape shape) {
    this.shape = Objects.requireNonNull(shape).copy();
    this.events = new ArrayList<EventInstructions>();
  }

  /**
   * Constructs an animated shape object copy of animatedShape.
   *
   * @param animatedShape object we want to copy.
   */
  public AnimatedShape(IAnimatedShape animatedShape) {
    this.shape = Objects.requireNonNull(animatedShape.getShape().copy());
    this.events = Objects.requireNonNull(animatedShape.getEvents());
  }

  /**
   * Gets a copy of the local shape for this animated class.
   *
   * @return an IShape inside this class.
   */
  @Override
  public IShape getShape() {
    return this.shape.copy();
  }

  /**
   * Gets a copy of the events this animation represents.
   *
   * @return a new ArrayList of EventInstructions.
   */
  @Override
  public ArrayList<EventInstructions> getEvents() {
    ArrayList<EventInstructions> temp = new ArrayList<EventInstructions>();
    for (EventInstructions event : this.events) {
      temp.add(new EventInstructions(event));
    }
    return temp;
  }

  /**
   * Tweening method given to us in assignment 6 that will help us get a value at a certain tick,
   * withing a bounds of ticks.
   *
   * @param startVal  the value at time = startTick.
   * @param endVal    the calue at time = endTick.
   * @param startTick the beginning of the event.
   * @param endTick   the end tick value at the end of the event.
   * @param currTick  the tick inbetween start and end that we want to calculate.
   * @return the calculated tweened value.
   * @throws IllegalArgumentException if the currTick is not within start and end tick.
   */
  private double tween(double startVal, double endVal, int startTick, int endTick, int currTick) {
    Utils.requireNonNegative(startTick, endTick, currTick);
    int deltaTick = endTick - startTick;
    double tweenVal = (startVal * ((double) (endTick - currTick) / (double) deltaTick)
            + endVal * ((double) (currTick - startTick) / (double) deltaTick));
    // used to solve java's rounding bug
    // issue reference - https://www.geeksforgeeks.org/rounding-off-errors-java/
    // Solution - https://stackoverflow.com/questions/8825209/rounding-decimal-points
    return Math.round(tweenVal * 1000) / 1000.0;
  }

  /**
   * Gets a version of this shape at a given tick.
   *
   * @param tick the tick that we want the shape at. Must be > 0.
   * @throws IllegalArgumentException if the shape doesn't exist.
   */
  @Override
  public IShape getShapeAtTick(int tick) {
    if (tick < 0) {
      throw new IllegalArgumentException(ErrorMessages.BadTickValue.errorMessage());
    }
    for (int i = 0; i < this.events.size(); i++) {
      EventInstructions event = this.events.get(i);
      if (event.getStartTick() <= tick && tick <= event.getEndTick()) {

        boolean startEventIsPosition = i == 0;

        Position2D startPosn;
        int startHeight;
        int startWidth;
        Color startColor;
        int startAngle;
        if (startEventIsPosition) {
          startPosn = this.shape.getPosition();
          startHeight = this.shape.getHeight();
          startWidth = this.shape.getWidth();
          startColor = this.shape.getColor();
          startAngle = (int)this.shape.getAngle();
        } else {
          startPosn = this.events.get(i - 1).getEndPosn();
          startHeight = this.events.get(i - 1).getEndHeight();
          startWidth = this.events.get(i - 1).getEndWidth();
          startColor = this.events.get(i - 1).getEndColor();
          startAngle = (int)this.events.get(i - 1).getEndAngle();
        }

        TweenLambda tweenLambda = (double a, double b) ->
                tween(a, b, event.getStartTick(), event.getEndTick(), tick);


        double x = tweenLambda.tween(startPosn.getX(), event.getEndPosn().getX());

        double y = tweenLambda.tween(startPosn.getY(), event.getEndPosn().getY());

        int height = (int) tweenLambda.tween(startHeight, event.getEndHeight());

        int width = (int) tweenLambda.tween(startWidth, event.getEndWidth());

        int r = (int) tweenLambda.tween(startColor.getRed(), event.getEndColor().getRed());

        int g = (int) tweenLambda.tween(startColor.getGreen(), event.getEndColor().getGreen());

        int b = (int) tweenLambda.tween(startColor.getBlue(), event.getEndColor().getBlue());

        int a = (int) tweenLambda.tween(startAngle, event.getEndAngle());

        EventInstructions eventStateAtTick =
                new EventInstructions(tick, tick, new Position2D(x, y),
                        width, height, new Color(r, g, b), a);
        IShape newShape = this.shape.copy();
        newShape.executeInstructions(eventStateAtTick);
        return newShape;
      }
    }
    throw new IllegalArgumentException(ErrorMessages.BadTickValue.errorMessage());
  }

  /**
   * Adds a copy of the event to the list of events.
   *
   * @param event EventInstruction that we want to add.
   * @throws IllegalArgumentException if the end time of the last added element is not the same as
   *                                  the start time of the new event.
   */
  @Override
  public void addEvent(EventInstructions event) {
    boolean isInvalidInput = this.events.size() != 0 &&
            this.events.get(this.events.size() - 1).getEndTick() != event.getStartTick();
    if (isInvalidInput) {
      throw new IllegalArgumentException(
              ErrorMessages.InvalidEventAdded.errorMessage("End: " +
                      this.events.get(this.events.size() - 1).getEndTick() +
                      " Start: " + event.getStartTick()));
    }
    this.events.add(new EventInstructions(Objects.requireNonNull(event)));
  }

  /**
   * Splits a single event instruction and adds the KeyFrame params in between the start and end
   * ticks.
   *
   * @param event instruct that we want to split.
   * @param t     the tick at which we want to add the key frame.
   * @param p     Position.
   * @param w     Width.
   * @param h     Height.
   * @param c     Color.
   * @return array of two events.
   */
  private ArrayList<EventInstructions> splitAndInsert(
          EventInstructions event, int t, Position2D p, int w, int h, Color c) {
    ArrayList<EventInstructions> splitEvents = new ArrayList<>();
    splitEvents.add(new EventInstructions(event.getStartTick(), t, p, w, h, c));

    splitEvents.add(
            new EventInstructions(
                    t,
                    event.getEndTick(),
                    event.getEndPosn(),
                    event.getEndWidth(),
                    event.getEndHeight(),
                    event.getEndColor()));
    return splitEvents;
  }

  @Override
  public void placeKeyFrame(int t, Position2D p, int w, int h, Color c) {
    if (this.events.size() == 0) {
      this.events.add(new EventInstructions(t, t, p, w, h, c));
      return;
    }

    if (this.events.size() == 1 &&
            this.events.get(0).getStartTick() == this.events.get(0).getEndTick()) {
      if (t < this.events.get(0).getStartTick()) {
        EventInstructions temp =
                new EventInstructions(t, this.events.get(0).getStartTick(), p, w, h, c);
        this.shape.executeInstructions(temp);
        this.events.add(new EventInstructions(
                t,
                this.events.get(0).getStartTick(),
                this.events.get(0).getEndPosn(),
                this.events.get(0).getEndWidth(),
                this.events.get(0).getEndHeight(),
                this.events.get(0).getEndColor()
        ));
      } else {
        this.events.add(new EventInstructions(this.events.get(0).getStartTick(), t, p, w, h, c));
        this.shape.executeInstructions(this.events.get(0));
      }
      this.events.remove(0);
      return;
    }
    boolean beforeFirstTick = t < this.events.get(0).getStartTick();
    boolean afterLastTick = t > this.events.get(this.events.size() - 1).getEndTick();
    if (beforeFirstTick) {
      this.events.add(0,
              new EventInstructions(
                      t,
                      this.events.get(0).getStartTick(),
                      p, w, h, c
              ));
    } else if (afterLastTick) {
      this.addEvent(new EventInstructions(
              this.events.get(this.events.size() - 1).getEndTick(),
              t, p, w, h, c
      ));
    } else {
      for (EventInstructions event : this.events) {
        if (event.getStartTick() < t && t < event.getEndTick()) {
          int i = this.events.indexOf(event);
          this.events.addAll(i, splitAndInsert(event, t, p, w, h, c));
          this.events.remove(event);
          return;
        } else if (event.getStartTick() == t || event.getEndTick() == t) {
          throw new IllegalArgumentException(ErrorMessages.KeyFrameBadTick.errorMessage());
        }
      }
      throw new IllegalArgumentException(ErrorMessages.BadTickValue.errorMessage());
    }

  }


  @Override
  public void removeKeyFrame(int index) {
    Utils.requireNonNegative(index);
    if (index == 0 && this.events.size() == 0) {
      throw new IllegalArgumentException("The shape must have 1 keyframe to know it's start");
    }

    if (index > this.events.size()) {
      throw new IllegalArgumentException("KeyEvent Doesn't exist at this time");
    }

    if (index == 1 && this.events.size() == 1) {
      EventInstructions curr = this.events.get(index - 1);
      this.events.remove(curr);
      this.events.add(
              new EventInstructions(
                      curr.getStartTick(),
                      curr.getStartTick(),
                      this.shape.getPosition(),
                      this.shape.getWidth(),
                      this.shape.getHeight(),
                      this.shape.getColor()
              ));
    } else if (index == 0) {
      EventInstructions e = this.events.get(0);
      this.shape.executeInstructions(e);
      this.events.remove(0);
    } else if (index == this.events.size()) {
      this.events.remove(index - 1);
    } else {

      EventInstructions curr = this.events.get(index - 1);
      EventInstructions next = this.events.get(index);
      this.events.remove(curr);
      this.events.remove(next);
      this.events.add(index - 1,
              new EventInstructions(
                      curr.getStartTick(),
                      next.getEndTick(),
                      next.getEndPosn(),
                      next.getEndWidth(),
                      next.getEndHeight(),
                      next.getEndColor()
              ));
    }

  }


  /**
   * Interface used to create lambda for implementations of the tween function where the start, end
   * and current tick are the same value for multiple function calls but the values to calculate
   * change.
   */
  private interface TweenLambda {
    /**
     * Find the in between value from the start and end val.
     *
     * @param startVal the starting val (a).
     * @param endVal   the ending val (b).
     * @return the value in between the two.
     */
    double tween(double startVal, double endVal);
  }
}
