package com.trindadeisencoes.crm.controller;

import com.trindadeisencoes.crm.model.User;
import com.trindadeisencoes.crm.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> listarUsuarios(){
        return ResponseEntity.ok(userService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<User> criarUsuario(@RequestBody @Valid User user){
        return ResponseEntity.ok(userService.salvarUsuario(user));
    }
}