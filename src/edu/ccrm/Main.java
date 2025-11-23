package edu.ccrm;

import edu.ccrm.cli.CliManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting CCRM Application...");
        CliManager cliManager = new CliManager();
        cliManager.start();
    }
}