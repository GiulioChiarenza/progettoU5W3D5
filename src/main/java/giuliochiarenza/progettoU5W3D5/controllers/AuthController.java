package giuliochiarenza.progettoU5W3D5.controllers;

import giuliochiarenza.progettoU5W3D5.entities.User;
import giuliochiarenza.progettoU5W3D5.exceptions.BadRequestException;
import giuliochiarenza.progettoU5W3D5.payloads.NewUserDTO;
import giuliochiarenza.progettoU5W3D5.payloads.NewUserRespDTO;
import giuliochiarenza.progettoU5W3D5.payloads.UserLoginDTO;
import giuliochiarenza.progettoU5W3D5.payloads.UserLoginResponseDTO;
import giuliochiarenza.progettoU5W3D5.services.AuthService;
import giuliochiarenza.progettoU5W3D5.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UsersService usersService;

    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginDTO payload){
        return new UserLoginResponseDTO(this.authService.authenticateUserAndGenerateToken(payload));
    }
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserRespDTO saveUser(@RequestBody @Validated NewUserDTO body, BindingResult validation){
        if(validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return new NewUserRespDTO(this.usersService.save(body).getUserId());
    }

}
