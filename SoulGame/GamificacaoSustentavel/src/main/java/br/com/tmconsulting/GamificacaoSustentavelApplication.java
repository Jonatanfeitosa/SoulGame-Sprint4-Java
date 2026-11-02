package br.com.tmconsulting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "br.com.tmconsulting",
        "resource",
        "service",
        "dao",
        "conexao"
})
public class GamificacaoSustentavelApplication {

    public static void main(String[] args) {
        SpringApplication.run(GamificacaoSustentavelApplication.class, args);
    }
}