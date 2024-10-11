CREATE TABLE pets
(
    id                  bigserial PRIMARY KEY,
    name                text,
    status              text,
    photo_url            text,
    created_at          TIMESTAMP WITH TIME ZONE,
    updated_at          TIMESTAMP WITH TIME ZONE
);
