package net.stickycode.coercion.resource;

import javax.inject.Inject;

import net.stickycode.coercion.AbstractNoDefaultCoercion;
import net.stickycode.coercion.CoercionTarget;
import net.stickycode.resource.ResourceCodecRegistry;
import net.stickycode.resource.ResourceConnection;
import net.stickycode.resource.ResourceProtocolRegistry;

public class ResourceConnectionCoercion
    extends AbstractNoDefaultCoercion<ResourceConnection> {

  @Inject
  ResourceProtocolRegistry protocols;

  @Inject
  ResourceCodecRegistry codecs;

  @Override
  public ResourceConnection coerce(CoercionTarget type, String value) {
    return null;
  }

  @Override
  public boolean isApplicableTo(CoercionTarget target) {
    return false;
  }

}
