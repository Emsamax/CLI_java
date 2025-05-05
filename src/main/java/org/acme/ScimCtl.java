package org.acme;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;
import org.acme.subcommands.GetCommand;
import org.acme.subcommands.ImportCommand;
import org.acme.subcommands.ReadCommand;
import picocli.CommandLine;

@QuarkusMain
@TopCommand
@CommandLine.Command(name = "cli", subcommands = {ReadCommand.class, GetCommand.class, ImportCommand.class})
public class ScimCtl implements Runnable, QuarkusApplication {
    @Inject CommandLine.IFactory factory;

    @Override
    public void run() {
        try{
            CommandLine commandLine = new CommandLine(this, factory);
            if (commandLine.getSubcommands() == null) {
                commandLine.usage(System.err);
                return;
            }
            commandLine.execute();
        } catch (Exception e){
            System.err.println(e.getMessage());
        }

    }

    @Override
    public int run(String... args) throws Exception {
        return new CommandLine(this, factory).execute(args);
    }
}

