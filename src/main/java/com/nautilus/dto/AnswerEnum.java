package com.nautilus.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = AnswerEnumDeserializer.class)
public enum AnswerEnum {
    APPROVE,
    DECLINE
}
