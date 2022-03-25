Hello welcome to astrotalkassignment
For running the submitted code following steps and endpoints are need to be followed.

1. Create database "astrotalkassignment" in mysql with username- "root" and password- "root"
use following queries to setup tables;

CREATE TABLE user(name VARCHAR(200), 
email_Id VARCHAR(200),
address  VARCHAR(200),
education VARCHAR(200), 
phone_Number VARCHAR(200), 
bio VARCHAR(200),
serial_Number INT
PRIMARY KEY(email_Id)
);

CREATE TABLE user_friend_list(id INT NOT NULL,
user_email_Id VARCHAR(200),
friend_Email_Id VARCHAR(200),
PRIMARY KEY(id)
);

CREATE TABLE hibernate_sequence(next_val BIGINT,
PRIMARY KEY(next_val)
);

INSERT INTO hibernate_sequence(next_val) VALUES(1);

2. After setting up the database, we are ready to test the application using REST API endpoints.

3. To create User profile send , open postman and type localhost:8080/user with  a json data in following format, select method type as POST and hit send. After successfull completion same profie will be received as JSON with a message "you have been successfully registered".
{
    "name":"tarun",
    "emailId":"tks@gmail.com",
    "address":"delhi",
    "education":"b.com",
    "phoneNumber":"83454928887",
    "bio":"I am good"
}

4. To add a friend to your friend list , you need send data as json in following form with rest api end point as localhost:8080/addfriend, the method type will be POST, after successfull post you will receive a success message as string.
{
    "userEmailID":"singhvk94@gmail.com",
    "friendEmailId":"thegreatshiney@gmail.com"
}

5. To remove a friend from your friend list , you need send data as json in following form with rest api end point as localhost:8080/removefriend, the method type will be DELETE, after successfull post you will receive a success message as string.
{
    "userEmailID":"singhvk94@gmail.com",
    "friendEmailId":"thegreatshiney@gmail.com"
}

6.To get  your friend list ,the rest api end point will be localhost:8080/getfriendslist, and the method type will be GET, hit send and the output received will be a list of json objects 
as shown below.
{
        
        "userEmailId": "tks@gmail.com",
        "friendEmailId": "jessygrey0@gmail.com"
    }
    {
       
        "userEmailId": "tks@gmail.com",
        "friendEmailId": "singhvk94@gmail.com"
    }

7. The last functionality of getting connections at distance k is implemented by a tree and using depth first search algorithm.the rest api for that functionality is /getconnectionatK and the data to be sent in bodyas show belows
  {
        "userEmailId": "tks@gmail.com",
       "k":"1"
  }

