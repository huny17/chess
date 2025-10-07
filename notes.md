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
DataAccess --> Service: UserData
Service --> Server: DoesNotExistException
Server --> Client: 500\n{"message": "Error: user not in database"}
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
Server -> Handler: {"authToken":" "}
Handler -> Service: logout(LogoutRequest)
Service -> DataAccess: getUser(username)
DataAccess -> db:Find AuthData by token
break Username and authToken don't match
DataAccess --> Service: AuthData
Service --> Server: NoMatchException
Server --> Client: 401\n{"message": "unauthorized"}
end
DataAccess -> db:Find UserData by username
break User not found
DataAccess --> Service: UserData
Service --> Server: UserDoesNotExistException
Server --> Client: 500\n{"message": "Error: User does not exist"}
end
DataAccess --> Service: UserData
Service -> DataAccess: RemoveAuth(authData)
DataAccess -> db: Remove AuthData
DataAccess --> Service: null
Service --> Handler: LogoutResult
Handler --> Server: {}
Server --> Client: 200\n{}
end

group#red #pink List Games
Client -> Server: [GET] /game\nauthToken
Server -> Handler: {"authToken":" "}
Handler -> Service: getGames(GamesRequest)
Service -> DataAccess: VerifyToken(UserData)
DataAccess -> db: Find UserData by Token
break AuthToken not valid
DataAccess --> Service: AuthData
Service --> Server: NotValidTokenException
Server --> Client: 401\n{"message": "unauthorized"}
end
DataAccess -> db: Find available games
break No Games Found
DataAccess --> Service: GameData
Service --> Server: NoGamesException
Server --> Client: 500\n{"message": "Error: No Games Found"}
end

DataAccess --> Service: GameData
Service --> Handler: GamesResult
Handler --> Server: { "games": [{"gameID" : " " ,  "whiteUsername" : " ", "blackUsername" : " " , "gameName" : " " }]}
Server --> Client: 200\n{ "games": [{"gameID" : " ", "whiteUsername" : " ", "blackUsername" : " ", "gameName" : " " } ]}
end

group#d790e0 #E3CCE6 Create Game
Client -> Server: [POST] /game\nauthToken\n{gameName}
Server -> Handler: {"authToken":" "}
Handler -> Service: CreateGame(CreateRequest)
Service -> DataAccess: CreateGame(CreateRequest)
DataAccess -> db: Create Game
break AuthToken not valid
DataAccess --> Service: AuthData
Service --> Server: NotValidTokenException
Server --> Client: 401\n{"message": "unauthorized"}
end
break No Games Found
DataAccess --> Service: GameData
Service --> Server: NoGamesException
Server --> Client: 500\n{"message": "Error: Could not create Game"}
end
DataAccess --> Service: null
Service -> DataAccess: returnGame(GameData)
DataAccess -> db: Add GameData
Service --> Handler: GameResult
Handler --> Server: { "gameID" : " " }
Server --> Client: 200\n{ "gameID" : " " }
end

group#yellow #lightyellow Join Game #black
Client -> Server: [PUT] /game\nauthToken\n{playerColor, gameID}
Server -> Handler: { "playerColor": " ", "gameID": " " }
Handler -> Service: JoinGame(JoinRequest)
Service -> DataAccess: JoinGame(JoinRequest)
DataAccess -> db: Join Game
break AuthToken not valid
DataAccess --> Service: AuthData
Service --> Server: NotValidTokenException
Server --> Client: 401\n{"message": "unauthorized"}
end
break Game Not Found
DataAccess --> Service: GameData
Service --> Server: GameNotFoundException
Server --> Client: 500\n{"message": "Error: Game Not Found"}
end
DataAccess --> Service: null
Service -> DataAccess: returnGame(GameData)
DataAccess -> db: Get GameData
Service --> Handler: GameResult
Handler --> Server: { "gameID" : " " }
Server --> Client: 200\n{}
end

group#gray #lightgray Clear application
Client -> Server: [DELETE] /db
Server -> Handler: {}
Handler -> Service: ClearGame(ClearRequest)
Service -> DataAccess: ClearGame(ClearRequest)
DataAccess -> db: Clear Game
break Game Not Found
DataAccess --> Service: GameData
Service --> Server: GameNotFoundException
Server --> Client: 500\n{"message": "Error: Game could not be cleared"}
end
DataAccess --> Service: null
Service --> Handler: GameResult
Handler --> Server: {}
Server --> Client: 200\n{}
end






