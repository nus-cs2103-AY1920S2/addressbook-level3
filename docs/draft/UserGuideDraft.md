<!-- omit in toc -->
# FitBiz User Guide

- [1. Introduction](#1-introduction)
- [2. Quick Start](#2-quick-start)
- [3. Features](#3-features)
  - [3.1. View help: `help`](#31-view-help-help)
  - [3.2. Add a new client profile: `add client`](#32-add-a-new-client-profile-add-client)
  - [3.3. View a client profile: `view client`](#33-view-a-client-profile-view-client)
  - [3.4. Edit a client’s profile: `edit client`](#34-edit-a-clients-profile-edit-client)
  - [3.5. Delete a client: `delete client`](#35-delete-a-client-delete-client)
  - [3.6. List all clients: `list`](#36-list-all-clients-list)
  - [3.7. Display a list of predefined exercises: `view exercise`](#37-display-a-list-of-predefined-exercises-view-exercise)
  - [3.8. Add a new exercise: `add exercise template`](#38-add-a-new-exercise-add-exercise-template)
  - [3.9. Start a timer: `time`](#39-start-a-timer-time)
  - [3.10. Add a new routine template: `add routine template`](#310-add-a-new-routine-template-add-routine-template)
  - [3.11. View the list of routine template: `view routine template`](#311-view-the-list-of-routine-template-view-routine-template)
  - [3.12. Edit a routine template: `edit routine template`](#312-edit-a-routine-template-edit-routine-template)
  - [3.13. Tag a client: `tag`](#313-tag-a-client-tag)
  - [3.14. Sort clients based on attribute: `sort clients`](#314-sort-clients-based-on-attribute-sort-clients)
  - [3.15. List routines: `list routines`](#315-list-routines-list-routines)
  - [3.16. Check total earnings: `earnings`](#316-check-total-earnings-earnings)
  - [3.18. Track payment date: `view payment`](#318-track-payment-date-view-payment)
  - [3.19. View cliental best: `view pb`](#319-view-cliental-best-view-pb)
  - [3.20. View schedule for the day/week: `view schedule`](#320-view-schedule-for-the-dayweek-view-schedule)
  - [3.21. View client summary: `show summary`](#321-view-client-summary-show-summary)
  - [3.22. Export as CSV: `export`](#322-export-as-csv-export)
  - [3.23. Add a new food template: `add food template`](#323-add-a-new-food-template-add-food-template)
  - [3.24. Edit an existing food template: `edit food template`](#324-edit-an-existing-food-template-edit-food-template)
  - [3.25. Delete food item: `delete food template`](#325-delete-food-item-delete-food-template)
  - [3.26. Display visualisations of training progress: `training graph`](#326-display-visualisations-of-training-progress-training-graph)
  - [3.27. List all gyms: `list gyms`](#327-list-all-gyms-list-gyms)
  - [3.28. Find out information about a gym: `view gym`](#328-find-out-information-about-a-gym-view-gym)
  - [3.29. Make meal plans: `meal`](#329-make-meal-plans-meal)
  - [3.28. Compares the daily and target caloric intake: `calories`](#328-compares-the-daily-and-target-caloric-intake-calories)
  - [3.29. Show Competitors: `find competitor`](#329-show-competitors-find-competitor)
  - [3.30. Find the nearest gyms: `find gym`](#330-find-the-nearest-gyms-find-gym)
  - [3.31. Booking a facility: `book`](#331-booking-a-facility-book)
  - [3.32. Add photos to client’s photo album: `add photo`](#332-add-photos-to-clients-photo-album-add-photo)
  - [3.33. View client photo album: `view photo`](#333-view-client-photo-album-view-photo)

## 1. Introduction

FitBiz is for fitness coaches who are managing multiple clients and prefer to use a desktop app for managing their clients. More importantly, FitBiz is optimized for those who prefer to work with Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, FitBiz can get your client management and tracking tasks done faster than traditional GUI apps. Interested? Jump to the Section 2, “Quick Start” to get started. Enjoy!

## 2. Quick Start

1. Ensure you have Java 11 or above installed in your Computer.
2. Download the latest FitBiz.jar here.
3. Copy the file to the folder you want to use as the home folder for your Fitness Manager.
4. Double-click the file to start the app. The GUI should appear in a few seconds.
5. Type the command in the command box and press Enter to execute it.
e.g. typing help and pressing Enter will open the help window.
6. Some example commands you can try:
    - `list` : lists all clients
    - `add client n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : adds a contact named John Doe to the Address Book.
    - `exit` : exits the app
7. Refer to Section 3, “Features” for details of each command.

## 3. Features

Format of commands:

- Words in `UPPER_SNAKE_CASE` are the parameters to be supplied by the user e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`
- Items in square brackets are optional e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`
- Items with `…` after them can be used multiple times including zero times e.g. `[t/TAG]…` can be used as   (i.e. 0 times), `t/friend`, `t/friend t/family` etc.
- Parameters can be in any order e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

### 3.1. View help: `help`

Lists all available commands and a short description of what they do. Specify the command for more detailed explanation.

Format: `help [c/COMMAND]`

Examples:

- `help`
  - Lists all commands
- `help add client`
  - Shows a detailed explanation of the `add client` command

### 3.2. Add a new client profile: `add client`

Initialises and adds a new client profile.

Format: `add client n/NAME`

- `n/NAME` is case insensitive. e.g `hans` will match `Hans`
- The order of words will matter. e.g `Hans Ong` will not match `Ong Hans`
- Only exact match will be shown. e.g. `Hans` will not match `Hans Ong`

Examples:

- `add client n/Ming Liang`
  - Adds a new client: Ming Liang
- `add client n/Low Tah Kiow, John`
  - Adds a new client: Low Tah Kiow, John

### 3.3. View a client profile: `view client`

Shows all available information of the client.

Format: `view client n/NAME`

- `n/NAME` is case insensitive. e.g `hans` will match `Hans`
- The order of words will matter. e.g `Hans Ong` will not match `Ong Hans`
- Only exact match will be shown. e.g. `Hans` will not match `Hans Ong`

Examples:

- `view client n/Ming Liang`
  - Shows information about Ming Liang
- `view client n/Low Tah Kiow, John`
  - Shows information about Low Tah Kiow, John

### 3.4. Edit a client’s profile: `edit client`

Updates the client’s cliental details by specifying the attribute and the new value.

Format: `edit client n/NAME [a/ATTRIBUTE v/VALUE]...`

- `n/NAME` is case insensitive. e.g `hans` will match `Hans`
- The order of words will matter. e.g `Hans Ong` will not match `Ong Hans`
- Only exact match will be shown. e.g. `Hans` will not match `Hans Ong`
- `[a/ATTRIBUTE]` has to be a valid attribute for the client.
- `[v/VALUE]` has to be of matching type to the attribute of the client

Examples:

- `edit client n/Ming Liang a/age v/60 a/gender v/male`
  - Sets Ming Liang’s age to 60 and gender to male.

### 3.5. Delete a client: `delete client`

Deletes a client from the program.

Format: `delete n/NAME`

- `n/NAME` is case insensitive. e.g `hans` will match `Hans`
- The order of words will matter. e.g `Hans Ong` will not match `Ong Hans`
- Only exact match will be shown. e.g. `Hans` will not match `Hans Ong`

Examples:

- `delete n/Ming Liang`
  - Removes Ming Liang’s profile.

### 3.6. List all clients: `list`

Shows all clients currently entered in this program.

Format: `list`

### 3.7. Display a list of predefined exercises: `view exercise`

Displays a list of exercises available in the program. Specify the muscle group(s) to list only exercises that target that muscle group(s).

Format: `view exercise [m/MUSCLE]...`

Examples:

- `view exercise`
  - Shows a list of all exercises and their information stored in the program
- `view exercise m/abdomens m/chest`
  - Shows all exercises that target the abdomens and chest

### 3.8. Add a new exercise: `add exercise template`

If the list of predefined exercises are not enough, you may choose to add a new exercise for future use.

Format: `add exercise template e/EXERCISE_NAME t/TARGET_MUSCLE...`

- There must be at least one `TARGET_MUSCLE` specified

Examples:

- `add exercise template e/Skipping Rope t/Quadriceps t/Calves`
  - Adds a new exercise Skipping Rope which targets the Quadricepts and Calves muscle group

### 3.9. Start a timer: `time`

Starts a timer which will notify you when it ends.

Format: `time [h/HOURS] [m/MINUTES] [s/SECONDS]`

Examples:

- `time m/4 s/40`
  - Starts a timer for 4 minutes and 40 seconds

### 3.10. Add a new routine template: `add routine template`

Adds a new routine template with the specified name and exercise templates.

Format: `add routine template n/NAME_OF_TEMPLATE e/[EXERCISE_TEMPLATES]...`

- `n/NAME_OF_TEMPLATE` is case insensitive. e.g `back` will match `Back`
- The order of words will matter. e.g `Push Pull` will not match `Pull Push`
- Only exact match will be shown. e.g `Push` will not match `Push Pull`
- `e/EXERCISE_TEMPLATES` must exist in the predefined list of exercise templates

Example:

- `add routine template n/Push Pull e/Pull Up e/Bench Press`
  - Adds a new routine template with called Push Pull with 2 exercises, Pull Up and Bench Press.

### 3.11. View the list of routine template: `view routine template`

Lists all routine templates.

Format: `view routine template`

### 3.12. Edit a routine template: `edit routine template`

Edits a new routine template with the specified name and exercise templates.

Format: `edit routine template n/NAME_OF_TEMPLATE [a/ATTRIBUTE v/VALUE]...`

- `n/NAME_OF_TEMPLATE` is case insensitive. e.g `back` will match `Back`
- The order of words will matter. e.g `Push Pull` will not match `Pull Push`
- Only exact match will be shown. e.g `Push` will not match `Push Pull`
- `[a/ATTRIBUTE]` has to be a valid attribute for the routine template
- `[v/VALUE]` has to be of matching type to the attribute of the routine template

Examples:

- `edit routine template n/Push Pull a/NAME_OF_TEMPLATE v/Pull Push`
  - Renames the routine template called Push Pull to Pull Push

Example:

- `add routine template n/Push Pull e/Pull Up e/Bench Press`
  - Adds a new routine template with called Push Pull with 2 exercises, Pull Up and Bench Press

### 3.13. Tag a client: `tag`

Assigns a tag to a client for ease of grouping and searching.

Format: `tag c/CLIENT t/TAG`

Examples:

- `tag c/Jeffreigh t/Professional`
 - The client Jeffreigh is now tagged as "Professional". Future searches for the Professional tag will include Jeffreigh

### 3.14. Sort clients based on attribute: `sort clients`

Sorts clients by descending order based on the specified attribute.

Format: `sort clients a/ATTRIBUTE`

Examples:

- `sort clients a/Height`
    - returns a sorted list of all clients in descending order

### 3.15. List routines: `list routines`

Returns a list of all routines.

Format: `list routines`

Examples:

- `list routines`
    - returns a complete list of all routines.

Sorts clients by descending order based on the specified attribute.

Format: `sort clients a/ATTRIBUTE`

Examples:

- `sort clients a/Height`
    - returns a sorted list of all clients in descending order

### 3.16. Check total earnings: `earnings`

Shows the total earnings or the specific earnings for a client.

Format: `earnings [n/NAME] [t/TAG]`

- If no name is given, total earnings from all clients will be shown
- If a name is given, only earnings from that client is shown
- `[n/NAME]` is case insensitive. e.g hans will match Hans
- The order of words will matter. e.g Hans Ong will not match Ong Hans
- Only exact match will be shown. e.g. Hans will not match Hans Ong

Examples:

- `earnings`
  - Shows the complete list of clients and total earnings.
- `earnings n/Jane Doe`
  - Shows the earnings from client Jane Doe only.

### 3.18. Track payment date: `view payment`

Shows the list of payment information.

Format: `view payment d/DETAIL`

- `d/DETAIL` can be `n/NAME`, `d/DATE` or `m/MONTH`
- `n/NAME`is case insensitive. e.g `hans` will match `Hans`
- The order of words will matter. e.g `Hans Ong` will not match `Ong Hans`
- Only exact match will be shown. e.g. `Hans` will not match `Hans Ong`
- `d/DATE` must be in the format `DD/MM/YYYY`
- `m/MONTH` must be spelt in full e.g. `january`

Examples:

- `view payment n/tom`
  - Shows a payment details of clients name Tom
- `view payment d/12/12/2020`
  - Shows all payment details on 12 December 2020
- `view payment m/august`
  - Shows all payment details in August

### 3.19. View cliental best: `view pb`

Displays the cliental best of all exercises of a client.

Format: `view pb n/NAME`

- `n/NAME` is case insensitive. e.g `hans` will match `Hans`
- The order of words will matter. e.g `Hans Ong` will not match `Ong Hans`
- Only exact match will be shown. e.g. `Hans` will not match `Hans Ong`

Example:

- `view pb n/Raymond tan`
  - Shows the best record done for all exercises done by Raymond Tan

### 3.20. View schedule for the day/week: `view schedule`

Shows the schedule for today or the time specified.

Format: `view schedule t/TYPE`

- `t/TYPE` can be `d/DATE`, `week` or `month`
- `d/DATE` must be of the format `DD/MM/YYYY`
- to view schedule for today, leave `t/TYPE` blank

Examples:

- `view schedule`
  - Shows the schedule for today
- `view schedule t/week`
  - Shows the schedule of the current week

### 3.21. View client summary: `show summary`

Shows all the trainings done by the client.

Format: `view summary n/NAME`

- `n/NAME` is case insensitive. e.g `hans` will match `Hans`
- The order of words will matter. e.g `Hans Ong` will not match `Ong Hans`
- Only exact match will be shown. e.g. `Hans` will not match `Hans Ong`

Example:

- `view summary n/Timothy Lee`
  - Shows all the training records of Timothy Lee

### 3.22. Export as CSV: `export`

Exports client's training record to a CSV file.

Format: `export n/NAME`

- `n/NAME` is case insensitive. e.g `hans` will match `Hans`
- The order of words will matter. e.g `Hans Ong` will not match `Ong Hans`
- Only exact match will be shown. e.g. `Hans` will not match `Hans Ong`

Example:

- `export n/Lucy Liu`
  - Exports training records of Lucy Liu as a CSV file

### 3.23. Add a new food template: `add food template`

Adds a new food template with the specified name and calories per serving.

Format: `add food n/NAME_OF_FOOD c/CALORIES`

- `n/NAME_OF_FOOD` is case insensitive. e.g `laksa` will match `Laksa`
- The order of words will matter. e.g `Nasi Lemak` will not match `Lemak Nasi`
- Only exact match will be shown. e.g `Nasi` will not match `Nasi Lemak`
- `c/CALORIES` is the calories per serving

Example:

- `add food template n/Chilli Crab c/100`
  - Adds a new food template with food name Chilli Crab and 100 calories per serving.

### 3.24. Edit an existing food template: `edit food template`

Edits an existing food template.

Format: `edit food template n/NAME_OF_FOOD [a/ATTRIBUTE v/VALUE]...`

- `n/NAME_OF_FOOD` is case insensitive. e.g `laksa` will match `Laksa`
- The order of words will matter. e.g `Nasi Lemak` will not match `Lemak Nasi`
- Only exact match will be shown. e.g `Nasi` will not match `Nasi Lemak`
- `[a/ATTRIBUTE]` has to be a valid attribute for the food
- `[v/VALUE]` has to be of matching type to the attribute of the food

Example:

- `edit n/Chilli Crab a/CALORIES v/200`
  - Edits the calories per serving for Chilli Crab to be 200g per serving.  

### 3.25. Delete food item: `delete food template`

Deletes an existing food template.

Format: `delete food template n/NAME_OF_FOOD`

- `n/NAME_OF_FOOD` is case insensitive. e.g `laksa` will match `Laksa`
- The order of words will matter. e.g `Nasi Lemak` will not match `Lemak Nasi`
- Only exact match will be shown. e.g `Nasi` will not match `Nasi Lemak`
- `[a/ATTRIBUTE]` has to be a valid attribute for the food.

Examples:

- `delete food template n/Chilli Crab`
  - Removes food template for Chilli Crab.

### 3.26. Display visualisations of training progress: `training graph`

Shows visualisations of client’s exercise progress.

Format: `training graph n/NAME a/ATTRIBUTE [s/START] [e/END]`

- Generates a graphical representation of the client’s progress
- Client is specified by `n/NAME`
- `a/ATTRIBUTE` include client’s weight, workout cliental best, fat percentage etc
- `[s/START]`, `[e/END]` are optional
- Date format of `[s/START]`, `[e/END]` is `DD/MM/YYYY`

Examples:

- `training graph n/Ming Liang a/weight`
  - Shows a graph of Ming Liang’s weight losing progress since he first started to current date.

### 3.27. List all gyms: `list gyms`

Lists all available gyms in Singapore.

Format: `list gyms`

### 3.28. Find out information about a gym: `view gym`

Finds and lists information about a gym, including opening and closing times, popularity etc.

Format: `view gym g/GYM`

- `g/GYM` is case insensitive. e.g `clementi gym` will match `Clementi Gym`
- The order of words will matter. e.g `Gym Clementi` will not match `Clementi Gym`
- Only exact match will be shown. e.g. `Jurong` will not match `Jurong East Gym`.

Example:

- `view gym g/Jurong East Fitness Club`
  - Returns the address, opening and closing times and average occupancy.  

### 3.29. Make meal plans: `meal`

Stores meal plans into the storage.

Format: `meal [n/NAME] [l/] [f/FOOD] [c/CALORIES]`

- Saves the meal into storage for reference and to assign to client.
- Multiple ingredients are separated by `[l/]`.
- Can have multiple `[l/]` for the breakdown of different ingredients in the food.
- `[c/CALORIES]` can be used to calculate client’s daily calories intake automatically.
- `[c/CALORIES]` must be a number.

Examples:

- `meal n/Chicken Breast with Brocolli l/f/Chicken breast c/165 calories 1/f/Brocolli c/34 calories`
  - Stores meal plan chicken breast with broccoli with the breakdown of calories from chicken breast and broccoli.

### 3.28. Compares the daily and target caloric intake: `calories`

Calculates the difference between client’s current calorie intake and expected intake value

Format: `calories [n/NAME]`

- `[n/Name]` is case insensitive. e.g `hans` will match `Hans`
- The order of words will matter. e.g `Hans Ong` will not match `Ong Hans`
- Only exact match will be shown. e.g. `Hans` will not match `Hans Ong`

Examples:

- `calories n/Ming Liang`
  - Returns Ming Liang’s current calorie intake out of expected calorie intake.

### 3.29. Show Competitors: `find competitor`

Shows a list of clients who have the specified competition.

Format: `find competitors [n/NAME]`

- Shows a list of clients with the specified competition tagged to their profile.
- `n/[Name]` is case insensitive. e.g hometeamns will match HomeTeamNS
- The order of words will not matter. e.g `Fitness Ironman` will match `Ironman Fitness`
- Only full words will be matched. e.g. `Iron` will not match `Irons`
- Competition matching at least one keyword will be returned. e.g. `Ironman Powerlifting` will return `HomeTeamNS Fitness Ironman 2019, Sheffield 2020 Powerlifting`

Examples:

- `find competitors HomeTeamNS Fitness Ironman 2019`
  - Shows a list of clients competing for HomeTeamNS Fitness Ironman 2019.
- `find competitors Sheffield 2020 Powerlifting`
  - Shows a list of clients competing for Sheffield 2020 Powerlifting.

### 3.30. Find the nearest gyms: `find gym`

Finds the nearest gyms to a client according to their address.

Format: `find gym [n/NAME]`

- `n/NAME` is case insensitive. e.g `hans` will match `Hans`
- The order of words will matter. e.g `Hans Ong` will not match `Ong Hans`
- Only exact match will be shown. e.g. `Hans` will not match `Hans Ong`
- At most 5 gyms will be shown

Examples:

- `find gym n/Kee Ah Siow`
  - Finds the nearest gyms to Kee Ah Siow

### 3.31. Booking a facility: `book`

Books a fitness facility from a in-built list of available facilities.

Format: book `[f/FACILITY] [t/TIME] [d/DURATION]`

- Books the facility specified in `[f/FACILITY]`. The facility needs to be found in the in-built list. Else, an error would occur
- `[f/FACILITY]`,  `[t/TIME]`, `[d/DURATION]` must be provided
- Facilities have different operating hours and an error would occur if user book outside the operating hours
- Format for `[t/TIME]` is 24-hour clock
- Format for `[d/DURATION]` is in minutes and should be multiples of 30. Else, an error would occur

Examples:

- `book f/Farrer Park Field t/1400 d/60`
  - Books Farrer Park Field from 2pm to 3pm
- `book f/Burghley Tennis Centre t/0900 d/120`
  - Books Burghley Tennis Centre from 9am to 11am

### 3.32. Add photos to client’s photo album: `add photo`

Add photo to a client’s photo album to track physique progress.

Format: `add photo n/NAME

- `[n/NAME]` is case insensitive. e.g `hans` will match `Hans`
- If there are 2 people with the same name, enter the `INDEX` of the correct client
- After the client is identified, a file attachment window will appear
- Select the file you want from the file attachment window
- The timestamp of the photo added will be recorded

Example:

- `add photo tom`
  - Adds photo tommy.png(chosen)to Tom’s photo album
- `add photo Betty`
  - Adds photo betty.png(chosen) to Betty Koh’s photo album

### 3.33. View client photo album: `view photo`

Shows client's photo in an album format.

Format: `view photo [n/NAME]`

- Photos displayed in photo album are sorted by date(Newest to Oldest)
- The search is case insensitive. e.g `hans` will match `Hans`
- The order of the keywords will matter. e.g. `Hans Bo` will not match `Bo Hans`
- Only the name is searched.
- Only full words will be matched e.g. `Han` will not match `Hans`
- Clients matching at least one keyword will be returned (i.e. OR search). e.g. `Hans Bo` will return `Hans Gruber, Bo Yang`
- If there are 2 people identified, enter the `INDEX` of the correct client

Examples:

- `view photo Diana`
  - Shows photo album of Diana
