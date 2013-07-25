package w.rdy;

import w.rdy.domain.Contact;
import w.rdy.domain.ContactsList;
import w.rdy.domain.Message;

import java.util.List;

/**
 * Simple context.io REST API interface, incomplete.
 */
public interface ContextIo {

public ContactsList getContacts(String accountId, Contact.ContactField sortBy, SortOrder order);
public List<Message> getMessages(String accountId, String email, boolean includeBody);

public static enum SortOrder {
  ASC("asc"), DESC("desc");

  public final String param;

  SortOrder(String param) {
    this.param = param;
  }

  @Override
  public String toString() {
    return param;
  }
}
}
