# Hera :boom:

Hera is a multi purpose chatbot, coming with an array of different features like a music player, server metrics tracking, general server moderation features and other fun commands!

[Invite Hera to your server!](https://discord.com/api/oauth2/authorize?client_id=403816989191176192&permissions=271809600&scope=bot)  

Use this [link](https://discord.com/api/oauth2/authorize?client_id=403816989191176192&permissions=0&scope=bot) to add Hera without having Discord create a managed role (Some commands might be disabled if Hera is missing necessary Discord permissions)

Application | Status
------------|--------------
Hera Chatbot | ![build status](https://hera-badges-images-prod.s3-eu-west-1.amazonaws.com/hera-codebuild.svg) ![pipeline status](https://hera-badges-images-prod.s3-eu-west-1.amazonaws.com/hera-code-pipeline.svg)
Hera API | _comming soon_
Hera Website | _comming soon_

## Development Setup :rocket:

The project is structured as a Maven multi module project and functions as a mono-repo containing the Hera chatbot, API & website.  
Hera expects a local MySQL database to be ready and available.  
To streamline setting up the development environment there are several scripts available in [`environment/scripts/`](https://github.com/AarKro/Hera/tree/master/environment/scripts).  

:warning: &nbsp; _Scripts/Instructions are tested on macOS/Linux, there may be complications using other operating systems_

### Hera Chatbot :robot:

Hera Chatbot is the heart of the project and is what users directly interact with via Discord.  
The code for it can be found in the [`core/`](https://github.com/AarKro/Hera/tree/master/core) module and is written in Java using the [Discord4J](https://github.com/Discord4J/Discord4J) Discord API wrapper.

#### Setup

* Copy the file [`env_var_template.sh`](https://github.com/AarKro/Hera/blob/master/environment/scripts/env_var_template.sh) and create a new file with its contents called `env_var.sh` in [`environment/scripts/`](https://github.com/AarKro/Hera/tree/master/environment/scripts)
* Fill out the environment variables in `env_var.sh`, run the script and make sure the environment variables are properly set
* Run the [`mysql_prep.sh`](https://github.com/AarKro/Hera/blob/master/environment/scripts/mysql_prep.sh) script and follow its instructions
* Run the [`setup_db.sh`](https://github.com/AarKro/Hera/blob/master/environment/scripts/setup_db.sh) script to create the database and fill it with test data.
* Start Hera by running the main method in [`core/src/main/java/hera/core/Core.java`](https://github.com/AarKro/Hera/blob/master/core/src/main/java/hera/core/Core.java)

:information_source: &nbsp; _Rerun the [`setup_db.sh`](https://github.com/AarKro/Hera/blob/master/environment/scripts/setup_db.sh) script during development to reset the database_

#### Deployment

The Hera Chatbot is automatically built and deployed to an AWS EC2 instance using AWS CodePipeline.  
The process is triggered when pushing to `master`.  
To start Hera, `ssh` into the EC2 instance and run the script `script/start_hera.sh`.

:exclamation: &nbsp; Don't forget to update the database (which is on an AWS RDS instance) with relevant changes as you deploy

### Hera Website :globe_with_meridians:

Through the website users can easily configure some of Heras behaviour in their Discord server, as well as get an overview of their servers activity.  
The website is written as a React app using TypeScript. The code for it can be found in the [`website/`](https://github.com/AarKro/Hera/tree/master/core) module.

#### Setup

* Make sure you have [Node.js](https://nodejs.org/en/) >= `12.0.0` installed
* Navigate to [`website/`](https://github.com/AarKro/Hera/tree/master/website) and run `npm install`
* Run `npm start` to start the local node server and the website will be served at `localhost:3000`

#### Deployment

The Hera Website will be deployed using Github Pages.  
This means the website will be automatically deployed when pushing to `master`.

* Run `npm build` to create a deployable JS bundle
* Move the bundle to [`docs/`](https://github.com/AarKro/Hera/tree/master/docs) and replace the existing one


### Hera API :gear:
_coming soon_
