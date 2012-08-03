package net.stickycode.coercion.resource;

import java.beans.Introspector;

import javax.inject.Inject;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.configuration.ConfigurationTarget;
import net.stickycode.configuration.ResolvedConfiguration;
import net.stickycode.resource.ResourceCodec;
import net.stickycode.resource.ResourceLocation;
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

    return codec.load(protocol.createConnection(uri), uri.getResourceTarget());
  }

  public void set(T value) {
    codec.store(uri.getResourceTarget(), value, null);
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
