package com.unit.studentmgmt.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationResponse {
    
    private Long registrationId;
    private Long studentId;
    private Long sectionId;
    private String registrationDate;
}
