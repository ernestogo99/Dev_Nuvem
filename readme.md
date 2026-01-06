## Pretty cake

This project is a menu for a candy shop, we will use these resources:
DynamoDB, Postgres, MinIO, RabbitMQ, Docker and Kubernetes

## Team

- Ernesto Dalva
- Guilherme Barbosa
- Bruna Gomes
- Marcos Antonio
- Michael Young

## Technologies Used

- Java: Programming language for back-end
- Spring boot: framework used for apis development
- Typescript: Programming language for front-end
- React: Library for building user interfaces on the front-end

 - DynamoDB: Fast and flexible NoSQL database service managed by AWS
 - Postgres: Relational database storing user credential data
 - MinIO: Object storage compatible with S3 used for media and assets
 - RabbitMQ: Message broker used for async processing between services
 - Docker: Containerization to run the stack locally via docker compose
 - Kubernetes: Container orchestration platform used to deploy, scale, and manage containerized applications in production environments



## Running with Docker Compose

### Prerequisites

- [Docker](https://docs.docker.com/get-docker/) installed
- [Docker Compose](https://docs.docker.com/compose/install/) installed


### Steps

1. **Clone the repository**
    ```
    git clone <respository-url>
    cd Dev_Nuvem
    ```
2. **Configure environment variables**
   
   Create a `.env` file in the root directory to override default values:
   ```env
   # Database
   DATASOURCE_URL=jdbc:postgresql://db:5432/pretty-cake-db
   DATASOURCE_USERNAME=postgre
   DATASOURCE_PASSWORD=postgrescake

   # MinIO
   MINIO_ENDPOINT=http://minio:9000
   MINIO_ACCESS_KEY=minioadmin
   MINIO_SECRET_KEY=minioadmin123
   MINIO_URL=http://localhost:9000

   # RabbitMQ
   RABBITMQ_DEFAULT_USER=myuser
   RABBITMQ_DEFAULT_PASS=secret
   RABBITMQ_HOST=rabbitmq

   # AWS (DynamoDB)
   AWS_REGION=us-east-1
   AWS_ACCESS_KEY=myaccesskey
   AWS_SECRET_ACCESS=mysecretkey
   AWS_SESSION_TOKEN=mytoken

   # JWT
   JWT_SECRET=7Z4/ixOoIRj48XHPDdY2FIXEWLUWHYl7UU9lmVDlhz=

   # Frontend
   FRONTEND_URL=http://localhost:5173       
    ```
 
3. **Create frontend environment file**
   
   Create a `.env` file inside the `frontend/` directory:
   ```env
   VITE_API_URL=http://localhost:8080
   ```
4. **Build and start all services**
   ```bash
   docker-compose up --build
   ```

   Or run in detached mode:
   ```bash
   docker-compose up --build -d
   ```
     
5. **Stop all services**
   ```bash
   docker-compose down
   ```

   To also remove volumes (database data):
   ```bash
   docker-compose down -v
   ```

## Running with Kubernetes

### Prerequisites

- [kubectl](https://kubernetes.io/docs/tasks/tools/) installed
- A Kubernetes cluster running (e.g., [Minikube](https://minikube.sigs.k8s.io/docs/start/), [Kind](https://kind.sigs.k8s.io/), or a cloud provider)

### Steps

1. **Start your Kubernetes cluster**

   Using Minikube:
   ```bash
   minikube start
   ```

   Using Kind:
   ```bash
   kind create cluster --name pretty-cake
   ```

2. **Create the secrets

    Inside the root directory, you should have created the `.env` file and execute:
    ```bash
    kubectl create secret generic pretty-secrets --from-env-file=.env
    ```
    And delete it if you want:
    ```bash
    kubectl delete secret pretty-secrets
    ```
3. **Deploy all resources**

   Apply all Kubernetes manifests:
   ```bash
   kubectl apply -f k8s/
   ```

   Or apply them individually in order:
   ```bash
   # Storage (PVCs)
   kubectl apply -f k8s/pg-pvc.yaml
   kubectl apply -f k8s/minio-pvc.yaml

   # Databases and Services
   kubectl apply -f k8s/pg-statefulset.yaml
   kubectl apply -f k8s/pg-service.yaml
   kubectl apply -f k8s/minio-statefulset.yaml
   kubectl apply -f k8s/minio-service.yaml

   # Message Broker
   kubectl apply -f k8s/rabbitmq-deployment.yaml
   kubectl apply -f k8s/rabbitmq-service.yaml

   # Application
   kubectl apply -f k8s/api-deployment.yaml
   kubectl apply -f k8s/api-service.yaml
   kubectl apply -f k8s/worker-deployment.yaml
   kubectl apply -f k8s/front-deployment.yaml
   kubectl apply -f k8s/front-service.yaml
   ```

4. **Verify the deployment**
   ```bash
   # Check all pods
   kubectl get pods

   # Check all services
   kubectl get services

   # Check deployment status
   kubectl get deployments
   ``` 
5. **Access the application**

   **Using Minikube:**
   ```bash
   minikube service frontend --url
   minikube service api --url
   ```

   **Using NodePort (default):**
   
   | Service   | URL                          |
   |-----------|------------------------------|
   | Frontend  | http://-node-ip-:30080       |
   | API       | http://-node-ip-:30081       |

   Get the node IP:
   ```bash
   kubectl get nodes -o wide
   ```

6. **Scale deployments**
   ```bash
   # Scale API to 3 replicas
   kubectl scale deployment api --replicas=3

   # Scale Worker to 2 replicas
   kubectl scale deployment worker --replicas=2
   ```
