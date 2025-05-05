package org.acme.subcommands;

import jakarta.inject.Inject;
import org.acme.entity.UserService;
import picocli.CommandLine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * GetCommand
 * If there is no id then return all users
 * Else return the user with the id
 * Throw exception if user not found
 * return JSON file with users if found on the specified path
 */

@CommandLine.Command(name = "get")
public class GetCommand implements Runnable {

    @Inject
    UserService userService;

    @CommandLine.Option(names = {"--id"}, description = "Id")
    private String id;

    @CommandLine.Option(names = {"-p", "--path"}, description = "Absolute path of the output file")
    private String path;


    @Override
    public void run() {
        try {
            var fileName = createNameFile();
            var file = createFile(fileName);
            writeFile(file);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private File createFile(String fileName) throws IOException {
        var file = new File(path + File.separator + fileName);
        if (!file.createNewFile()) {
            throw new IOException("Error while creating file : " + file.getAbsolutePath());
        } else {
            return file;
        }
    }

    private void writeFile(File file) throws Exception {
        try (var writer = new FileWriter(file)) {
            if (id.isEmpty()) {
                //if id is empty return all users
                writer.write(userService.userToCSV(userService.findAll()));
            } else {
                //return user with the id
                var longId = Long.valueOf(id);
                writer.write(userService.userToCSV(longId));
            }
        } catch (Exception e) {
            throw new Exception("Error while writing to file : " + e.getMessage());
        }
    }

    private String createNameFile() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".csv";
    }
}
