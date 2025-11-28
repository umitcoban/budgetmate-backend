-- =============== Spring Modulith Event Publication ===============
CREATE TABLE IF NOT EXISTS event_publication
(
    id                     UUID PRIMARY KEY,
    completion_date        TIMESTAMPTZ,
    event_type             VARCHAR(512) NOT NULL,
    listener_id            VARCHAR(512) NOT NULL,
    publication_date       TIMESTAMPTZ NOT NULL,
    serialized_event       VARCHAR(4000) NOT NULL,
    status                 VARCHAR(20),
    completion_attempts    INT,
    last_resubmission_date TIMESTAMPTZ
);

CREATE INDEX IF NOT EXISTS event_publication_listener_event_idx
    ON event_publication (listener_id, serialized_event);

CREATE INDEX IF NOT EXISTS event_publication_completion_date_idx
    ON event_publication (completion_date);

-- =============== User Profiles ===============
CREATE TABLE IF NOT EXISTS user_profiles
(
    id          BIGSERIAL PRIMARY KEY,
    email       VARCHAR(255) NOT NULL UNIQUE,
    full_name   VARCHAR(255) NOT NULL,
    created_at  TIMESTAMPTZ  NOT NULL,
    updated_at  TIMESTAMPTZ  NOT NULL
);
