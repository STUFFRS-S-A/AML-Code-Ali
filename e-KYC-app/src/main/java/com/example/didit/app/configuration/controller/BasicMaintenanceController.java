package com.example.didit.app.configuration.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;
import java.util.HashMap;

// ============================================================
// 1. Basic REST Controller
// ============================================================
@RestController
@RequestMapping("/api/v1")
class BasicMaintenanceController {

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("code", "SERVICE_UNAVAILABLE");
        response.put("message", "System is currently under maintenance. Please try again later.");
        response.put("retryAfter", "2026-04-17T08:00:00Z");

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}


// ============================================================
// 2. Controller with Custom Response DTO
// ============================================================
@RestController
@RequestMapping("/api/v2")
class DtoMaintenanceController {

    @GetMapping("/**")
    public ResponseEntity<MaintenanceResponse> handleAll() {
        MaintenanceResponse response = new MaintenanceResponse(
            "maintenance_mode",
            "We are performing scheduled maintenance. We'll be back shortly.",
            Instant.now().toString(),
            "support@example.com"
        );

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    record MaintenanceResponse(
        String status,
        String message,
        String timestamp,
        String support
    ) {}
}


// ============================================================
// 3. Controller with Retry-After Header
// ============================================================
@RestController
@RequestMapping("/api/v3")
class HeaderMaintenanceController {

    @RequestMapping("/**")
    public ResponseEntity<Map<String, String>> handleAll() {
        Map<String, String> body = new HashMap<>();
        body.put("error", "service_unavailable");
        body.put("message", "System is in maintenance mode. Please try again in 1 hour.");
        body.put("estimatedDowntime", "1 hour");

        return ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .header("Retry-After", "3600")
            .body(body);
    }
}


// ============================================================
// 4. Controller with Maintenance Window Details
// ============================================================
@RestController
@RequestMapping("/api/v4")
class WindowMaintenanceController {

    private static final String MAINTENANCE_START = "2026-04-16T22:00:00Z";
    private static final String MAINTENANCE_END   = "2026-04-17T02:00:00Z";

    @GetMapping("/**")
    public ResponseEntity<Map<String, Object>> handleAll() {
        Map<String, Object> maintenance = new HashMap<>();
        maintenance.put("active", true);
        maintenance.put("startedAt", MAINTENANCE_START);
        maintenance.put("estimatedEnd", MAINTENANCE_END);
        maintenance.put("reason", "Scheduled database upgrade");

        Map<String, Object> body = new HashMap<>();
        body.put("message", "System is temporarily offline for maintenance.");
        body.put("maintenance", maintenance);

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(body);
    }
}


// ============================================================
// 5. Controller with Localized / User-Friendly HTML Response
// ============================================================
@Controller
@RequestMapping("/maintenance")
class HtmlMaintenanceController {

    @GetMapping
    public ResponseEntity<String> maintenancePage() {
        String html = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <title>Under Maintenance</title>
                <style>
                    body { font-family: sans-serif; text-align: center; padding: 80px; background: #f5f5f5; }
                    h1 { font-size: 3rem; color: #333; }
                    p  { font-size: 1.2rem; color: #666; }
                </style>
            </head>
            <body>
                <h1>🔧 Under Maintenance</h1>
                <p>Our system is currently undergoing scheduled maintenance.</p>
                <p>We expect to be back online by <strong>17 Apr 2026, 02:00 UTC</strong>.</p>
                <p>For urgent help, contact <a href="mailto:support@example.com">support@example.com</a>.</p>
            </body>
            </html>
            """;

        return ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .header("Content-Type", "text/html")
            .body(html);
    }
}