/**
 * CLASS TAKEN FROM Lecture 12 - https://course.ccs.neu.edu/cs3500/lec_gui_basics_notes.html
 */

package cs3500.animator.misc;

import java.util.Objects;

/**
 * This class represents a 2D position.
 * Given to us in the Turtle MVC example.
 */
public final class Position2D {
  private final double x;

  /**
   * Gets the x value.
   * @return the x value of the position.
   */
  public double getX() {
    return x;
  }

  /**
   * Gets the y value.
   * @return the y value of the position.
   */
  public double getY() {
    return y;
  }

  private final double y;

  /**
   * Initialize this object to the specified position.
   */
  public Position2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Copy constructor.
   */
  public Position2D(Position2D v) {
    this(v.x, v.y);
  }

  /**
   * Gets a new position that is in between this position and that
   * position, where the position returned is a given ratio along the
   * linear path between this and that position.
   * @param that the ending position to get distance in between
   * @param ratio must be a postive number less than or equal to 1.
   * @return the position in between this position and that position, scaled to ratio
   * @throws IllegalArgumentException if the ratio is inputted incorrectly.
   */
  public Position2D getPositionInBetween(Position2D that, double ratio) {
    if (ratio > 1 || ratio < 0) {
      throw new IllegalArgumentException(ErrorMessages.RatioOutofBounds.errorMessage());
    }
    return new Position2D(this.getX() + (that.getX() - this.getX()) * ratio,
            this.getY() + (that.getY() - this.getY()) * ratio);
  }


  @Override
  public String toString() {
    return String.format("(%f, %f)", this.x, this.y);
  }

  @Override
  public boolean equals(Object a) {
    if (this == a) {
      return true;
    }
    if (!(a instanceof Position2D)) {
      return false;
    }

    Position2D that = (Position2D) a;

    return ((Math.abs(this.x - that.x) < 0.01) && (Math.abs(this.y - that.y) < 0.01));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }
}
