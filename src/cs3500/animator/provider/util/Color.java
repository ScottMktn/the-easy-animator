package cs3500.animator.provider.util;

import java.util.Objects;

/**
 * Represents a red, green, and blue value as a custom Color.
 */
public class Color {

  private int red;
  private int green;
  private int blue;

  /**
   * Constructs a Color for an Object.
   * All parameters must be within the range of 0-255.
   *
   * @param red value for amount of red.
   * @param green value for amount of green.
   * @param blue value for amount of blue.
   */
  public Color(int red, int green, int blue) {
    if (red < 0 || green < 0 || blue < 0) {
      throw new IllegalArgumentException("A color value cannot be less than 0!");
    } else if (red > 255 || green > 255 || blue > 255) {
      throw new IllegalArgumentException("A color value cannot exceed 255!");
    } else {
      this.red = red;
      this.green = green;
      this.blue = blue;
    }
  }

  /**
   * Copy constructor for Color.
   */
  public Color(Color c) {
    this(c.red, c.green, c.blue);
  }

  /**
   * Gets the red numeric value of a Color.
   *
   * @return the numeric value for red.
   */
  public int getRed() {
    return this.red;
  }

  /**
   * Gets the green numeric value of a Color.
   *
   * @return the numeric value for green.
   */
  public int getGreen() {
    return this.green;
  }

  /**
   * Gets the blue numeric value of a Color.
   *
   * @return the numeric value for blue.
   */
  public int getBlue() {
    return this.blue;
  }

  /**
   * Gets the Color.
   *
   * @return the color.
   */
  Color getColor() {
    return this;
  }

  /**
   * Sets the red numeric value for a Color.
   *
   * @param red the numeric value to replace red with.
   */
  private void setRed(int red) {
    this.red = red;
  }

  /**
   * Sets the green numeric value for a Color.
   *
   * @param green the numeric value to replace green with.
   */
  private void setGreen(int green) {
    this.green = green;
  }

  /**
   * Sets the blue numeric value for a Color.
   *
   * @param blue the numeric value to replace blue with.
   */
  private void setBlue(int blue) {
    this.blue = blue;
  }

  @Override
  public String toString() {
    return String.format("(%d, %d, %d)", this.red, this.green, this.blue);
  }

  public String toSVGString() {
    return String.format("\"rgb(%d, %d, %d)\"", this.red, this.green, this.blue);
  }

  @Override
  public boolean equals(Object a) {
    if (!(a instanceof Color)) {
      return false;
    }
    if (this == a) {
      return true;
    }
    Color that = (Color) a;
    return ((Math.abs(this.red - that.red) < 0.01)
        && (Math.abs(this.green - that.green) < 0.01)
        && (Math.abs(this.blue - that.blue) < 0.01));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.red, this.green, this.blue);
  }
}