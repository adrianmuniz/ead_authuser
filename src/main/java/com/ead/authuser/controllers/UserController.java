package com.ead.authuser.controllers;

import com.ead.authuser.configs.security.AuthenticationCurrentUserService;
import com.ead.authuser.configs.security.UserDetailsImpl;
import com.ead.authuser.dto.UserDTO;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.ead.authuser.specifications.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@CrossOrigin(origins = "x", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationCurrentUserService authenticationCurrentUserService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(SpecificationTemplate.UserSpec spec,
                                                        @PageableDefault(page =0, size =10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
                                                       Authentication authentication){
        UserDetails userDetails = (UserDetailsImpl) authentication.getPrincipal();
        log.info("Authentication {}",userDetails.getUsername());
        Page<UserModel> userModelPage = userService.findAll(spec, pageable);
        if(!userModelPage.isEmpty()){
            for(UserModel user : userModelPage.toList()){
                user.add(linkTo(methodOn(UserController.class).getOneUser(user.getUserId())).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "userId") UUID userId) {
        UUID currentUserId = authenticationCurrentUserService.getCurrentUser().getUserId();
        if(currentUserId.equals(userId)) {
            Optional<UserModel> userModelOptional = userService.findById(userId);
            if (!userModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
            }
        }else {
            throw new AccessDeniedException("Forbidden");
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId) {
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(!userModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        } else {
            userService.deleteUser(userModelOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("User deleted succes");
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "userId") UUID userId,
                                             @RequestBody @Validated(UserDTO.UserView.UserPut.class) @JsonView(UserDTO.UserView.UserPut.class) UserDTO userDTO) {
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(!userModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        } else {
            var userModel = userModelOptional.get();
            userModel.setFullName(userDTO.getFullName());
            userModel.setPhoneNumber(userDTO.getPhoneNumber());
            userModel.setCpf(userDTO.getCpf());
            userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            userService.updateUser(userModel);

            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable(value = "userId") UUID userId,
                                             @RequestBody @Validated(UserDTO.UserView.PasswordPut.class) @JsonView(UserDTO.UserView.PasswordPut.class) UserDTO userDTO) {
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(!userModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        }if(!userModelOptional.get().getPassword().equals(userDTO.getOldPassword())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Eror: Mismatched old password");
        } else {
            var userModel = userModelOptional.get();
            userModel.setPassword(userDTO.getPassword());
            userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            userService.updatePassword(userModel);
            return ResponseEntity.status(HttpStatus.OK).body("Password Updated Sucessfully.");
        }
    }

    @PutMapping("/{userId}/image")
    public ResponseEntity<Object> updateImage(@PathVariable(value = "userId") UUID userId,
                                                @RequestBody @Validated(UserDTO.UserView.imagePut.class) @JsonView(UserDTO.UserView.imagePut.class) UserDTO userDTO) {
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(!userModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        } else {
            var userModel = userModelOptional.get();
            userModel.setImageUrl(userDTO.getImageUrl());
            userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            userService.updateUser(userModel);
            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }
    }
}
