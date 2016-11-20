import java.util.Arrays;
import java.util.Random;

public class BinarySearchTest {
  public static Random rand = new Random();
  
  /**
   * requires: - 'array' is not null.
   *           - the array is sorted by increasing value, i.e. for each index
   *             'i' and index 'j', if 'i' < 'j' then 'array[i]' < 'array[j]'.
   * ensures:  - if there is an index 'i' such that 'array[i] == value', the
   *             method returns true.
   *           - otherwise, it returns false.
   *           - in either case, 'array' is left unchanged.
   **/
  public static boolean search(int[] array, int value) {
    int left = 0;
    int right = array.length - 1;
    while (left <= right) {
      int index = (right + left) / 2;
      if (array[index] == value)
        return true;
      if (array[index] < value)
        right = index - 1;
      else
        left = index + 1;
    }
    return false;

  }

  public static boolean contains(int[] array, int value) {
    for (int a : array)
      if (a == value)
        return true;
    return false;
  }

  public static int[] generateArray(int length) {
    // TODO: generate array.
    int[] array = new int[length];
    for (int i = 0; i < length; i++) array[i] = rand.nextInt(Integer.MAX_VALUE);
    return array;
  }

  public static int pickElementFrom(int[] array) {
    // TODO: pick random element from array.
    return array[rand.nextInt(array.length)];
  }

  public static int pickElementOutside(int[] array) {
    // TODO: pick random value unequal to any value in the array.
    int randVal = -1;
    do {
        randVal = rand.nextInt(Integer.MAX_VALUE);
    } while (contains(array, randVal));
    return randVal;
  }

  public static boolean test(int[] array, int value) {
    try {
      // TODO: check that the function 'search' gives the same result as
      // the function 'contains'.
      
      return false;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public static void main(String[] arg) {
    for (int i = 0; i < 1000; i++) {
      int[] array = generateArray(rand.nextInt(20) + 1);
      int value = rand.nextBoolean() ? pickElementFrom(array)
          : pickElementOutside(array);
      if (!test(array, value)) {
        System.out.println("Value: " + value);
        System.out.println("Array: " + Arrays.toString(array));
        System.exit(0);
      }
    }
    System.out.println("Done.");
  }
}
