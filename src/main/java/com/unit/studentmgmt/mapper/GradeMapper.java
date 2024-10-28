package com.unit.studentmgmt.mapper;

import com.unit.studentmgmt.dto.request.GradeRequest;
import com.unit.studentmgmt.dto.request.GradeUpdateReq;
import com.unit.studentmgmt.dto.response.GradeResponse;
import com.unit.studentmgmt.entity.Grade;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GradeMapper {
	Grade toGrade(GradeRequest gradeRequest);

	GradeResponse toGradeResponse(Grade grade);

	Grade updateGrade(@MappingTarget Grade grade, GradeUpdateReq request);

}
