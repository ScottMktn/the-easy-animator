package cs3500.animator.provider.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import cs3500.animator.provider.util.Canvas;
import cs3500.animator.provider.util.AnimationBuilder;
import cs3500.animator.provider.util.Color;
import cs3500.animator.provider.util.KeyFrame;

/**
 * Represents a Model for an Animation of user-created Shapes and their desired changes at a given
 * time. Can be layered to produce complex animations (Samples in resources package).
 */
public class SimpleAnimationImpl implements ISimpleAnimation {

  /**
   * Creates a Builder to build a SimpleAnimation Model from provided code that parses files.
   */
  public static class Builder implements AnimationBuilder<SimpleAnimationImpl> {

    final SimpleAnimationImpl model;
    final Canvas canvas;
    int speed = 1;

    public Builder() {
      model = new SimpleAnimationImpl();
      canvas = new Canvas();
    }

    public void setSpeed(int s) {
      this.speed = s;
    }

    @Override
    public SimpleAnimationImpl build() {
      return model;
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
      canvas.setCanvas(x, y, width, height);
      model.setCanvas(this.canvas);
    }

    @Override
    public void declareShape(String name, String type) {
      model.declareShape(name, type);
    }

    @Override
    public void addMotion(String name, int t1, int x1, int y1,
        int w1, int h1, int r1, int g1, int b1,
        int t2, int x2, int y2, int w2, int h2,
        int r2,
        int g2, int b2) {
      model.addMotion(name, t1, t2, x1, y1, x2, y2, r1, g1, b1, r2, g2, b2, w1, h1, w2, h2);
    }

    @Override
    public AnimationBuilder<SimpleAnimationImpl> addKeyframe() {
      throw new UnsupportedOperationException("What is a KeyFrame?");
    }
  }

  public List<Shapes> shapes;
  public List<Motion> changes;
  private Canvas canvas;
  private final Comparator<Motion> byStartTime = Comparator.comparing(Motion::getStartTime);

  /**
   * Creates default for SimpleAnimationImpl object.
   */
  private SimpleAnimationImpl() {
    changes = new ArrayList<>();
    changes.sort(byStartTime);
    shapes = new ArrayList<>();
  }

  /**
   * Creates SimpleAnimationImpl Object.
   *
   * @param changes list of changes given to specific shape.
   * @param shapes  list of enum shapes.
   */
  public SimpleAnimationImpl(List<Motion> changes, List<Shapes> shapes) {
    this.changes = changes;
    Comparator<Motion> byStartTime = Comparator.comparing(Motion::getStartTime);
    changes.sort(byStartTime);
    this.shapes = shapes;
    this.orderShapes();
  }

  /**
   * Builds a formatted string of motions for a specific shape.
   *
   * @param output  the mutable String representation of a model.
   * @param key     the unique name of the Shape to be represented.
   * @param changes the list of Motions to be checked with the key.
   */
  public static void modelToString(StringBuilder output, String key, List<Motion> changes) {
    for (int j = 0; j < changes.size(); j++) {
      Motion change = changes.get(j);
      if (key.equals(change.getName())) {
        if (j > 0) {
          output.append("\n");
        }
        output.append(change.toString());
      }
    }
  }

  /**
   * Orders a Model's list of Shapes to be animated.
   */
  private void orderShapes() {
    List<Shapes> orderedShapes = new ArrayList<>();
    for (Motion motion : changes) {
      String key = motion.getName();
      for (Shapes shape : shapes) {
        if (shape.getName().equals(key)) {
          if (!orderedShapes.contains(shape)) {
            orderedShapes.add(shape);
          }
        }
      }
    }
    this.shapes = orderedShapes;
  }

  @Override
  public ArrayList<Motion> getChanges() {
    return new ArrayList<>(changes);
  }

  @Override
  public ArrayList<Shapes> getShapesList() {
    return new ArrayList<>(shapes);
  }

  @Override
  public Canvas getCanvas() {
    return canvas;
  }

  /**
   * Sets the Canvas sized to be used with the Model.
   *
   * @param c the Canvas size to be used when creating Model for animation.
   */
  public void setCanvas(Canvas c) {
    this.canvas = c;
  }

  @Override
  public void declareShape(String name, String type) {
    if (type.equalsIgnoreCase("rectangle")) {
      this.shapes.add(new Shapes(name, type));
    } else if (type.equalsIgnoreCase("ellipse")) {
      this.shapes.add(new Shapes(name, type));
    } else {
      throw new IllegalArgumentException("Invalid shape type");
    }
  }

  @Override
  public String getShapes() {
    StringBuilder createdShapes = new StringBuilder();
    for (int i = 0; i < shapes.size(); i++) {
      Shapes shape = shapes.get(i);
      createdShapes.append(String.format("%s %s", shape.getName(), shape.getType()));
      if (i < shapes.size() - 1) {
        createdShapes.append("\n");
      }
    }
    return createdShapes.toString();
  }

