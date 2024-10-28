package com.unit.studentmgmt.service;

import com.unit.studentmgmt.entity.CourseSection;
import com.unit.studentmgmt.entity.Grade;
import com.unit.studentmgmt.exception.AppException;
import com.unit.studentmgmt.exception.ErrorCode;
import com.unit.studentmgmt.repository.CourseSectionRepository;
import com.unit.studentmgmt.repository.GradeRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ExcelExportService {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private CourseSectionRepository courseSectionRepository;

    public ByteArrayOutputStream generateGradeReportExcel(Long sectionId) {
        CourseSection courseSection = courseSectionRepository.findById(sectionId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTS));
        List<Grade> grades = gradeRepository.findByCourseSection(courseSection);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Bảng Điểm");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Mã Sinh Viên");
        headerRow.createCell(1).setCellValue("Tên Sinh Viên");
        headerRow.createCell(2).setCellValue("Điểm");
        headerRow.createCell(3).setCellValue("Nhận Xét");

        int rowIndex = 1;
        for (Grade grade : grades) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(grade.getStudent().getFullName());
            row.createCell(1).setCellValue(grade.getStudent().getFullName());
            row.createCell(2).setCellValue(grade.getGrade());
            row.createCell(3).setCellValue(grade.getComments());
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream;
    }
}
