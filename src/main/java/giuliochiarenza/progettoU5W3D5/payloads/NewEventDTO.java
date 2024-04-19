package giuliochiarenza.progettoU5W3D5.payloads;

import giuliochiarenza.progettoU5W3D5.entities.Status;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record NewEventDTO(@NotEmpty(message = "title required") String title,
                          @NotEmpty(message = "description required") String description,
                          @NotEmpty(message = "date required") Date date,
                          @NotEmpty(message = "locatiion required") String location,
                          @NotEmpty(message = "capacity required") int capacity,
                          @NotEmpty(message = "status required") Status status,
                          List<UUID> usersId)

{
    @Override
    public String title(){ return title;}
    @Override
    public String description(){ return description;}
    @Override
    public Date date(){ return date;}
    @Override
    public String location(){ return location;}
    @Override
    public int capacity(){ return capacity;}
    @Override
    public Status status(){ return status;}
    @Override
    public List<UUID> usersId(){ return usersId;}
}
