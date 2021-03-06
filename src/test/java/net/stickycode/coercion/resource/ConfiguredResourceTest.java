package net.stickycode.coercion.resource;

import static org.fest.assertions.Assertions.assertThat;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.stickycode.mockwire.MockwireConfigured;
import net.stickycode.mockwire.junit4.MockwireRunner;
import net.stickycode.stereotype.configured.Configured;
import net.stickycode.stereotype.resource.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MockwireRunner.class)
@MockwireConfigured({
    "configuredResourceTest.bean.uri=bean.xml"
})
public class ConfiguredResourceTest {

  @XmlRootElement
  public static class Bean {

    @XmlAttribute
    String value;
  }

  @Configured
  Resource<Bean> bean;

  @Test
  public void configured() {
    assertThat(bean.get().value).isEqualTo("loaded");
  }
}
