package giuliochiarenza.progettoU5W3D5.controllers;

import giuliochiarenza.progettoU5W3D5.entities.Event;
import giuliochiarenza.progettoU5W3D5.exceptions.BadRequestException;
import giuliochiarenza.progettoU5W3D5.payloads.NewEventDTO;
import giuliochiarenza.progettoU5W3D5.payloads.NewEventRespDTO;
import giuliochiarenza.progettoU5W3D5.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping
    @PreAuthorize("hasAuthority('MANAGER')")
    private NewEventRespDTO saveEvent(@RequestBody @Validated NewEventDTO body, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.getAllErrors());
            throw new BadRequestException(validation.getAllErrors());
        }
        return new NewEventRespDTO(this.eventService.saveEvent(body).getEventId());
    }
    @GetMapping
    @PreAuthorize("hasAuthority('MANAGER')")
    private Page<Event> getAllEvent(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "deviceId") String sortBy){
        return this.eventService.getEventList(page, size, sortBy);
    }
    @GetMapping("/{eventId}")
    @PreAuthorize("hasAuthority('MANAGER')")
    private Event findByEventId(@PathVariable UUID eventId) {
        return this.eventService.findById(eventId);
    }
    @PutMapping("/{eventId}")
    @PreAuthorize("hasAuthority('MANAGER')")
    private Event findByIdAndUpdate(@PathVariable UUID eventId, @RequestBody Event body){
        return  this.eventService.findByIdAndUpdate(eventId, body);
    }
    @DeleteMapping("/{eventId}")
    @PreAuthorize("hasAuthority('MANAGER')")
    private void findEventByIdAndDelete(@PathVariable UUID eventId){
        this.eventService.findByIdAndDelete(eventId);
    }

}
