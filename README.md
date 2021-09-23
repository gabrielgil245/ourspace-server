# OurSpace Social Media Server
## Project Description
In this social network, everyone is friends with everyone. This application is designed to handle multimedia consuming S3.

OurSpace Social Media Web Platform/Frontend: https://github.com/gabrielgil245/ourspace-client

## Technologies Used
- Spring Boot
- Spring WebMVC
- Spring Data
- Lombok
- JUnit
- Mockito
- S3
- AWS SDK for Java
- JavaMail API
- H2
- Log4J
- JavaDocs

## Features
### Users can:
- Register.
- Login/Logout.
- Reset their password (utilizing an email feature).
- Modify their information.
- Upload a profile picture (using AWS: S3).
- Search other people.
- Create a post.
  - Image(s) can be added to these posts (using AWS: S3).
- View his own profile.
  - Including posts.
- View others’ profile.
  - Including posts.
- See their feed.
  - Posts of everyone should show here.
  - Pagination should be implemented (only fetching 20 posts at a time).
- Like someone’s post.
  - Old school Facebook, only one type of like.

Todo-List:
- Add a YouTube link to their post.
  - Utilize a YouTube API to display it.
- Users get notifications.
- Frontend: Deploy web application (web application initially deployed with complications)

## Getting Started
- git clone https://github.com/gabrielgil245/ourspace-client.
- npm install to install this application's packages.
- The server domain string is set to "http://localhost:9000"; it may be changed under services in the generic.service.ts.
- HTTP requests are made to a S3 bucket, go to the generic.service.ts to change the values that pertain to your S3 bucket.

## Usage
- [ng serve -o] to build and serve the application at http://localhost:4200.

## Contributors
- Roel Crodua
  - 
- Gabriel Gil
  - 
- Jack Gildea
  - 
