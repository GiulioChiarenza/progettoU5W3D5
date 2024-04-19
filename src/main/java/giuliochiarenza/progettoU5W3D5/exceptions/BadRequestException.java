package giuliochiarenza.progettoU5W3D5.exceptions;

import java.util.List;
import lombok.Getter;
import org.springframework.validation.ObjectError;

@Getter
public class BadRequestException extends RuntimeException{
    private List<ObjectError> errorsList;
    public BadRequestException(String message){
        super(message);
    }
    public BadRequestException(List<ObjectError> errorsList){
        super("Validation errors in the payload");
        this.errorsList = errorsList;
    }
}