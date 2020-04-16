package cs3500.animator.misc;

import java.util.Objects;

/**
 * This class will house all utility methods that will be used many times throughout the program.
 * Static methods included in this class are: -requireNonNegative(double ... d)
 * -requireNonZero(double ... d) -requireNonNull(Objects ... o)
 * All of the static methods in this class throw IllegalArgumentExceptions if the parameters passed
 * in are invalid.
 */
public final class Utils {

  /**
   * Makes sure that the passed in parameters are all non-negative. Includes zero.
   *
   * @param elements the elements that we are checking for non-negative
   * @throws IllegalArgumentException if any elements is less than 0.
   */
  public static void requireNonNegative(double... elements) {
    for (double element : elements) {
      if (element < 0) {
        throw new IllegalArgumentException("The integer: " + element + " is less than zero");
      }
    }
  }

  /**
   * Makes sure that the passed in parameters are all . Includes zero.
   *
   * @param elements the elements that we are checking for non-negative
   * @throws IllegalArgumentException if any elements is 0.
   */
  public static void requireNonZero(double... elements) {
    for (double element : elements) {
      if (element == 0.0) {
        throw new IllegalArgumentException("The integer: " + element + " is zero");
      }
    }
  }

  /**
   * Simply checks the objects passed in if they are not null, and throws an
   * IllegalArgumentException if they are null.
   *
   * @param objs the elements that we are checking if they aren't null
   * @throws IllegalArgumentException if any elements are null.
   */
  public static void requireNonNull(Object... objs) {
    try {
      for (Object obj : objs) {
        Objects.requireNonNull(obj);
      }
    } catch (NullPointerException e) {
      throw new IllegalArgumentException(ErrorMessages.NullParameter.errorMessage());
    }
  }

}
