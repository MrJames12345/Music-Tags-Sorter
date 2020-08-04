//General
import java.util.*;
import java.io.*;

class MusicTagsSorter
{

    public static void main(String args[])
    {

// - Variables - //

        // Playlist
        YouTubePlaylistScraper playlistScraper = new YouTubePlaylistScraper();
        int numConnectTries = 10;
        // Setting Tags
        MusicTagger musicTagger = new MusicTagger();
        // Output Box
        OutputBox outputBox = new OutputBox( "Music Tags Sorter", true );
        // Data (constants)
        String musicPlaylistUrl = "https://www.youtube.com/playlist?list=PLMc_DKXs_N0PjMQaxzDGd9EadF17wKf-_";
        String musicFolder = "C:\\Users\\James\\Music\\#New";
        // Data (temp)
        HashMap<String, String> songData = null;


// - Main - //

        // Set playlist for playlist scraper and folder for music tagger
        // PRINT
        outputBox.newHeading();
        outputBox.update( "Retrieving list of songs..." );
        playlistScraper.setPlaylist( musicPlaylistUrl, numConnectTries );
        musicTagger.setFolder( musicFolder );

        // WHILE songs left, set tags of next song
        while ( !playlistScraper.endOfPlaylist() )
        {
            
            // PRINT
            outputBox.newHeading();
            try
            {

                // Get data of next song (title, artist, album)
                songData = playlistScraper.getNextSongData();
                // PRINT
                outputBox.update( "Tagging song:\n" + songData.get( "title" ) + "\nby " + songData.get( "artist" ) + ".\nAlbum: " + songData.get( "album" ) );
                // Set song's tags, given filename as the song's title and the tags
                musicTagger.setTags( songData.get( "title" ), songData, outputBox );
                // PRINT
                outputBox.update( "> Success mate!" );


            }
            catch( FileNotFoundException e )
            {
                outputBox.update( "> ERROR: Couldn't find the file for christ's sake!" );
            }
            catch( SongAlreadyProcessedException e )
            {
                outputBox.update( "> This song has already been processed buddy!" );
            }
            catch( NotYouTubeSongException e )
            {
                outputBox.update( "> ERROR: This song isn't an official YouTube song, couldn't get tags!" );
            }
            catch( IOException e )
            {
                outputBox.update( "> ERROR: I/O shat the bed!" );
            }
        }

        outputBox.newHeading();
        outputBox.update( "All done!" );

    }

}