package com.agenda.agenda.exception;

import org.springframework.validation.BindingResult;

public class BindingResultException extends RuntimeException{
    BindingResult bindingResult;
    public BindingResultException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }
    public BindingResult getBindingResult() {
        return bindingResult;
    }
    public void setBindingResult(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }
}
