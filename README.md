# Bank System
This is the repo of my first ever web aplication.

## Structure
It is structured into a backend written with SpringBoot and a Vue frontend.

## New information
The main goal was to create an application with all its formalities. 
- Testing was implemented for 80% test coverage.
  - Mocking was used to write appropriate tests. 
- CI/CD pipeline was used to test and deploy on push.
  - Deployment was done to a now deprecated server.
  - Deployment was not ideal, due to building the app on the server instead of just deploying the already built .jar of the app.
- Authentication was implemented with email confirmation.
  - Due to inexperience and the release of a new Spring Security version (with deprecated documentation), a handmade version of authentication was implemented.
    - May contain security flaws so will attempt to avoid such approach in the future.
- Storage was done using simple JSON files, that are not ideal for such use. -> Future development will try to use proper SQL databases.
