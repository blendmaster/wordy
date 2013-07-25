package w.rdy;

import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import w.rdy.domain.Contact;

import java.util.Map;
import java.util.OptionalDouble;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Wordy {

public static void main(String[] args) {
  if (args.length < 3) {
    System.err.println("Usage: wordy key secret account-id");
    System.exit(-1);
  }

  final String key = args[0], secret = args[1], accountId = args[2];

  AbstractHttpClient client = new DefaultHttpClient();
  ContextIo contextIo = new RealContextIo(client, "https://api.context.io/2.0", key, secret);

  System.out.printf("Ranking literacy for account %s ...\n", accountId);

  Map<String, Double> ranks = getRank(accountId, contextIo);

  int maxLen = ranks.keySet().stream().mapToInt(String::length).max().orElse(0);
  ranks.entrySet().stream().sorted((b, a) -> a.getValue().compareTo(b.getValue())).forEach(entry ->
    System.out.printf("%-" + (maxLen + 1) + "s : %f\n", entry.getKey(), entry.getValue())
  );

  System.exit(0);
}

public static Map<String, Double> getRank(String accountId, ContextIo contextIo) {
  Function<String, OptionalDouble> rank = LiteracyRank.rank.apply(contextIo).apply(accountId);

  return contextIo.getContacts(accountId, Contact.ContactField.RECEIVED_COUNT, ContextIo.SortOrder.DESC).matches.stream()
  .map(Contact::getEmail)
  .collect(Collectors.toMap(Function.<String>identity(), rank.andThen(d -> d.orElse(0))));
}

}
