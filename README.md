# Lobox IMDb Coding Challenge

## Requirements

* Java 25
* Maven 3.9+
* Docker (optional)

## Running the Application

### Using Maven

```bash
./mvnw spring-boot:run
```

The application will start on:

```
http://localhost:8181/api/lobox/imdb-service
```

Swagger UI:

```
http://localhost:8181/api/lobox/imdb-service/swagger-ui/index.html
```

---

# Importing the IMDb Dataset

The application does **not** include the IMDb datasets.

Download the required TSV files from:

https://datasets.imdbws.com/

Required files:

* `title.basics.tsv.gz`
* `title.ratings.tsv.gz`
* `title.crew.tsv.gz`
* `title.principals.tsv.gz`
* `name.basics.tsv.gz`

The import API accepts both compressed (`.tsv.gz`) and extracted (`.tsv`) files.

Import each dataset using the corresponding endpoint. The recommended order is:

1. Name Basics
2. Title Basics
3. Title Crew
4. Title Principals
5. Title Ratings

The import runs asynchronously. You can monitor its progress using the import status endpoint.

After the required datasets have finished importing, the application automatically builds the derived projection tables used to optimize query performance.

---

# Notes

* The application uses an embedded H2 database.
* Imported data is stored locally and remains available until the database is removed.
