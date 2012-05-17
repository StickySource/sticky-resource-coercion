package net.stickycode.coercion.resource;

import static net.stickycode.exception.Preconditions.notNull;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.resource.ResourceLocation;
import net.stickycode.resource.ResourceProtocol;

public class UriResourceLocation
    implements ResourceLocation {

  private ResourceProtocol protocol;

  private URI uri;

  private CoercionTarget type;

  public UriResourceLocation(CoercionTarget type, ResourceProtocol protocol, URI uri) {
    this.type = notNull(type, "A resource location must have a target");
    this.uri = notNull(uri, "A resource location must have a uri");
    this.protocol = notNull(protocol, "A url resource location must have a protocol");
  }

  public ResourceProtocol getProtocol() {
    return protocol;
  }

  public URI getUri() {
    return uri;
  }

  @Override
  public CoercionTarget getResourceTarget() {
    return type;
  }

  @Override
  public InputStream getInputStream() {
    return protocol.getInputStream(this);
  }

  @Override
  public OutputStream getOutputStream() {
    return protocol.getOutputStream(this);
  }

  @Override
  public String getPath() {
    String schemeSpecificPart = uri.getSchemeSpecificPart();
    if (schemeSpecificPart.length() < 2)
      throw new IllegalStateException("Expected a specific schema part but got nothing useful from " + uri);

    return schemeSpecificPart.substring(2);
  }

}
