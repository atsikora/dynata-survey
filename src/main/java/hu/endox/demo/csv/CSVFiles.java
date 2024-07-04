package hu.endox.demo.csv;

public enum CSVFiles {

    MEMBERS("Members.csv"), PARTICIPATION("Participation.csv"), STATUSES("Statuses.csv"), SURVEYS("Surveys.csv");

    private String fileName;

    private CSVFiles(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

}
