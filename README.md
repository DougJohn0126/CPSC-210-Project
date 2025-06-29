# My Switch Desktop Application

## Introduction
In March 2023, the Nintendo Switch will have its sixth anniversary. 
It's incredible to think that the adored console/handheld hybrid has been available for more than six years, 
selling more than 114 million devices, more than 900 million games, and providing countless unforgettable moments.
Nintendo puts an immense amount of money and time into producing titles that give its players the best experiences.
<br>

Despite this, the console/brand has fallen behind its social networking features, a tool that is very
integrated in today's society. The current app offered has very limited features and lacks appeal, when the whole 
point of it should be to enhance the users experience. There is currently no convenient way for one to share their 
Switch experiences with others.


## Project
<br>
The main goal of my project would be to create a desk top app that records and shares a user's experiences/records for
Nintendo Switch players to use. Considering how games have a handful of attributes and quirks for discussion, the 
list for features would go on and on. Some examples include:

1. Add a new game to library/backlog 
2. Add completed games to a list of completed games
3. Keep track of how many hours a user has played a game
4. See how many hours a specific game takes on average to finish
5. Total number of hours spent playing 
6. Total number of hours needed to clear/finish backlog 
7. Most played genres

Note that the examples 5-7 could only be made after the 1-4 is implemented carefully. Therefore, it will be 
crucial to plan out how those functions would work and the data is designed.

## User Stories

- As a user, I want to be able to add games to my library
- As a user, I want the games I add to be real ones and not just fake
- As a user, I want to be able to see what games I have in my backlog
- As a user, I want to see how many hours it would take me to finish my backlog
- As a user, I want to be able to drop certain games from my backlog or library

# Phase 2
- As a user, when I want to be able to save my backlog, so it doesn't get lost
- As a user, when I start the application, I want to be given the option to load my to-do list from file.

# Phase 3 Instructions for Grader
- You can add a game by clicking filling in the field next to the 'Add Game' button and clicking the button,
  given that the game exists.
- You can locate an image next to the Add Game Button that indicates whether your game was successfully added or not.
- You can remove a game in your Backlog listing by clicking on the 'Remove Game' button after selecting a game
- You can save the current state of my application by clicking on the 'Save Backlog' button.
- You can reload the previous state of my application by clicking on the 'Load Last Backlog' button.


# Phase 4: Task 2

- Mon Apr 10 03:23:17 PDT 2023 Created new backlog
- Mon Apr 10 03:23:38 PDT 2023 Added  Crash Bandicoot N. Sane Trilogy
- Mon Apr 10 03:23:38 PDT 2023 Added  Donkey Kong Country: Tropical Freeze
- Mon Apr 10 03:23:50 PDT 2023 Added 12 hours to Crash Bandicoot N. Sane Trilogy
- Mon Apr 10 03:24:29 PDT 2023 Removed  Crash Bandicoot N. Sane Trilogy

# Phase 4: Task 3

First and foremost, I decided to color code the classes in my UML diagram depending on what package or origin they come
from. While I don't know if this is an industry standard or not, I did it for the sake of clarity for people who would 
read my code base for the first time. Furthermore, I decided to include some of the Java builtin classes that are used
by the GUI. This was also done for clarity, since I wanted to convey what thing I'm using to make my GUI. In general, my UML 
diagram is clear since I used the fewest number of classes to achieve functionality. Game is its own object and Backlog
can store an arbitrary long amount of them and do specific operations on them. 

Much of my program is simple since I was learning the course content as I progressed my project. If given more time, I 
wish to make Game an interface instead, and make different classes for games from different platforms like Playstation and
Xbox. I decided to limit my scope to just Nintendo Switch games since other games would've had different attributes like 
missions, milestones, and etc. However, the biggest change I would make would be revolving the Backlog class that stores 
all the games. While it is not apparent in the UML diagram, I believe more of what's done in the GUI could've been moved
to the Backlog class instead. For example, I decided last minute that I wanted to be able to filter the games in the list
view through radio buttons. Since the backlog didn't have this functionality I had to do it through a helper in the GUI. Since this is my 
first time makaing a project with Java, it was difficult to plan out what should be done by my model classes since I didn't know 
what the GUI would be like or is capable of (e.g. having radio buttons). However, this was a great learning experience as I was able to adapt the program 
to my desires and get a thorough understanding of jFrame.
