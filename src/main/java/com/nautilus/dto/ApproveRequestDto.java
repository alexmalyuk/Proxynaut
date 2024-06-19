package com.nautilus.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class ApproveRequestDto {
    private UUID id;
    private String comment;
    @JsonDeserialize(using = AnswerEnumDeserializer.class)
    private AnswerEnum answer;
}
