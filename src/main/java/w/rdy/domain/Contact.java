package w.rdy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import static java.util.Objects.requireNonNull;

/**
 * Context.io contact resource, incomplete;
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact {

public String getEmail() {
  return email;
}

public int getCount() {
  return count;
}

public final String email;
public final int count;

public Contact(@JsonProperty("email") String email,
               @JsonProperty("count") int count) {
  this.email = requireNonNull(email);
  this.count = requireNonNull(count);
}

public static enum ContactField {
  EMAIL("email"), COUNT("count"), RECEIVED_COUNT("received_count"), SENT_COUNT("sent_count");
  public final String param;

  ContactField(String param) {
    this.param = param;
  }

  @Override
  public String toString() {
    return param;
  }
}
}
