package cs3500.animator.provider.model;

import java.util.Objects;

import cs3500.animator.provider.util.Color;

/**
 * A Motion is the changes on a user-created Shape to be animated within a View.
 */
public class Motion {

  private String shapeName;
  private int startTime;
  private int endTime;
  private int x1;
  private int y1;
  private int x2;
  private int y2;
  private Color oldColor;
  private Color newColor;
  private int w1;
  private int h1;
  private int w2;
  private int h2;

  /**
   * Constructs a Motion to be used in Animation.
   *
   * @param shapeName the name of the shape to be changed.
   * @param startTime the time to start the change.
   * @param endTime the time to end the change.
   * @param x1 the starting x position.
   * @param y1 the starting y position.
   * @param x2 the ending x position.
   * @param y2 the ending y position.
   * @param oldColor the old color of the Object.
   * @param newColor the new color of the Object.
   * @param w1 the starting x position.
   * @param h1 the starting y position.
   * @param w2 the ending x position.
   * @param h2 the ending y position.
   */
  public Motion(String shapeName, int startTime, int endTime, int x1, int y1, int x2, int y2,
      Color oldColor, Color newColor, int w1, int h1, int w2, int h2) {
    if (startTime >= 0 && endTime >= 0 && startTime <= endTime
        && w1 > 0 && h1 > 0 && w2 > 0 && h2 > 0) {
      this.shapeName = shapeName;
      this.startTime = startTime;
      this.endTime = endTime;
      this.x1 = x1;
      this.y1 = y1;
      this.x2 = x2;
      this.y2 = y2;
      this.oldColor = oldColor;
      this.newColor = newColor;
      this.w1 = w1;
      this.h1 = h1;
      this.w2 = w2;
      this.h2 = h2;
    } else {
      throw new IllegalArgumentException("Invalid input detected!");
    }
  }

  /**
   * Gets the name of the shape.
   *
   * @return the String value for shape name.
   */
  public String getName() {
    return shapeName;
  }

  /**
   * Gets the numeric value for start.
   *
   * @return the numeric value for start time.
   */
  public int getStartTime() {
    return startTime;
  }

  /**
   * Gets the numeric value for end.
   *
   * @return the numeric value for end time.
   */
  public int getEndTime() {
    return endTime;
  }

  /**
   * Gets the x position for start.
   *
   * @return the numeric value for start position.
   */
  public int getStartX() {
    return x1;
  }

  /**
   * Gets the y position for start.
   *
   * @return the numeric value for start position.
   */
  public int getStartY() {
    return y1;
  }

  /**
   * Gets the x position for end.
   *
   * @return the numeric value for end position.
   */
  public int getEndX() {
    return x2;
  }

  /**
   * Gets the y position for end.
   *
   * @return the numeric value for end position.
   */
  public int getEndY() {
    return y2;
  }

  /**
   * Gets the color for the change.
   *
   * @return the numeric value for color.
   */
  public Color getNewColor() {
    return newColor;
  }

  /**
   * Gets the color for the change.
   *
   * @return the numeric value for color.
   */
  public Color getOldColor() {
    return oldColor;
  }

  /**
   * Gets the starting width.
   *
   * @return the numeric value for the new dimension.
   */
  public int getStartingWidth() {
    return w1;
  }

  /**
   * Gets the starting height.
   *
   * @return the numeric value for the new dimension.
   */
  public int getStartingHeight() {
    return h1;
  }

  /**
   * Gets the ending width.
   *
   * @return the numeric value for the new dimension.
   */
  public int getEndingWidth() {
    return w2;
  }

  /**
   * Gets the ending height.
   *
   * @return the numeric value for the new dimension.
   */
  public int getEndingHeight() {
    return h2;
  }

  /**
   * Applies a color change animation.
   *
   * @return the motion to apply the animation.
   */
  public Motion changeColor() {
    return new Motion(this.shapeName, this.startTime, this.endTime, x1, y1, x2, y2,
        this.oldColor, this.newColor, w1, h1, w2, h2);
  }

  /**
   * Applies a size change animation.
   *
   * @param w the new width.
   * @param h the new height.
   * @param newStartTime the time to start the animation.
   * @param newEndTime the time to end the animation.
   * @return the motion to apply the animation.
   */
  public Motion changeSize(int w, int h, int newStartTime, int newEndTime) {
    return new Motion(this.shapeName, newStartTime, newEndTime, x1, y1, x2, y2,
        this.oldColor, this.newColor, w1, h1, w, h);
  }

  /**
   * Applies a position change animation.
   *
   * @param x the new x-value for the position.
   * @param y the new y-value for the position.
   * @param newStartTime the time to start the animation.
   * @param newEndTime the time to end the animation.
   * @return the motion to apply the animation.
   */
  public Motion changePosition(int x, int y, int newStartTime, int newEndTime) {
    return new Motion(this.shapeName, newStartTime, newEndTime, x1, y1, x, y,
        this.oldColor, this.newColor, w1, h1, w2, h2);
  }

  @Override
  public String toString() {
    return String.format("motion %s %d %d %d %d %d %d %d %d    "
            + "%d %d %d %d %d %d %d %d",
        this.getName(), this.getStartTime(),
        this.getStartX(), this.getStartY(),
        this.getStartingWidth(), this.getStartingHeight(),
        this.getOldColor().getRed(), this.getOldColor().getGreen(),
        this.getOldColor().getBlue(),
        this.getEndTime(), this.getEndX(), this.getEndY(),
        this.getEndingWidth(), this.getEndingHeight(),
        this.getNewColor().getRed(), this.getNewColor().getGreen(),
        this.getNewColor().getBlue());
  }

  @Override
  public boolean equals(Object a) {
    if (!(a instanceof Motion)) {
      return false;
    }
    if (this == a) {
      return true;
    }
    Motion that = (Motion) a;
    return (this.shapeName.equals(that.shapeName)
        && (Math.abs(this.startTime - that.startTime) < 0.01)
        && (Math.abs(this.endTime - that.endTime) < 0.01)
        && (equalsIgnoreTime(a)));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.shapeName, this.startTime, this.endTime, this.x1, this.y1, this.x2,
        this.y2, this.w1, this.w2, this.h1, this.h2, this.oldColor, this.newColor);
  }


  /**
   * Checks Motion equality regardless of time.
   *
   * @param a the motion to be checked.
   * @return the boolean of whether two Motions are equal.
   */
  private boolean equalsIgnoreTime(Object a) {
    if (!(a instanceof Motion)) {
      return false;
    }
    if (this == a) {
      return true;
    }
    Motion that = (Motion) a;
    return (this.shapeName.equals(that.shapeName)
        && (Math.abs(this.x1 - that.x1) < 0.01)
        && (Math.abs(this.y1 - that.y1) < 0.01)
        && (Math.abs(this.x2 - that.x2) < 0.01)
        && (Math.abs(this.y2 - that.y2) < 0.01)
        && (Math.abs(this.w1 - that.w1) < 0.01)
        && (Math.abs(this.h1 - that.h1) < 0.01)
        && (Math.abs(this.w2 - that.w2) < 0.01)
        && (Math.abs(this.h2 - that.h2) < 0.01)
        && (this.oldColor.equals(that.oldColor))
        && (this.newColor.equals(that.newColor)));
  }
}

