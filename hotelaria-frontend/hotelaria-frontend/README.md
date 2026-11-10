# Hotelaria — Front-end React

## Requisitos
- Node.js 20+ e npm
- Backend Spring Boot executando na porta 8080 **no mesmo Codespaces**

## Instalação
```bash
cd /workspaces/hotelaria
npm create vite@latest hotelaria-frontend -- --template react
```
Alternativamente, extraia esta pasta `hotelaria-frontend` em `/workspaces/hotelaria` e execute:
```bash
cd /workspaces/hotelaria/hotelaria-frontend
npm install
npm run dev
```

Em outro terminal, inicie o backend:
```bash
cd /workspaces/hotelaria/hotelaria
./mvnw spring-boot:run
```

No Codespaces, abra a porta 5173 na aba **PORTS**. O proxy do Vite encaminha as chamadas `/api/hospedes` etc. para `http://localhost:8080/hospedes`, sem precisar de configuração CORS no modo de desenvolvimento.

## API esperada
- GET/POST/PUT/DELETE `/hospedes`, `/quartos`, `/servicos` (PUT/DELETE por `/:id`)
- GET/POST `/reservas`; PATCH `/reservas/:id/cancelar`
- GET/POST `/pagamentos`

## Observações
- O backend H2 em memória perde os registros ao reiniciar a aplicação.
- O frontend não implementa autenticação nem processamento real de pagamentos.
- A interface usa o `id` gerado pelo backend; as reservas usam listas `quartosIds` e `servicosIds`.
- A API deve retornar os DTOs discutidos, especialmente `ReservaResponseDTO` e `PagamentoResponseDTO`.
- Erros de negócio retornados pelo Spring Boot podem aparecer como erro HTTP genérico até existir um `@RestControllerAdvice`.
