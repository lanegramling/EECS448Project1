# Team Wubba Lubba Dub Dub's Schedule Planner

## EECS448 Project 1
Members: Damian Vu, Dustin Bingham, Lane Gramling

### TODOLIST
(out-of-scope) items are ideas that we do NOT NEED, but want to implement if there is enough time

##### DatabaseHelper
- ~~Implement Database 1.0 Schema in DBContract~~ **DONE!**
- ~~Dummy method implementation~~ **DONE!**
- ~~Implement User Table methods~~ **DONE!**
- Implement Event Table methods
- Implement Signup Table methods

##### SelectUserActivity
- ~~Display list of users from database~~ **DONE!**
- ~~Add ability to create users~~ **DONE!**
- ~~Selecting a user takes you to ListActivity~~ **DONE!**
- ~~Proper username validation~~ **DONE!**
- Add ability to delete users (out-of-scope)

##### ListActivity
- Display List of Events from database
- Toggle User/Admin mode (Switch displayed events)
- 24hr Time Toggle
- Add Event button takes you to AddEventAcitivity
- Selecting an Event takes you to ViewActivity
- Add ability to sort events (out-of-scope)

##### ViewActivity
- Display info about event dependent on if current user is the creator
- 24hr Time Toggle
- Admin Mode
  - Display event times and users signed up at each timeslot from Signup table
- User Mode
  - Pull previous availability from Signup table
  - Display availability and timeslot boxes
  
##### AddEventActivity
- Date picking widget for the day of the year (restrict to only today + future days???)
- Gray out (disable) timeslots of times that have already passed if Today is chosen date?
- ~~Ability to select timeslots in which the event will occur~~ **DONE!**
- ~~24hr Time Toggle~~ **DONE!**
- Hook into database
