# Journal Frontend

React frontend for the Spring Boot journal backend in the repository root.

## Backend contract used

- `POST /public/signup`
- `POST /public/login`
- `GET /journal`
- `POST /journal`
- `GET /journal/id/{myid}`
- `PUT /journal/id/{myid}`
- `DELETE /journal/id/{myid}`
- `GET /user`
- `PUT /user`
- `DELETE /user`

The Vite dev server proxies `/api/*` to `http://localhost:8080/*`.

## Local run

1. Install dependencies: `npm install`
2. Start the frontend: `npm run dev`
3. Start the Spring Boot backend on port `8080`
4. Open `http://localhost:5173`

If you want to bypass the Vite proxy, create `.env` with:

`VITE_API_BASE_URL=http://localhost:8080`
