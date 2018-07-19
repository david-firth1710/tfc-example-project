package net.atos.tfc.example.test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import net.atos.tfc.example.GreetingGenerator;
import org.junit.Test;

public class GreetingGeneratorTest
{
    private GreetingGenerator testee = new GreetingGenerator();

    @Test
    public void shouldSayHelloWorld()
    {
        String message = testee.generateGreeting();

        assertThat("message", message, is(equalTo("Hello World!")));
    }

    @Test
    public void shouldSayHelloBob()
    {
        String message = testee.generateGreeting("Bob");

        assertThat("message", message, is(equalTo("Hello Bob!")));
    }
}