  @Override
  public void removeShape(String name) {
    int initSize = this.shapes.size();
    for (int i = 0; i < shapes.size(); i++) {
      if (shapes.get(i).getName().equals(name)) {
        shapes.remove(i);
        break;
      }
    }
    if (initSize == this.shapes.size()) {
      throw new IllegalArgumentException("That shape does not exist!");
    }
  }

  @Override
  public void addMotion(String name,
      int t1, int t2, int x1, int y1, int x2, int y2, int r1, int g1,
      int b1, int r2, int g2, int b2, int w1, int h1, int w2, int h2) {
    Motion m = new Motion(name, t1, t2, x1, y1, x2, y2, new Color(r1, g1, b1),
        new Color(r2, g2, b2), w1, h1, w2, h2);
    changes.add(m);
  }

  @Override
  public String getMotionsForShape(String name) {
    StringBuilder motions = new StringBuilder();
    for (Motion motion : changes) {
      if (motion.getName().equals(name)) {
        if (!(motions.toString().isEmpty())) {
          motions.append("\n");
        }
        motions.append(motion.toString());
      }
    }
    return motions.toString();
  }

  /**
   * Returns the amount of key frames per model based on ticks-per-second(?).
   *
   * @param oldVal  value to be changed.
   * @param newVal  the intended value to be generated.
   * @param time    the amount of time to apply to the change over.
   * @param oldTime the start time for the change.
   * @param newTime the time to end the change.
   * @return the rounded new value.
   */
  private int tweener(double oldVal, double newVal, double time, double oldTime, double newTime) {
    double tweened = oldVal * ((newTime - time) / (newTime - oldTime))
        + newVal * ((time - oldTime) / (newTime - oldTime));
    return (int) Math.round(tweened);
  }

  /**
   * Creates key frames of the Model to be used with classes implementing IVIew.
   *
   * @param time ticks-per-second(?).
   * @return list of key frames to be used for IView implementing classes.
   */
  public ArrayList<KeyFrame> makeAnimation(int time) {
    ArrayList<KeyFrame> frame = new ArrayList<>();
    for (Shapes shape : this.shapes) {
      String key = shape.getName();
      for (Motion change : this.changes) {
        if (key.equals(change.getName())
            && (change.getStartTime() <= time)
            && (change.getEndTime() >= time)) {
          frame.add(createFrame(change, shape, time));
        }
      }
    }
    return frame;
  }

  /**
   * Creates a KeyFrame from a Motion, Shape, and Tick-Per-Second(?).
   *
   * @param m    Motion to be tweened.
   * @param s    Shape to be tweened.
   * @param time ticks-per-second?
   * @return tweened frame to be animated.
   */
  private KeyFrame createFrame(Motion m, Shapes s, int time) {
    int x = tweener(m.getStartX(), m.getEndX(), time, m.getStartTime(), m.getEndTime());
    int y = tweener(m.getStartY(), m.getEndY(), time, m.getStartTime(), m.getEndTime());

    int width = tweener(m.getStartingWidth(), m.getEndingWidth(),
        time, m.getStartTime(), m.getEndTime());
    int height = tweener(m.getStartingHeight(), m.getEndingHeight(),
        time, m.getStartTime(), m.getEndTime());
    int colorRed = tweener(m.getOldColor().getRed(), m.getNewColor().getRed(),
        time, m.getStartTime(), m.getEndTime());
    int colorGreen = tweener(m.getOldColor().getGreen(), m.getNewColor().getGreen(),
        time, m.getStartTime(), m.getEndTime());
    int colorBlue = tweener(m.getOldColor().getBlue(), m.getNewColor().getBlue(),
        time, m.getStartTime(), m.getEndTime());

    Color frameColor = new Color(colorRed, colorGreen, colorBlue);
    KeyFrame frame;

    if (s.getType().equals("rectangle")) {
      frame = new KeyFrame(time, height, width, x, y, frameColor, "rectangle");
    } else if (s.getType().equals("ellipse")) {
      frame = new KeyFrame(time, height, width, x, y, frameColor, "ellipse");
    } else {
      throw new IllegalArgumentException("not a valid shape");
    }
    return frame;
  }

  @Override
  public void removeMotion(String name, int t1, int x1, int y1, int w1, int h1, int r1, int g1,
      int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2) {
    Motion check = new Motion(name, t1, t2, x1, y1, x2, y2, new Color(r1, g1, b1), new Color(r2,
        g2, b2), w1, h1, w2, h2);
    int initSize = this.changes.size();
    for (int i = 0; i < changes.size(); i++) {
      if (changes.get(i).equals(check)) {
        changes.remove(i);
      }
    }
    if (this.changes.size() == initSize) {
      throw new IllegalArgumentException("That motion does not exist!");
    }
    changes.sort(byStartTime);
  }

  /**
   * Gets the total end time for the Animation.
   *
   * @return the final time of the animation.
   */
  public int getFinalTime() {
    ArrayList<Motion> endTimeSorted = new ArrayList<>(changes);
    Comparator<Motion> byEndTime = Comparator.comparing(Motion::getEndTime);
    endTimeSorted.sort(byEndTime);
    return endTimeSorted.get(endTimeSorted.size() - 1).getEndTime();
  }
}