package com.analytics.infrastructure.api.controller;

import com.analytics.domain.usecase.DataStreamUseCase;
import com.analytics.infrastructure.api.dto.StreamDto;
import com.analytics.infrastructure.api.mapper.DataStreamMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DataStreamController {

    private final DataStreamMapper mapper;

    private final DataStreamUseCase sendDataUseCase;

    @PostMapping("/produce/feed")
    ResponseEntity processorFeeds(@RequestBody StreamDto streamDto) {

        this.sendDataUseCase.sendDataStream(this.mapper.toDataStreamDomain(streamDto));
        return ResponseEntity.accepted().build();
    }
}
