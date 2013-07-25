package w.rdy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Context.io message resource, incomplete.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

public List<BodyPart> getBody() {
  return body;
}

public final List<BodyPart> body;

public Message(@JsonProperty("body") List<BodyPart> body) {
  this.body = body != null ? Collections.unmodifiableList(body) : Collections.emptyList();
}

@JsonIgnoreProperties(ignoreUnknown = true)
public static class BodyPart {

  public String getContent() {
    return content;
  }

  public String getType() {
    return type;
  }

  public final String content;
  public final String type;

  public BodyPart(@JsonProperty("content") String content, @JsonProperty("type") String type) {
    this.content = requireNonNull(content);
    this.type = requireNonNull(type);
  }
}

}
