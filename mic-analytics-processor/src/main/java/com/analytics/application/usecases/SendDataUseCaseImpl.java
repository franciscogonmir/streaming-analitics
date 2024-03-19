package com.analytics.application.usecases;

import com.analytics.domain.entities.Stream;
import com.analytics.domain.producer.SendData;
import com.analytics.domain.usecase.SendDataUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendDataUseCaseImpl implements SendDataUseCase {

    @Autowired
    private  SendData sendData;
    @Override
    public void processDataStream(Stream dataStream) {
        sendData.send(dataStream);
    }
}
