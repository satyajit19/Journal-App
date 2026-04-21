import http from "./http";

export async function fetchGreeting() {
  const response = await http.get("/user");
  return response.data;
}

export async function updateCurrentUser(payload) {
  const response = await http.put("/user", payload);
  return response.data;
}

export async function deleteCurrentUser() {
  const response = await http.delete("/user");
  return response.data;
}
