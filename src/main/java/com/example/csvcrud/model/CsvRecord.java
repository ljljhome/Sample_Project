package com.example.csvcrud.model;

import java.util.Objects;

public class CsvRecord {
    private String id;
    private String fieldName;
    private String description;
    private String validFrom;
    private boolean activeIn;

    public CsvRecord() {}

    public CsvRecord(String id, String fieldName, String description, String validFrom, boolean activeIn) {
        this.id = id;
        this.fieldName = fieldName;
        this.description = description;
        this.validFrom = validFrom;
        this.activeIn = activeIn;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFieldName() { return fieldName; }
    public void setFieldName(String fieldName) { this.fieldName = fieldName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getValidFrom() { return validFrom; }
    public void setValidFrom(String validFrom) { this.validFrom = validFrom; }

    public boolean isActiveIn() { return activeIn; }
    public void setActiveIn(boolean activeIn) { this.activeIn = activeIn; }

    @Override
    public String toString() {
        return id + "," + fieldName + "," + description + "," + validFrom + "," + activeIn;
    }

    public static CsvRecord fromCsvLine(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length != 5) {
            throw new RuntimeException("Invalid CSV format");
        }
        return new CsvRecord(
                parts[0],
                parts[1],
                parts[2],
                parts[3],
                Boolean.parseBoolean(parts[4])
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CsvRecord record = (CsvRecord) obj;
        return activeIn == record.activeIn &&
                Objects.equals(id, record.id) &&
                Objects.equals(fieldName, record.fieldName) &&
                Objects.equals(description, record.description) &&
                Objects.equals(validFrom, record.validFrom);
    }
}
