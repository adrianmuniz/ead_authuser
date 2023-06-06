package com.ead.authuser.controllers;

import com.ead.authuser.clients.UserClient;
import com.ead.authuser.dto.CourseDto;
<<<<<<< HEAD
import com.ead.authuser.models.UserModel;
=======
>>>>>>> parent of eba63d6 (Comunicação Síncrona - Inscrição de User em Course - Parte 3)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "x", maxAge = 3600)
public class UserCourseController {

    @Autowired
<<<<<<< HEAD
    UserClient userClient;
=======
    CourseClient userClient;
>>>>>>> parent of eba63d6 (Comunicação Síncrona - Inscrição de User em Course - Parte 3)

    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<Page<CourseDto>> getAllCoursesByUser(@PageableDefault(page =0, size =10, sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable,
                                                               @PathVariable(value = "userId") UUID userId){
        return ResponseEntity.status(HttpStatus.OK).body(userClient.getAllCoursesByUser(userId, pageable));
    }
}
