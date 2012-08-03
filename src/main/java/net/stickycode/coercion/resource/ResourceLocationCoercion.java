package net.stickycode.coercion.resource;

import java.net.URI;
import java.net.URISyntaxException;

import net.stickycode.coercion.AbstractNoDefaultCoercion;
import net.stickycode.coercion.CoercionTarget;
import net.stickycode.resource.ResourceLocation;
import net.stickycode.stereotype.plugin.StickyExtension;

@StickyExtension
public class ResourceLocationCoercion
    extends AbstractNoDefaultCoercion<UriResourceLocation> {

  @Override
  public UriResourceLocation coerce(CoercionTarget type, String value) {
    URI url = uri(value);
    return new UriResourceLocation(type.getParent().getComponentCoercionTypes()[0], url);
  }

  private URI uri(String value) {
    try {
      if (value.contains("://"))
        return new URI(value);
      else
        return new URI("classpath://" + value);
    }
    catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean isApplicableTo(CoercionTarget type) {
    return type.getType().isAssignableFrom(ResourceLocation.class);
  }

}
