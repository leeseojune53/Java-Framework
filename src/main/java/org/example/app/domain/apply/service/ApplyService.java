package org.example.app.domain.apply.service;

import org.example.annotataion.Component;
import org.example.app.domain.auth.service.AuthService;

@Component
public class ApplyService {

    private final AuthService authService;

    public ApplyService(AuthService authService) {
        this.authService = authService;
    }

    public void dependencyInjectionSuccess() {
        authService.getUser();
    }
}
