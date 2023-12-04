package main;

public interface Command {
    /**
     * Execute the command.
     */
    void execute();

    void accept(CommandVisitor visitor);
}
