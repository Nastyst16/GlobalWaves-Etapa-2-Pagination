package main.commands;

import main.Command;
import main.CommandVisitor;

public class Default implements Command {


    public Default() {

    }

    public void execute() {
        System.out.println("Invalid command");
    }

    @Override
    public void accept(CommandVisitor visitor) {
    }


}
