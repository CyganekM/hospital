package by.asb.traningprogect.hospital.service;

import by.asb.traningprogect.hospital.model.dto.EmployeeAuthDto;
import by.asb.traningprogect.hospital.security.JwtAuthenticationResponse;

public interface AuthenticationService {

    JwtAuthenticationResponse signIn(EmployeeAuthDto request);
}
