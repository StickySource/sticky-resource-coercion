package net.stickycode.coercion.resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.resource.ResourceCodec;
import net.stickycode.resource.ResourceInput;
import net.stickycode.resource.ResourceLocation;
import net.stickycode.resource.ResourceNotFoundException;
import net.stickycode.resource.ResourceOutput;
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
      implements ResourceInput, ResourceOutput {

    ResourceLocation resourceLocation;

    public DummyResourceConnection(ResourceLocation resourceLocation) {
      super();
      this.resourceLocation = resourceLocation;
    }

    @Override
    public void close()
        throws IOException {
    }

    @Override
    public <T> void store(T value, CoercionTarget type, ResourceCodec<T> codec) {
      throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public <T> T load(CoercionTarget resourceTarget, ResourceCodec<T> codec, Charset characterSet) {
      try {
        return codec.load(resourceTarget, new ByteArrayInputStream(resourceLocation.getPath().getBytes("UTF-8")), characterSet);
      }
      catch (UnsupportedEncodingException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public ResourceInput createInput(ResourceLocation resourceLocation)
      throws ResourceNotFoundException {
    return new DummyResourceConnection(resourceLocation);
  }

  @Override
  public ResourceOutput createOutput(ResourceLocation resourceLocation)
      throws ResourceNotFoundException {
    return new DummyResourceConnection(resourceLocation);
  }

}
