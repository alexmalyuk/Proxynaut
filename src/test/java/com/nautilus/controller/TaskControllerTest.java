package com.nautilus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nautilus.dto.AnswerEnum;
import com.nautilus.dto.ApproveRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(TaskController.class)
@SpringBootTest
@ActiveProfiles("dev")
@AutoConfigureMockMvc
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private final UUID taskId = UUID.fromString("1717d6e4-19c1-11ee-a570-00155d0a0186");
    private final UUID randomTaskId = UUID.randomUUID();
    private final String invalidTaskId = "123456789";
    public static final String APPROVE_PATH = "/edo/task/approve";
    public static final String GET_TASK_PATH = "/edo/task/";

    @Test
    void getTaskPage_Ok() throws Exception {
        mockMvc.perform(get(GET_TASK_PATH + taskId))
                .andExpect(status().isOk());
    }

    @Test
    void getTaskPage_RandomId() throws Exception {
        mockMvc.perform(get(GET_TASK_PATH + randomTaskId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTaskPage_InvalidId() throws Exception {

        mockMvc.perform(get(GET_TASK_PATH + invalidTaskId))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Server error"));
    }

    @Test
    void approveTask_Approve() throws Exception {
        ApproveRequestDto requestDto = new ApproveRequestDto()
                .setId(taskId)
                .setComment("comment qwerty comment")
                .setAnswer(AnswerEnum.APPROVE);

        mockMvc.perform(post(APPROVE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }
    @Test
    void approveTask_Decline() throws Exception {
        ApproveRequestDto requestDto = new ApproveRequestDto()
                .setId(taskId)
                .setComment("comment qwerty comment")
                .setAnswer(AnswerEnum.DECLINE);

        mockMvc.perform(post(APPROVE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }
@Test
    void approveTask_DeclineWithoutComment() throws Exception {
        ApproveRequestDto requestDto = new ApproveRequestDto()
                .setId(taskId)
                .setComment("")
                .setAnswer(AnswerEnum.DECLINE);

        mockMvc.perform(post(APPROVE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void approveTask_RandomId() throws Exception {

        ApproveRequestDto requestDto = new ApproveRequestDto()
                .setId(randomTaskId)
                .setComment("comment qwerty comment")
                .setAnswer(AnswerEnum.APPROVE);

        mockMvc.perform(post(APPROVE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void approveTask_InvalidId() throws Exception {
        String requestBody = String.format("""
                {
                  "id": "%s",
                  "comment": "comment qwerty comment",
                  "answer": "approve"
                }
                """, invalidTaskId);

        mockMvc.perform(post(APPROVE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void approveTask_InvalidAnswer() throws Exception {
        String requestBody = String.format("""
                {
                  "id": "%s",
                  "comment": "comment qwerty comment",
                  "answer": "something"
                }
                """, taskId);

        mockMvc.perform(post(APPROVE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isInternalServerError());
    }
    @Test
    void approveTask_EmptyAnswer() throws Exception {
        String requestBody = String.format("""
                {
                  "id": "%s",
                  "comment": "comment qwerty comment",
                  "answer": ""
                }
                """, taskId);

        mockMvc.perform(post(APPROVE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void approveTask_EmptyRequest() throws Exception {

        mockMvc.perform(post(APPROVE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void approveTask_BlaBlaBlaRequest() throws Exception {

        mockMvc.perform(post(APPROVE_PATH)
                        .contentType(MediaType.ALL_VALUE)
                        .content("How can a clam cram in a clean cream can?"))
                .andExpect(status().isInternalServerError());
    }
}