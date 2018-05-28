# Challenge platform

This application is a platform for providing challenges for candidates. 
Candidates can not see the challenge until they start it.
Once started, the challenge has a time countdown until the challenge should be completed. Uploading solutions after countdown time expired is also possible but the timestamp will be stored.

The application also provides an administration interface to manage challenges for Admins.   
An admin creates a challenge, it receives an ID of the challenge and the link to send to the candidate. (like: `http://localhost:8080/challengeSources/102f11c4-9ef5-4b7a-a152-4278b6b95931`)


## Technology used:

Java with SpringBoot, Spring Security, JSP, Jackson, JSR303, JUnit, Mokito and JavaScript. 

The persistence is file storage. Why? Just for fun! (Ideally would be RDB.)

Tests are also included on important parts. (see todo for rest)

### Start development

1. Ensure Maven 3.5 and Java 8 are installed.
2. to build and run tests: `mvn clean install`
3. to start app run: `mvn spring-boot:run`

! The admin user and password can be found here:
https://github.com/razorcd/challenge-provider/blob/master/src/main/resources/application.yml#L5-L6

#### Use app like this:
 - open admin dashboard in browser: http://localhost:8080/admin/challengeSources
 - create a challenge and it will generate a unique public link to that challenge. 
 - then send the link to your candidate.

### Start docker locally

//TODO


## Todo:

- [x] define business domains
- [x] create file manager class
- [x] create json parser class
- [x] create repositories using TDD
- [x] create UIs for challenge sources
- [x] integrate USs for challenge solutions
- [x] use mockable Clock bean 
- [x] add start challenge with countdown timer
- [x] add confirm alert for start challenge button
- [ ] implement business layer for all domains (remove repository injections from controllers)
- [ ] add controller method to download solution file
- [ ] display duration of solution 
- [ ] fix the N+1 reads when getting all challenges 
- [x] add Spring Security with Basic Auth
- [x] limit admin interface by user Role
- [ ] add Challenge state machine (not started, running, ended-not-solved, ended-solved)
- [ ] add functional tests with Selenium
- [ ] cleanup JSP templates to decouple admin and candidates views completely
- [ ] add DTOs
- [ ] add CustomExceptions and global 500 Error page
- [ ] setup CI with Travis
- [ ] deploy to DigitalOcean
- [ ] setup CD with Docker Cloud


## License

GNU 3. See: [LICENSE](https://github.com/razorcd/challenge-provider/blob/master/LICENSE)
