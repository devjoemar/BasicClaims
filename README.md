# BasicClaims

POST - Create Claim
http://localhost:8080/api/v1/claims/createClaim

Request - application/JSON
```json
{
	"amount" : "123",
	"policyNumber": "1234",
	"dateCreated": "2020-12-20 12:10:00",
	"username": "doe",
	"docs": ["file1", "file2"]
}
```

Response 201 Created

```json
{
    "claimNumber": "-LF6gGklOENZkaVF-lWX3",
    "docs": [
        "file1",
        "file2"
    ],
    "amount": 123,
    "policyNumber": "1234",
    "username": "doe",
    "dateCreated": "2020-12-20 12:10:00",
    "_links": {
        "self": [
            {
                "href": "http://localhost:8080/api/v1/claims/getClaims",
                "type": "application/json"
            },
            {
                "href": "http://localhost:8080/api/v1/claims/updateClaim",
                "type": "application/json"
            },
            {
                "href": "http://localhost:8080/api/v1/claims/deleteClaim",
                "type": "application/json"
            }
        ]
    }
}
```

GET - Get Claims
http://localhost:8080/api/v1/claims/getClaims

Request - application/JSON
```json
{ "username": "doe"}
```

Response 200
```json
{
    "_embedded": {
        "claimEntityList": [
            {
                "claimNumber": "-LF6gGklOENZkaVF-lWX3",
                "docs": [
                    "file1",
                    "file2"
                ],
                "amount": 123.00,
                "policyNumber": "1234",
                "username": "doe",
                "dateCreated": "2020-12-20 12:10:00"
            }
        ]
    },
    "_links": {
        "self": [
            {
                "href": "http://localhost:8080/api/v1/claims/getClaims",
                "type": "application/json"
            },
            {
                "href": "http://localhost:8080/api/v1/claims/updateClaim",
                "type": "application/json"
            },
            {
                "href": "http://localhost:8080/api/v1/claims/deleteClaim",
                "type": "application/json"
            }
        ]
    }
}
```

PUT - Update Claim
http://localhost:8080/api/v1/claims/updateClaim

Request - application/JSON
```json
{
	"amount" : "999",
	"policyNumber": "1234",
	"dateCreated": "2020-12-21 12:10:00",
	"username": "doe",
	"docs": ["file101", "file200"]
}
```

Response 200
```json
{
    "claimNumber": "-LF6gGklOENZkaVF-lWX3",
    "docs": [
        "file101",
        "file200"
    ],
    "amount": 999,
    "policyNumber": "1234",
    "username": "doe",
    "dateCreated": "2020-12-21 12:10:00",
    "_links": {
        "self": [
            {
                "href": "http://localhost:8080/api/v1/claims/getClaims",
                "type": "application/json"
            },
            {
                "href": "http://localhost:8080/api/v1/claims/updateClaim",
                "type": "application/json"
            },
            {
                "href": "http://localhost:8080/api/v1/claims/deleteClaim",
                "type": "application/json"
            }
        ]
    }
}
```

DELETE - Delete Claim
http://localhost:8080/api/v1/claims/deleteClaim

Request - application/JSON
```json
{ "username": "doe", "claimNumber": "-LF6gGklOENZkaVF-lWX3" }
```

Response 200
Deleted

