const BASE_URL = import.meta.env.VITE_API_BASE_URL;

async function safeReadText(res: Response): Promise<string> {
  try {
    return await res.text();
  } catch {
    return "";
  }
}

export async function apiGet<T>(path: string, init?: RequestInit): Promise<T> {
  const url = `${BASE_URL}${path}`;
  const res = await fetch(url, {
    method: "GET",
    ...init,
    headers: {
      Accept: "application/json",
      ...(init?.headers ?? {}),
    },
  });
  if (!res.ok) {
    const body = await safeReadText(res);
    throw new Error(
      `Request failed (${res.status}) for ${url}${body ? `: ${body}` : ""}`,
    );
  }

  return (await res.json()) as T;
}
