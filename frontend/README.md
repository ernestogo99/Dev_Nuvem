## How to Run the Project

1. Install the dependencies:
   ```sh
   npm install
   ```
2. Run the project:
   ```sh
   npm run dev
   ```

## Project Folder Structure

```
/my-react-app
│── /public
│
│── /src
│   ├── /assets         # Images, icons, global styles, fonts, etc.
│   ├── /pages          # Main pages of the application
│   ├── /routes         # Application route configuration
│   ├── /shared         # Shared resources
│   │   ├── /components # Reusable components (buttons, tables, inputs, etc.)
│   │   ├── /contexts   # React Contexts (Context API)
│   │
│   │   ├── /interfaces # TypeScript types and interfaces
│   │   ├── /layout     # Standard layouts (e.g., with sidebar, header, etc.)
│   │   ├── /services   # API services, HTTP requests, etc.
│   │
│
│   ├── App.tsx         # Main application component
│   ├── main.tsx        # React entry point
│   ├── vite.config.ts  # Vite configuration file (if using Vite)
│
│── package.json
│── tsconfig.json       # TypeScript configuration
│── .eslintrc.js        # ESLint configuration
│── .gitignore


```
