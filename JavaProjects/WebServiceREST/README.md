# Uninorte2019-2-movil
Api para el curso de de programacion movil usando android estudio!


## Controladores
* User Controller
* Location Controller
* Message Controller
* Util Controller

### User Controller Methods
* [GET METHOD] @ (http://ip:8080/MovilAPI/api/users) - Este metodo retorna todos los usuarios.
* [POST METHOD] @ (http://ip:8080/MovilAPI/api/users) - This method is used to register users.<br/>
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
