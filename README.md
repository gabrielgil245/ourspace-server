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
- Postman
- H2
- Log4J
- JavaDocs
- Postgres

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
- git clone https://github.com/gabrielgil245/ourspace-server.
- Create a relational database called "ourspace-server".
- List of environment variables:
  - AWS_DATABASE_URI - Relational Database Host Name.
  - AWS_DATABASE_USERNAME - Database Username.
  - AWS_DATABASE_PASSWORD - Database Password.
  - AWS_S3_ID - S3 ID.
  - AWS_S3_KEY - S3 Key.
  - AWS_S3_BUCKET - S3 Bucket Name.
- The CROSS_ORIGIN_VALUE is located in the CrossOriginUtil class; it's set to "http://localhost:4200" but may be changed.

## Usage
- Locate the OurspaceserverApplication class and select the green play button to run the application.
- HTTP requests may be made via post at "http://localhost:9000" or using the web platform (link is at the top of this file).

## Contributors
* Roel Crodua
  - 
* Gabriel Gil
  - 
* Jack Gildea
  - Increased overall test coverage by incorporating Mockito.
  - Utilized JpaRepository to customize data retrieval from the database.
  - Integrated S3 functionality for uploading profile and post images.
