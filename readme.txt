Deadview Documentation

Aaron Tuor
Hang Ta
CS 345 - MVC Write up

Deadview.java
	container for the Boardview and Stats gui components;
		methods: 
			void main(): used for testing gui;

Boardview.java
	contains all gui elements displaying the game board
	and elements where user interacts with board in 
	game play;
		methods: 
			void newPlayer(): changes which PlayerView is currently active
			void gotoTrailers(): sets display location of all PlayerViews to region of trailers.

Stats.java
	contains all gui elements that display player info
	and buttons for player actions not directly related to
	to board in game play;

Layout.java
	parses board.xml to create a hashmap with keys of 
	room names and values of Layout.Room (a container for room display info);

CardLayout.java
	parses card2.xml to create an array (ordered sequentially by image name)
	of CardLayout.Card (a container for card display info); 
	
PlayerView.java
	extended JLabel for displaying player die icon;
	could be used as button for acting but this is not 
	recommended.
		methods: void changeRank(String rank);
			function for changing player die icon;

RoomView.java
	container for SceneView, Shotcounter, and Roleview gui components.
		methods: void nextPlayPosition(); function for keeping track of where to place a player
											when they move to a room.
				void addScene(CardLayout.card card): function for assigning a card to a Roomview;

Shotcounter.java
	extended JLabel: displays shot icon when acting is successful. May be used
			as button for rehearsing but this is not recommended.

Roleview.java
	extended JLabel used as button for taking a role;

SceneView.java
	extended JPanel containing JLabel of scene pictures and RoleViews of scene roles;

MoveButtons.java
	parses move.xml file to create an array of JLabels which are buttons controlling movement
	and displaying a move icon. 

UpgradeButtons.java
	parses upGrade.xml file to create an array of UgradeButtons.UButton(extended JLabel used as button
	for upgrading);
Status.java
	extended JPanel use to display the Player's status. 
	
Results: 
	extended JPanel use to display the day, some notes, and display the winner.
	
Controller.java
	contains all actions for players and the board
		methods: 
			move(): take care of player's move
			takeRole(): let the player's take a role
			rehearse(): to rehearse when they are in a role
			upGrade(): with money or credit to have higher's rank 
			act(): let the player act when they are in a role
			endGame(): end of the game's trigger
			endDay(): reset the board when the day's end
			end(): end the player's turn
			refreshStats(): refresh the player's status on their rank and money
			refreshResults(): to change the line, day, and winner when necessary. 
			main(): run the controller







