package w.rdy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

import java.util.Collections;
import java.util.List;

/**
 * Context.io contacts resource, incomplete.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactsList {

@NonNull
public final List<Contact> matches;

public ContactsList(@JsonProperty("matches") List<Contact> matches) {
  this.matches = Collections.unmodifiableList(matches);
}
}
