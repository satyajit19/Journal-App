import http from "./http";
import { normalizeJournal, objectIdToString } from "../lib/journal";

export async function fetchJournals() {
  const response = await http.get("/journal");
  return Array.isArray(response.data) ? response.data.map(normalizeJournal) : [];
}

export async function fetchJournalById(journalId) {
  const response = await http.get(`/journal/id/${objectIdToString(journalId)}`);
  return normalizeJournal(response.data);
}

export async function createJournal(payload) {
  const response = await http.post("/journal", payload);
  return normalizeJournal(response.data);
}

export async function updateJournal(journalId, payload) {
  const response = await http.put(`/journal/id/${objectIdToString(journalId)}`, payload);
  return normalizeJournal(response.data);
}

export async function deleteJournal(journalId) {
  const response = await http.delete(`/journal/id/${objectIdToString(journalId)}`);
  return response.data;
}
