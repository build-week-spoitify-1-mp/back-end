# back-end
<!-- BASE URL -->
base url = "https://bw-spotify1-mp.herokuapp.com"
<!-- ENDPOINTS -->
<!-- Get User requests -->
GET: "/users/getuserinfo" = returns the currently authenticated user(auth required)

GET: "/users/users" = returns a list of all users(requires auth)

GET: "/users/user/{userid}" = returns user by the user id(requires auth)

GET: "/users/user/name/{username}" = returns a user by username(requires auth)

GET: "/users/user/name/like/{username}" = returns a list of users conting the string username(requires auth)
<!-- Register New User -->
POST: "/users/createnewuser" = creates a new user(no auth required)
<!-- object requirements for a new User -->
new user = 
{
    "username": "newusername",
    "password": "password",
    "email": "newusername"
}
<!-- Login -->
POST: "/login" = checks the database for the username and password and returns authentication(no auth required)
<!-- object requirements for to login -->
user login = 
{
    "username": "newusername",
    "password": "password",
}
<!-- DELETE a user -->
DELETE: "/users/user/{userid}" = deletes a user by id(auth required)

PUT: `/users/user/{userid}` = edits a user
`{
    "username": "editedusername",
    "password": "editedpassword",
    "email": "editedusername"
}`
<!-- Fave Song Endpoints -->
GET: `/favesongs/username/{username}` = returns the list fo favesongs associated with current user(auth required)

GET: `/favesongs/favesong/{favesongid}` = returns a fave song(auth required)
<!-- add new fave song to a user -->
POST: `/favesongs/user/{userid}/favesong/{trackid}` = adds a new trackid to the favesongs array to a user(auth required)

<!-- object requirements to add a trackid -->
{
    "trackid": "newtrackid"
}
<!-- delete a fave song -->
DELETE: `/favesongs/favesong/{favesongid}` = deletes a fave song by the favesongid(auth required)
<!-- for Axios with auth -->
axiosWithAuth: 
`headers: {
            Authorization: Bearer ${localStorage.getItem('token')}
         }`
<!-- FOR login -->
LOGIN:
`axios
        .post('https://bw-spotify1-mp.herokuapp.com/users/createnewuser', `grant_type=password&username=${credentials.username}&password=${credentials.password}&email=${credentials.email}`, {
            headers: {
            // btoa is converting our client id/client secret into base64
            Authorization: `Basic ${btoa('lambda-client:lambda-secret')}`,
            'Content-Type': 'application/x-www-form-urlencoded'
            }
        }`