package com.analytics.infrastructure.api.controller;

import com.analytics.domain.usecase.SendDataUseCase;
import com.analytics.infrastructure.api.dto.StreamDto;
import com.analytics.infrastructure.api.mapper.DataStreamMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/statistics")
public class SendDataController {

    private final DataStreamMapper mapper;

    private final SendDataUseCase sendDataUseCase;


    @PostMapping("/send/data")
    public ResponseEntity<String> processorStatistics(@Valid @RequestBody final StreamDto streamDto) {
        this.sendDataUseCase.send(this.mapper.toDataStreamDomain(streamDto));
        return ResponseEntity.accepted().build();
    }

}
