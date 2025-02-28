package com.example.csvcrud.service;

import com.example.csvcrud.dao.CsvDao;
import com.example.csvcrud.model.CsvRecord;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CsvService {
    private final CsvDao csvDao;

    public CsvService(CsvDao csvDao) {
        this.csvDao = csvDao;
    }

    public List<CsvRecord> getAllRecords() throws IOException {
        return csvDao.readAll();
    }

    public CsvRecord getRecordById(String id) throws IOException {
        return csvDao.findById(id);
    }

    public void addRecord(CsvRecord record) throws IOException {
        csvDao.save(record);
    }

    public String updateRecord(CsvRecord record) throws IOException {
        return csvDao.update(record);
    }

    public void deleteRecord(String id) throws IOException {
        csvDao.delete(id);
    }
}
