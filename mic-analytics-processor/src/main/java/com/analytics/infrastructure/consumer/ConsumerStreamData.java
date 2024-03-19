package com.analytics.infrastructure.consumer;

import com.analytics.domain.entities.Stream;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RabbitListener(queues = "myQueue",containerFactory = "myRabbitListenerContainerFactory")
@Component
public class ConsumerStreamData {

    private final List<Stream> accumulatedMessages = new ArrayList<>();
    private static final int BATCH_SIZE = 5;
    @RabbitHandler
    //TODO traer mensaje y pasar a entidad de dominio
    public void listen(Stream in) {
        if(accumulatedMessages.size() < BATCH_SIZE){
            accumulatedMessages.add(in);
        }else{
            //TODO procesar calculo de media
            System.out.println("Message read from myQueue : " + accumulatedMessages.size());
            accumulatedMessages.clear();
        }
    }
}
