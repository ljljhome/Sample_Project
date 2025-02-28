package com.example.csvcrud.controller;

import com.example.csvcrud.model.CsvRecord;
import com.example.csvcrud.service.CsvService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/csv")
public class CsvController {
    private final CsvService csvService;

    public CsvController(CsvService csvService) {
        this.csvService = csvService;
    }

    @GetMapping("/{id}")
    public CsvRecord getRecordById(@PathVariable String id) throws IOException {
        return csvService.getRecordById(id);
    }

    @GetMapping("/all")
    public List<CsvRecord> getAllRecords() throws IOException {
        return csvService.getAllRecords();
    }

    @PostMapping
    public String addRecord(@RequestBody CsvRecord record) throws IOException {
        csvService.addRecord(record);
        return "Record added successfully!";
    }

    @PutMapping
    public String updateRecord(@RequestBody CsvRecord record) throws IOException {
        return csvService.updateRecord(record);
    }

    @DeleteMapping("/{id}")
    public String deleteRecord(@PathVariable String id) throws IOException {
        csvService.deleteRecord(id);
        return "Record deleted successfully!";
    }
}
