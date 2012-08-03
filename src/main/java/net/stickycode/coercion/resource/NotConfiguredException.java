package net.stickycode.coercion.resource;

import net.stickycode.exception.PermanentException;

@SuppressWarnings("serial")
public class NotConfiguredException
    extends PermanentException {

  public NotConfiguredException(Class<?> target, String fieldName) {
    super("Expected {}.{} to be configured but it was null", target, fieldName);
  }

}
