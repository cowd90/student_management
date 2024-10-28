package com.unit.studentmgmt.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    
    private Long userId;
    private String username;
    private String fullName;
    private String email;
    private String roleName;
}
