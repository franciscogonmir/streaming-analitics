package com.analytics.application.usecases;

import com.analytics.domain.entities.Stream;
import com.analytics.domain.producer.SendData;
import com.analytics.domain.usecase.SendDataUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataStreamUseCaseImpl implements SendDataUseCase {

    @Autowired
    private  SendData sendData;

    @Override
    public void processDataStream(Stream dataStream) {
        this.sendData.send(dataStream);
    }
}
