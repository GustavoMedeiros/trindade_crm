package com.trindadeisencoes.crm.config;

import com.trindadeisencoes.crm.model.User;
import com.trindadeisencoes.crm.model.Role;
import com.trindadeisencoes.crm.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class UserDataLoader {

    @Bean
    CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            if (userRepository.findByEmail("gustavosimon4g@gmail.com").isEmpty()) {
                User gestor = new User();
                gestor.setName("Gustavo Simon Medeiros");
                gestor.setEmail("gustavosimon4g@gmail.com");
                gestor.setRole(Role.valueOf("GESTOR")); // corrigido
                gestor.setCriadoEm(LocalDateTime.now());
                gestor.setSenha(passwordEncoder.encode("23*0821Gr"));
                userRepository.save(gestor);
            }

            if (userRepository.findByEmail("rebeccapatriciodemoraes@gmail.com").isEmpty()) {
                User vendedor = new User();
                vendedor.setName("Rebecca Patrício Medeiros");
                vendedor.setEmail("rebeccapatriciodemoraes@gmail.com");
                vendedor.setRole(Role.valueOf("VENDEDOR")); // corrigido
                vendedor.setCriadoEm(LocalDateTime.now());
                vendedor.setSenha(passwordEncoder.encode("123456"));
                userRepository.save(vendedor);
            }

            if (userRepository.findByEmail("fernandoreule@outlook.com").isEmpty()) {
                User colaborador = new User();
                colaborador.setName("Fernando Reule");
                colaborador.setEmail("fernandoreule@outlook.com");
                colaborador.setRole(Role.valueOf("VENDEDOR")); // corrigido
                colaborador.setCriadoEm(LocalDateTime.now());
                colaborador.setSenha(passwordEncoder.encode("123456"));
                userRepository.save(colaborador);
            }

            System.out.println("Usuários pré-carregados com sucesso.");
        };
    }
}