package com.unit.studentmgmt.mapper;

import com.unit.studentmgmt.dto.request.CourseSectionRequest;
import com.unit.studentmgmt.dto.response.CourseSectionResponse;
import com.unit.studentmgmt.entity.CourseSection;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CourseSectionMapper {
	CourseSection toCourseSection(CourseSectionRequest request);

	CourseSectionResponse toCourseSectionResponse(CourseSection courseSection);

	CourseSection updateCourseSection(@MappingTarget CourseSection courseSection, CourseSectionRequest request);

}
