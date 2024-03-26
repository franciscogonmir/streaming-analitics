#!/bin/bash

# Function to generate a random number within a given range
random_number() {
  local min=$1
  local max=$2
  echo $(($min + RANDOM % ($max - $min + 1)))
}

# Function to generate JSON data with random numbers
generate_json_data() {
  local timestamp=1711478049 # Current timestamp in milliseconds
  local datastream_values=()

  for ((i = 0; i < 4; i++)); do
    local value=$(random_number 1 100) # Numbers range from 1 to 100
    datastream_values+=("{\"at\": $timestamp, \"value\": $value}")
    timestamp=$((timestamp + 1000)) # Increment by 1 second
  done

  # Create the final JSON with generated data
  echo "{
  \"version\": \"1.0.0\",
  \"datastreams\": [
    {
      \"id\": \"temperature\",
      \"feed\": \"feed_1\",
      \"datapoints\": [
        {
          \"at\": $timestamp,
          \"value\": $value
        }
      ]
    },
    {
      \"id\": \"datastream\",
      \"datapoints\": [
        $(IFS=,; echo "${datastream_values[*]}")
      ]
    }
  ]
}"
}

# Perform curl calls five times with random data
for ((i = 0; i < 5; i++)); do
  json_data=$(generate_json_data)
  echo "Call $((i+1)):"
  echo "$json_data"
  curl --location --request POST 'localhost:8080/api/statistics/send/data' \
    --header 'Content-Type: application/json' \
    --data-raw "$json_data"
  echo -e "\n"
  sleep 1 # Wait for 1 second between calls
done
