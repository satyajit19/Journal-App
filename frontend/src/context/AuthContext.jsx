import { createContext, useContext, useMemo, useState } from "react";
import { loginUser, signupUser } from "../api/auth";
import {
  clearStoredToken,
  clearStoredUsername,
  getStoredToken,
  getStoredUsername,
  setStoredToken,
  setStoredUsername,
} from "../lib/storage";

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [token, setToken] = useState(getStoredToken());
  const [username, setUsername] = useState(getStoredUsername());
  const [authLoading, setAuthLoading] = useState(false);

  async function login(credentials) {
    setAuthLoading(true);
    try {
      const jwt = await loginUser(credentials);
      setToken(jwt);
      setUsername(credentials.username);
      setStoredToken(jwt);
      setStoredUsername(credentials.username);
      return { ok: true };
    } catch (error) {
      return {
        ok: false,
        message: error.response?.data || "Login failed. Check your credentials.",
      };
    } finally {
      setAuthLoading(false);
    }
  }

  async function signup(payload) {
    setAuthLoading(true);
    try {
      await signupUser(payload);
      return { ok: true };
    } catch (error) {
      return {
        ok: false,
        message: error.response?.data || "Signup failed. Try a different username.",
      };
    } finally {
      setAuthLoading(false);
    }
  }

  function logout() {
    setToken(null);
    setUsername(null);
    clearStoredToken();
    clearStoredUsername();
  }

  const value = useMemo(
    () => ({
      token,
      username,
      authLoading,
      isAuthenticated: Boolean(token),
      login,
      signup,
      logout,
    }),
    [authLoading, token, username],
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within AuthProvider");
  }
  return context;
}
