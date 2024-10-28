package com.unit.studentmgmt.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GradeRequest {
    private Long studentId;
    private Long sectionId;
    private Double grade;
    private String comments;
}
