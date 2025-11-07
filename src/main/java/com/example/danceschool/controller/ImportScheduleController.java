package com.example.danceschool.controller;

import com.example.danceschool.service.ScheduleEntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImportScheduleController {

    private final ScheduleEntryService scheduleEntryService;

    public ImportScheduleController(ScheduleEntryService scheduleEntryService) {
        this.scheduleEntryService = scheduleEntryService;
    }

    @PostMapping("/schedule-entries/import")
    public ResponseEntity<String> importSchedule() {
        scheduleEntryService.importSchedule();
        return ResponseEntity.accepted().body("Imported");
    }
}