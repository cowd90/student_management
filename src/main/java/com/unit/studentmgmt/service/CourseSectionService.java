package com.unit.studentmgmt.service;

import com.unit.studentmgmt.dto.request.CourseSectionRequest;
import com.unit.studentmgmt.dto.response.CourseSectionResponse;
import com.unit.studentmgmt.entity.CourseSection;
import com.unit.studentmgmt.entity.User;
import com.unit.studentmgmt.exception.AppException;
import com.unit.studentmgmt.exception.ErrorCode;
import com.unit.studentmgmt.mapper.CourseSectionMapper;
import com.unit.studentmgmt.repository.CourseSectionRepository;
import com.unit.studentmgmt.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CourseSectionService {
    
    @Autowired
    private CourseSectionRepository courseSectionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseSectionMapper courseSectionMapper;

    public CourseSectionResponse createCourseSection(CourseSectionRequest request) {
        CourseSection courseSection = courseSectionMapper.toCourseSection(request);
        User instructor = userRepository.findById(request.getInstructorId())
                        .orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTS));
        courseSection.setInstructor(instructor);
        courseSection = courseSectionRepository.save(courseSection);
        CourseSectionResponse courseSectionResponse = courseSectionMapper.toCourseSectionResponse(courseSection);
        courseSectionResponse.setInstructorName(instructor.getFullName());
        return courseSectionResponse;
    }

    public CourseSectionResponse getCourseSectionById(Long sectionId) {
        CourseSection courseSection = courseSectionRepository.findById(sectionId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTS));
        return courseSectionMapper.toCourseSectionResponse(courseSection);
    }

    public List<CourseSectionResponse> getAllCourseSections() {
        List<CourseSection> courseSections = courseSectionRepository.findAll();
        return courseSections
                .stream()
                .map(courseSectionMapper::toCourseSectionResponse)
                .toList();
    }

    public CourseSectionResponse updateCourseSection(Long sectionId, CourseSectionRequest request) {
        CourseSection courseSection = courseSectionRepository.findById(sectionId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTS));
        courseSection = courseSectionMapper.updateCourseSection(courseSection, request);
        courseSection = courseSectionRepository.save(courseSection);
        return courseSectionMapper.toCourseSectionResponse(courseSection);
    }

    public void deleteCourseSection(Long sectionId) {
        courseSectionRepository.deleteById(sectionId);
    }

}
