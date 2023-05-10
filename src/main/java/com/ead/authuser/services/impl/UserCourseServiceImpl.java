package com.ead.authuser.services.impl;

import com.ead.authuser.services.UserCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCourseServiceImpl implements UserCourseService {

    final
    UserCourseService userCourseService;

    public UserCourseServiceImpl(UserCourseService userCourseService) {
        this.userCourseService = userCourseService;
    }
}
