package net.stickycode.coercion.resource;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;

import net.stickycode.coercion.AbstractNoDefaultCoercion;
import net.stickycode.coercion.CoercionTarget;
import net.stickycode.resource.ResourceCodecRegistry;
import net.stickycode.resource.ResourceLocation;
import net.stickycode.resource.ResourceProtocol;
import net.stickycode.resource.ResourceProtocolRegistry;
import net.stickycode.stereotype.component.StickyExtension;

@StickyExtension
public class ResourceLocationCoercion
    extends AbstractNoDefaultCoercion<UriResourceLocation> {

  @Inject
  ResourceProtocolRegistry protocols;

  @Inject
  ResourceCodecRegistry codecs;

  @Override
  public UriResourceLocation coerce(CoercionTarget type, String value) {
    URI url = uri(value);
    ResourceProtocol protocol = protocols.find(url.getScheme());
    return new UriResourceLocation(type.getParent().getComponentCoercionTypes()[0], protocol, url);
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
