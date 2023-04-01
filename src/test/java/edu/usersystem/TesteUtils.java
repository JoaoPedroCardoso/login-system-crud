package edu.usersystem;

import org.springframework.core.io.ClassPathResource;
import wiremock.org.apache.commons.io.IOUtils;

public class TesteUtils {

    public static String loadPayload(String nomeArquivo) {
        try {
            return String.join("", IOUtils.readLines(new ClassPathResource(nomeArquivo).getInputStream(), "UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
