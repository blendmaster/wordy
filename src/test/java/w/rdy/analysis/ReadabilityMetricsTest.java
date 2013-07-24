package w.rdy.analysis;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class ReadabilityMetricsTest {

@Test
public void fleschKincaidReadability() {
  assertThat(ReadabilityMetrics.fleschKincaidGradeLevel.apply("b"), is(equalTo(0.39 + 11.8 - 15.59)));
}
}
