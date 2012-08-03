package net.stickycode.coercion.resource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import net.stickycode.resource.ResourceConnection;
import net.stickycode.resource.ResourceLocation;
import net.stickycode.resource.ResourceNotFoundException;
import net.stickycode.resource.ResourceProtocol;
import net.stickycode.stereotype.plugin.StickyExtension;

@StickyExtension
public class DummyProtocol
    implements ResourceProtocol {

  @Override
  public boolean canResolve(String protocol) {
    return "dummy".equals(protocol);
  }

  static class DummyResourceConnection
      implements ResourceConnection {

    ResourceLocation resourceLocation;

    public DummyResourceConnection(ResourceLocation resourceLocation) {
      super();
      this.resourceLocation = resourceLocation;
    }

    @Override
    public InputStream getInputStream() throws ResourceNotFoundException {
      try {
        return new ByteArrayInputStream(resourceLocation.getPath().getBytes("UTF-8"));
      }
      catch (UnsupportedEncodingException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public OutputStream getOutputStream() {
      throw new UnsupportedOperationException("Dummy protocol does not support writing");
    }

    @Override
    public Charset getCharacterSet() {
      return Charset.forName("UTF-8");
    }

    @Override
    public ResourceLocation getLocation() {
      return resourceLocation;
    }

    @Override
    public void store(Object content) {
    }
  }

  @Override
  public ResourceConnection createConnection(ResourceLocation resourceLocation)
      throws ResourceNotFoundException {
    return new DummyResourceConnection(resourceLocation);
  }
}
