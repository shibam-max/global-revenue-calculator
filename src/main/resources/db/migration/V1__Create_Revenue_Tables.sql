-- Create business_units table
CREATE TABLE business_units (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    unit_code VARCHAR(50) NOT NULL UNIQUE,
    unit_name VARCHAR(255) NOT NULL,
    revenue_share_percentage DECIMAL(5,2) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Create revenue_transactions table
CREATE TABLE revenue_transactions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    transaction_id VARCHAR(255) NOT NULL UNIQUE,
    business_unit_id UUID NOT NULL,
    client_id UUID NOT NULL,
    revenue_amount DECIMAL(19,2) NOT NULL,
    currency_code VARCHAR(3) NOT NULL,
    transaction_type VARCHAR(50) NOT NULL,
    transaction_date TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    processed_at TIMESTAMP,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING'
);

-- Create indexes for performance
CREATE INDEX idx_business_unit ON revenue_transactions(business_unit_id);
CREATE INDEX idx_client ON revenue_transactions(client_id);
CREATE INDEX idx_transaction_date ON revenue_transactions(transaction_date);
CREATE INDEX idx_status ON revenue_transactions(status);

-- Insert sample business units
INSERT INTO business_units (unit_code, unit_name, revenue_share_percentage) VALUES
('SALES', 'Sales Department', 40.00),
('MARKETING', 'Marketing Department', 25.00),
('SUPPORT', 'Customer Support', 20.00),
('OPERATIONS', 'Operations Department', 15.00);