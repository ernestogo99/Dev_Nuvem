## Estrutura de pastas


```
com.example.demo/
├── configs/          ← Configurações gerais da aplicação (Beans, CORS, etc.)
├── controller/       ← Endpoints REST, coordena a comunicação com services
├── domain/           ← Entidades e objetos de domínio (ex: Carro, Vaga, Ticket)
├── exceptions/       ← Exceções personalizadas para regras de negócio e erros
├── infra/            ← Infraestrutura (persistência, repositórios, integrações)
├── shared/           ← Classes e componentes reutilizáveis entre módulos
├── services/         ← Implementação das regras de negócio
├── utils/            ← Funções utilitárias e helpers
└── BackendApplication.java  ← Ponto de entrada (Spring Boot Application)
```