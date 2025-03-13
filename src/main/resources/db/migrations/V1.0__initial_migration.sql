CREATE SCHEMA IF NOT EXISTS url_shortener;

CREATE TABLE IF NOT EXISTS short_urls
(
    short_url     VARCHAR(255)            NOT NULL PRIMARY KEY,
    long_url      VARCHAR(1000)           NOT NULL
    );

CREATE UNIQUE INDEX IF NOT EXISTS short_url_index ON short_urls (short_url);