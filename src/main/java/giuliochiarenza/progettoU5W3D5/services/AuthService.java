package giuliochiarenza.progettoU5W3D5.services;

import giuliochiarenza.progettoU5W3D5.entities.User;
import giuliochiarenza.progettoU5W3D5.exceptions.UnauthorizedException;
import giuliochiarenza.progettoU5W3D5.payloads.UserLoginDTO;
import giuliochiarenza.progettoU5W3D5.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UsersService usersService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateUserAndGenerateToken(UserLoginDTO payload){
        User user = this.usersService.findByEmail(payload.email());
        if (bcrypt.matches(payload.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Invalid credentials, Please log in again");
        }


    }
}