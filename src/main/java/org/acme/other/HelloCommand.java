package org.acme.other;

import picocli.CommandLine;

import jakarta.inject.Inject;

@CommandLine.Command
public class HelloCommand implements Runnable {
    @Inject GreetingService greetingService;

    @CommandLine.Option(names = {"-n", "--name"}, description = "Your name.", defaultValue = "world")
    String name;

    @Override
    public void run() {
    }

    public String greeting() {
        return greetingService.greeting(name);
    }
}
