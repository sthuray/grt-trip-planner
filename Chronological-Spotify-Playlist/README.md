This is a simple python script to create a Spotify playlist of an artist's full discography in chronological order. It uses their wikipedia page to determine the chronological order and uses spotipy to edit your account's playlist and search Spotify's data. 

This project was specifically created to listen to a film composer's entire chronological discography in order to recognize their growth as a composer throughout their career. 

Setup: 
- you must enter the artist's name in the artist variable, enter your user_id, enter the wikipedia page of the artist in albumtables and identify the index of the discography table in albumtable. 
- Script expects you to create a cred file that has your CLIENT_ID, CLIENT_SECRET, and REDIRECT_URI. These can be found by activating developer mode on your spotify account. 