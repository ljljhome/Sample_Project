package com.example.csvcrud.dao;

import com.example.csvcrud.model.CsvRecord;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.*;
import java.util.*;

@Repository
public class CsvDao {
    private static final String CSV_FILE = "src/main/resources/data.csv";
    private static final String HEADER = "id,field_name,description,valid_from,active_in";

    public List<CsvRecord> readAll() throws IOException {
        List<CsvRecord> records = new ArrayList<>();
        Path path = Paths.get(CSV_FILE);

        if (!Files.exists(path)) {
            return records;
        }

        BufferedReader br = new BufferedReader(new FileReader(CSV_FILE));
        String line;
        boolean isFirstLine = true;
        while ((line = br.readLine()) != null) {
            if (isFirstLine) {
                isFirstLine = false; // Skip the header line
                continue;
            }
            records.add(CsvRecord.fromCsvLine(line));
        }
        br.close();
        return records;
    }

    public CsvRecord findById(String id) throws IOException {
        List<CsvRecord> records = readAll();
        for (CsvRecord record : records) {
            if (record.getId().equals(id)) {
                return record;
            }
        }
        throw new RuntimeException("ID " + id + " not found");
    }

    public void save(CsvRecord newRecord) throws IOException {
        List<CsvRecord> records = readAll();

        for (CsvRecord record : records) {
            if (record.getId().equals(newRecord.getId())) {
                throw new RuntimeException("ID already exists: " + newRecord.getId());
            }
        }

        records.add(newRecord);
        writeAll(records);
    }

    public String update(CsvRecord updatedRecord) throws IOException {
        List<CsvRecord> records = readAll();
        boolean found = false;

        for (int i = 0; i < records.size(); i++) {
            CsvRecord existingRecord = records.get(i);
            if (existingRecord.getId().equals(updatedRecord.getId())) {
                found = true;

                // Check if the values are identical
                if (existingRecord.equals(updatedRecord)) {
                    return "No changes detected. Update not needed.";
                }

                // Update the record
                records.set(i, updatedRecord);
                writeAll(records);
                return "Record updated successfully!";
            }
        }

        if (!found) {
            return "ID " + updatedRecord.getId() + " not found. Update failed.";
        }

        return "Unexpected error occurred during update.";
    }

    public void delete(String id) throws IOException {
        List<CsvRecord> records = readAll();
        boolean exists = false;

        Iterator<CsvRecord> iterator = records.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(id)) {
                iterator.remove();
                exists = true;
                break;
            }
        }

        if (!exists) {
            throw new RuntimeException("ID " + id + " not found");
        }

        writeAll(records);
    }

    private void writeAll(List<CsvRecord> records) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE));
        writer.write(HEADER); // Write the header line
        writer.newLine();

        for (CsvRecord record : records) {
            writer.write(record.toString());
            writer.newLine();
        }
        writer.close();
    }
}
