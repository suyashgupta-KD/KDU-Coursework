## Why GraphQL is More Efficient Than REST (Simple Explanation)

In a **REST API**, data is spread across multiple endpoints.

To get a movie and its director, the client usually has to:

1. Call `/movies/1` → get movie data (includes `directorId`)
2. Call `/directors/5` → get director details

This means:

- **Multiple network calls**
- More waiting time
- Extra client-side logic to connect the data

---

### How GraphQL Improves This

With **GraphQL**, the client makes **one request** and asks for exactly what it needs.

Example:

```graphql
query {
  findMovieById(id: "1") {
    title
    director {
      name
    }
  }
}
```

- Only **one HTTP call**
- Server handles the relationship between Movie and Director
- Response contains **only requested fields**

---
