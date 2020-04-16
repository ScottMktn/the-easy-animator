import java.io.IOException;

/**
 * This class was created for the purpose of testing a failing appendable in the Controller.
 */
public class FailingAppendable implements Appendable {
  /**
   * All of the methods for this class will throw an IOException, so there are no method sigs.
   * These methods all throw exceptions to util a failing appendable.
   */

  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException();
  }

  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException();
  }

  public Appendable append(char c) throws IOException {
    throw new IOException();
  }
}
