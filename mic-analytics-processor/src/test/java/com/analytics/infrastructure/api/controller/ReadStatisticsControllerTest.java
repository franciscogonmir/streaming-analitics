package com.analytics.infrastructure.api.controller;

import com.analytics.domain.entities.persistence.Stats;
import com.analytics.domain.usecase.ReadStatsUseCase;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReadStatisticsControllerTest {

    private static final int A_MEAN_VALUE = 40;
    private static final int A_MEDIAN_VALUE = 27;
    private static final List<Integer> A_MODE_VALUE = List.of(32, 16);
    private static final double A_STANDARD_DEVIATION_VALUE = 27.2;
    private static final double A_FIRST_QUARTILE_VALUE = 25.0;
    private static final double A_THIRD_QUARTILE_VALUE = 51.0;
    private static final double A_MAX_VALUE = 102.0;
    private static final double A_MIN_VALUE = 16.0;
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
        Stats stats = getStats();
        StatsDto expectedDto = getStatsDto();

        when(this.readStatsUseCase.getStatsById(id)).thenReturn(stats);
        when(this.statsMapper.toStatsDto(stats)).thenReturn(expectedDto);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/statistics/{id}", id))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        StatsDto objectResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), StatsDto.class);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(id, objectResponse.id());

    }

    @Test
    public void testGetStatsByFields() throws Exception {
        String field = "someField";
        String operator = "greaterThan";
        double value = 10.0;
        List<Stats> statsList = List.of(getStats());
        List<StatsDto> expectedDtoList = List.of(getStatsDto());

        when(this.readStatsUseCase.filterStatsByField(field, operator, value)).thenReturn(statsList);
        when(this.statsMapper.toStatsListDto(statsList)).thenReturn(expectedDtoList);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/statistics/field/{field}?operator={operator}&value={value}", field, operator, value))
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    private Stats getStats() {
        return Stats.builder()
                .id("someId")
                .at(Instant.now().toEpochMilli())
                .mean(A_MEAN_VALUE)
                .median(A_MEDIAN_VALUE)
                .mode(A_MODE_VALUE)
                .standardDeviation(A_STANDARD_DEVIATION_VALUE)
                .firstQuartile(A_FIRST_QUARTILE_VALUE)
                .thirdQuartile(A_THIRD_QUARTILE_VALUE)
                .maxValue(A_MAX_VALUE)
                .minValue(A_MIN_VALUE)
                .build();
    }

    private StatsDto getStatsDto() {
        return StatsDto.builder()
                .id("someId")
                .at(Instant.now().toEpochMilli())
                .mean(A_MEAN_VALUE)
                .median(A_MEDIAN_VALUE)
                .mode(A_MODE_VALUE)
                .standardDeviation(A_STANDARD_DEVIATION_VALUE)
                .firstQuartile(A_FIRST_QUARTILE_VALUE)
                .thirdQuartile(A_THIRD_QUARTILE_VALUE)
                .maxValue(A_MAX_VALUE)
                .minValue(A_MIN_VALUE)
                .build();
    }

}