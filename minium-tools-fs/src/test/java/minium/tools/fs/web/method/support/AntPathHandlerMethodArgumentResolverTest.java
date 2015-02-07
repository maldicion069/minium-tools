package minium.tools.fs.web.method.support;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class AntPathHandlerMethodArgumentResolverTest {

    @Test
    public void testDecode() {
        AntPathHandlerMethodArgumentResolver resolver = new AntPathHandlerMethodArgumentResolver();
        assertThat(resolver.decode("/a/b d/c"), equalTo("/a/b d/c"));
        assertThat(resolver.decode("/a/b+d/c"), equalTo("/a/b d/c"));
        assertThat(resolver.decode("/a/b%20d/c"), equalTo("/a/b d/c"));
    }

}
