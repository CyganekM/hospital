package by.asb.traningprogect.hospital.service.impl;

import by.asb.traningprogect.hospital.model.dto.EmployeeAuthDto;
import by.asb.traningprogect.hospital.security.JwtAuthenticationResponse;
import by.asb.traningprogect.hospital.security.JwtService;
import by.asb.traningprogect.hospital.service.AuthenticationService;
import by.asb.traningprogect.hospital.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final EmployeeService employeeService;
    private final JwtService jwtService;

    @Override

    public JwtAuthenticationResponse signIn(EmployeeAuthDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        ));
        var user = employeeService.loadUserByUsername(request.email());
        var jwt = jwtService.generateToken(user);
        log.info("Пользователь {} вошел в систему", user.getUsername());
        return new JwtAuthenticationResponse(jwt);
    }
}
