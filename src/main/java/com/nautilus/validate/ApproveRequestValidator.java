package com.nautilus.validate;

import com.nautilus.dto.AnswerEnum;
import com.nautilus.dto.ApproveRequestDto;
import com.nautilus.exception.ApproveRequestValidationException;
import org.springframework.stereotype.Component;

@Component
public class ApproveRequestValidator {
    public void validate(ApproveRequestDto requestDto) throws ApproveRequestValidationException {
        if (AnswerEnum.DECLINE.equals(requestDto.getAnswer()) && requestDto.getComment().isEmpty()) {
            throw new ApproveRequestValidationException("Empty comment when decline");
        }
    }
}
