package giuliochiarenza.progettoU5W3D5.repositories;

import giuliochiarenza.progettoU5W3D5.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventDAO extends JpaRepository<Event, UUID> {
}
