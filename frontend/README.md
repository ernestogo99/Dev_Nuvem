## Como rodar o projeto

1. Instale as dependências com:
   ```sh
   npm install
   ```
2. Rode o projeto com:
   ```sh
   npm run dev
   ```

## Estrutura de pastas

```
/my-react-app
│── /public
│
│── /src
│   ├── /assets         # Imagens, ícones, estilos globais, fontes etc.
│   ├── /pages          # Páginas principais da aplicação
│   ├── /routes         # Configuração das rotas da aplicação
│   ├── /shared         # Recursos compartilhados
│   │   ├── /components # Componentes reutilizáveis (botões, tabelas, inputs, etc.)
│   │   ├── /contexts   # Contextos do React (Context API)
│   │
│   │   ├── /interfaces # Tipagens e interfaces TypeScript
│   │   ├── /layout     # Layouts padrão (ex: com menu lateral, cabeçalho etc.)
│   │   ├── /services   # Serviços de API, requisições HTTP etc.
│   │
│
│   ├── App.tsx         # Componente principal da aplicação
│   ├── main.tsx        # Ponto de entrada do React
│   ├── vite.config.ts  # Configuração do Vite (se estiver usando Vite)
│
│── package.json
│── tsconfig.json       # Configuração do TypeScript
│── .eslintrc.js        # Configuração do ESLint
│── .gitignore

```
