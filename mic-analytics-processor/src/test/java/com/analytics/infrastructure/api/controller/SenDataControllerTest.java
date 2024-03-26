package com.analytics.infrastructure.api.controller;

import com.analytics.domain.entities.Messaging.Stream;
import com.analytics.domain.usecase.SendDataUseCase;
import com.analytics.infrastructure.api.dto.DataPointDto;
import com.analytics.infrastructure.api.dto.DataStreamDto;
import com.analytics.infrastructure.api.dto.StreamDto;
import com.analytics.infrastructure.api.mapper.DataStreamMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SenDataControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DataStreamMapper mapper;

    @Mock
    private SendDataUseCase sendDataUseCase;

    @InjectMocks
    private SendDataController controller;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
    }

    @Test
    void testProcessorStatistics_ValidRequest() throws Exception {
        StreamDto streamDto = getStreamDto();
        when(this.mapper.toDataStreamDomain(streamDto)).thenReturn(Stream.builder().build());
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/statistics/send/data")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(streamDto)))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.ACCEPTED.value(), response.getStatus());
    }


    @Test
    void testProcessorStatistics_InvalidRequest() throws Exception {
        StreamDto streamDto = getStreamDtoNullDataStream();

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/statistics/send/data")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(streamDto)))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void testProcessorStatistics_InvalidRequest_NullDataPoint() throws Exception {
        StreamDto streamDto = getStreamDtoNullDataDataPoint();

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/statistics/send/data")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(streamDto)))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    private StreamDto getStreamDtoNullDataStream() {
        return StreamDto.builder()
                .version("1.0.0")
                .datastreams(null)
                .build();
    }

    private StreamDto getStreamDtoNullDataDataPoint() {
        return StreamDto.builder()
                .version("1.0.0")
                .datastreams(List.of(DataStreamDto.builder().build()))
                .build();
    }


    private StreamDto getStreamDto() {
        return StreamDto.builder()
                .version("1.0.0")
                .datastreams(List.of(getDataStreamDto()))
                .build();
    }

    private DataPointDto getDataPointDto() {
        return DataPointDto.builder()
                .at(Instant.now().toEpochMilli())
                .value(25)
                .build();
    }

    private DataStreamDto getDataStreamDto() {
        return DataStreamDto.builder()
                .id("temperature")
                .feed("feed")
                .datapoints(List.of(getDataPointDto()))
                .build();
    }

    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}