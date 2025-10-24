-- Initialize database for revenue calculator
CREATE DATABASE revenue_calculator;
CREATE USER revenue_user WITH PASSWORD 'revenue_pass';
GRANT ALL PRIVILEGES ON DATABASE revenue_calculator TO revenue_user;