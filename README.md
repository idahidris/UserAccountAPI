# UserAccount REST API 

 a simple REST API backend with spring boot.

The entire application is contained within the `UserAccountAPI` folder.

`application.properties` contains minimal  configuration for the application.

`mvn clean test` runs a simplistic test and
`mvn jacoco:report`  generates the API coverage reports.


## Install

    mvn clean package

## Run the app

    mvn spring-boot:run

## Run the tests

    mvn test

# REST API

The REST API to the example app is described below.

## Get list of All UserAccount with pagination

### Request

`GET /users`

    curl --location --request GET 'http://0.0.0.0:8080/api/v1/users?size=100&page=0'

### Response

    HTTP/1.1 200 OK
 
    {
        "responseCode": "00",
        "errors": null,
        "data": [
            {
                "id": 27,
                "title": "Mrs",
                "firstname": "Ubong2",
                "lastname": "Idah1",
                "email": "idris4real@gmail.com",
                "mobile": "08030816765",
                "password": "password2$",
                "role": {
                    "id": 1,
                    "name": "ADMIN"
                },
                "registered": "2021-04-03 11:51:24",
                "verified": true,
                "verifiedDate": "2021-04-05 09:53:33",
                "deactivatedDate": "2021-04-05 09:53:06",
                "status": {
                    "id": 2,
                    "name": "VERIFIED"
                },
                "verificationCode": "c7bc97c0-f770-45ce-9565-dda7bd33ab77"
            }
        ]
    }

## Create a new user

### Request

`POST /user`

    curl --location --request POST 'http://0.0.0.0:8080/api/v1/user' \
     --header 'Content-Type: application/json' \
     --data-raw '{
             "title": "Mrs",
             "firstname": "Ubong2",
             "lastname": "Idah1",
             "email": "idris4real@gmail.com",
             "mobile": "08030816765",
             "password": "password2$",
             "role": 1,
             "registered": "2021-04-03 10:51:24",
             "verified": 1,
             "verifiedDate": "2021-04-03 10:51:24",
             "deactivatedDate": "2021-04-03 10:51:24",
             "status": 2
        
     }'

### Response

    HTTP/1.1 200 Created

    {
        "responseCode": "00",
        "errors": null,
        "data": {
            "id": 28,
            "title": "Mrs",
            "firstname": "Ubong2",
            "lastname": "Idah1",
            "email": "idris4real1@gmail.com",
            "mobile": "08030816765",
            "password": "password2$",
            "role": {
                "id": 1,
                "name": "ADMIN"
            },
            "registered": "2021-04-03 11:51:24",
            "verified": false,
            "verifiedDate": "2021-04-03 11:51:24",
            "deactivatedDate": "2021-04-03 11:51:24",
            "status": {
                "id": 1,
                "name": "REGISTERED"
            },
            "verificationCode": "0b3dd3ed-f6bb-473e-82dc-cf456e8c2fd5"
        }
    }



### Request

`GET /user/id`

    curl --location --request GET 'http://0.0.0.0:8080/api/v1/user/4'

### Response

    HTTP/1.1 200 OK
  

    {
        "responseCode": "00",
        "errors": null,
        "data": {
            "id": 4,
            "title": "Mrs",
            "firstname": "Ubong",
            "lastname": "Idah",
            "email": null,
            "mobile": "08030816765",
            "password": "password2$",
            "role": {
                "id": 1,
                "name": "ADMIN"
            },
            "registered": "2021-04-03 11:51:24",
            "verified": true,
            "verifiedDate": "2021-04-03 11:51:24",
            "deactivatedDate": "2021-04-03 11:51:24",
            "status": {
                "id": 3,
                "name": "DEACTIVATED"
            }
        }
    }

## Get a non-existent user

### Request

`GET /user/id`

    curl --location --request GET 'http://0.0.0.0:8080/api/v1/user/1000'

### Response

    HTTP/1.1 200 OK

    {
        "responseCode": "00",
        "errors": null,
        "data": null
    }



## Change a user's state

### Request

