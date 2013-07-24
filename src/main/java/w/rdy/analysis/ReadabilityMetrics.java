package w.rdy.analysis;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Common readability metrics, straight from Wikipedia.
 */
public class ReadabilityMetrics {

public static <T, U, R> Function<Function<T, Function<U, R>>, Function<Function<T, U>, Function<T, R>>> ap() {
  return a -> b -> c -> a.apply(c).apply(b.apply(c));
}

public static final Function<Double, Function<Double, Double>> add = a -> b -> a + b;
public static final Function<Double, Function<Double, Double>> sub = a -> b -> b - a; // note reverse
public static final Function<Double, Function<Double, Double>> mul = a -> b -> a * b;
public static final Function<Double, Function<Double, Double>> div = a -> b -> a / b;
public static final Function<String, Function<String, List<String>>> split = regex -> text ->
    Arrays.asList(text.split(regex));
public static final Function<List<?>, Double> len = a -> (double) a.size();

public static final Function<String, Double> words = len.compose(split.apply("\\s+"));
public static final Function<String, Double> sentences = len.compose(split.apply("\\. "));
public static final Function<String, Double> syllables =
    len.compose(split.apply("(a|e(?!$)|i(?!ng$)|o|u|y){1,3}|le$|ing$"));

//  subtract 15.59 . ap ((+) . ap ((/) . (0.39 *) . words) sentences) (ap ((/) . (11.8 *) . syllables) words)
public static final Function<String, Double> fleschKincaidGradeLevel =
    sub.apply(15.59).compose(ReadabilityMetrics.<String, Double, Double>ap()
        .apply(add.compose(
            ReadabilityMetrics.<String, Double, Double>ap()
                .apply(div.compose(mul.apply(0.39)).compose(words)).apply(sentences)))
        .apply(ReadabilityMetrics.<String, Double, Double>ap()
            .apply(div.compose(mul.apply(11.8)).compose(syllables)).apply(words)));
}
