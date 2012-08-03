package net.stickycode.coercion.resource;

import static net.stickycode.exception.Preconditions.notNull;

import java.net.URI;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.resource.ResourceLocation;

public class UriResourceLocation
    implements ResourceLocation {

  private URI uri;

  private CoercionTarget type;

  public UriResourceLocation(CoercionTarget type, URI uri) {
    this.type = notNull(type, "A resource location must have a target");
    this.uri = notNull(uri, "A resource location must have a uri");
  }

  public URI getUri() {
    return uri;
  }

  @Override
  public CoercionTarget getResourceTarget() {
    return type;
  }

  @Override
  public String getPath() {
    String schemeSpecificPart = uri.getSchemeSpecificPart();
    if (schemeSpecificPart.length() < 2)
      throw new IllegalStateException("Expected a specific schema part but got nothing useful from " + uri);

    return schemeSpecificPart.substring(2);
  }

  @Override
  public String getScheme() {
    return uri.getScheme();
  }

}
