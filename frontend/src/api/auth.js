import http from "./http";

export async function loginUser(credentials) {
  const response = await http.post("/public/login", credentials);
  return response.data;
}

export async function signupUser(payload) {
  const response = await http.post("/public/signup", payload);
  return response.data;
}
