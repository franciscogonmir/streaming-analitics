# Micro-streaming-analytics

It is a service that receives messages and sends them to a queue. These messages are processed by a consumer, statistical calculations are performed, and they are stored in a MongoDB database. The stored data can also be queried through a REST API.

## How to Publish a Message

To publish a message, an endpoint has been exposed that receives a message. Messages can be sent continuously, and the consumer is configured to read from the queue every 10 seconds with a maximum size of 5 messages. [Link to configuration section](#Configuration)

## API Documentation

The service includes the springdoc-openapi plugin for API documentation generation. The API documentation can be accessed once the service is up and running at [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs). Additionally, Swagger can be accessed at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html). Swagger can be used to test the solution.

![img_1.png](images/img_1.png)

![img_2.png](images/img_2.png)

![img_3.png](images/img_3.png)

![img_4.png](images/img_4.png)

![img_5.png](images/img_5.png)

![img_6.png](images/img_6.png)

## Data Model

### Message Publishing Model

```json
{
  "version": "1.0.0",
  "datastreams": [
    {
      "id": "temperature",
      "feed": "feed_1",
      "datapoints": [
        {
          "at": 1431602523123,
          "value": 25
        },
        {
          "at": 1431602523123,
          "value": 26
        },
        {
          "at": 1431602523123,
          "value": 27
        }
      ]
    }
  ]
}
```

# Data Model

As indicated in the functional requirements for message consumption, the format is similar to the OpenGate device integration API, albeit simplified.

It is a Stream object composed of a list of datastreams. Datastreams, in turn, are composed of datapoints containing a numerical value and a timestamp.

## Results Data Model

```json
{
  "id": "d1a9778d-78b1-4502-b06b-90406777d0df",
  "at": 1711462423670,
  "mean": 39.857142857142854,
  "median": 27.0,
  "mode": [32, 16, 51, 102, 25, 26, 27],
  "standardDeviation": 27.24192447276058,
  "firstQuartile": 25.0,
  "thirdQuartile": 51.0,
  "maxValue": 102.0,
  "minValue": 16.0
}
```

The results model is also very simple, containing an autogenerated identifier, a timestamp indicating the time it was stored, and fields with the results of the processed messages.

# Design

Although initially, I had thought of creating a solution with 2 microservices, I decided to make it simpler since the database is shared, and publishing messages via a REST endpoint seemed to be the most appropriate solution.

I opted for a hexagonal architecture because, although a simpler one could have been used (such as a layered architecture), I believe it allows for more robust and scalable software.

The REST API is public because I didn't have time to implement security features. From a messaging perspective, serialization/deserialization has been implemented with Jackson2JsonMessageConverter since the default Java one is not recommended due to a vulnerability. I encountered that issue.

Info-level logs have been left as traces where operations can be tracked.

# Configuration

## Messaging

The Rabbit configuration is located at `com/analytics/infrastructure/messaging/config`, where values are read from the `application.yml` file.

## Persistence

MongoDB configuration is defined in the `application.yml` file.

Additionally, there is configuration in the `com/analytics/config` folder, where the use case beans are located. The idea behind this structure is to keep Spring out of the domain as it's not good practice to use framework-specific functionality in the domain.

## Containers

The service has a Dockerfile located at `mic-analytics-processor/Dockerfile`.

The docker-compose file is in the root of the project.

# Running the Application

To run the project, simply execute the command:

```bash
docker compose -f docker-compose.yml (path to the file) up
```

Or if you are in the directory where the file is located:

```bash
docker compose up
```

# Testing

Due to time constraints, the application only contains unit tests for the statistics calculation service and controllers, and not all cases are covered.


# Functional Testing

To test the application, there is a script located at random_data_sender.sh which can be executed from within the application.
You can observe the inserted records through the logs or by connecting to the database. Afterwards, you can test the query endpoints.





