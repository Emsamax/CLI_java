package org.acme.subcommands;

import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@CommandLine.Command(name = "read")
public class ReadCommand implements Runnable {

    @CommandLine.Option(names = {"-f", "--file"}, description = "The file to read", required = true)
    String file;

    private String EMPTY_FILE_EXCEPTION = "File is empty";

    public void run() {
        var inputFile = new File(file);
        // Vérifier si le fichier existe et est lisible
        if (!inputFile.exists() || !inputFile.canRead()) {
            System.err.println("File does not exist or cannot be read.");
            return;
        }

        //automatically closes the resources within the try block
        try (var bufferedReader = new BufferedReader(new FileReader(file))) {
            var line = bufferedReader.readLine();
            if (line == null) {
                throw new IOException(EMPTY_FILE_EXCEPTION);
            }
            while (line != null) {
                System.out.println(line);
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
