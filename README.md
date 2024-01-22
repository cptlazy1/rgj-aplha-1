GET http://localhost:8080/admin/users


DELETE http://localhost:8080/admin/users/{{username}}


GET http://localhost:8080/users/{{username}}


GET http://localhost:8080/users/{{username}}/download-grp


GET http://localhost:8080/users/{{username}}/download-pp


POST http://localhost:8080/users/{{username}}/upload-grp


POST http://localhost:8080/users/{{username}}/upload-pp


GET http://localhost:8080/games


GET http://localhost:8080/users/{{username}}/games


GET http://localhost:8080/users/{{username}}/games/{{gameID}}/download-game-photo


POST http://localhost:8080/users/{{username}}/games/{{gameID}}/upload-game-photo


GET http://localhost:8080/users/{{username}}/games/{{id}}


DELETE http://localhost:8080/users/{{username}}/games/{{id}}


GET http://localhost:8080/game-systems


GET http://localhost:8080/users/{{username}}/game-systems


GET http://localhost:8080/users/{{username}}/game-systems/{{gameSystemID}}/download-game-system-photo


POST http://localhost:8080/users/{{username}}/game-systems/{{gameSystemID}}/upload-game-system-photo


GET http://localhost:8080/users/{{username}}/game-systems/{{id}}


DELETE http://localhost:8080/users/{{username}}/game-systems/{{id}}


PUT http://localhost:8080/users/{{username}}/change-email
{
          "newEmail" : ""
        }

PUT http://localhost:8080/users/{{username}}/change-password
{
          "oldPassword" : "",
          "newPassword" : ""
        }

POST http://localhost:8080/users/{{username}}/games
{
          "gameDto" : { },
          "gameConditionDto" : { }
        }

POST http://localhost:8080/users/{{username}}/game-systems
{
          "gameSystemDto" : { },
          "gameSystemConditionDto" : { }
        }

POST http://localhost:8080/authentication/login
{
          "username" : "",
          "password" : ""
        }

POST http://localhost:8080/authentication/register
{
          "username" : "",
          "password" : "",
          "email" : "",
          "isEnabled" : true
        }
