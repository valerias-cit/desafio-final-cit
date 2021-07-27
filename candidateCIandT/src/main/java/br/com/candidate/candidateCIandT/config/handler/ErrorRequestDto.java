package br.com.candidate.candidateCIandT.config.handler;

public class ErrorRequestDto {

    private String field;
    private String error;

    public ErrorRequestDto(String field, String error) {
        this.field = field;
        this.error = error;
    }

    public String getField() {
        return field;
    }

    public String getError() {
        return error;
    }
}
