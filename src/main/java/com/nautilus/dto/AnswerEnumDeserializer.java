package com.nautilus.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class AnswerEnumDeserializer extends JsonDeserializer<AnswerEnum> {
    @Override
    public AnswerEnum deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String value = jsonParser.getText().toUpperCase();
        return AnswerEnum.valueOf(value);
    }

}
