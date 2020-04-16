package cs3500.animator.provider.view;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JFrame;

import cs3500.animator.provider.model.SimpleAnimationImpl;
import cs3500.animator.provider.util.Canvas;
import cs3500.animator.provider.util.KeyFrame;


/**
 * Creates a Visual View of an Animation created by the User.
 */
public class VisualAnimationView extends JPanel implements ActionListener, IView {

  private final SimpleAnimationImpl model;
  private final Canvas canvas;
  private int time;
  private final Timer t;

  /**
   * Creates a Visual View for the Animation.
   *
   * @param model the model to be animated.
   * @param canvas the canvas size to be used in the View.
   */
  public VisualAnimationView(SimpleAnimationImpl model, Canvas canvas) {
    this.model = model;
    this.canvas = canvas;
    this.time = 0;
    t = new Timer(10, this); // = new Timer(1, this);
    t.start();
  }

  @Override
  public void setSpeed(int s) {
    if (s > 0 && s <= 1000) {
      t.setDelay(1000 / s);
    }
    else {
      throw new IllegalArgumentException("speed can't be zero");
    }
  }

  @Override
  public void display(String out) throws IllegalArgumentException {
    makeFrame(out);
  }

  /**
   * Creates a JFrame to be used with the Visual View.
   *
   * @param out the name of a file to be saved to.
   */
  private void makeFrame(String out) {
    if (!(out.equals(""))) {
      throw new IllegalArgumentException("Cannot save a visual view to an output file!");
    }
    JFrame f = new JFrame();
    f.add(this, BorderLayout.CENTER);
    f.setVisible(true);
    f.setSize(this.canvas.w, this.canvas.h);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setTitle("move");
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    ArrayList<KeyFrame> frame = this.model.makeAnimation(this.time);
    Comparator<KeyFrame> byTime = Comparator.comparing(KeyFrame::getT);
    frame.sort(byTime);
    for (int i = 0; i < frame.size(); i ++) {
      setShapeColorForView(g2, frame, i);
      if (frame.get(i).type.equals("rectangle")) {
        g2.fillRect(frame.get(i).x, frame.get(i).y, frame.get(i).w, frame.get(i).h);
      }
      if (frame.get(i).type.equals("ellipse")) {
        g2.fillOval(frame.get(i).x, frame.get(i).y, frame.get(i).w, frame.get(i).h);
      }
    }
  }

  /**
   * Sets the color for a shape to be animated.
   *
   * @param g2 the graphic to be mutated.
   * @param frame the Frame to be used.
   * @param i the index location for the Frame.
   */
  static void setShapeColorForView(Graphics2D g2, ArrayList<KeyFrame> frame, int i) {
    Color c = new Color(frame.get(i).c.getRed(),
        frame.get(i).c.getGreen(), frame.get(i).c.getBlue());
    g2.setColor(c);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    time++;
    repaint();
  }
}
