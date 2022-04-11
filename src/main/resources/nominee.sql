CREATE TABLE nominee
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    year      INT                   NULL,
    title     VARCHAR(255)          NULL,
    studios   VARCHAR(255)          NULL,
    producers VARCHAR(255)          NULL,
    winner    VARCHAR(255)          NULL,
    CONSTRAINT pk_nominee PRIMARY KEY (id)
);
