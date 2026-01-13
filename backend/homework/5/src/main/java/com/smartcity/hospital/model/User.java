package com.smartcity.hospital.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class User {
    private Long id;
    private String username;
    private Boolean loggedIn;
    private String timezone;
    private Long tenantId;
}
