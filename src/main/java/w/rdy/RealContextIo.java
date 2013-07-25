package w.rdy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.AbstractHttpClient;
import w.rdy.domain.Contact;
import w.rdy.domain.ContactsList;
import w.rdy.domain.Message;

import java.io.IOException;
import java.util.List;

/**
 * It's real, it's actual. Everything is satisfactual.
 */
public class RealContextIo implements ContextIo {

private static final ObjectMapper json = new ObjectMapper();
public static final TypeReference<List<Message>> MESSAGES = new TypeReference<List<Message>>() {};
public static final TypeReference<ContactsList> CONTACTS_LIST = new TypeReference<ContactsList>() {};

private final AbstractHttpClient client;
private final String base;
private final OAuthConsumer consumer;

public RealContextIo(AbstractHttpClient client, String base, String apiKey, String apiSecret) {
  this.client = client;
  this.base = base;
  consumer = new CommonsHttpOAuthConsumer(apiKey, apiSecret);
}

@Override
public ContactsList getContacts(String accountId, Contact.ContactField sortBy, SortOrder order) {
  return getAccount(accountId, String.format("contacts?sort_by=%s&order=%s", sortBy, order),
                    CONTACTS_LIST);
}

@Override
public List<Message> getMessages(String accountId, String email, boolean includeBody) {
  return getAccount(accountId,
                    String.format("messages?email=%s&include_body=%s", email, includeBody ? "1" : "0"),
                    MESSAGES);
}

private <T> T getAccount(String accountId, String path, final TypeReference<T> type) {
  HttpGet get = new HttpGet(String.format("%s/accounts/%s/%s", base, accountId, path));
  try {
    consumer.sign(get);
  } catch (OAuthException e) {
    throw new ContextIoException(e);
  }
  try {
    // sadly, java 8 can't infer the return type if we change ResponseHandler to a lambda expression :(
    return client.execute(get, new ResponseHandler<T>() {
      @Override
      public T handleResponse(HttpResponse response) throws IOException {
        if (response.getEntity() != null) {
          return json.readValue(response.getEntity().getContent(), type);
        } else {
          throw new ContextIoException("ContextIO returned empty response!");
        }
      }
    });
  } catch (IOException e) {
    throw new ContextIoException(e);
  }
}
}
