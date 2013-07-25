package w.rdy;

/**
 * Context.io isn't perfect, you know.
 */
public class ContextIoException extends RuntimeException {

public ContextIoException(Throwable e) {
  super(e.getMessage(), e);
}

public ContextIoException(String message) {
  super(message, null);
}
}
