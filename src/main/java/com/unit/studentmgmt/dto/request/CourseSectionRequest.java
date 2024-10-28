package com.unit.studentmgmt.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CourseSectionRequest {
    
    private String courseId;

    private String sectionName;

    private Long instructorId;
}
