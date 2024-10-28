package com.unit.studentmgmt.controller;

import com.unit.studentmgmt.dto.request.GradeRequest;
import com.unit.studentmgmt.dto.request.GradeUpdateReq;
import com.unit.studentmgmt.dto.response.BaseRes;
import com.unit.studentmgmt.dto.response.GradeResponse;
import com.unit.studentmgmt.dto.response.StudentGradeDetail;
import com.unit.studentmgmt.service.GradeService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/grades")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @PostMapping
    public BaseRes<GradeResponse> addGrade(@RequestBody GradeRequest request) {
        return BaseRes.<GradeResponse>builder()
                .message("Thêm điểm thành công")
                .data(gradeService.assignGrade(request))
                .build();
    }

    @PutMapping("/{gradeId}")
    public BaseRes<GradeResponse> updateGrade(@PathVariable Long gradeId, @RequestBody GradeUpdateReq request) {
        return BaseRes.<GradeResponse>builder()
                .message("Cập nhật điểm thành công")
                .data(gradeService.updateGrade(gradeId, request))
                .build();
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentGradeDetail>> getCourseNamesAndScoresByStudentId(@PathVariable Long studentId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        List<StudentGradeDetail> response = gradeService.getGradesByStudentId(studentId);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @GetMapping("/export/excel/section/{sectionId}")
    public void exportGradesToExcel(@PathVariable Long sectionId, HttpServletResponse response) {
        gradeService.exportGradesToExcel(sectionId, response);
    }
}
