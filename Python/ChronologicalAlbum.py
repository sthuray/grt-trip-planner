import pandas as pd
import spotipy
from spotipy.oauth2 import SpotifyOAuth
import cred

user_id = "31kmezols3jp2yhannitby4r3dey"
artist = "Santhosh Narayanan"
scope = "playlist-modify-private"

sp = spotipy.Spotify(auth_manager=SpotifyOAuth(client_id = cred.CLIENT_ID, 
                                              client_secret = cred.CLIENT_SECRET, 
                                               redirect_uri = cred.REDIRECT_URI,
                                               scope=scope))

albumtables = pd.read_html("https://en.wikipedia.org/wiki/Santhosh_Narayanan")
albumtable = albumtables[2]
albumIDList = []

def albumSearch(albumName, artistID):
    print("...........................")
    getAlbumIDList(albumName, artistID)

    # Searches with subtitle if Album has one
    if ":" in albumName:
        substrindex = albumName.index(":")
        albumSubtitle = albumName[(substrindex + 1):len(albumName)]
        getAlbumIDList(albumSubtitle, artistID)

    return


def getAlbumIDList(albumName, artistID):
    print(f"Now searching: {albumName}")
    albumSearch = sp.search(q=albumName, limit=50, type="album")

    # Searches for a single album from this album, "... (From "Album")"
    if "(From " in albumName:
        print("")
    else:
        querySingle = f"(From \"{albumName}\")"
        getAlbumIDList(querySingle, artistID)

    # adds all found albums to album ID list
    startingLength = len(albumIDList)
    i = 0
    for i in range(0, len(albumSearch["albums"]["items"])):

        # loops through all artists in album to see if any match
        j = 0
        for j in range(0, len(albumSearch["albums"]["items"][i]["artists"])):
            albumArtistID = albumSearch["albums"]["items"][i]["artists"][j]["id"]
            if albumArtistID == artistID:
                albumIDList.append(albumSearch["albums"]["items"][i]["id"])
    
    # tries other search queries
    if len(albumIDList) == startingLength:
        #base cases
        if " (Original Motion Picture Soundtrack)" in albumName:
            print(f"No albums found for {albumName}")
            return
        
        if "(From" in albumName:
            print(f"No albums found for {albumName}")
            return

        # Search with "Album (Original Motion Picture Soundtrack)"
        queryTwo = albumName + " (Original Motion Picture Soundtrack)"
        getAlbumIDList(queryTwo, artistID)

    else:
        print(f"{len(albumIDList) - startingLength} albums found for {albumName}")


def addAlbumToPlaylist(albumID, playlistID):
    albumTracks = sp.album_tracks(album_id=albumID)

    tracks = []
    i = 0
    for i in range (0, len(albumTracks["items"])):
        trackID = albumTracks["items"][i]["id"]
        tracks.append(trackID)
    
    sp.user_playlist_add_tracks(user=user_id, playlist_id=playlistID, tracks=tracks)

def removeDuplicateAlbums():
    i = 0
    length = len(albumIDList)

    for i in range(0, len(albumIDList) - 1):
        j = i + 1
        while j < length:
            while albumIDList[i] == albumIDList[j]:
                del albumIDList[j]
                length = len(albumIDList)
                print("Duplicate removed")
                if j == length:
                    break
            
            j += 1
                      
def main():
    # searches with the artist name to get artist ID
    artistSearch = sp.search(q=artist, limit=1, type="artist")
    artistID = artistSearch["artists"]["items"][0]["id"]

    # loops through each movie and adds it's albums to an albumIDList
    i = 0
    for i in range (0, len(albumtable)):
        albumSearch(albumName=albumtable.iat[i, 1], artistID=artistID)
    print("Added all albums to list of album IDs")

    # removes duplicate albums from albumIDList
    removeDuplicateAlbums()
    print("Removed all duplicate albums")

    # creates playlist with the artist name
    artistPlaylist = sp.user_playlist_create(user=user_id, name=artist, public=False)

    # gets the id of the playlist
    playlistID = artistPlaylist["id"]

    # adds all albums to the playlist
    j = 0
    for j in range(0, len(albumIDList)):
        addAlbumToPlaylist(albumID=albumIDList[j], playlistID=playlistID)


main()