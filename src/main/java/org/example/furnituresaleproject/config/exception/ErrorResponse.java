package org.example.furnituresaleproject.config.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class ErrorResponse {

    @NonNull
    private String message;
    @NonNull
    private String detailMessage;
    @NonNull
    private Object error;
    @NonNull
    private Integer code;
    @NonNull
    private String moreInformation;

}
