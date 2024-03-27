package com.analytics.application.usecases;

import com.analytics.domain.entities.Messaging.Stream;
import com.analytics.domain.messaging.ProducerDataStream;
import com.analytics.domain.usecase.SendDataUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SendDataUseCaseImpl implements SendDataUseCase {

    private final ProducerDataStream producerDataStream;

    @Override
    public void send(final Stream dataStream) {

        this.producerDataStream.execute(dataStream);

    }
}
