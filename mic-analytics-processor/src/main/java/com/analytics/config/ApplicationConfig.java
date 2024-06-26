package com.analytics.config;

import com.analytics.application.usecases.SendDataUseCaseImpl;
import com.analytics.application.usecases.ReadStatsUseCaseImpl;
import com.analytics.domain.messaging.ProducerDataStream;
import com.analytics.domain.service.repository.StatisticsRepositoryService;
import com.analytics.domain.usecase.SendDataUseCase;
import com.analytics.domain.usecase.ReadStatsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public SendDataUseCase sendDataUseCase(final ProducerDataStream producer) {
        return new SendDataUseCaseImpl(producer);
    }

    @Bean
    public ReadStatsUseCase readStreamUseCase(final StatisticsRepositoryService repositoryService) {
        return new ReadStatsUseCaseImpl(repositoryService);
    }
}
