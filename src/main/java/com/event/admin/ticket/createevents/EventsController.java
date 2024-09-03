package com.event.admin.ticket.createevents;

import com.event.admin.ticket.domain.Event;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class EventsController {

    private EventsHandler eventsHandler;


    // Endpoint to create a new event
    @PostMapping("/events")
    @Transactional
    public ResponseEntity<Event> createEvent(@Valid @RequestBody Event event) {
        var createdEvent = eventsHandler.handle(event);
        return ResponseEntity.ok(createdEvent);
    }

}
