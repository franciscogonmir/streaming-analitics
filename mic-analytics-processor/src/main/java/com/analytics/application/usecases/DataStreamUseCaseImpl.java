package com.analytics.application.usecases;

import com.analytics.domain.entities.Stream;
import com.analytics.domain.producer.ProducerDataStream;
import com.analytics.domain.usecase.DataStreamUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataStreamUseCaseImpl implements DataStreamUseCase {

    @Autowired
    private ProducerDataStream producerDataStream;

    @Override
    public void sendDataStream(Stream dataStream) {
        this.producerDataStream.send(dataStream);
    }
}
