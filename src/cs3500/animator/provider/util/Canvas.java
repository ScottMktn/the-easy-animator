package cs3500.animator.provider.util;

import java.util.Objects;

/**
 * Creates a Canvas to be used for the Animation.
 *
 */
public class Canvas {

  private int x;
  private int y;
  public int w;
  public int h;

  /**
   * Creates a Canvas.
   *
   * @param x the value for x.
   * @param y the value for y.
   * @param w the width of the canvas.
   * @param h the height of the canvas.
   */
  public Canvas(int x, int y, int w, int h) {
    if (w < 0 || h < 0) {
      throw new IllegalArgumentException("A canvas value cannot be 0 or less!");
    } else {
      this.x = x;
      this.y = y;
      this.w = w;
      this.h = h;
    }
  }

  /**
   * Used for Model Builder.
   */
  public Canvas() {
    // default empty constructor to be used with builder.}
  }

  /**
   * Sets the size of the Canvas.
   *
   * @param x the value for x.
   * @param y the value for y.
   * @param width the width of the canvas.
   * @param height the height of the canvas.
   */
  public void setCanvas(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.w = width;
    this.h = height;
  }

  @Override
  public String toString() {
    return String.format("%d %d %d %d", this.x, this.y, this.w, this.h);
  }

  /**
   * Generates a formatted string for the View's canvas.
   *
   * @return the formatted string output of a canvas
   */
  public String getCanvasString() {
    return "canvas " + this.toString();
  }

  String toSVGString() {
    return String.format("<svg width=\"%d\" height=\"%d\" version=\"1.1\" "
        + "xmlns=\"http://www.w3.org/2000/svg\">", this.w, this.h);
  }

  @Override
  public boolean equals(Object a) {
    if (!(a instanceof Canvas)) {
      return false;
    }
    if (this == a) {
      return true;
    }
    Canvas that = (Canvas) a;
    return ((Math.abs(this.x - that.x) < 0.01)
        && (Math.abs(this.y - that.y) < 0.01)
        && (Math.abs(this.w - that.w) < 0.01)
        && (Math.abs(this.h - that.h) < 0.01));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y, this.w, this.h);
  }
}