package com.analytics.application.config;

import com.analytics.application.usecases.DataStreamUseCaseImpl;
import com.analytics.application.usecases.ReadStatsUseCaseImpl;
import com.analytics.domain.producer.ProducerDataStream;
import com.analytics.domain.service.StatisticsRepositoryService;
import com.analytics.domain.usecase.DataStreamUseCase;
import com.analytics.domain.usecase.ReadStatsUseCase;
import com.analytics.infrastructure.persistence.repository.StatisticsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public DataStreamUseCase dataStreamUseCase(ProducerDataStream producer){
        return new DataStreamUseCaseImpl(producer);
    }

    @Bean
    public ReadStatsUseCase readStreamUseCase(StatisticsRepositoryService repositoryService){
        return new ReadStatsUseCaseImpl(repositoryService);
    }
}
