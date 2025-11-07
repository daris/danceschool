package com.example.danceschool.controller;

import com.example.danceschool.service.DummyDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImportDataController {

    private final DummyDataService dummyDataService;

    public ImportDataController(DummyDataService dummyDataService) {
        this.dummyDataService = dummyDataService;
    }

    @PostMapping("/import")
    public ResponseEntity<String> importData() {
//        dummyDataService.importSchedule();
        dummyDataService.importUsers();
        return ResponseEntity.accepted().body("Imported");
    }
}