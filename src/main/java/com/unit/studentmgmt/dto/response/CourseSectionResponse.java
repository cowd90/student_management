package com.unit.studentmgmt.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CourseSectionResponse {
    
    private Long sectionId;
    private String courseId;
    private String sectionName;
    private String instructorName;
}
