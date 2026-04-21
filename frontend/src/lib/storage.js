const TOKEN_KEY = "journal_app_token";
const USERNAME_KEY = "journal_app_username";

export function getStoredToken() {
  return window.localStorage.getItem(TOKEN_KEY);
}

export function setStoredToken(token) {
  window.localStorage.setItem(TOKEN_KEY, token);
}

export function clearStoredToken() {
  window.localStorage.removeItem(TOKEN_KEY);
}

export function getStoredUsername() {
  return window.localStorage.getItem(USERNAME_KEY);
}

export function setStoredUsername(username) {
  if (username) {
    window.localStorage.setItem(USERNAME_KEY, username);
  }
}

export function clearStoredUsername() {
  window.localStorage.removeItem(USERNAME_KEY);
}
