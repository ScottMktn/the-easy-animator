package cs3500.animator.provider.view;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JFrame;

import cs3500.animator.provider.model.Motion;
import cs3500.animator.provider.model.Shapes;
import cs3500.animator.provider.model.SimpleAnimationImpl;
import cs3500.animator.provider.util.Canvas;
import cs3500.animator.provider.util.KeyFrame;


/**
 * Creates a Visual View that can be edited by the User.
 */
public class EditAnimationView extends JPanel implements ActionListener, IView {

  private SimpleAnimationImpl model;
  private Canvas canvas;
  private JPanel topPanelBox = new JPanel();
  private JPanel bottomPanelBox = new JPanel();
  private JButton begin = new JButton("Begin!");
  private JButton restart = new JButton("Restart");
  private JButton speedUp = new JButton("Speed+");
  private JButton slowDown = new JButton("Speed-");
  private JToggleButton playPause = new JToggleButton("Play/Pause");
  private JToggleButton loop = new JToggleButton("Loop");
  private JTextField textArea = new JTextField(10);
  private JButton deleteShape = new JButton("Delete Shape");
  // id: background t: 50 end
  private JButton deleteKeyframe = new JButton("Delete Frame");
  private JButton addKeyframe = new JButton("Add Frame");
  // id: background t: 50 x: 40 y: 50 w: 70 h: 80 r: 240 g: 60 b: 70 end
  private JButton modifyKeyframe = new JButton("Modify Frame");
  //addShape format example:
  // "id: bob type: rectangle t1: 1 t2: 200 x1: 20 x2: 50 y1: 200 y2: 300 w1: 50 w2: 50 h1: 50
  // h2: 50 r1: 250 r2: 0 g1: 40 g2: 80 b1: 250 b2: 90 end
  private JButton addShape = new JButton("Add Shape");
  private JButton addKeyFrame = new JButton("Add Frame");
  private ArrayList<Shapes> shapesCopy;
  private ArrayList<Motion> motionsCopy;
  private Timer t;
  private int speed;
  private int time;
  private int freeze;
  private int looper;
  private int finalTime;
  private boolean beginning;

  /**
   * Creates a Visual View that the User can edit.
   *
   * @param model  the model to be animated.
   * @param canvas the size for the animation.
   */
  public EditAnimationView(SimpleAnimationImpl model, Canvas canvas) {
    this.model = model;
    this.canvas = canvas;
    this.finalTime = model.getFinalTime();
    shapesCopy = new ArrayList<>(model.shapes);
    motionsCopy = new ArrayList<>(model.getChanges());
    beginning = true;
    looper = 1;
    freeze = 1;
    speed = 1;
    time = 0;
    t = new Timer(1000 / this.speed, this);
    t.start();
  }

  @Override
  public void setSpeed(int s) {
    if (s > 0 && s <= 1000) {
      this.speed = s;
      t.setDelay(1000 / s);
    }
  }

