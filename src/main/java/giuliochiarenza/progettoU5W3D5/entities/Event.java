package giuliochiarenza.progettoU5W3D5.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="events")
public class Event {
    @Id
    @GeneratedValue
    private UUID eventId;
    private String title;
    private String description;
    private Date date;
    private String location;
    private int capacity;
    private Status status;
    @ManyToMany
    private List<User> users;

    public Event( String title, String description, Date date, String location, int capacity, List<User> users) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.capacity = capacity;
        this.status = Status.AVAILABLE;
        this.users = users;
    }
}
