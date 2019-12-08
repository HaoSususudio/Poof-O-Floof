# Revature Project Two Proposal
### Project Name: Poof-O-Flooff
#### Team Members: Tim Clifton, Hao Shen and Boris Sun
#### Team Lead: Hao Shen (GitHub: HaoSususudio)
#### Main APIs used:
* www.petfinder.com/developers/v2/docs/
* IPGeolocation.com (or other ip geolocating services)

## Description
This proposed application **Poof-O-Flooff** (a sensational spelling for **Puff or Fluff**), is your grandma's daily fix for cute animal pictures in disguise. Upon using the website, the user will be presented with two random photos of cute animals such as cats, dogs, rabbits or other adorable mammals. The user will be encouraged to click on the one he/she prefers. Soon afterward, two new animal photos will be shown and the user can keep continue to input the preference by repeating the process.

At any point of the cute animal viewing process, the user may click on the corner of the photograph marked with a star to "save as favorites." This information will be stored in the user's account should he/she choose to create an account and log in, or anonymously in a cookie. As soon as the favorite animal is added, the user will be notified with a message such as *"This animal needs a new home. Would you like to know more about this cutie?"* They user may proceed to learn more about the adoption information about the animal shown.

Essentially, all the cute animal photos are the actual pet photos waiting for adoption supplied via petfinder.com's API. This application would first silently estimate a user's geolocation via an IP-geolcation service and supply the adoptable pet photos near the user's location. Once the user is actively showing interests in adopting a pet by adding favorites and/or inquiring adoption information, the application may ask permission to inquire the user's precise location to provide more relevant pet showcases.

The back-end server of the application will record the preferences of any user, logged in or not, for future research and commercial purposes. As a stretched goal of this project, the substituted photo upon user clicking on a preferred photo may be intelligently picked by an algorithm catering to the selection history of the user.

## Application Features - Client Side
* User may create an account to login, or use it anonymously.
* User may click on the animal photo they prefer and acquire a new substitute photo for the rejected one.
* User may save favorite animals to his/her account or anonymously in the cookie.
* User may view their saved animals.
* User would be notified the fact that these animals were up for adoption and offered with further information.
* User may share a photo via email and/or other third-party social media plug-ins.

## Application Features - Server Side
* The application server will record the information of each user, logged in or not.
* The recorded information may include: 
  * user's account
  * user's IP address
  * user's fine geolocation
  * user's preference about pets such as:
    * species
    * breeds
    * colors
    * age
    * gender
    * size
    * coat
    * attribute
    * environments

## Application Features - Admin Side
* The Admin of this application can view all the recorded information on the server
* In addition, the Admin may
  * View users' IP/Geolocation on a map.
  * View each user's preference for pets presented as pie charts.
  * view overall users' preference for pets on this server as pie charts.

## Application Features - Evil Side
* When a user wants to remove/release an animal in the favorite list, the user has to drag the animal thumbnail into a meat mincer.
* After an animal is removed, the user will be awarded with a sausage badge.