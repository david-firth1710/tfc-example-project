package net.atos.tfc.example;

import static java.lang.String.format;

public class GreetingGenerator
{
    public String generateGreeting()
    {
        return generateGreeting("World");
    }

    public String generateGreeting(String name)
    {
        return format("Hello %s!", name);
    }
}
