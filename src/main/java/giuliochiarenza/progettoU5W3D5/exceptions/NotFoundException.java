package giuliochiarenza.progettoU5W3D5.exceptions;


import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id){
        super("The record with id: " + id + "It was not found");
    }
    public NotFoundException(String message){ super(message);}
}