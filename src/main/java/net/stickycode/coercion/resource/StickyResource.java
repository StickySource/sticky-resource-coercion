package net.stickycode.coercion.resource;

import java.beans.Introspector;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.inject.Inject;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.configuration.ConfigurationTarget;
import net.stickycode.configuration.ResolvedConfiguration;
import net.stickycode.resource.ResourceCodec;
import net.stickycode.resource.ResourceInput;
import net.stickycode.resource.ResourceLocation;
import net.stickycode.resource.ResourceOutput;
import net.stickycode.resource.ResourceProtocol;
import net.stickycode.resource.ResourceProtocolRegistry;
import net.stickycode.stereotype.configured.Configured;
import net.stickycode.stereotype.configured.PostConfigured;
import net.stickycode.stereotype.resource.Resource;

public class StickyResource<T>
    implements Resource<T>, ConfigurationTarget {

  @Configured
  private ResourceLocation uri;

  @Configured
  private ResourceCodec<T> codec;
  
  @Configured
  private Charset characterSet = Charset.forName("UTF-8");

  @Inject
  private ResourceProtocolRegistry protocols;

  private CoercionTarget coercionTarget;

  private ResourceProtocol protocol;

  public StickyResource(CoercionTarget coercionTarget) {
    this.coercionTarget = coercionTarget;
  }

  @PostConfigured
  public void configure() {
    if (codec == null)
      throw new NotConfiguredException(getClass(), "codec");

    if (uri == null)
      throw new NotConfiguredException(getClass(), "uri");

    protocol = protocols.find(uri.getScheme());
  }

  public T get() {
    if (codec == null)
      throw new RuntimeException();

    if (uri == null)
      throw new RuntimeException();

    ResourceInput input = protocol.createInput(uri);
    try {
      return input.load(uri.getResourceTarget(), codec, characterSet);
    }
    finally {
      try {
        input.close();
      }
      catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public void set(T value) {
    ResourceOutput output = protocol.createOutput(uri);
    try {
      output.store(value, uri.getResourceTarget(), codec);
    }
    finally {
      try {
        output.close();
      }
      catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public String join(String delimeter) {
    return Introspector.decapitalize(coercionTarget.getOwner().getSimpleName()) + delimeter + coercionTarget.getName();
  }

  @Override
  public void resolvedWith(ResolvedConfiguration resolved) {
  }

  @Override
  public CoercionTarget getCoercionTarget() {
    return coercionTarget;
  }

}
