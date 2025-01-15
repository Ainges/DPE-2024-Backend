package de.thi.service;


import de.thi.dto.AnnualStatementNotificationDto;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.codec.binary.Base64;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


@ApplicationScoped
public class Base64Service {

    public String convertBase64ToAnnualStatement(String base64, AnnualStatementNotificationDto annualStatementNotificationDto) throws IOException {
        String base64WithoutPrefix = removeBase64Prefix(base64);

        byte[] data = Base64.decodeBase64(base64WithoutPrefix);

        //create dir if not exists
        java.nio.file.Path path = java.nio.file.Paths.get("target/output/annualStatements");
        java.nio.file.Files.createDirectories(path);

        //Filename
        long currentTime = System.currentTimeMillis();
        String filename = "annual-statement-of-rentalAgreement-" + annualStatementNotificationDto.getData().getRentalAgreement().getRentalAgreementId()+"-"+currentTime+".pdf";

        //Filepath (=path + name)
        String filePath = "target/output/annualStatements/" + filename;

        try (OutputStream stream = new FileOutputStream(filePath)) {
            stream.write(data);
        }

        return filePath;

    }

    public static String removeBase64Prefix(String base64String) {
        // Überprüfen, ob das Präfix vorhanden ist
        if (base64String.startsWith("data:application/pdf;base64,")) {
            return base64String.substring("data:application/pdf;base64,".length());
        }
        return base64String; // Falls kein Präfix vorhanden ist, Rückgabe des Original-Strings
    }
}
