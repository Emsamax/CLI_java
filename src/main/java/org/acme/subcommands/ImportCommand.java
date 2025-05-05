package org.acme.subcommands;

import jakarta.inject.Inject;
import org.acme.entity.UserService;
import picocli.CommandLine;

@CommandLine.Command(name = "import")
public class ImportCommand implements Runnable{
    @Inject
    UserService userService;

    @CommandLine.Option(names = {"-f", "--file"}, description = "The file to import", required = true)
    private String path;

    @Override
    public void run() {
        try{
            userService.csvToUser(path);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }


}
