package edu.ccrm.config;

public class AppConfig {
    private static AppConfig instance;
    private final String dataExportDirectory;

    private AppConfig() {

        this.dataExportDirectory = "exported-data";
    }

    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    public String getDataExportDirectory() {
        return dataExportDirectory;
    }
}