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

### `PUT /api/password?oldPassword=&newPassword=`
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
**For test only**

```json
->  [
        {
            "username": "a",
            "password": "$2a$10$gj4OtbyETCraxaVKBg5CXuyIxjOy93vo83nJCgnytwkv5BUHpjmi.",
            "displayName": "displayD",
            "email": "1611985@hcmut.edu.vn"
        }
    ]
```


### `GET /api/user?search=`
```
input:  token in Authorization header
```
```json
->  [
        {
            "username": "huynhha12798",
            "displayName": "Huynh Ha",
            "email": "huynhha12798@gmail.com",
            "avatarUrl": "default.jpg",
            "online": false
        }
    ]
```

### `POST /api/conversation`
```json
input:  token in Authorization header
{
    "listUsername": [
        "username1",
        "username2",
        "username2"
    ]
}
```          
```json
->  {
        "conversationId": "12345"
    }
```


### `GET /api/conversation`

```json
input: token in Authorization header
```

```json
[
    {
        "conversationId": "2",
        "conversationName": null,
        "lastMessage": {
            "messageId": "2",
            "type": "TEXT",
            "content": "2",
            "datetime": "2018-10-19",
            "conversationId": "2",
            "username": "b",
            "read": false
        },
        "listUser": [
            {
                "displayName": "displayD",
                "username": "a",
                "nickname": null,
                "avatarUrl": "default.jpg",
                "online": false
            },
            {
                "displayName": "b",
                "username": "c",
                "nickname": null,
                "avatarUrl": "default.jpg",
                "online": false
            }
        ],
        "notiCount": 1
    },
    {
        "conversationId": "3",
        "conversationName": null,
        "lastMessage": null,
        "listUser": [
            {
                "displayName": "displayD",
                "username": "a",
                "nickname": null,
                "avatarUrl": "default.jpg",
                "online": false
            },
            {
                "displayName": "b",
                "username": "d",
                "nickname": null,
                "avatarUrl": "default.jpg",
                "online": false
            }
        ],
        "notiCount": 0
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

