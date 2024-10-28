package com.unit.studentmgmt.service;

import com.unit.studentmgmt.constant.PredefinedRole;
import com.unit.studentmgmt.dto.request.GradeRequest;
import com.unit.studentmgmt.dto.request.GradeUpdateReq;
import com.unit.studentmgmt.dto.response.GradeResponse;
import com.unit.studentmgmt.dto.response.StudentGradeDetail;
import com.unit.studentmgmt.entity.CourseSection;
import com.unit.studentmgmt.entity.Grade;
import com.unit.studentmgmt.entity.User;
import com.unit.studentmgmt.exception.AppException;
import com.unit.studentmgmt.exception.ErrorCode;
import com.unit.studentmgmt.mapper.GradeMapper;
import com.unit.studentmgmt.repository.CourseSectionRepository;
import com.unit.studentmgmt.repository.GradeRepository;
import com.unit.studentmgmt.repository.UserRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GradeService {
    
    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private CourseSectionRepository courseSectionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GradeMapper gradeMapper;

    @PreAuthorize("hasRole('ADMIN') or hasRole('LECTURER')")
    public GradeResponse assignGrade(GradeRequest request) {
        Grade grade = gradeMapper.toGrade(request);
        CourseSection courseSection = courseSectionRepository.findBySectionId(request.getSectionId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTS));

        var context = SecurityContextHolder.getContext();
        String userId = context.getAuthentication().getName();

        if (!Objects.equals(userId, courseSection.getInstructor().getUserId().toString())) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }

        User student = userRepository.findUserByUserId(request.getStudentId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTS));

        if (student.getRole().getRoleName().equals(PredefinedRole.LECTURER_ROLE)) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }

        grade.setStudent(student);
        grade.setCourseSection(courseSection);
        grade = gradeRepository.save(grade);

        GradeResponse gradeResponse = gradeMapper.toGradeResponse(grade);
        gradeResponse.setStudentId(student.getUserId());
        gradeResponse.setSectionId(courseSection.getSectionId());

        return gradeResponse;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('LECTURER')")
    public GradeResponse updateGrade(Long gradeId, GradeUpdateReq request) {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTS));

        CourseSection courseSection = courseSectionRepository.findBySectionId(grade.getCourseSection().getSectionId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTS));

        var context = SecurityContextHolder.getContext();
        String userId = context.getAuthentication().getName();

        if (!Objects.equals(userId, courseSection.getInstructor().getUserId().toString())) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }

        grade = gradeMapper.updateGrade(grade, request);
        GradeResponse gradeResponse = gradeMapper.toGradeResponse(gradeRepository.save(grade));
        gradeResponse.setStudentId(grade.getStudent().getUserId());
        gradeResponse.setSectionId(grade.getCourseSection().getSectionId());

        return gradeResponse;
    }

    public List<StudentGradeDetail> getGradesByStudentId(Long studentId) {
        List<Grade> grades = gradeRepository.findByStudentId(studentId);

        return grades.stream()
                .map(grade -> new StudentGradeDetail(grade.getCourseSection().getSectionName(), grade.getGrade()))
                .collect(Collectors.toList());
    }

    public void exportGradesToExcel(Long courseSectionId, HttpServletResponse response) {
        try {
            CourseSection courseSection = courseSectionRepository.findBySectionId(courseSectionId)
                    .orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTS));
            List<Grade> grades = gradeRepository.findByCourseSection(courseSection);

            // Tạo workbook và sheet
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Bảng Điểm");

            // Tạo tiêu đề
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("STT");
            headerRow.createCell(1).setCellValue("Họ và Tên");
            headerRow.createCell(2).setCellValue("Email");
            headerRow.createCell(3).setCellValue("Điểm");

            // Thêm dữ liệu vào sheet
            for (int i = 0; i < grades.size(); i++) {
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(grades.get(i).getStudent().getFullName());
                row.createCell(2).setCellValue(grades.get(i).getStudent().getEmail());
                row.createCell(3).setCellValue(grades.get(i).getGrade());
            }

            String courseSectionName = "Bang diem LHP " + courseSection.getSectionName() + courseSection.getCourseId();
            String fileNameFormatted = Normalizer.normalize(courseSectionName, Normalizer.Form.NFD)
                    .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            String safeCourseSectionName = fileNameFormatted.replaceAll("[^\\p{L}\\p{N}]", "_");

            String fileName = safeCourseSectionName + ".xlsx";
            log.debug(fileName);

            // Cấu hình phản hồi
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

            // Xuất file
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.close();
            workbook.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
