package com.p4r.paymentsystem.services;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.p4r.paymentsystem.entities.Payment;
import com.p4r.paymentsystem.entities.Student;
import com.p4r.paymentsystem.enums.PaymentStatus;
import com.p4r.paymentsystem.enums.PaymentType;
import com.p4r.paymentsystem.repository.PaymentRepository;
import com.p4r.paymentsystem.repository.StudentRepository;

@Service
@Transactional
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Payment savePayment(MultipartFile file, double quanity, PaymentType type, LocalDate date,
            String studentCode) throws IOException {
        /*
         * Creamos una ruta donde se guardará el archivo
         * System.getProperty("user.home"): Obtiene la ruta del directio personal del
         * usuario del S.O.
         * Paths.get(..): Crea un objteo Path apuntando a una carpeta llamada
         * enset-payments dentro del directorio usuario
         */
        Path folderPath = Paths.get(System.getProperty("user.home"), "enset-data", "payments");

        if (!Files.exists(folderPath)) {

            Files.createDirectories(folderPath);

        }

        String fileName = UUID.randomUUID().toString();

        // Creamos un Path para el archivo PDF que se guardará en enset/data
        Path filePath = Paths.get(System.getProperty("user.home"), "enset-data", "payments", fileName + ".pdf");

        // file.getInputStream(): Obtiene el flujo de datos del archivo recibido desde
        // la solicitud HTTP
        // files.copy(): Copia los datos del archivo al destino filePath
        Files.copy(file.getInputStream(), filePath);

        Student studendt = studentRepository.findByCode(studentCode);

        Payment payment = Payment.builder().type(type).status(PaymentStatus.CREATED).date(date).student(studendt)
                .quantity(quanity).file(filePath.toUri().toString()).build();

        return paymentRepository.save(payment);
    }

    public byte[] getFileById(Long paymentId) throws IOException {

        Payment payment = paymentRepository.findById(paymentId).get();
        /*
         * payment.getFile(): Obtiene la URI del archivo guardado
         * URI.create(...): Convierte la cadena en un objeto URI
         * Path.off(...): Convierte el URI en un Path
         * Files.readAllBytes(): Lee el contenido del archivo y lo devuelve como un
         * array de bytes
         */
        return Files.readAllBytes(Path.of(URI.create((payment.getFile()))));

    }

    public Payment updatePaymentByStatus(PaymentStatus status, Long id) {
        Payment payment = paymentRepository.findById(id).get();
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }
}
