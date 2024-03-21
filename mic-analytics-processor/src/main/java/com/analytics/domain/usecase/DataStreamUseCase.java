package com.analytics.domain.usecase;

import com.analytics.domain.entities.Stream;

public interface DataStreamUseCase {

    void sendDataStream(Stream dataStream);
}
