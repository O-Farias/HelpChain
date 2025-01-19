-- Inserir usuários
INSERT INTO users (id, name, email, password, role) VALUES (1, 'John Doe', 'john.doe@example.com', 'password123', 'USER');

-- Inserir campanhas 
INSERT INTO campaigns (id, title, description, goal_amount, current_amount, start_date, end_date, user_id) 
VALUES 
(1, 'Campanha 1', 'Descrição da campanha 1', 10000.00, 0.00, '2025-01-01', '2025-12-31', 1);

-- Inserir doações
INSERT INTO donations (id, amount, donation_date, user_id, campaign_id) 
VALUES 
(1, 100.00, '2025-01-19', 1, 1);
