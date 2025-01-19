CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE campaigns (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    goal_amount DECIMAL(15, 2) NOT NULL, 
    current_amount DECIMAL(15, 2) NOT NULL DEFAULT 0.00, 
    start_date DATE NOT NULL, 
    end_date DATE NOT NULL, 
    user_id BIGINT NOT NULL, 
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE donations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(15, 2) NOT NULL, 
    donation_date DATE NOT NULL, 
    user_id BIGINT NOT NULL, 
    campaign_id BIGINT NOT NULL, 
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (campaign_id) REFERENCES campaigns (id)
);
