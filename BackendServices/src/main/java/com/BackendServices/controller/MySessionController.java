// package com.BackendServices.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import com.BackendServices.entity.MySession;
// import com.BackendServices.service.MySessionService;

// import java.util.Optional;
// @RestController
// @RequestMapping("/api/sessions")
// public class MySessionController {
    
//     @Autowired
//     private MySessionService mySessionService; // Inject the MySessionService

//     // Create a new MySession
//     @PostMapping
//     public ResponseEntity<MySession> createMySession(@RequestBody MySession mySession) {
//         MySession createdMySession = mySessionService.createMySession(mySession);
//         return ResponseEntity.status(HttpStatus.CREATED).body(createdMySession);
//     }

//     // Get MySession by ID
//     @GetMapping("/{sessionId}")
//     public ResponseEntity<MySession> getMySessionById(@PathVariable String sessionId) {
//         Optional<MySession> mySessionOptional = mySessionService.getMySessionById(sessionId);
//         if (mySessionOptional.isPresent()) {
//             MySession mySession = mySessionOptional.get();
//             return ResponseEntity.ok(mySession);
//         } else {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     // Update MySession by ID
//     @PutMapping("/{sessionId}")
//     public ResponseEntity<MySession> updateMySession(@PathVariable String sessionId, @RequestBody MySession updatedMySession) {
//         MySession mySession = mySessionService.updateMySession(sessionId, updatedMySession);
//         if (mySession != null) {
//             return ResponseEntity.ok(mySession);
//         } else {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     // Delete MySession by ID
//     @DeleteMapping("/{sessionId}")
//     public ResponseEntity<Void> deleteMySession(@PathVariable String sessionId) {
//         boolean deleted = mySessionService.deleteMySession(sessionId);
//         if (deleted) {
//             return ResponseEntity.noContent().build();
//         } else {
//             return ResponseEntity.notFound().build();
//         }
//     }
// }
