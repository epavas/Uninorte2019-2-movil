# Uninorte2019-2-movil
Api para el curso de de programacion movil usando android estudio!


## Controladores
* User Controller
* Location Controller
* Message Controller
* Util Controller

### User Controller Methods
* [GET METHOD] @ (http://localhost:8080/WebServiceREST/resources/users) - Este metodo retorna todos los usuarios.
* [GET METHOD] @ (http://localhost:8080/WebServiceREST/resources/users/pw) - Este metodo retorna todos los usuarios y sus contraceñas.
* [GET METHOD] @ (http://localhost:8080/WebServiceREST/resources/users/{user}/{password}) - Retorna usuario sin sus contraceña.
* [GET METHOD] @ (http://localhost:8080/WebServiceREST/resources/users/{user}) - Retorna usuario sin sus contraceña.
* [GET METHOD] @ (http://localhost:8080/WebServiceREST/resources/users/ps/{user}) - Retorna usuario y su contraceña.
* [POST METHOD] @ (http://ip:8080/MovilAPI/api/users) - Este metodo es usado para registrar udsuarios.<br/>
**The body should look like:**
```
{
	"username": "Elvis",
	"password": "1234",
	"first_name": "Elvis",
	"last_name": "Pavas",
	"full_name": "Elvis Pavas",
	"email": "epavas@uninorte.edu.co",
	"lastLat": "9.963889",
	"lastLon": "-74.796387",
	"status": "online",
	"lastSeen": "Sep 29, 2019 6:21:32 AM"
}
```
* [PUT METHOD] @ (http://ip:8080/WebServiceREST/resources/users/{username}) - Actualiza el usuario username.<br/>
**The body should look like:**
```
{
	"username": "Elvis",
	"password": "1235",
	"first_name": "Elvis",
	"last_name": "Pavas",
	"full_name": "Elvis Pavas",
	"email": "epavas@uninorte.edu.co",
	"lastLat": "9.963889",
	"lastLon": "-74.796387",
	"status": "online",
	"lastSeen": "Sep 29, 2019 6:21:32 AM"
}
```
* [PUT METHOD] @ (http://ip:8080/WebServiceREST/resources/users/pw/{username}) - Actualiza el usuario username y contraceña.<br/>
**The body should look like:**
```
{
	"username": "Elvis",
	"password": "1235",
	"first_name": "Elvis",
	"last_name": "Pavas",
	"full_name": "Elvis Pavas",
	"email": "epavas@uninorte.edu.co",
	"lastLat": "9.963889",
	"lastLon": "-74.796387",
	"status": "online",
	"lastSeen": "Sep 29, 2019 6:21:32 AM"
}
```
* [DELETE METHOD] @ (http://ip:8080/WebServiceREST/resources/users/{username}) - Elimina  el usuario username.<br/>

### Location Controller Methods
* [GET METHOD] @ (http://localhost:8080/WebServiceREST/resources/location) - Este metodo retorna las ubicaciones de los usuarios.
* [POST METHOD] @ (http://ip:8080/MovilAPI/api/users) - Este metodo actualiza la ubicacion del usuario .<br/>
  ```
  {
    "username": "Elvis",
    "full_name": "Elvis",
    "lat": 10.963889,
    "lon": -74.796387,
    "lastSeen": "2019-09-29 01:20:00.000",
    "status": "online"
  }
  ```
