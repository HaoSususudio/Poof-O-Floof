# Poof-O-Floof

```json
Registration:
URI: /Poof-O-Floof/api/register
Method: POST
Body:
{ 
  "displayName": "duck",
  "password": "goose"
}
Returns 409 error if username is already taken

Login:
URI: /Poof-O-Floof/api/login
Method: POST
Body:
{ 
  "username": "goose",
  "password": "frog"
}
Returns 401 error code if username is not in database or if password is incorrect

Don't ask why one is displayName and one is username
```

```json
//Format for sending favorite requests
//Saving favorites:
//URI: /Poof-O-Floof/api/favorite
//Method: POST
//Parameter: userId=2
//can also write parameter in URI in the form /Poof-O-Floof/api/favorite?userId=2
//Body:
{ 
  "animalId": 456,
  "photoId": 1234,
  "fullUrl": "postdog.com",
  "type": "spider"
}

//Getting list of photos:
//URI: /Poof-O-Floof/api/favorite
//Method: GET
//Parameter: userId=2
//can also write parameter in URI in the form /Poof-O-Floof/api/favorite?userId=2
//Body: NONE!!!
```
## 2019/12/08
* The app has barebone functionality: POST ip-location to server, and display the animal photos
* Things that need to be done before presentation:
  * User Login/View Favorite: Boris
  * App routing/Nav Bar/Social media sharing: Gabby
  * Fix the location Service/Photo Randomization: Hao
  * User Favorite/Like Statistics or graph: Tim

## 2019/12/04 Hope to finish basic functionalities by Friday
* Stage one basic function: client-side POST a ip-location JSON to Java server, and get a big JSON bundling multiple animal photos of all sizes 
* Just use the coords for animal query. Store other location data later.
* No log-in, no sorting of animal photos 
* JSON formats:
```json 
//UserIpLocInfo
{ 
  "ip": "2600:8806:4000:3d0:38ab:bebe:5851:cc34",
  "coords": "38.9841,-77.3672",
  "postal": "20170",
  "city": "Herndon",
  "region": "Virginia",
  "country": "United States" 
}

//BundleOfAnimalPhotos
[
  {
    "animalId":46731997,
    "photoId":1575302300,
    "type":"dog",
    "fullUrl":"https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/46731997/1/?bust=1575302300"
  },
  {
    "animalId":46747816,
    "photoId":1575420521,
    "type":"dog",
    "fullUrl":"https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/46747816/2/?bust=1575420522"
  },
  {
    "animalId":46747816,
    "photoId":1575420521,
    "type":"dog",
    "fullUrl":"https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/46747816/2/?bust=1575420522"
  },
  {
    "animalId":46709502,
    "photoId":1574968530,
    "type":"dog",
    "fullUrl":"https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/46709502/1/?bust=1574968530"
  },
  {
    "animalId":46709502,
    "photoId":1574968535,
    "type":"dog",
    "fullUrl":"https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/46709502/2/?bust=1574968535"
  },
  {
    "animalId":46709502,
    "photoId":1574968541,
    "type":"dog",
    "fullUrl":"https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/46709502/3/?bust=1574968541"
  }
]
```
## 2019/11/26
* Welcome Gabby on board!
* Current responsibilities of the project: Gabby and Hao on the client-side/Angular. Boris and Tim on the backend.
* First-Stage goals (by 2019-11-30):
  * Get the user ip, and then translate into geolocation from a third-party API
    * Hao: I think we should use (latitude, longitude) because:
      1. It is pure geo-location data, independant of the arbitration of USPS/gerrymandering
      2. It is the same amount of work compared with using zip code/city state
      3. If the user ip is outsite of US, we don't need to handle those exceptions. Just make the search radius larger and larger and decide a limit.
      4. If we also record the coordinates of the animal/photo location, we can serve cached photo urls from our database by doing simple triangulation (instead of dragging extra Java libraries to parse the zip code. This is an extended goal of course.)
  * Fetch the JSON from petfinder
    * Hao: the API token expires every 3600 seconds. We need some logic to automatically renew it. Say by recording the timestamp of last renewal and check it periodically and/or upon serving new photos to users. 
  * Parse JSON into photo POJOs and store them in the DB
  * Display photo url properly in the client side
  * User can click 'next' and fetch the next photo from our server.
## 2019/11/25 Agreed on the following directions:
1. Use Java to fetch ip-geolocation data and petfinder.com
2. Use "photo" as a basis of user interaction. The app will generate multiple photo POJOs from a single animal object in the petfinder.com JSON response. The phto POJO has: photoId, animalId and url. Only the large-size url is stored.
3. animalId on petfinder.com seems to be an 8-digit number. The phto id will be a 10-digit number. The last two digit marks the photos sequence. e.g animalId 12345678 => photoIds: 1234567801, 1234567802, 1234567803 etc.

