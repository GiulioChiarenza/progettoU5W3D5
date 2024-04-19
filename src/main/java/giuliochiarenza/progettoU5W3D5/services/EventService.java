package giuliochiarenza.progettoU5W3D5.services;

import giuliochiarenza.progettoU5W3D5.entities.Event;
import giuliochiarenza.progettoU5W3D5.entities.User;
import giuliochiarenza.progettoU5W3D5.exceptions.NotFoundException;
import giuliochiarenza.progettoU5W3D5.payloads.NewEventDTO;
import giuliochiarenza.progettoU5W3D5.repositories.EventDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EventService {
    @Autowired
    private EventDAO eventDAO;
    @Autowired
    private UsersService usersService;

    public Page<Event> getEventList(int page, int size, String sortBy){
        if(size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.eventDAO.findAll(pageable);
    }
    public Event saveEvent(NewEventDTO body){
        Event newEvent = new Event(body.title(),body.description(),body.date(),body.location(), body.capacity(),new ArrayList<>() );
        return eventDAO.save(newEvent);
    }
    public Event addUsersToEvent(UUID eventId, List<UUID> usersId) {
        Event newEvent = eventDAO.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event with id: " + eventId + " not found"));
        List<User> users = usersService.findAllById(usersId);
        newEvent.getUsers().addAll(users);

        return eventDAO.save(newEvent);
    }
    public Event findById(UUID eventId){
        return this.eventDAO.findById(eventId).orElseThrow(()-> new NotFoundException(eventId));
    }
    public Event findByIdAndUpdate(UUID eventId, Event updatedEvent){
        Event found = this.findById(eventId);
        found.setTitle(updatedEvent.getTitle());
        found.setDescription(updatedEvent.getDescription());
        found.setDate(updatedEvent.getDate());
        found.setLocation(updatedEvent.getLocation());
        found.setCapacity(updatedEvent.getCapacity());
        found.setStatus(updatedEvent.getStatus());
        found.setUsers(updatedEvent.getUsers());
        return this.eventDAO.save(found);
    }
    public void findByIdAndDelete(UUID eventId){
        Event found = this.findById(eventId);
        this.eventDAO.delete(found);
    }
}
