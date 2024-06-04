package com.nautilus.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class ApproveRequestDto {
    private UUID id;
    private String comment;
    private AnswerEnum answer;
}
