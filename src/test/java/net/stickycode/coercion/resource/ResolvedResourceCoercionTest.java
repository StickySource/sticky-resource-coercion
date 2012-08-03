package net.stickycode.coercion.resource;

import static org.fest.assertions.Assertions.assertThat;

import javax.inject.Inject;

import net.stickycode.coercion.CoercionTarget;
import net.stickycode.coercion.target.CoercionTargets;
import net.stickycode.mockwire.MockwireConfigured;
import net.stickycode.mockwire.MockwireContainment;
import net.stickycode.mockwire.junit4.MockwireRunner;
import net.stickycode.reflector.Fields;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MockwireRunner.class)
@MockwireContainment
@MockwireConfigured({})
public class ResolvedResourceCoercionTest {

  @SuppressWarnings("unused")
  private String blah;

  @Inject
  private ResolvedResourceCoercion coercion;

  @Test
  public void defaultClasspath() {
    assertThat(coercion.coerce(target(), "stringResource.txt")).isEqualTo("hmm");
  }

  @Test
  public void loadedResource() {
    Object actual = coercion.coerce(target(), "stringResource.txt");
    assertThat(actual).isEqualTo("hmm");
  }

  @Test
  public void classpathResource() {
    assertThat(coercion.coerce(target(), "classpath://stringResource.txt")).isEqualTo("hmm");
  }

  @Test
  public void classpathWithSlashResource() {
    assertThat(coercion.coerce(target(), "classpath:///net/stickycode/coercion/resource/stringResource.txt")).isEqualTo(
        "hmm");
  }

  // resource:type:protocol:uri
  @Test
  public void cachedHttpResource() {
    assertThat(coercion.coerce(target(), "dummy://blah")).isEqualTo("blah");
  }

  private CoercionTarget target() {
    return CoercionTargets.find(Fields.find(getClass(), "blah"));
  }

}
