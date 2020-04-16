package cs3500.animator.model;

import java.awt.Color;
import java.util.Objects;

import cs3500.animator.misc.Position2D;
import cs3500.animator.misc.Utils;

/**
 * Houses all of the instructions for an event in the animation. Contains the start and end fields
 * during an event.
 */
public final class EventInstructions {
  private final int startTick; // INVARIANT: must be greater than or equal to zero
  private final int endTick; // INVARIANT: must be greater than or equal to zero
  private final Position2D endPosn;
  // INVARIANT: X and Y fields must be greater than or equal to zero
  private final int endWidth; // INVARIANT: must be greater than zero
  private final int endHeight; // INVARIANT: must be greater than zero
  private final Color endColor;

  /**
   * Constructor for the event instructions with the given parameters.
   *
   * @param startTick the start time
   * @param endTick   the end time
   * @param endPosn   the end position of the shape
   * @param endWidth  the end width of the shape
   * @param endHeight the end height of the shape
   * @param endColor  the end color of the shape
   */
  public EventInstructions(int startTick, int endTick,
                           Position2D endPosn, int endWidth, int endHeight, Color endColor) {
    Utils.requireNonNegative(startTick, endTick);
    if (endTick < startTick) {
      throw new IllegalArgumentException("The end tick must be greater than the start tick.");
    }

    this.startTick = startTick;
    this.endTick = endTick;
    this.endPosn = Objects.requireNonNull(endPosn);
    this.endWidth = endWidth;
    this.endHeight = endHeight;
    this.endColor = Objects.requireNonNull(endColor);
  }

  /**
   * Copy constructor that copies the fields of e.
   *
   * @param e EventInstruction to make a copy of.
   */
  public EventInstructions(EventInstructions e) {
    this(e.startTick,
            e.endTick,
            new Position2D(e.endPosn),
            e.endWidth,
            e.endHeight,
            new Color(e.endColor.getRGB()));
  }

  /**
   * Start time.
   */
  public int getStartTick() {
    return this.startTick;
  }

  /**
   * End time.
   */
  public int getEndTick() {
    return this.endTick;
  }

  /**
   * End Position.
   */
  public Position2D getEndPosn() {
    return new Position2D(this.endPosn);
  }

  /**
   * End Width.
   */
  public int getEndWidth() {
    return this.endWidth;
  }

  /**
   * End Height.
   */
  public int getEndHeight() {
    return this.endHeight;
  }

  /**
   * Gets a copy for the end color.
   */
  public Color getEndColor() {
    return new Color(this.endColor.getRGB());
  }
}
