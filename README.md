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
- Run the application from the OurspaceserverApplication class.
- HTTP requests may be made to "http://localhost:9000" using the web platform (the link is located at the top of this file).

## Contributors
### Roel Crodua
  - 
### Gabriel Gil
  - Integrated Spring Boot for easier dependency management and testing.
  - Devised the User, Post, and Comment Entity Models.  
  - Developed the services, utilizing Spring's dependency injection.
  - Utilized Spring Data annotations for data querying and manipulation.
  - Developed a set of JPQL commands to retrieve the requested results.
  - Assembled API endpoints with Spring WebMVC.
  - Utilized backend test coverage with Spring Boot Testing and Mockito to achieve 86% test coverage.
  - Successfully deployed the server side of the application via a docker container.
### Jack Gildea
  - Increased overall test coverage by incorporating Mockito.
  - Utilized JpaRepository to customize data retrieval from the database.
  - Integrated S3 functionality for uploading profile and post images.
