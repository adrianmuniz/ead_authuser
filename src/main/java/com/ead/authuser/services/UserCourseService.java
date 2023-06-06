package com.ead.authuser.services;

<<<<<<< HEAD
public interface UserCourseService {
=======
import com.ead.authuser.models.UserCourseModel;
import com.ead.authuser.models.UserModel;

import java.util.UUID;

public interface UserCourseService {

    boolean existsByUserAndCourseId(UserModel userModel, UUID courseId);

    UserCourseModel save(UserCourseModel userCourseModel);
>>>>>>> 263d9aebb969870145e99e81f61c9e24b607e27f
}
