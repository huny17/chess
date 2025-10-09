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
Service -> DataAccess: createUser(userData)
DataAccess -> db: Add UserData
Service -> DataAccess: createAuth(authData)
DataAccess -> db: Add AuthData
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
DataAccess --> Service: null
Service --> Server: DoesNotExistException
Server --> Client: 400\n{"message": "Error: user not in database"}
end
break Username or passward incorrect
DataAccess --> Service: UserData
Service --> Server: IncorrectUserException
Server --> Client: 401\n{"message": "Error: unauthorized"}
end
DataAccess --> Service: UserData
Service -> DataAccess: createAuth(authData)
DataAccess -> db: Add AuthData
Service --> Handler: LoginResult
Handler --> Server: {"username" : " ", "authToken" : " "}
Server --> Client: 200\n{"username" : " ", "authToken" : " "}
end

group#green #lightgreen Logout
Client -> Server: [DELETE] /session\nauthToken
Server -> Handler: authToken
Handler -> Service: logout(LogoutRequest)
Service -> DataAccess: getAuth(token)
DataAccess -> db:Find AuthData by token
break authToken does not exist
DataAccess --> Service: null
Service --> Server: NoTokenException
Server --> Client: 401\n{"message": "unauthorized"}
end
Service -> DataAccess: RemoveAuth(authData)
DataAccess -> db: Remove token from AuthData
DataAccess --> Service: null
Service --> Handler: LogoutResult
Handler --> Server: {}
Server --> Client: 200\n{}
end

group#red #pink List Games
Client -> Server: [GET] /game\nauthToken
Server -> Handler: authToken
Handler -> Service: getGames(GamesRequest)
Service -> DataAccess: VerifyToken(token)
DataAccess -> db: Find AuthData by Token
break AuthToken not valid
DataAccess --> Service: null
Service --> Server: NotValidTokenException
Server --> Client: 401\n{"message": "unauthorized"}
end
Service -> DataAccess: getGames()
DataAccess -> db: Find all games
DataAccess --> Service: GameData(list)
Service --> Handler: GamesResult(list)
Handler --> Server: { "games": [{"gameID" : "1234" ,  "whiteUsername" : "Alec", "blackUsername" : "Emma" , "gameName" : "24" }]}
Server --> Client: 200\n{ "games": [{"gameID" : "1234", "whiteUsername" : "Alec", "blackUsername" : "Emma", "gameName" : "24" } ]}
end

group#d790e0 #E3CCE6 Create Game
Client -> Server: [POST] /game\nauthToken\n{gameName}
Server -> Handler: authToken, {"gameName": " "}
Handler -> Service: CreateGame(CreateRequest)
Service -> DataAccess: VerifyToken(token)
DataAccess -> db: Find AuthData by Token
break AuthToken not valid
DataAccess --> Service: null
Service --> Server: NotValidTokenException
Server --> Client: 401\n{"message": "unauthorized"}
end
Service -> DataAccess: CreateGame(CreateRequest)
DataAccess -> db: Add GameData
break No Games Found
DataAccess --> Service: null
Service --> Server: NoGamesException
Server --> Client: 500\n{"message": "Error: Could not create Game"}
end
DataAccess --> Service: GameData
Service --> Handler: GameResult
Handler --> Server: { "gameID" : "1234" }
Server --> Client: 200\n{ "gameID" : "1234" }
end

group#yellow #lightyellow Join Game #black
Client -> Server: [PUT] /game\nauthToken\n{playerColor, gameID}
Server -> Handler: authToken, { "playerColor": " ", "gameID": " " }
Handler -> Service: JoinGame(JoinRequest)
Service -> DataAccess: VerifyToken(token)
DataAccess -> db: Find AuthData by Token
break AuthToken not valid
DataAccess --> Service: null
Service --> Server: NotValidTokenException
Server --> Client: 401\n{"message": "unauthorized"}
end
Service -> DataAccess: JoinGame(JoinRequest)
DataAccess -> db: Get GameData
break Team color not available
DataAccess --> Service: GameData
Service --> Server: Team color not available
Server --> Client: 403\n{"message": "unauthorized"}
end
break Game Not Found
DataAccess --> Service: null
Service --> Server: GameNotFoundException
Server --> Client: 400\n{"message": "Error: Game Not Found"}
end
DataAccess -> db: Join Game
DataAccess --> Service: GameData
Service --> Handler: GameResult
Handler --> Server: {}
Server --> Client: 200\n{}
end

group#gray #lightgray Clear application
Client -> Server: [DELETE] /db
Server -> Handler: {}
Handler -> Service: ClearGame(ClearRequest)
Service -> DataAccess: ClearGame(ClearRequest)
DataAccess -> db: Clear GameData
DataAccess -> db: Clear UserData
DataAccess -> db: Clear AuthData
break SQL Issue
DataAccess --> Service: void
Service --> Server: SQLIssueException
Server --> Client: 500\n{"message": "Error: Game could not be cleared"}
end
DataAccess --> Service: null
Service --> Handler: ClearResult
Handler --> Server: {}
Server --> Client: 200\n{}
end






