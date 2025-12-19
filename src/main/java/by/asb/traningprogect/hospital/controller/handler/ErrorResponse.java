package by.asb.traningprogect.hospital.controller.handler;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(LocalDateTime timeStamp, String message, List<String> details) {

    public ErrorResponse{
        if (details.isEmpty()){
            details = null;
        }
    }

}
