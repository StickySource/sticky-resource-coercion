package net.stickycode.coercion.resource;

import javax.inject.Inject;

import net.stickycode.coercion.AbstractNoDefaultCoercion;
import net.stickycode.coercion.CoercionTarget;
import net.stickycode.resource.ResourceCodec;
import net.stickycode.resource.ResourceCodecRegistry;
import net.stickycode.stereotype.component.StickyExtension;

@StickyExtension
public class ResourceCodecCoecion<T>
    extends AbstractNoDefaultCoercion<ResourceCodec<T>> {

  @Inject
  private ResourceCodecRegistry codecs;

  @Override
  public ResourceCodec<T> coerce(CoercionTarget target, String value) {
    return getDefaultValue(target);
  }

  @Override
  public boolean isApplicableTo(CoercionTarget target) {
    return target.getType().isAssignableFrom(ResourceCodec.class);
  }

  @Override
  public boolean hasDefaultValue() {
    return true;
  }

  @Override
  public ResourceCodec<T> getDefaultValue(CoercionTarget target) {
    return codecs.find(target.getComponentCoercionTypes()[0]);
  }

}
