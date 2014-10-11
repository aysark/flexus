Pusher Java classes for Google App Engine
=========================================

This Java classes can be used to communicate very easily with the Pusher REST API (http://www.pusherapp.com) from any Google App engine Web application.

Get Started
-----------
Simply replace the Pusher specific constants in Pusher.java:

	private final static String pusherApplicationId = "";
	
	private final static String pusherApplicationKey = "";
	
	private final static String pusherApplicationSecret = "";
	
Call one of the two available static methods called "triggerPush" and pass channel name, event name and the message body (JSON encoded data) as parameters:
	
	Pusher.triggerPush("test_channel", "my_event", jsonData);
	
The second "triggerPush" method provides an additional parameter for the socket_id:

	Pusher.triggerPush("test_channel", "my_event", jsonData, socketId);
	
That's it.

Default values
--------------
Sometimes it can be very convenient to prepulate a PusherRequest instance with default channel and/or event name:

	PusherRequest p = new PusherRequest("test_channel","my_event");
	
To send a request to the Pusher API just call "triggerPush" on this instance:

	p.triggerPush(jsondata);
	
License
-------
Copyright 2010, Stephan Scheuermann. Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php