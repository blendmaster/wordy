package w.rdy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

import java.util.Collections;
import java.util.List;

/**
 * Context.io message resource, incomplete.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

@NonNull
public final String subject;

@NonNull
public final List<BodyPart> body;

public Message(@JsonProperty("subject") String subject, @JsonProperty("body") List<BodyPart> body) {
  this.subject = subject;
  this.body = body != null ? Collections.unmodifiableList(body) : Collections.emptyList();
}

@Data
public static class BodyPart {

  @NonNull
  public final String content;
  @NonNull
  public final String type;

  public BodyPart(@JsonProperty("content") String content, @JsonProperty("type") String type) {
    this.content = content;
    this.type = type;
  }
}

}
