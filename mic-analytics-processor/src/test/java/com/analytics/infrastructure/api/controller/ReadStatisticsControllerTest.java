package com.analytics.infrastructure.api.controller;

import com.analytics.domain.entities.persistence.Stats;
import com.analytics.domain.usecase.ReadStatsUseCase;
import com.analytics.infrastructure.api.dto.OperatorDto;
import com.analytics.infrastructure.api.dto.StatsDto;
import com.analytics.infrastructure.api.mapper.StatsMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReadStatisticsControllerTest {
    public static final OperatorDto OPERATOR_DTO = OperatorDto.GT;
    private MockMvc mockMvc;

    @Mock
    private StatsMapper statsMapper;

    @Mock
    private ReadStatsUseCase readStatsUseCase;

    @InjectMocks
    private ReadStatisticsController controller;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetStatsById() throws Exception {
        String id = "someId";
        Stats stats = Stats.builder().id("someId").build();
        StatsDto expectedDto = StatsDto.builder().id("someId").build();

        when(this.readStatsUseCase.getStatsById(id)).thenReturn(stats);
        when(this.statsMapper.toStatsDto(stats)).thenReturn(expectedDto);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/statistics/filter/id/{id}", id))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        StatsDto objectResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), StatsDto.class);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(id, objectResponse.id());

    }

    @Test
    void testfilterByMean_okResponse() throws Exception {
        var value = 5.0;
        var operator = OPERATOR_DTO.name();
        List<Stats> statsList = List.of(mock(Stats.class));
        List<StatsDto> expectedDtoList = List.of(mock(StatsDto.class));

        when(this.readStatsUseCase.filterMean(operator.toLowerCase(), value)).thenReturn(statsList);
        when(this.statsMapper.toStatsListDto(statsList)).thenReturn(expectedDtoList);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/statistics/filter/mean?operator={operator}&value={value}", operator,value))
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void testfilterByMinValue_okResponse() throws Exception {
        var value = 5.0;
        var operator = OPERATOR_DTO.name();
        List<Stats> statsList = List.of(mock(Stats.class));
        List<StatsDto> expectedDtoList = List.of(mock(StatsDto.class));

        when(this.readStatsUseCase.filterMinValue(operator.toLowerCase(), value)).thenReturn(statsList);
        when(this.statsMapper.toStatsListDto(statsList)).thenReturn(expectedDtoList);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/statistics/filter/minvalue?operator={operator}&value={value}", operator,value))
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void testfilterByMinValueAndMean_okResponse() throws Exception {
        var meanValue = 5.0;
        var minValue = 6.0;
        var operator = OPERATOR_DTO.name();
        List<Stats> statsList = List.of(mock(Stats.class));
        List<StatsDto> expectedDtoList = List.of(mock(StatsDto.class));

        when(this.readStatsUseCase.filterMeanAndMinValue(operator.toLowerCase(), meanValue,minValue)).thenReturn(statsList);
        when(this.statsMapper.toStatsListDto(statsList)).thenReturn(expectedDtoList);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/statistics/filter/mean/and/minvalue?operator={operator}&meanValue={meanValue}&minValue={minValue}", operator,meanValue,minValue))
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }


}