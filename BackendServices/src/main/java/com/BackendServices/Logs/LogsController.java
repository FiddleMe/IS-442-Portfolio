package com.BackendServices.Logs;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogsController {
    private final LogsService logsService;


    public LogsController(LogsService logsService) {
        this.logsService = logsService;
    }

    @PostMapping("/post")
    public Logs postLog(@RequestParam String userId, @RequestParam String action) {
        return logsService.postLog(userId, action);
    }

    @GetMapping("/all")
    public List<Logs> getAllLogs() {
        return logsService.getAllLogs();
    }
}
