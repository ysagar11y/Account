package com.sbank.Account.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class ErrorResponseDTO {

    private String apiPath;
    private HttpStatus httpsStatus;

    private String errorMessage;

    private LocalDateTime errorTime;

}
