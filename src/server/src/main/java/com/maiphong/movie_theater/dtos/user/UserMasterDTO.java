package com.maiphong.movie_theater.dtos.user;

import java.time.LocalDate;

import com.maiphong.movie_theater.dtos.MasterDTO;
import com.maiphong.movie_theater.entities.Gender;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserMasterDTO extends MasterDTO {
    private String firstName;

    private String lastName;

    private String displayName;

    private String username;

    private LocalDate dateOfBirth;

    private Gender gender;

    private String email;

    private String phoneNumber;

    private String address;

}
