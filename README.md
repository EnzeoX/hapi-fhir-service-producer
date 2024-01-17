[![Build](https://github.com/EnzeoX/hapi-fhir-service-producer/actions/workflows/build.yml/badge.svg?branch=dev)](https://github.com/EnzeoX/hapi-fhir-service-producer/actions/workflows/build.yml)

## HAPI-FHIR Producer API

Simple API which sends processed bundles of type "Encounter" to Kafka server

## API Reference

### Test method (Allowed to all users)

```http
  GET /api/v1
```

Response with just simple `"Working"` message.

### Registration of new user (Allowed to all users)

```http
  POST /api/v1/authentication/register
```
#### Body

```json
    {
        "username": STRING,
        "password": STRING
    }
```

`username` - Required, should be unique; `passowrd` - Required

#### Response - json with JWT token

```json
    {
        "token": STRING
    }
```
On success - json response with `token` attribute value of JWT token.

### User authentication (Allowed to all users)

```http
  POST /api/v1/authentication/auth
```
#### Body

```json
    {
      "username": STRING,
      "password": STRING
    }
```

`username` - Required; `password` - Required

#### Response - json with JWT token

```json
    {
        "token": STRING
    }
```

On success - json response with `token` attribute value of JWT token.


