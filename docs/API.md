# Global Revenue Calculator API Documentation

## Overview
The Global Revenue Calculator provides REST APIs for calculating revenue attribution across business units and clients.

## Base URL
```
http://localhost:8080/api/revenue
```

## Authentication
The API uses HTTP Basic Authentication. Include credentials in the Authorization header.

## Endpoints

### Calculate Revenue Attribution
**POST** `/calculate`

Calculate revenue attribution for a single transaction.

**Request Body:**
```json
{
  "transactionId": "TXN-001",
  "businessUnitId": "550e8400-e29b-41d4-a716-446655440000",
  "clientId": "550e8400-e29b-41d4-a716-446655440001",
  "amount": 1000.00,
  "currencyCode": "USD",
  "transactionType": "SALE"
}
```

**Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440002",
  "transactionId": "TXN-001",
  "totalAmount": 1000.00,
  "attributions": [250.00, 150.00, 100.00],
  "calculatedAt": "2024-01-15T10:30:00"
}
```

### Batch Calculate Revenue
**POST** `/calculate/batch`

Process multiple revenue calculations concurrently.

**Request Body:**
```json
[
  {
    "transactionId": "TXN-001",
    "businessUnitId": "550e8400-e29b-41d4-a716-446655440000",
    "clientId": "550e8400-e29b-41d4-a716-446655440001",
    "amount": 1000.00,
    "currencyCode": "USD",
    "transactionType": "SALE"
  }
]
```

### Get Revenue by Business Unit
**GET** `/business-unit/{businessUnitId}`

**Parameters:**
- `startDate`: ISO datetime (required)
- `endDate`: ISO datetime (required)

**Response:**
```json
15000.00
```

### Get Revenue by Client
**GET** `/client/{clientId}`

**Parameters:**
- `startDate`: ISO datetime (required)
- `endDate`: ISO datetime (required)

**Response:**
```json
25000.00
```

## Error Responses

### Validation Error (400)
```json
{
  "code": "VALIDATION_ERROR",
  "message": "Validation failed",
  "details": {
    "amount": "Amount must be positive"
  },
  "timestamp": "2024-01-15T10:30:00"
}
```

### Internal Error (500)
```json
{
  "code": "INTERNAL_ERROR",
  "message": "Revenue calculation failed",
  "details": null,
  "timestamp": "2024-01-15T10:30:00"
}
```

## Rate Limits
- 1000 requests per minute per client
- Batch operations limited to 100 transactions per request

## Monitoring
- Health check: `GET /actuator/health`
- Metrics: `GET /actuator/prometheus`