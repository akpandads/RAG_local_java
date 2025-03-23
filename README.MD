# Step by Step guide for a complete RAG setup using Java and Spring AI

My perspective is java and spring ecosystem developers might be feeling left-out in the whole world of AI.
This project is an attempt to use spring AI and setup an end to end working of RAG in a local system.
What it means we will not be using any paid API or tools. 
Although it means slower response times but still it will give us a good undertstanding of the different components and their usage.

## Componenets we will be using to run RAG locally

#### 1. Spring boot
#### 2. Spring AI
#### 3. Qdrant as the vector database
#### 4. Ollama for supporting the embedding model
#### 5. mxbai-embed-large as the Embedding model


## Project Setup : Step by Step

### Setting up Qdrant

There is a docker-compose file in the follwowing path of project
RAG_LLM_JAVA/infra/vector_db_setup

simply run docker compose up -d

Please do not change the contents or the config yaml unless you know what you are doing


### Setting up ollama

Run the script setup_ollama.sh in below location.

RAG_LLM_JAVA/infra/ollama_setup

The script pulls ollama image. waits till it is ready
Logs in into ollama and puls the mxbai-embed-large
The maximum time it waits for ollama to be ready is 5 minutes after that the script exits.
If that happens, please investigate locally





