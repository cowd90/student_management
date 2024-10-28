package com.unit.studentmgmt.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.unit.studentmgmt.dto.request.RegistrationRequest;
import com.unit.studentmgmt.dto.response.RegistrationResponse;
import com.unit.studentmgmt.dto.response.StudentDetailDto;
import com.unit.studentmgmt.entity.CourseSection;
import com.unit.studentmgmt.entity.Registration;
import com.unit.studentmgmt.entity.User;
import com.unit.studentmgmt.exception.AppException;
import com.unit.studentmgmt.exception.ErrorCode;
import com.unit.studentmgmt.mapper.RegistrationMapper;
import com.unit.studentmgmt.repository.CourseSectionRepository;
import com.unit.studentmgmt.repository.RegistrationRepository;
import com.unit.studentmgmt.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class RegistrationService {
    
    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseSectionRepository courseSectionRepository;

    @Autowired
    private RegistrationMapper registrationMapper;

    public RegistrationResponse registerStudent(RegistrationRequest request) {

        CourseSection courseSection = courseSectionRepository.findById(request.getSectionId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTS));

        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTS));

        boolean isRegistered = registrationRepository.existsByStudentAndCourseSection(student, courseSection);

        if (isRegistered) {
            throw new AppException(ErrorCode.EXIST);
        }

        Registration registration = registrationMapper.toRegistration(request);

        registration.setStudent(student);
        registration.setCourseSection(courseSection);
        registration.setRegistrationDate(LocalDateTime.now());

        registration = registrationRepository.save(registration);
        RegistrationResponse registrationResponse = registrationMapper.toRegistrationResponse(registration);
        registrationResponse.setStudentId(student.getUserId());
        registrationResponse.setSectionId(courseSection.getSectionId());

        return registrationResponse;
    }

    public RegistrationResponse getRegistrationById(Long registrationId) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTS));
        return registrationMapper.toRegistrationResponse(registration);
    }

    public List<RegistrationResponse> getAllRegistrations() {
        List<Registration> registrations = registrationRepository.findAll();
        return registrations.stream()
                .map(registrationMapper::toRegistrationResponse)
                .toList();
    }

    public void deleteRegistration(Long registrationId) {
        registrationRepository.deleteById(registrationId);
    }

    public byte[] generateStudentListPdf(Long sectionId) {
        List<StudentDetailDto> students = this.getStudentsByCourseSectionId(sectionId);
        CourseSection courseSection = courseSectionRepository.findById(sectionId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTS));
        Document document = new Document();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter.getInstance(document, baos);
            document.open();

            BaseFont baseFont = BaseFont.createFont("src/main/resources/font-times-new-roman.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            com.itextpdf.text.Font font = new com.itextpdf.text.Font(baseFont, 12);

            Paragraph title = new Paragraph("Danh sách sinh viên", font);
            title.setAlignment(Paragraph.ALIGN_CENTER);

            String subTitle = "Lớp học phần " + courseSection.getSectionName() + " - " + courseSection.getCourseId();
            Paragraph subTitleS = new Paragraph(subTitle, font);
            subTitleS.setAlignment(Paragraph.ALIGN_CENTER);

            document.add(title);
            document.add(subTitleS);
            document.add(new Paragraph("\n", font));

            PdfPTable table = new PdfPTable(3);
            table.setWidths(new float[]{1, 3, 4});

            table.addCell(new Paragraph("STT", font));
            table.addCell(new Paragraph("Họ và tên", font));
            table.addCell(new Paragraph("Email", font));

            for (int i = 0; i < students.size(); i++) {
                StudentDetailDto user = students.get(i);
                table.addCell(String.valueOf(i + 1));
                table.addCell(new Paragraph(user.getFullName(), font));
                table.addCell(user.getEmail());
            }

            document.add(table);
            document.close();

            return baos.toByteArray();
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    private List<StudentDetailDto> getStudentsByCourseSectionId(Long sectionId) {
        CourseSection courseSection = courseSectionRepository.findBySectionId(sectionId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTS));

        List<Registration> registrations = registrationRepository.findByCourseSection(courseSection);

        return registrations.stream().map(registration -> {
            User user = userRepository.findById(registration.getStudent().getUserId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTS));
            return new StudentDetailDto(user.getFullName(), user.getEmail());
        }).toList();
    }
}
