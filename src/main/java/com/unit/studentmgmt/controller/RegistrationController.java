package com.unit.studentmgmt.controller;

import com.unit.studentmgmt.dto.request.RegistrationRequest;
import com.unit.studentmgmt.dto.response.BaseRes;
import com.unit.studentmgmt.dto.response.RegistrationResponse;
import com.unit.studentmgmt.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping
    public BaseRes<RegistrationResponse> registerStudent(@RequestBody RegistrationRequest request) {
        return BaseRes.<RegistrationResponse>builder()
                .message("Đăng ký lớp học phần thành công")
                .data(registrationService.registerStudent(request))
                .build();
    }

    @DeleteMapping("/{registrationId}")
    public BaseRes<RegistrationResponse> unregister(@PathVariable Long registrationId) {
        registrationService.deleteRegistration(registrationId);
        return BaseRes.<RegistrationResponse>builder()
                .message("Hủy đăng ký thành công")
                .build();
    }

    @GetMapping("/{sectionId}/students/pdf")
    public byte[] exportStudentListByCourseSectionId(@PathVariable Long sectionId) {
        return registrationService.generateStudentListPdf(sectionId);
    }
}
