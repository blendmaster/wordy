package w.rdy;

public class Wordy {

public static interface Fn {
  public int apply(int input);

}

public static void main(String[] args) {
  Fn square = i -> i * i;
  System.out.println("Wordy: " + square.apply(2));
  System.exit(0);
}
}
