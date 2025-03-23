#!/bin/bash

# Start Ollama using Docker Compose
docker compose up -d

# Max wait time (in seconds)
MAX_WAIT_TIME=300  # 5 minutes
WAIT_INTERVAL=5
TIME_ELAPSED=0

echo "Waiting for Ollama to start (max ${MAX_WAIT_TIME}s)..."

# Loop until Ollama is ready or timeout occurs
until curl -s http://localhost:11434/api/tags > /dev/null; do
    sleep $WAIT_INTERVAL
    TIME_ELAPSED=$((TIME_ELAPSED + WAIT_INTERVAL))

    if [ "$TIME_ELAPSED" -ge "$MAX_WAIT_TIME" ]; then
        echo "Error: Ollama did not start within $MAX_WAIT_TIME seconds. Exiting."
        exit 1
    fi
done

# If Ollama is ready, pull the required model
echo "Ollama is running! Pulling mxbai-embed-large model..."
docker exec -it ollama ollama pull mxbai-embed-large

echo "Ollama is ready with the model!"

