package org.acme.subcommands;

import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.FileReader;

@CommandLine.Command(name = "read")
public class ReadCommand implements Runnable {

    @CommandLine.Option(names = {"-f", "--file"}, description = "The file to read", required = true)
    String file;

    public void run() {
        System.out.println("Read command");
        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while(line != null){
                System.out.println(line);
                line = bufferedReader.readLine();
                System.out.println(line);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