`PUT /user/:id`

    curl --location --request PUT 'http://0.0.0.0:8080/api/v1/user/7' \
     --header 'Content-Type: application/json' \
     --data-raw '{
             "title": "Mrs",
             "firstname": "Ubong",
             "lastname": "Idah",
             "email": "idris4real555@gmail.com",
             "mobile": "08030816765",
             "password": "password2$",
             "role": 2,
             "registered": "2021-04-03 10:51:24",
             "verified": 1,
             "verifiedDate": "2021-04-03 10:51:24",
             "deactivatedDate": "2021-04-03 10:51:24",
             "status": 1
        
     }'

### Response

    HTTP/1.1 200 OK


    {
        "responseCode": "00",
        "errors": null,
        "data": {
            "id": 7,
            "title": "Mrs",
            "firstname": "Ubong",
            "lastname": "Idah",
            "email": "idris4real555@gmail.com",
            "mobile": "08030816765",
            "password": "password2$",
            "role": {
                "id": 2,
                "name": "USER"
            },
            "registered": "2021-04-03 11:51:24",
            "verified": true,
            "verifiedDate": "2021-04-03 11:51:24",
            "deactivatedDate": "2021-04-03 11:51:24",
            "status": {
                "id": 1,
                "name": "REGISTERED"
            },
            "verificationCode": null
        }
    }

#


## Attempt to change a user using invalid params

### Request

`PUT /user/:id`

    curl --location --request PUT 'http://0.0.0.0:8080/api/v1/user/1000' \
     --header 'Content-Type: application/json' \
     --data-raw '{
             "title": "Mrs",
             "firstname": "Ubong",
             "lastname": "Idah",
             "email": "idris4real555@gmail.com",
             "mobile": "08030816765",
             "password": "password2$",
             "role": 2,
             "registered": "2021-04-03 10:51:24",
             "verified": 1,
             "verifiedDate": "2021-04-03 10:51:24",
             "deactivatedDate": "2021-04-03 10:51:24",
             "status": 1
        
     }'

### Response

    HTTP/1.1 200 OK
 

    {
        "responseCode": "00",
        "errors": null,
        "data": [
            "This user does not exists:1000"
        ]
    }






## verify

### Request

`POST /verify/code`

    curl --location --request POST 'http://0.0.0.0:8080/api/v1/verify?code=c7bc97c0-f770-45ce-9565-dda7bd33ab77'

### Response

    HTTP/1.1 200 OK
  

    
        {
            "responseCode": "00",
            "errors": null,
            "data": {
                "id": 27,
                "title": "Mrs",
                "firstname": "Ubong2",
                "lastname": "Idah1",
                "email": "idris4real@gmail.com",
                "mobile": "08030816765",
                "password": "password2$",
                "role": {
                    "id": 1,
                    "name": "ADMIN"
                },
                "registered": "2021-04-03 11:51:24",
                "verified": true,
                "verifiedDate": "2021-04-05 09:53:33",
                "deactivatedDate": "2021-04-05 09:53:06",
                "status": {
                    "id": 2,
                    "name": "VERIFIED"
                },
                "verificationCode": "c7bc97c0-f770-45ce-9565-dda7bd33ab77"
            }
        }
    

## Try to delete a user

### Request

`DELETE /user/id`

    curl --location --request DELETE 'http://0.0.0.0:8080/api/v1/user/27'

### Response

    HTTP/1.1 200 OK
  

    {
        "responseCode": "00",
        "errors": null,
        "data": {
            "id": 27,
            "title": "Mrs",
            "firstname": "Ubong2",
            "lastname": "Idah1",
            "email": "idris4real@gmail.com",
            "mobile": "08030816765",
            "password": "password2$",
            "role": {
                "id": 1,
                "name": "ADMIN"
            },
            "registered": "2021-04-03 11:51:24",
            "verified": true,
            "verifiedDate": "2021-04-05 09:53:33",
            "deactivatedDate": "2021-04-05 10:42:11",
            "status": {
                "id": 3,
                "name": "DEACTIVATED"
            },
            "verificationCode": "c7bc97c0-f770-45ce-9565-dda7bd33ab77"
        }
    }
