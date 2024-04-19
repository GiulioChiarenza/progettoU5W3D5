package giuliochiarenza.progettoU5W3D5.services;

import giuliochiarenza.progettoU5W3D5.entities.User;
import giuliochiarenza.progettoU5W3D5.exceptions.BadRequestException;
import giuliochiarenza.progettoU5W3D5.exceptions.NotFoundException;
import giuliochiarenza.progettoU5W3D5.payloads.NewUserDTO;
import giuliochiarenza.progettoU5W3D5.repositories.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class UsersService {
    @Autowired
    private UsersDAO usersDAO;
    @Autowired
    private PasswordEncoder bcrypt;

    public Page<User> getUsers(int page, int size, String sortBy){
        if(size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.usersDAO.findAll(pageable);
    }
    public User save(NewUserDTO body) throws BadRequestException {
        this.usersDAO.findByEmail(body.email()).ifPresent(
                user -> {
                    throw new BadRequestException("Email " + user.getEmail() + " it is already in use");
                }
        );
        User newUser = new User(body.name(), body.surname(), body.email(),  bcrypt.encode(body.password()));

        return usersDAO.save(newUser);
    }
    public User findById(UUID userId){
        return this.usersDAO.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }
    public User findByIdAndUpdate(UUID userId, User modifiedUser){
        User found = this.findById(userId);
        found.setName(modifiedUser.getName());
        found.setSurname(modifiedUser.getSurname());
        found.setEmail(modifiedUser.getEmail());
        found.setPassword(modifiedUser.getPassword());
        return this.usersDAO.save(found);
    }
    public void findByIdAndDelete(UUID userId){
        User found = this.findById(userId);
        this.usersDAO.delete(found);
    }
    public User findByEmail(String email) {
        return usersDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("User with email " + email + " not found"));
    }
    public List<User> findAllById(List<UUID> usersId){return usersDAO.findAllById(usersId);}


}
