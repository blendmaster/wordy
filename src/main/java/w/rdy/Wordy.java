package w.rdy;

import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import w.rdy.domain.Contact;

import java.util.OptionalDouble;
import java.util.function.Function;

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

  Function<String, OptionalDouble> rank = LiteracyRank.rank.apply(contextIo).apply(accountId);

  contextIo.getContacts(accountId, Contact.ContactField.RECEIVED_COUNT, ContextIo.SortOrder.DESC).matches.stream()
      .map(Contact::getEmail)
      .map(email -> String.format("%s : %f", email, rank.apply(email).orElse(0)))
      .forEach(System.out::println);

  System.exit(0);
}

}
