# Stranger Things Notifier

## What is this?

Stranger Things Notifier is a set of tools to receive an incoming message (either via 
HTTP POST or by way of an SMS Gateway like Twilio) and display it ala the "Ouija Wall" as seen on the 
Stranger Things show on Netflix.

There are three components to this process:
* A Spring Controller to handle incoming messages (and mongo storage of those messages)
* A scheduled task that processes incoming messages and sends them to a renderer
* A renderer that displays the messages by turning on and off "lights"

## Why did we do it?

The obvious answers are either "Because we could" or "It's for art." Both are correct.  On top of that the only 
projects we've seen are in the virtual world. Even the Netflix show itself doesn't appear to use a real, programmable 
wall. From reviewing the scenes, it looks like someone is controlling a light with a switch.

It's entirely possible (though not proven) that we're the first ones to do this.

## Who's involved?

Well, quite a few people:

* Laura Anthony - The person who had the idea
* Coday Anthony - Laura's Husband (and builder's assistant)
* Aaron Stewart - The person who decided to make SMS work
* Adam Stewart - Aaron's brother, and the geek behind the hardware

## What can you do with it?

Well, with a little hardware knowhow you can build your own stranger things wall, and send it messages, startle your 
friends, excite your enemies.

Seriously though, it might just be a gimmicky thing, but we figured there are enough fans out there that it was 
worth sharing!

Without the hardware though, you're pretty much relegated to receiving text messages and displaying them on a virtual 
terminal (which is kinda neat itself). 


## How to Run It

* Clone the github project.
* Run the following:

```
mvn spring-boot:run
```

* To test the endpoint:

POST to http://localhost:8080/message

Provide all key value pairs, but the big one  you need to worry about is "Body" which is the message to display.

## What about the hardware?

Okay, so you want to build your own wall to receive messages from the Upside Down?

### What you'll need

* BeagleBone Black (or raspberry pi, though I haven't tried it with this)
* 8gb microSD card (for flashing latest ubuntu onto the BBB)
* A Soldering Iron (and solder)
* Some resistors
* At least 26 LEDs

### Experience Required
* Knowledge of how to wire together components (including an i2c led controller chip), or knowledge of someone who does.
* Java experience (see below for getting started with this project -- COMING SOON)
* Patience.
* Some lumber to build a wall
* Some drywall to cover the wall frame
* Some wallpaper to put on the wall
* Strands of Christmas lights (be prepared to GUT at least one strand)
* A drill (and 1/4" drill bit)

