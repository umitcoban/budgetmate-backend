CREATE TABLE IF NOT EXISTS incomes
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT        NOT NULL,
    amount      NUMERIC(19,2) NOT NULL,
    currency    VARCHAR(3)    NOT NULL,
    source      VARCHAR(100)  NOT NULL,
    description VARCHAR(500),
    income_date DATE          NOT NULL,
    created_at  TIMESTAMPTZ   NOT NULL,
    updated_at  TIMESTAMPTZ   NOT NULL
    );

CREATE INDEX IF NOT EXISTS idx_incomes_user_id
    ON incomes (user_id);

CREATE INDEX IF NOT EXISTS idx_incomes_user_date
    ON incomes (user_id, income_date);
