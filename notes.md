# My notes

shortcuts
    edit variables = shift + f6
    multiple lines = shift + alt + click

# Phase 2
actor Client
participant Server
participant Handler
participant Service
participant DataAccess
database db

entryspacing 0.9
group#43829c #lightblue Registration
Client -> Server: [POST] /user\n{"username":" ", "password":" ", "email":" "}
Server -> Handler: {"username":" ", "password":" ", "email":" "}
Handler -> Service: register(RegisterRequest)
Service -> DataAccess: getUser(username)
DataAccess -> db:Find UserData by username
break User with username already exists
DataAccess --> Service: UserData
Service --> Server: AlreadyTakenException
Server --> Client: 403\n{"message": "Error: username already taken"}
end
DataAccess --> Service: null
Service -> DataAccess:createUser(userData)
DataAccess -> db:Add UserData
Service -> DataAccess:createAuth(authData)
DataAccess -> db:Add AuthData
Service --> Handler: RegisterResult
Handler --> Server: {"username" : " ", "authToken" : " "}
Server --> Client: 200\n{"username" : " ", "authToken" : " "}
end

group#orange #FCEDCA Login
Client -> Server: [POST] /session\n{username, password}
Server -> Handler: {"username":" ", "password":" "}
Handler -> Service: login(LoginRequest)
Service -> DataAccess: getUser(username)
DataAccess -> db:Find UserData by username
break User with username does not exist
DataAccess --> Service: UserData
Service --> Server: DoesNotExistException
Server --> Client: 500\n{"message": "Error: user not in database"}
end
break Username or passward incorrect
DataAccess --> Service: UserData
Service --> Server: IncorrectUserException
Server --> Client: 401\n{"message": "Error: unauthorized"}
end
DataAccess --> Service: null
DataAccess -> db:grab UserData
Service -> DataAccess:createAuth(authData)
DataAccess -> db:Add AuthData
Service --> Handler: LoginResult
Handler --> Server: {"username" : " ", "authToken" : " "}
Server --> Client: 200\n{"username" : " ", "authToken" : " "}
end

group#green #lightgreen Logout
Client -> Server: [DELETE] /session\nauthToken
Server -> Handler: {"username":" ", "authToken":" "}
Handler -> Service: logout(LogoutRequest)
Service -> DataAccess: getUser(username)
DataAccess -> db:Find UserData by username
break Username and authToken don't match
DataAccess --> Service: UserData
Service --> Server: NoMatchException
Server --> Client: 401\n{"message": "unauthorized"}
end
break User not found
DataAccess --> Service: UserData
Service --> Server: UserDoesNotExistException
Server --> Client: 500\n{"message": "Error: User does not exist"}
end
DataAccess --> Service: null
Service -> DataAccess:returnUser(userData)
DataAccess -> db:Clean UserData
Service -> DataAccess:removeAuth(authData)
DataAccess -> db:Remove AuthData
Service --> Handler: LogoutResult
Handler --> Server: {"username" : " ", "authToken" : " "}
Server --> Client: 200\n{"username" : " ", "message" : "succesfully logged out"}
end

group#red #pink List Games
Client -> Server: [GET] /game\nauthToken
Server -> Handler: {"authToken":" "}
Handler -> Service: getGames(GamesRequest)
Service -> DataAccess: getGames(GamesRequest)
DataAccess -> db: Find available games
break AuthToken not valid
DataAccess --> Service: AuthToken
Service --> Server: NotValidTokenException
Server --> Client: 401\n{"message": "unauthorized"}
end
break User not found
DataAccess --> Service: UserData
Service --> Server: UserDoesNotExistException
Server --> Client: 500\n{"message": "Error: User does not exist"}
end
DataAccess --> Service: null
Service -> DataAccess:returnGames(GamesData)
DataAccess -> db:Get GamesData
Service --> Handler: GamesResult
Handler --> Server: { "games": [{"gameID" : " " ,  "whiteUsername" : " ", "blackUsername" : " " , "gameName" : " " }]}
Server --> Client: 200\n{ "games": [{"gameID" : " ", "whiteUsername" : " ", "blackUsername" : " ", "gameName" : " " } ]}
end

group#d790e0 #E3CCE6 Create Game
Client -> Server: [POST] /game\nauthToken\n{gameName}
end

group#yellow #lightyellow Join Game #black
Client -> Server: [PUT] /game\nauthToken\n{playerColor, gameID}
end

group#gray #lightgray Clear application
Client -> Server: [DELETE] /db
end
