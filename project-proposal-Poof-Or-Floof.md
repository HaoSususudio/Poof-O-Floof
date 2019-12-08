# Revature Project Two Proposal
### Project Name: Poof-O-Flooff
#### Batch: 1909-Blake-William Performance Engineer
#### Updated: 2019-11-26
#### Team Members: Tim Clifton (goodsirtimothy), Gabrielle Griggs (gabbyg53), Hao Shen (HaoSususudio) and Boris Sun (sunbor)
#### Team Lead: Hao Shen
#### Main APIs used:
* www.petfinder.com/developers/v2/docs/
* IPGeolocation.com (or other ip geolocating services)
* https://developers.google.com/maps/documentation
* Social media photo sharing functions such as
  * https://developers.facebook.com/docs/instagram-api
  * https://developers.facebook.com/docs/sharing/reference/share-dialog


## Description
This proposed application **Poof-O-Flooff** (a sensational spelling for **Puff or Fluff**), is your grandma's daily fix for cute animal pictures in disguise. Upon using the website, the user will be presented with random photos of cute animals such as cats, dogs, rabbits or other adorable mammals. The user will be encouraged to click on the one he/she prefers. Soon afterward, a new animal photo will be shown and the user can keep continue to input the preference by repeating the process.

At any point of the cute animal viewing process, the user may click on the corner of the photograph marked with a star to "save as favorites." This information will be stored in the user's account should he/she choose to create an account and log in, or anonymously in a cookie. As soon as the favorite animal is added, the user will be notified with a message such as *"This animal needs a new home. Would you like to know more about this cutie?"* They user may proceed to learn more about the adoption information about the animal shown.

Essentially, all the cute animal photos are the actual pet photos waiting for adoption supplied via petfinder.com's API. This application would first silently estimate a user's geolocation via an IP-geolcation service and supply the adoptable pet photos near the user's location. Once the user shows interests in an pet by adding favorites and/or inquiring adoption information, the application may ask permission to inquire the user's precise location to provide more relevant pet showcases.

The back-end server of the application will record the preferences of any user, logged in or not, for future research and commercial purposes. As a stretched goal of this project, the photos displayed to a user may be intelligently picked by an algorithm catering to the selection history of the user.

## Application Features - Client Side
* User may create an account to login, or use it anonymously.
* User may click on the animal photo they prefer and acquire a new substitute photo for the rejected one.
* User may save favorite animals to his/her account or anonymously using a cookie.
* User may view their saved animals.
* User would be notified the fact that these animals were up for adoption and offered with further information.
* User may share a photo via email and/or other third-party social media plug-ins.

## Application Features - Server Side
* The application server will record the information of each user, logged in or not.
* The recorded information may include: 
  * user's account
  * user's IP address
  * user's fine geolocation
  * user's viewing history, preference, and collection of favorite animals
* The application will also cache the animal data supplied by petfinder.com for any photo ever shown to a user, including:
  * type
  * species
  * breeds
  * colors
  * age
  * gender
  * size
  * coat
  * status
  * url

## Application Features - Admin Side
* The Admin of this application can view all the recorded information on the server
* In addition, the Admin may
  * View users' IP/Geolocation on a map.
  * View animal photos on a map.
  * View each user's preference for pets presented as charts.
  * View overall users' preference for pets presented as charts.

## Application Features - Evil Side
* When a user wants to remove/release an animal in the favorite list, the user has to drag the animal thumbnail into a meat mincer.
* After an animal is minced, the user will be awarded with a sausage badge.
  