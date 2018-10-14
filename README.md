POST /api/login
    username:
    password:
    -> token in header
    Authorization: CHAT eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI3NzlkZjNkOC03NGQwLTQ4M2YtODlmNy05ZmY4ZTE5NTQwN2QiLCJzdWIiOiJhIiwiaWF0IjoxNTM5NDQ3NDM5LCJleHAiOjE1NDAwNTIyMzl9.RoxXnb38achZ6EjRwYKiYIcd35pac96w3NvFwQfZkhbqYh6C1z-9iqcuqLl_nDmF_I54soNPXSGZ16MMOHhsmA
    active: true/false (verify by email, true is default)

POST /api/signup
    username
    password
    displayName
    email
    -> success: true
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

PUT /api/changePassword
    token in Authorization header
    {
	"oldPassword": "aa",
	"newPassword": "a"
	}
	-> {
    "timestamp": 1539520526152,
    "status": 400,
    "error": "Bad Request",
    "message": "Mật khẩu cũ sai",
    "path": "/api/changePassword"
	}
	-> {"result":"success"}


    
            

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

