package org.acme;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.acme.subcommands.ReadCommand;
import org.acme.subcommands.ImportCommand;
import org.acme.subcommands.GetCommand;
import picocli.CommandLine;


// ImportCommand.class, GetCommand.class
@QuarkusMain
@TopCommand
@CommandLine.Command(subcommands = {ReadCommand.class})
public class ScimCtl implements Runnable, QuarkusApplication {
    @Override
    public void run() {
        System.out.println("Hello from ScimCtl");
        var readCommand = new ReadCommand();
        readCommand.run();
    }

    @Override
    public int run(String... args) {
        return new CommandLine(this).execute(args);
    }
}

