package com.ead.authuser.models;

import com.fasterxml.jackson.annotation.JsonInclude;
<<<<<<< HEAD
import lombok.Data;
=======
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
>>>>>>> 263d9aebb969870145e99e81f61c9e24b607e27f

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
<<<<<<< HEAD
=======
@AllArgsConstructor
@NoArgsConstructor
>>>>>>> 263d9aebb969870145e99e81f61c9e24b607e27f
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_USERS_COURSES")
public class UserCourseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
<<<<<<< HEAD

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserModel user;

    @Column(nullable = false)
    private UUID courseId;
=======
    @Column(nullable = false)
    private UUID courseId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserModel user;

>>>>>>> 263d9aebb969870145e99e81f61c9e24b607e27f
}
