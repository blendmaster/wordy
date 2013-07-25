package w.rdy;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.Recorder;
import co.freeside.betamax.httpclient.BetamaxHttpsSupport;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.SystemDefaultHttpClient;
import org.junit.Rule;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Beta... max?
 */
public class WordyTest {

@Rule
public Recorder recorder = new Recorder();

@Betamax(tape="wordy")
@Test
public void testGetRank() throws Exception {
  AbstractHttpClient client = new SystemDefaultHttpClient();
  BetamaxHttpsSupport.configure(client);

  ContextIo cio = new RealContextIo(client, "https://api.context.io/2.0", "api-key", "secret");

  Map<String,Double> rank = Wordy.getRank("acct-id", cio);

  assertEquals(-4.3, rank.get("a@a"), 0.1);
  assertEquals(-4.3, rank.get("b@b"), 0.1);
}
}
