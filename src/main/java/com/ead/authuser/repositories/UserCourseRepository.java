package com.ead.authuser.repositories;

import com.ead.authuser.models.UserCourseModel;
<<<<<<< HEAD
=======
import com.ead.authuser.models.UserModel;
>>>>>>> 263d9aebb969870145e99e81f61c9e24b607e27f
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserCourseRepository extends JpaRepository<UserCourseModel, UUID> {
<<<<<<< HEAD
=======

    boolean existsByuserAndCourseId(UserModel userModel, UUID courseId);
>>>>>>> 263d9aebb969870145e99e81f61c9e24b607e27f
}