  @Override
  public void display(String out) throws IllegalArgumentException {
    if (!(out.equals(""))) {
      throw new IllegalArgumentException("Cannot save a visual view to an output file!");
    }
    JFrame f = new JFrame();
    f.add(this, BorderLayout.CENTER);
    f.setVisible(true);
    f.setSize(this.canvas.w, this.canvas.h * 2);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setTitle("Animator");

    topPanelBox.setBackground(Color.PINK);
    topPanelBox.setOpaque(true);
    topPanelBox.setSize(canvas.w, canvas.h);
    bottomPanelBox.setBackground(Color.PINK);
    bottomPanelBox.setOpaque(true);
    bottomPanelBox.setSize(canvas.w, canvas.h);
    addKeyFrame.setActionCommand("addKeyFrame");
    addKeyFrame.addActionListener(e -> {
      String text = textArea.getText();
      String[] parts = text.split("id: ");
      String[] parts2 = parts[1].split(" t: ");
      String name = parts2[0];
      System.out.println(name);
      String[] parts3 = parts2[1].split(" x: ");
      int frameTime = Integer.parseInt(parts3[0]);
      System.out.println(frameTime);
      String[] parts4 = parts3[1].split(" y: ");
      int x = Integer.parseInt(parts4[0]);
      System.out.println(x);
      String[] parts5 = parts4[1].split(" w: ");
      int y = Integer.parseInt(parts5[0]);
      System.out.println(y);
      String[] parts6 = parts5[1].split(" h: ");
      int w = Integer.parseInt(parts6[0]);
      System.out.println(w);
      String[] parts7 = parts6[1].split(" r: ");
      int h = Integer.parseInt(parts7[0]);
      System.out.println(h);
      String[] parts8 = parts7[1].split(" g: ");
      int r = Integer.parseInt(parts8[0]);
      System.out.println(r);
      String[] parts9 = parts8[1].split(" b: ");
      int g = Integer.parseInt(parts9[0]);
      System.out.println(g);
      String[] parts10 = parts9[1].split(" end");
      int b = Integer.parseInt(parts10[0]);
      System.out.println(b);

      for (int i = 0; i < model.changes.size(); i++) {
        if (model.changes.get(i).getName().equals(name)) {
          if (model.changes.get(i).getStartTime() == frameTime) {
            Motion m = model.changes.get(i);
            m = new Motion(m.getName(), m.getStartTime(), m.getEndTime()
                    , x, y, m.getEndX(), m.getEndY()
                    , new cs3500.animator.provider.util.Color(r, g, b), m.getNewColor()
                    , w, h, m.getEndingWidth(), m.getEndingHeight());
            model.changes.remove(i);
            model.changes.add(i, m);
          }
          if (model.changes.get(i).getEndTime() == frameTime) {
            Motion m = model.changes.get(i);
            m = new Motion(m.getName(), m.getStartTime(), m.getEndTime()
                    , m.getStartX(), m.getStartY(), x, y, m.getOldColor()
                    , new cs3500.animator.provider.util.Color(r, g, b), m.getStartingWidth()
                    , m.getStartingHeight(), w, h);
            model.changes.remove(i);
            model.changes.add(i, m);
          }
        }
      }
    });
    bottomPanelBox.add(addKeyFrame);
    deleteKeyframe.setActionCommand("deleteFrame");
    deleteKeyframe.addActionListener(e -> {
      String text = textArea.getText();
      String[] parts = text.split("id: ");
      String[] parts2 = parts[1].split(" t: ");
      String name = parts2[0];
      System.out.println(name);
      String[] parts3 = parts2[1].split(" end");
      int frameTime = Integer.parseInt(parts3[0]);
      Motion m1 = null;
      Motion m2;
      for (int i = 0; i < model.changes.size(); i++) {
        if (model.changes.get(i).getName().equals(name)) {
          if (model.changes.get(i).getStartTime() == frameTime) {
            m2 = model.changes.get(i);
            model.changes.remove(i);
            Motion merged = new Motion(name, m1.getStartTime(), m2.getEndTime()
                    , m1.getStartX(), m1.getStartY(), m2.getEndX(), m2.getEndY()
                    , m1.getOldColor(), m2.getNewColor(), m1.getStartingWidth()
                    , m1.getStartingHeight(), m2.getEndingWidth(), m2.getEndingHeight());
            if (m1 == null) {
              model.changes.remove(i);
            } else {
              model.changes.remove(i);
              model.changes.add(i, merged);
            }
          }
          if (model.changes.get(i).getEndTime() == frameTime) {
            m1 = model.changes.get(i);
            model.changes.remove(i);
          }
        }
      }
    });
    bottomPanelBox.add(deleteKeyframe);
    modifyKeyframe.setActionCommand("modify");
    modifyKeyframe.addActionListener(e -> {
      String text = textArea.getText();
      String[] parts = text.split("id: ");
      String[] parts2 = parts[1].split(" t: ");
      String name = parts2[0];
      System.out.println(name);
      String[] parts3 = parts2[1].split(" x: ");
      int frameTime = Integer.parseInt(parts3[0]);
      System.out.println(frameTime);
      String[] parts4 = parts3[1].split(" y: ");
      int x = Integer.parseInt(parts4[0]);
      System.out.println(x);
      String[] parts5 = parts4[1].split(" w: ");
      int y = Integer.parseInt(parts5[0]);
      System.out.println(y);
      String[] parts6 = parts5[1].split(" h: ");
      int w = Integer.parseInt(parts6[0]);
      System.out.println(w);
      String[] parts7 = parts6[1].split(" r: ");
      int h = Integer.parseInt(parts7[0]);
      System.out.println(h);
      String[] parts8 = parts7[1].split(" g: ");
      int r = Integer.parseInt(parts8[0]);
      System.out.println(r);
      String[] parts9 = parts8[1].split(" b: ");
      int g = Integer.parseInt(parts9[0]);
      System.out.println(g);
      String[] parts10 = parts9[1].split(" end");
      int b = Integer.parseInt(parts10[0]);
      System.out.println(b);
      for (int i = 0; i < model.changes.size(); i++) {
        if (model.changes.get(i).getName().equals(name)) {
          if (model.changes.get(i).getStartTime() == frameTime) {
            Motion m = model.changes.get(i);
            m = new Motion(m.getName(), m.getStartTime(), m.getEndTime()
                    , x, y, m.getEndX(), m.getEndY(),
                    new cs3500.animator.provider.util.Color(r, g, b), m.getNewColor()
                    , w, h, m.getEndingWidth(), m.getEndingHeight());
            model.changes.remove(i);
            model.changes.add(i, m);
          }
          if (model.changes.get(i).getEndTime() == frameTime) {
            Motion m = model.changes.get(i);
            m = new Motion(m.getName(), m.getStartTime(), m.getEndTime()
                    , m.getStartX(), m.getStartY(), x, y, m.getOldColor()
                    , new cs3500.animator.provider.util.Color(r, g, b), m.getStartingWidth()
                    , m.getStartingHeight(), w, h);
            model.changes.remove(i);
            model.changes.add(i, m);
          }
        }
      }
    });
    bottomPanelBox.add(modifyKeyframe);
    addShape.setActionCommand("addShape");
    addShape.addActionListener(e -> {
      String text = textArea.getText();
      String[] parts = text.split("id: ");
      String[] parts2 = parts[1].split(" type: ");
      String name = parts2[0];
      String[] parts3 = parts2[1].split(" t1: ");
      String type = parts3[0];
      System.out.println(name);
      System.out.println(type);
      Shapes addedShape = new Shapes(name, type);
      String[] parts4 = parts3[1].split(" t2: ");
      int time1 = Integer.parseInt(parts4[0]);
      System.out.println(time1);
      String[] parts5 = parts4[1].split(" x1: ");
      int time2 = Integer.parseInt(parts5[0]);
      System.out.println(time2);
      String[] parts6 = parts5[1].split(" x2: ");
      int x1 = Integer.parseInt(parts6[0]);
      System.out.println(x1);
      String[] parts7 = parts6[1].split(" y1: ");
      int x2 = Integer.parseInt(parts7[0]);
      System.out.println(x2);
      String[] parts8 = parts7[1].split(" y2: ");
      int y1 = Integer.parseInt(parts8[0]);
      System.out.println(y1);
      String[] parts9 = parts8[1].split(" w1: ");
      int y2 = Integer.parseInt(parts9[0]);
      System.out.println(y2);
      String[] parts10 = parts9[1].split(" w2: ");
      int w1 = Integer.parseInt(parts10[0]);
      System.out.println(w1);
      String[] parts11 = parts10[1].split(" h1: ");
      int w2 = Integer.parseInt(parts11[0]);
      System.out.println(w2);
      String[] parts12 = parts11[1].split(" h2: ");
      int h1 = Integer.parseInt(parts12[0]);
      System.out.println(h1);
      String[] parts13 = parts12[1].split(" r1: ");
      int h2 = Integer.parseInt(parts13[0]);
      System.out.println(h2);
      String[] parts14 = parts13[1].split(" r2: ");
      int r1 = Integer.parseInt(parts14[0]);
      System.out.println(r1);
      String[] parts15 = parts14[1].split(" g1: ");
      int r2 = Integer.parseInt(parts15[0]);
      System.out.println(r2);
      String[] parts16 = parts15[1].split(" g2: ");
      int g1 = Integer.parseInt(parts16[0]);
      System.out.println(g1);
      String[] parts17 = parts16[1].split(" b1: ");
      int g2 = Integer.parseInt(parts17[0]);
      System.out.println(g2);
      String[] parts18 = parts17[1].split(" b2: ");
      int b1 = Integer.parseInt(parts18[0]);
      System.out.println(b1);
      String[] parts19 = parts18[1].split(" end");
      int b2 = Integer.parseInt(parts19[0]);
      System.out.println(b2);
      Motion addedMotion = new Motion(name, time1, time2, x1, y1, x2, y2
              , new cs3500.animator.provider.util.Color(r1, g1, b1),
              new cs3500.animator.provider.util.Color(r2, g2, b2), w1, h1, w2, h2);
      model.shapes.add(addedShape);
      model.changes.add(addedMotion);
    });
    bottomPanelBox.add(addShape);
    deleteShape.setActionCommand("deleteShape");
    deleteShape.addActionListener(e -> {
      String s = textArea.getText();
      System.out.println(s);
      for (int i = 0; i < model.shapes.size(); i++) {
        String id = model.shapes.get(i).getName();
        if (s.equals(id)) {
          model.shapes.remove(i);
        }
      }
    });
    bottomPanelBox.add(deleteShape);
    bottomPanelBox.add(textArea);
    begin.addActionListener(e -> {
      beginning = false;
    });
    topPanelBox.add(begin);
    restart.setActionCommand("restart");
    restart.addActionListener(e -> {
      time = 0;
      model.shapes = new ArrayList<>(shapesCopy);
      model.changes = new ArrayList<>(motionsCopy);
    });
    topPanelBox.add(restart);
    speedUp.setActionCommand("speedup");
    speedUp.addActionListener(e -> {
      setSpeed(speed += 5);
    });
    topPanelBox.add(speedUp);
    slowDown.addActionListener(e -> {
      setSpeed(speed -= 5);
    });
    slowDown.setActionCommand("slowdown");
    topPanelBox.add(slowDown);
    playPause.addActionListener(e -> {
      freeze = freeze * -1;
    });
    playPause.setActionCommand("pause");
    topPanelBox.add(playPause);
    playPause.setActionCommand("loop");
    loop.addActionListener(e -> {
      looper = looper * -1;
    });
    topPanelBox.add(loop);
    f.add(bottomPanelBox, BorderLayout.SOUTH);
    f.add(topPanelBox, BorderLayout.NORTH);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (beginning) {
      time = 0;
    } else if (freeze > 0) {
      time++;
    }
    repaint();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    ArrayList<KeyFrame> frame = this.model.makeAnimation(this.time); //add this.time
    Comparator<KeyFrame> byTime = Comparator.comparing(KeyFrame::getT);
    frame.sort(byTime);

    for (KeyFrame keyFrame : frame) {
      if (keyFrame.type.equals("rectangle")) {
        Color c = new Color(keyFrame.c.getRed(),
                keyFrame.c.getGreen(), keyFrame.c.getBlue());
        g2.setColor(c);
        g2.fillRect(keyFrame.x / 2 + (canvas.w / 4)
                , keyFrame.y / 2 + (canvas.h / 4)
                , keyFrame.w / 2, keyFrame.h / 2);
      }
      if (keyFrame.type.equals("ellipse")) {
        Color c = new Color(keyFrame.c.getRed(),
                keyFrame.c.getGreen(), keyFrame.c.getBlue());
        g2.setColor(c);
        g2.fillOval(keyFrame.x / 2 + (canvas.w / 4)
                , keyFrame.y / 2 + (canvas.h / 4)
                , keyFrame.w / 2, keyFrame.h / 2);
      }
      if (this.time == finalTime && looper < 0) {
        time = 0;
      }
    }
  }
}
