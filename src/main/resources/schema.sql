CREATE TABLE IF NOT EXISTS title (id VARCHAR(16) PRIMARY KEY,
                                  title_type VARCHAR(32),
                                  primary_title VARCHAR(1000) NOT NULL,
                                  original_title VARCHAR(1000),
                                  adult BOOLEAN,start_year INT,
                                  end_year INT,runtime_minutes INT);

CREATE TABLE IF NOT EXISTS person (id VARCHAR(16) PRIMARY KEY,
                                   primary_name VARCHAR(1000) NOT NULL,
                                   birth_year INT,death_year INT);

CREATE TABLE IF NOT EXISTS title_genre (title_id VARCHAR(16) NOT NULL,
                                        genre VARCHAR(64) NOT NULL,
                                        PRIMARY KEY(title_id,genre));

CREATE TABLE IF NOT EXISTS title_director (title_id VARCHAR(16) NOT NULL,
                                           person_id VARCHAR(16) NOT NULL,
                                           PRIMARY KEY(title_id,person_id));

CREATE TABLE IF NOT EXISTS title_writer (title_id VARCHAR(16) NOT NULL,
                                         person_id VARCHAR(16) NOT NULL,
                                         PRIMARY KEY(title_id,person_id));

CREATE TABLE IF NOT EXISTS title_principal (title_id VARCHAR(16) NOT NULL,
                                            ordering INT NOT NULL,
                                            person_id VARCHAR(16) NOT NULL,
                                            category VARCHAR(64) NOT NULL,
                                            PRIMARY KEY(title_id,ordering));

CREATE TABLE IF NOT EXISTS title_rating (title_id VARCHAR(16) PRIMARY KEY,
                                         average_rating DECIMAL(3,1) NOT NULL,
                                         num_votes INT NOT NULL);

CREATE TABLE IF NOT EXISTS living_director_writer (title_id VARCHAR(16) NOT NULL,
                                                   person_id VARCHAR(16) NOT NULL,
                                                PRIMARY KEY (title_id, person_id));
CREATE TABLE IF NOT EXISTS best_genre_title_by_year (
                                                        genre VARCHAR(64) NOT NULL,
                                                        start_year INT NOT NULL,
                                                        title_id VARCHAR(16) NOT NULL,
                                                        average_rating DECIMAL(3, 1) NOT NULL,
                                                        num_votes INT NOT NULL,

                                                        PRIMARY KEY (genre, start_year)
);

CREATE INDEX IF NOT EXISTS idx_genre
    ON title_genre(genre,title_id);

CREATE INDEX IF NOT EXISTS idx_director_title_person
    ON title_director(title_id, person_id);

CREATE INDEX IF NOT EXISTS idx_writer_title_person
    ON title_writer(title_id, person_id);

CREATE INDEX IF NOT EXISTS idx_principal_person
    ON title_principal(person_id,category,title_id);

CREATE INDEX IF NOT EXISTS idx_director_person
    ON title_director(person_id,title_id);

CREATE INDEX IF NOT EXISTS idx_writer_person
    ON title_writer(person_id,title_id);

CREATE INDEX IF NOT EXISTS idx_title_year
    ON title(start_year);

CREATE INDEX IF NOT EXISTS idx_rating_rank
    ON title_rating(average_rating DESC,num_votes DESC);

CREATE INDEX IF NOT EXISTS idx_title_genre_genre_title
    ON title_genre(genre, title_id);

CREATE INDEX IF NOT EXISTS idx_title_start_year
    ON title(start_year, id);

CREATE INDEX IF NOT EXISTS idx_title_rating_rank
    ON title_rating(
                    average_rating DESC,
                    num_votes DESC,
                    title_id
        );
