package net.stickycode.coercion.resource;

import javax.inject.Inject;

import net.stickycode.coercion.Coercion;
import net.stickycode.coercion.CoercionTarget;
import net.stickycode.resource.ResourceCodecRegistry;
import net.stickycode.resource.ResourceProtocolRegistry;
import net.stickycode.stereotype.plugin.StickyExtension;
import net.stickycode.stereotype.resource.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@StickyExtension
public class ResourceCoercion
    implements Coercion<Object> {

  private Logger log = LoggerFactory.getLogger(getClass());

  @Inject
  ResourceProtocolRegistry protocols;

  @Inject
  ResourceCodecRegistry codecs;

  @Override
  public Object coerce(CoercionTarget type, String value) {
    log.debug("creating resource {} of {}", value, type);
    return new StickyResource(type);
  }

  @Override
  public boolean isApplicableTo(CoercionTarget type) {
    boolean assignableFrom = Resource.class.isAssignableFrom(type.getType());
    System.out.println(assignableFrom);
    return assignableFrom;
  }

  @Override
  public Object getDefaultValue(CoercionTarget type) {
    return new StickyResource(type);
  }

  @Override
  public boolean hasDefaultValue() {
    return true;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

}
