package com.unit.studentmgmt.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationRequest {
    
    private Long studentId;
    private Long sectionId;
}
