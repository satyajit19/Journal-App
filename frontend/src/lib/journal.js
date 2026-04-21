export function objectIdToString(value) {
  if (!value) {
    return "";
  }

  if (typeof value === "string") {
    return value;
  }

  if (typeof value === "object") {
    if (typeof value.$oid === "string") {
      return value.$oid;
    }
    if (typeof value.hexString === "string") {
      return value.hexString;
    }
    if (typeof value.timestamp === "number" && typeof value.date === "string") {
      const values = Object.values(value).filter((entry) => typeof entry === "string");
      const candidate = values.find((entry) => /^[a-fA-F0-9]{24}$/.test(entry));
      if (candidate) {
        return candidate;
      }
    }
  }

  return String(value);
}

export function normalizeJournal(journal) {
  if (!journal || typeof journal !== "object") {
    return journal;
  }

  return {
    ...journal,
    myid: objectIdToString(journal.myid),
  };
}
