# task-final-online_training

### Project deployment guide

#####1. Download or clone project 
```
    git clone https://github.com/Discord123/task-final-online_training.git
```
#####2. Execute 'maven package' goal
```
    mvn package
```
This will run all the tests, then package project
into the 'final_web_online_training**.war' and deploy it automatically due the 'maven tomcat'
plugin and embedded tomcat web server.
#####3. Type in the browser search next line to reveal the main page.
```
    http://localhost:9090
```
By default app will deploy on 9090 port with '/' context.


> #### Credentials:
- Admin - username: admin@example.com, password: password
- Teacher - username: teacher@example.com, password: password
- Student - username: student@example.com, password: password
