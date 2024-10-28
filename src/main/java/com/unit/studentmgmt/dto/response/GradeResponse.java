package com.unit.studentmgmt.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GradeResponse {
    
    private Long gradeId;
    private Long studentId;
    private Long sectionId;
    private Double grade;
    private String comments;
}
