package w.rdy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

/**
 * Context.io contact resource, incomplete;
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact {
@NonNull public final String email;
@NonNull public final int count;
@NonNull public final String name;

public Contact(@JsonProperty("email") String email,
               @JsonProperty("count") int count,
               @JsonProperty("name") String name) {
  this.email = email;
  this.count = count;
  this.name = name;
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
