# Spring Webflux & Spring Security reactive rest api server

Basic reactive example which integrates Spring webflux and Spring security 5 to provide end to end non blocking rest server.

Includes reactive drivers for H2 and Redis to complete the reactive end to end flow.

Service to service calls  uses non blocking Spring Webclient instead of RestTemplate

## Start redis docker image

```bash
docker run -d --restart=always --name redis -p 6379:6379 redis
```

## Custom security token

Demo includes validating a custom security token from header with

- POST user - allowed only for ADMIN role (authnToken=admintoken)
- GET users - allowed only for ADMIN or USER role (authnToken=admintoken/usertoken)

This dummy token validation can be replaced with a real token validation logic.

### Sample curls

#### 200 OK - Get Users

```bash
curl --request GET \
  --url http://localhost:8181/server/v1/app/users \
  --header 'authntoken: usertoken'
```
Output

```bash
[
  {
    "name": "user1",
    "department": "dept1"
  },
  {
    "name": "user2",
    "department": "dept2"
  }
]
```

#### 200 Ok - Create User

```bash
curl --request POST \
  --url http://localhost:8181/server/v1/app/user \
  --header 'authntoken: admintoken' \
  --header 'content-type: application/json' \
  --data '{
	"name":"user3",
	"department":"dept3"
}'
```

Output 

```bash
{
  "name": "user3",
  "department": "dept3"
}
```

#### 403 response

```bash
curl --request POST \
  --url http://localhost:8181/server/v1/app/user \
  --header 'authntoken: usertoken' \
  --header 'content-type: application/json' \
  --data '{
	"name":"user3",
	"department":"dept3"
}'
```

Output

```bash
{
  "code": 403,
  "message": "Access Denied"
}
```

