package com.analytics.domain.usecase;

import com.analytics.domain.entities.Messaging.Stream;

public interface SendDataUseCase {

    void execute(Stream dataStream);
}
