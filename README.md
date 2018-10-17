# DONE:

### `POST /api/login?username=&password=`
    
```json
->  token in header
    Authorization: CHAT eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI3NzlkZjNkOC03NGQwLTQ4M2YtODlmNy05ZmY4ZTE5NTQwN2QiLCJzdWIiOiJhIiwiaWF0IjoxNTM5NDQ3NDM5LCJleHAiOjE1NDAwNTIyMzl9.RoxXnb38achZ6EjRwYKiYIcd35pac96w3NvFwQfZkhbqYh6C1z-9iqcuqLl_nDmF_I54soNPXSGZ16MMOHhsmA
    {"active": "true/false"} 
    (verify by email, true is default)
```

### `POST /api/signup?username=&password=&displayName=&email=`
```json
->  {"success": "true"}

->  {
        "timestamp": 1539447546025,
        "status": 409,
        "error": "Conflict",
        "message": "User đã tồn tại",
        "path": "/api/signup"
    }

->  {
        "timestamp": 1539447546025,
        "status": 520,
        "error": "Unknown Error",
        "message": "Đã có lỗi xảy ra",
        "path": "/api/signup"
    }
```
PUT /api/changePassword?oldPassword=&newPassword=
```
input:  token in Authorization header
```
```json
->  {
        "timestamp": 1539520526152,
        "status": 400,
        "error": "Bad Request",
        "message": "Mật khẩu cũ không đúng",
        "path": "/api/changePassword"
    }

->  {"result":"success"}
```

### `GET /api/user`
```json
[
    {
        "username": "a",
        "password": "$2a$10$gj4OtbyETCraxaVKBg5CXuyIxjOy93vo83nJCgnytwkv5BUHpjmi.",
        "displayName": "displayD",
        "email": "1611985@hcmut.edu.vn"
    }
]
```


# TO DO:

            

GET /api/verify?username= &hash=

GET /api/forget?username=
    -> newPassword

POST /api/changeAvatar
    token
    file:


    


GET /api/message/{conversationId}

GET /api/



GET /friends
    token
    ->  displayName, photoUrl
        Message: lastMessage
        conversationId
        online
(subcribe /topic/online before GET request)    



SEND /app/private/{conversationId}

SUBCRIBE /topic/{conversationId}



Message:
    type: JOIN, LEAVE, TEXT, IMAGE, FILE, CALL
    content: null/...
    datetime: 
    conversationId:
    fromUsername:

User:
    username
    photoUrl
    displayName
    username
    password
    email
    online

