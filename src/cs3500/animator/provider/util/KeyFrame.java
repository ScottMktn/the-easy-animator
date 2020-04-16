package cs3500.animator.provider.util;

/**
 * Creates a KeyFrame to be used in an Animation.
 */
public class KeyFrame {

  private int t;
  public int h;
  public int w;
  public int x;
  public int y;
  public Color c;
  public String type;

  /**
   * Creates a KeyFrame.
   *
   * @param t the time.
   * @param h the height.
   * @param w the width.
   * @param x the x value.
   * @param y the y value.
   * @param c the color.
   * @param type the type.
   */
  public KeyFrame(int t, int h, int w, int x, int y, Color c, String type) {
    if (t < 0 || w < 0 || h < 0) {
      throw new IllegalArgumentException("Cant have negative values for t, h, h");
    }
    this.t = t;
    this.h = h;
    this.w = w;
    this.x = x;
    this.y = y;
    this.c = c;
    this.type = type;
  }

  /**
   * Gets t value.
   *
   * @return the value for t.
   */
  public int getT() {
    return t;
  }
}
