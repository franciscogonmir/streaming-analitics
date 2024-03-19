package com.analytics.domain.usecase;

import com.analytics.domain.entities.Stream;

public interface SendDataUseCase {

    void processDataStream(Stream dataStream);
}
