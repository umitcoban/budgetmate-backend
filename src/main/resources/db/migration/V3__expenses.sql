CREATE TABLE IF NOT EXISTS expenses
(
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT       NOT NULL,
    amount       NUMERIC(19,2) NOT NULL,
    currency     VARCHAR(3)   NOT NULL,
    category     VARCHAR(100) NOT NULL,
    description  VARCHAR(500),
    expense_date DATE         NOT NULL,
    created_at   TIMESTAMPTZ  NOT NULL,
    updated_at   TIMESTAMPTZ  NOT NULL
    );

CREATE INDEX IF NOT EXISTS idx_expenses_user_id
    ON expenses (user_id);

CREATE INDEX IF NOT EXISTS idx_expenses_user_date
    ON expenses (user_id, expense_date);
