package com.unit.studentmgmt.controller;

import com.unit.studentmgmt.dto.request.CourseSectionRequest;
import com.unit.studentmgmt.dto.response.BaseRes;
import com.unit.studentmgmt.dto.response.CourseSectionResponse;
import com.unit.studentmgmt.service.CourseSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/course-sections")
public class CourseSectionController {

    @Autowired
    private CourseSectionService courseSectionService;

    @PostMapping
    public BaseRes<CourseSectionResponse> createCourseSection(@RequestBody CourseSectionRequest request) {
        return BaseRes.<CourseSectionResponse>builder()
                .data(courseSectionService.createCourseSection(request))
                .build();
    }

    @GetMapping
    public BaseRes<List<CourseSectionResponse>> getAllCourseSections() {
        return BaseRes.<List<CourseSectionResponse>>builder()
                .data(courseSectionService.getAllCourseSections())
                .build();
    }

    @GetMapping("/{sectionId}")
    public BaseRes<CourseSectionResponse> getCourseSectionById(@PathVariable Long sectionId) {
        return BaseRes.<CourseSectionResponse>builder()
                .data(courseSectionService.getCourseSectionById(sectionId))
                .build();
    }

    @PutMapping("/{sectionId}")
    public BaseRes<CourseSectionResponse> updateCourseSection(
            @PathVariable Long sectionId,
            @RequestBody CourseSectionRequest request) {
        return BaseRes.<CourseSectionResponse>builder()
                .data(courseSectionService.updateCourseSection(sectionId, request))
                .build();
    }

    @DeleteMapping("/{sectionId}")
    public BaseRes<Void> deleteCourseSection(@PathVariable Long sectionId) {
        courseSectionService.deleteCourseSection(sectionId);
        return BaseRes.<Void>builder()
                .build();
    }
}
