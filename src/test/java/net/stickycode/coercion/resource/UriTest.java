package net.stickycode.coercion.resource;

import static org.fest.assertions.Assertions.assertThat;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;


public class UriTest {

  @Test
  public void scheme() throws URISyntaxException {
    assertThat(new URI("classpath://host/path.txt").getScheme()).isEqualTo("classpath");
  }
  
  @Test
  public void path() throws URISyntaxException {
    assertThat(new URI("classpath://host/path.txt").getPath()).isEqualTo("/path.txt");
  }
  
  @Test
  public void justpath() throws URISyntaxException {
    assertThat(new URI("classpath:///path.txt").getPath()).isEqualTo("/path.txt");
  }
}
