# Uninorte2019-2-movil
Api para el curso de de programacion movil usando android estudio!

## Modelo [ver carpeta](https://github.com/epavas/Uninorte2019-2-movil/tree/master/JavaProjects/WebServiceREST/WebServiceREST/src/main/java/com/server/objects)
Carpeta de objetos [objetos](https://github.com/epavas/Uninorte2019-2-movil/tree/master/JavaProjects/WebServiceREST/WebServiceREST/src/main/java/com/server/objects)
* User [Ver Objeto](https://github.com/epavas/Uninorte2019-2-movil/blob/master/JavaProjects/WebServiceREST/WebServiceREST/src/main/java/com/server/objects/User.java)
* Message [Ver Objeto](https://github.com/epavas/Uninorte2019-2-movil/blob/master/JavaProjects/WebServiceREST/WebServiceREST/src/main/java/com/server/objects/Message.java)
* Location [Ver Objeto](https://github.com/epavas/Uninorte2019-2-movil/blob/master/JavaProjects/WebServiceREST/WebServiceREST/src/main/java/com/server/objects/Location.java)
* CurrentLocation [Ver Objeto](https://github.com/epavas/Uninorte2019-2-movil/blob/master/JavaProjects/WebServiceREST/WebServiceREST/src/main/java/com/server/objects/CurrentLocation.java)

## Controladores [Ver carpeta](https://github.com/epavas/Uninorte2019-2-movil/tree/master/JavaProjects/WebServiceREST/WebServiceREST/src/main/java/com/mycompany/webservicerest)
* User Controller [ver Objeto](https://github.com/epavas/Uninorte2019-2-movil/blob/master/JavaProjects/WebServiceREST/WebServiceREST/src/main/java/com/mycompany/webservicerest/UsersResource.java)
* Location Controller [ver Objeto](https://github.com/epavas/Uninorte2019-2-movil/blob/master/JavaProjects/WebServiceREST/WebServiceREST/src/main/java/com/mycompany/webservicerest/LocationResource.java)
* Message Controller [ver Objeto](https://github.com/epavas/Uninorte2019-2-movil/blob/master/JavaProjects/WebServiceREST/WebServiceREST/src/main/java/com/mycompany/webservicerest/MessagesResource.java)
* Util Controller [ver Objeto]()

### User Controller Methods [ver Carpeta](https://github.com/epavas/Uninorte2019-2-movil/blob/master/JavaProjects/WebServiceREST/WebServiceREST/src/main/java/com/mycompany/webservicerest/UsersResource.java)
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
	"lastSeen": "2019-09-29 01:20:00.00"
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
	"lastSeen": "2019-09-29 01:20:00.00"
}
```
* [DELETE METHOD] @ (http://ip:8080/WebServiceREST/resources/users/{username}) - Elimina  el usuario username.<br/>

### Location Controller Methods [ver Objeto](https://github.com/epavas/Uninorte2019-2-movil/blob/master/JavaProjects/WebServiceREST/WebServiceREST/src/main/java/com/mycompany/webservicerest/LocationResource.java)
* [GET METHOD] @ (http://localhost:8080/WebServiceREST/resources/location) - Este metodo retorna las ubicaciones de los usuarios.
* [POST METHOD] @ (http://localhost:8080/WebServiceREST/resources/location) - Este metodo actualiza la ubicacion del usuario .<br/>
  ```
  {
    "username": "Elvis",
    "full_name": "Elvis",
    "lat": "10.963889",
    "lon": "-74.796387",
    "lastSeen": "2019-09-29 01:20:00.000",
    "status": "online"
  }
  ```
* [GET METHOD] @ (http://localhost:8080/WebServiceREST/resources/location/{username}) - Este metodo retorna las ubicaciones de un usuario.
* [POST METHOD] @ (http://localhost:8080/WebServiceREST/resources/location/{username}) - Este metodo retorna las ubicaciones de un usuario entre dos fechas, recordar (initialDate, lastDate] acotado de esa forma.
  ```
  {
    "first_Date": "2019-09-29 01:20:00.000",
    "last_Date": "2019-09-29 01:20:00.000"
  }
  ```
  ### Message Controller Methods [ver Objeto](https://github.com/epavas/Uninorte2019-2-movil/blob/master/JavaProjects/WebServiceREST/WebServiceREST/src/main/java/com/mycompany/webservicerest/MessagesResource.java)
* [GET METHOD] @ (http://localhost:8080/WebServiceREST/resources/messages) - Este metodo retorna todos los mensajes de los usuarios.
* [POST METHOD] @ (http://localhost:8080/WebServiceREST/resources/messages) - Este metodo envia un mensaje.<br/>
  ```
	{
		"body": "Primer mesnaje",
		"message_timestamp": "Sep 29, 2019 1:20:00 AM",
		"sender": "Elvis"
	}
  ```
  
 * [POST METHOD] @ (http://localhost:8080/WebServiceREST/resources/messages/withinDate) - Este metodo retorna las ubicaciones de los usuarios entre dos fechas.
```
  {
    "first_Date": "2019-09-29 01:20:00.000",
    "last_Date": "2019-09-29 01:20:00.000"
  }
```
  
 * [POST METHOD] @ (http://localhost:8080/WebServiceREST/resources/{limite}) - Este metodo retorna las ubicaciones de los usuarios con una cantidad limite.<br/>
```
  {
    "first_Date": "2019-09-29 01:20:00.000",
    "last_Date": "2019-09-29 01:20:00.000"
  }
```
