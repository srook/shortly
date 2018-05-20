# Shortly

URL shortener service

## Getting Started

After cloning repository executable jar file with all dependencies is available in target/ directory. If you wish to compile and run simply use Maven:

```
mvn spring-boot:run
```

### Using the application

Application exposes three REST endpoints: account, register and statistic.

#### POST /account
Endpoint used for registration of new users.
Required headers:
  - Content-Type: application/json
Content body must contain "AccountId" with desired username.
```
{ "AccountId": "shortly" }
```
If account generation was successful response containing password is sent.
```json
{
    "success": true,
    "description": "Successfully added account",
    "password": "KOgcbJiM"
}
```

#### POST /register
Endpoint for registering new URLs.
Required headers:
  - Content-Type: application/json
  - Authorization: Basic $credentials

Content body can contain:
  - url - URL to be shortend
  - redirectType - 301 or 302 HTTP status code (default: 302)

Response will contain short URL:
```json
{
    "success": true,
    "shortUrl": "http://shortly.net/Rgm8Bs"
}
```

#### GET /statistic/{AccountId}
Endpoint for retrieving statistics by user.
Required headers:
  - Authorization: Basic $credentials

Response will contain all registered URLs and their hit count:
```json
{
    "https://github.com/srook": 17,
    "https://google.com/someverylongquery": 3
}
```

## Running the tests

Running test is simple with Maven:

```
mvn test
```

## Authors

* **Saša Rošić** - https://github.com/srook

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
