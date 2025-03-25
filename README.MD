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

## Points to note

1. Spring AI has inbuilt libraries which do the embedding and generate vectors when adding a document.Those interested can take a look at  public void doAdd(List<Document> documents) in the QdrantVectoreStore.class file
2. ollama can be run natively or via docker. If you are using docker on a mac you might not be able to use the GPU capabilties. Hence i have seen a stark difference in response time when you are using ollama natively with GPU enabled. To enable GPU mode run as "OLLAMA_METAL=1 ollama run model_name" in mac systems


### Specific to "voice_ai_branch"
Modifications were made to the main branch to have a Audio processing embedded and create a Trello ticket with voice command.

It connects with AssemblyAI and generates a transcript.
The transcript is fed to the LLM and then an intent is created which ultimately transforms into an action to create trello ticket
This was just to demonstarte the different model integration so the code might be a little crude.

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

**As mesntioned above this is for running ollama in docker without GPU **

### Running a model

The model that runs with the script is codellama:13b
Feel free to modify the model as per your machine capabilities adn requirements


### Running the spring boot app

Start the spring boot as as usual from IDE, spring boot cli or maven

### Custom Data Setup for RAG

place the files which you want to be trained under resources/RAG_data folder
On start up it takes care of reading all the files and putting the data in vector store.
Currently on text and pdf file parsing is done.
_





