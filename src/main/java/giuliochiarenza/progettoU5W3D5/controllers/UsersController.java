package giuliochiarenza.progettoU5W3D5.controllers;

import giuliochiarenza.progettoU5W3D5.entities.User;
import giuliochiarenza.progettoU5W3D5.exceptions.BadRequestException;
import giuliochiarenza.progettoU5W3D5.payloads.NewUserDTO;
import giuliochiarenza.progettoU5W3D5.payloads.NewUserRespDTO;
import giuliochiarenza.progettoU5W3D5.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping
    private NewUserRespDTO saveUser(@RequestBody  NewUserDTO body){
//        if(validation.hasErrors()){
//            System.out.println(validation.getAllErrors());
//            throw new BadRequestException(validation.getAllErrors());
//        }
        return new NewUserRespDTO(this.usersService.save(body).getUserId());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('MANAGER')")
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sortBy) {
        return this.usersService.getUsers(page, size, sortBy);
    }
    @GetMapping("/me")
    public User getCurrentUser(@AuthenticationPrincipal User currentAuthenticatedUser){
        return currentAuthenticatedUser;
    }
    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public User getUser(@PathVariable UUID userId){
        return this.usersService.findById(userId);
    }
    @PutMapping("/me")
    public User updateCurrentUser(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody @Validated User updatedUser){
        return this.usersService.findByIdAndUpdate(currentAuthenticatedUser.getUserId(), updatedUser);
    }
    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public User updateUser(@PathVariable UUID userId, @RequestBody @Validated User body){
        return this.usersService.findByIdAndUpdate(userId, body);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCurrentUser(@AuthenticationPrincipal User currentAuthenticatedUser){
        this.usersService.findByIdAndDelete(currentAuthenticatedUser.getUserId());
    }
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('MANAGER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID userId){
        this.usersService.findByIdAndDelete(userId);
    }

}