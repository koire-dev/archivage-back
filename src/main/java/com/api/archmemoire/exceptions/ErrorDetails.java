package com.api.archmemoire.exceptions;

import lombok.Data;
import java.util.Date;

@Data
public class ErrorDetails {

    private Date timestamp;
    private String message;

    public ErrorDetails(Date timestamp, String message){
        this.timestamp = timestamp;
        this.message = message;
    }
}
