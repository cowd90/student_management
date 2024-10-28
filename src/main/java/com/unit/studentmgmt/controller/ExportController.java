//package com.unit.studentmgmt.controller;
//
//import com.unit.studentmgmt.exception.AppException;
//import com.unit.studentmgmt.exception.ErrorCode;
//import com.unit.studentmgmt.service.ExcelExportService;
//import com.unit.studentmgmt.service.PdfExportService;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.ByteArrayOutputStream;
//
//@RestController
//public class ExportController {
//
//    @Autowired
//    private PdfExportService pdfExportService;
//
//    @Autowired
//    private ExcelExportService excelExportService;
//
//    @GetMapping("/export/pdf/students/{sectionId}")
//    public void exportStudentsToPdf(@PathVariable Long sectionId, HttpServletResponse response) {
//        try {
//            ByteArrayOutputStream pdf = pdfExportService.generateStudentListPdf(sectionId);
//            response.setContentType("application/pdf");
//            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=student_list.pdf");
//            response.getOutputStream().write(pdf.toByteArray());
//        } catch (Exception e) {
//            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
//        }
//    }
//
//    // Xuất bảng điểm ra file Excel
//    @GetMapping("/export/excel/grades/{sectionId}")
//    public void exportGradesToExcel(@PathVariable Long sectionId, HttpServletResponse response) {
//        try {
//            ByteArrayOutputStream excel = excelExportService.generateGradeReportExcel(sectionId);
//            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=grade_report.xlsx");
//            response.getOutputStream().write(excel.toByteArray());
//        } catch (Exception e) {
//            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
//        }
//    }
//}
