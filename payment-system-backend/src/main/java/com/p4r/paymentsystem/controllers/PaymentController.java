package com.p4r.paymentsystem.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.p4r.paymentsystem.entities.Payment;
import com.p4r.paymentsystem.entities.Student;
import com.p4r.paymentsystem.enums.PaymentStatus;
import com.p4r.paymentsystem.enums.PaymentType;
import com.p4r.paymentsystem.repository.PaymentRepository;
import com.p4r.paymentsystem.repository.StudentRepository;
import com.p4r.paymentsystem.services.PaymentService;

@RestController
@CrossOrigin("*")
public class PaymentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/students")
    public List<Student> listStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/students/{code}")
    public Student listStudentByCode(@PathVariable String code) {
        return studentRepository.findByCode(code);
    }

    @GetMapping("/studentsByProgram")
    public List<Student> listStudentByProgram(@RequestParam String programId) {
        return studentRepository.findByProgramId(programId);
    }

    @GetMapping("/payments")
    public List<Payment> listPayments() {
        return paymentRepository.findAll();
    }

    @GetMapping("/payments/{id}")
    public ResponseEntity<Payment> listPaymentById(@PathVariable Long id) {
        return paymentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/students/{code}/pagos")
    public List<Payment> listPaymentsByStudentCode(@PathVariable String studentCode) {
        return paymentRepository.findByStudentCode(studentCode);
    }

    @GetMapping("/paymentByStatus")
    public List<Payment> listPayemntsByStatus(@RequestParam PaymentStatus status) {
        return paymentRepository.findByStatus(status);
    }

    @GetMapping("/paymentByType")
    public List<Payment> listPayemntsByType(@RequestParam PaymentType type) {
        return paymentRepository.findByType(type);
    }

    @PutMapping("/payments/{paymentId}/updatePayment")
    public Payment updatePaymentStatus(@RequestParam PaymentStatus status, @PathVariable Long paymentId) {
        return paymentService.updatePaymentByStatus(status, paymentId);
    }

    @PostMapping(path = "/payments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Payment savePayment(@RequestParam("file") MultipartFile file, double quanity,
            PaymentType type, LocalDate date, String studentCode) throws IOException {
        return paymentService.savePayment(file, quanity, type, date, studentCode);
    }

    @GetMapping(value = "/paymentFile/{paymentId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] listFileById(@PathVariable Long paymentId) throws IOException {
        return paymentService.getFileById(paymentId);
    }
}
