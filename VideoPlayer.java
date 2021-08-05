package com.google;

import java.util.*;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;

  /**
   * ===================================================
   * ===== Completed methods from Part 1, 2 and 3 ======
   * ===================================================
   */
  // Variables to keep track of stopped and playing videos
  private String stoppedVideo = "";
  private String playingVideo = "";
  private String pausedVideo = "";
  // Instance videoId variable for showPlaying method
  private String videoId = "";
  /** Using a TreeMap with a key and list of values as value
   as it supports case insensitivity unlike a standard HashMap */
  Map<String, List<String>> playlists = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
  // List to hold videos for general use
  List<String> videos = new ArrayList<>();

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    // adding video object string elements to another list individually for easy printing
    List<String> currentVideos = new ArrayList<>();
    System.out.println("Here's a list of all available videos:");
    for (int i = 0; i < videoLibrary.getVideos().size(); i++) {
      currentVideos.add(videoLibrary.getVideos().get(i).getTitle() + " " +
              "(" + videoLibrary.getVideos().get(i).getVideoId() + ")" + " " +
              videoLibrary.getVideos().get(i).getTags());
    }
    // sort lexicographically
    Collections.sort(currentVideos);
    // removing unwanted comma from tags
    String str = "";
    for(String i : currentVideos){
      if(i.contains(",")){
        str = i.replace(",", "");
        System.out.println(str);
      } else {
        System.out.println(i);
      }
    }
  }

  public void playVideo(String videoId) {
    // if there is an id present
    if(videoLibrary.getVideo(videoId) != null){
      // and there is a video to be stopped and a playing video to be stopped
      if(!stoppedVideo.isEmpty() && !playingVideo.isEmpty()) {
        // stop the video
        System.out.println("Stopping video: " + stoppedVideo);
        // remove string from stored playing video so stopVideo() can work properly
        playingVideo = "";
      }
      // save the stopped video
      stoppedVideo = videoLibrary.getVideo(videoId).getTitle();
      // play the video
      System.out.println("Playing video: " + videoLibrary.getVideo(videoId).getTitle());
      // add to playing video so this is also recorded
      playingVideo = videoLibrary.getVideo(videoId).getTitle();
      this.videoId = videoId;
      // if playing another video there is no need to pause the previous one anymore
      pausedVideo = "";
    } else {
      System.out.println("Cannot play video: Video does not exist");
    }
  }

  public void stopVideo() {
    if(!playingVideo.equals("")){
      System.out.println("Stopping video: " + playingVideo);
      // because playing video is made empty the else statement will work
      playingVideo = "";
      pausedVideo = "";
    } else {
      System.out.println("Cannot stop video: No video is currently playing");
    }
  }

  public void playRandomVideo() {
    // Play Random Video
    if(!playingVideo.equals("")){
      System.out.println("Stopping video: " + playingVideo);
      playingVideo = "";
    }
    playingVideo = videoLibrary.getVideos()
            .get((int)(Math.random()*videoLibrary.getVideos().size()))
            .getTitle();
      System.out.println("Playing video: " + playingVideo);
      pausedVideo = "";

    }

  public void pauseVideo() {
      if (!pausedVideo.equals("")) {
        System.out.println("Video already paused: " + pausedVideo);
      } else if (!playingVideo.equals("")) {
        pausedVideo = playingVideo;
        System.out.println("Pausing video: " + playingVideo);
      } else {
        System.out.println("Cannot pause video: No video is currently playing");
      }
  }

  public void continueVideo() {
    if(playingVideo.equals("")){
      System.out.println("Cannot continue video: No video is currently playing");
    } else if(pausedVideo.equals("")){
      System.out.println("Cannot continue video: Video is not paused");
    } else {
      System.out.println("Continuing video: " + pausedVideo);
      // video no longer paused
      pausedVideo = "";
    }
  }

  public void showPlaying() {
    if(playingVideo.equals("")){
      System.out.println("No video is currently playing");
    } else if(pausedVideo.equals("")) {
      // getting the video id retrieved by the parameter in the play method and using it here with the
      // get video method so it can be retrieved for the show playing method
      System.out.print("Currently playing: " + videoLibrary.getVideo(this.videoId).getTitle() + " (" +
              videoLibrary.getVideo(this.videoId).getVideoId() + ") ");
      // remove commas from tags list printing as formatted string
      String formattedString = videoLibrary.getVideo(this.videoId).getTags().toString()
              .replace(",", "")
              .trim();
      System.out.print(formattedString);
      System.out.println();
    } else {
      System.out.print("Currently playing: " + videoLibrary.getVideo(this.videoId).getTitle() + " (" +
              videoLibrary.getVideo(this.videoId).getVideoId() + ") ");
      // remove commas from tags list printing as formatted string
      String formattedString = videoLibrary.getVideo(this.videoId).getTags().toString()
              .replace(",", "")
              .trim();
      // if the video is paused show paused as status
      System.out.print(formattedString + " - PAUSED");
      System.out.println();
    }
  }

  public void createPlaylist(String playlistName) {
    String trimmedPlaylistName = playlistName.trim();
    // Check playlists map does not already contain playlistName as a key
    if(playlists.containsKey(trimmedPlaylistName)){
      System.out.println("Cannot create playlist: A playlist with the same name already exists");
    } else { //insert key with empty arraylist
      playlists.put(playlistName, videos);
      System.out.println("Successfully created new playlist: " + trimmedPlaylistName);
    }
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    // if playlist name is already present in playlists
    if(playlists.get(playlistName) != null) {
      // if video exists in file
      if (videoLibrary.getVideo(videoId) != null) {
        // if video doesn't already exist in videos list
        if (!videos.contains(videoId)) {
          // add videoId to videos list
          videos.add(videoId);
          // if the playlist exists, add to playlist
          if (playlists.containsKey(playlistName)) {
            playlists.put(playlistName, videos);
            System.out.println("Added video to " + playlistName + ": " +
                    videoLibrary.getVideo(videoId).getTitle());
          }
          // if video already added: warning message
        } else {
          System.out.println("Cannot add video to " + playlistName + ": " +
                  "Video already added");
        } // if video does not exist: warning message
      } else {
        System.out.println("Cannot add video to " + playlistName + ": " +
                "Video does not exist");
      }
      // if playlist does not exist: warning message
    } else {
      System.out.println("Cannot add video to " + playlistName + ": " +
              "Playlist does not exist");
    }
  }

  public void showAllPlaylists() {
    if(!playlists.isEmpty()){
      System.out.println("Showing all playlists:");
      for(String key: playlists.keySet()){
        System.out.println("\t"+key);
      }
    } else {
      System.out.println("No playlists exist yet");
    }
  }

  public void showPlaylist(String playlistName) {
    // if key exists
    if(playlists.containsKey(playlistName)) {
      System.out.println("Showing playlist: " + playlistName);
      // and that playlist values are not null
      if(!playlists.get(playlistName).isEmpty()) {
        // get all ids from the map and use them to retrieve attributes from file
        for(String i: playlists.get(playlistName)){
          System.out.println("\t" + videoLibrary.getVideo(i).getTitle() + " (" +
                  videoLibrary.getVideo(i).getVideoId() + ") " +
                  videoLibrary.getVideo(i).getTags().toString().replace(",",""));
        }
      } else {
        System.out.println("\t" + "No videos here yet");
      }
    } else {
      System.out.println("Cannot show playlist " + playlistName + ": " +
              "Playlist does not exist");
    }
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    if(playlists.containsKey(playlistName)){
      if(videoLibrary.getVideo(videoId) != null){
        if(playlists.get(playlistName).contains(videoId)){
          playlists.remove(playlistName, videoId);
          videos.remove(videoId);
          System.out.println("Removed video from " + playlistName + ": " +
                  videoLibrary.getVideo(videoId).getTitle());
        } else {
          System.out.println("Cannot remove video from " + playlistName + ": " +
                  "Video is not in playlist");
        }
      } else {
        System.out.println("Cannot remove video from " + playlistName + ": " +
                "Video does not exist");
      }
    } else {
      System.out.println("Cannot remove video from " + playlistName + ": " +
              "Playlist does not exist");
    }
  }

  public void clearPlaylist(String playlistName) {
    // remove all values from a key

    // if playlist exists
    if(playlists.containsKey(playlistName)){
      // access the list inside the hashmap
      // remove key and value
      playlists.remove(playlistName);
      // put back playlist with an empty list
      videos.clear();
      playlists.put(playlistName, videos);
      System.out.println("Successfully removed all videos from " + playlistName);
    } else {
      System.out.println("Cannot clear playlist " + playlistName + ": " +
              "Playlist does not exist");
    }
  }

  public void deletePlaylist(String playlistName) {
    if(playlists.containsKey(playlistName)) {
      playlists.remove(playlistName);
      System.out.println("Deleted playlist: " + playlistName);
    } else {
      System.out.println("Cannot delete playlist " + playlistName + ": " +
              "Playlist does not exist");
    }
  }

  public void searchVideos(String searchTerm) {
    List<String> searchResults = new ArrayList<>();
    Map<Integer, String> mappedResultsByNumber = new HashMap<>();
    // all arguments from object put in an array list with the correct format
    for (int i = 0; i < videoLibrary.getVideos().size(); i++) {
      searchResults.add(videoLibrary.getVideos().get(i).getTitle() + " (" +
              videoLibrary.getVideos().get(i).getVideoId() + ") " +
              videoLibrary.getVideos().get(i).getTags().toString().replace(",",""));
    }
    // sorted list lexicographically
    Collections.sort(searchResults);
    int videoNumber = 0;
    // filtered the results in the list by search term recording indices of results to provide numbers
    // then mapped number to keys in a hashMap with results
    for(String i: searchResults){
      if(i.contains(searchTerm.toLowerCase())){
        if(videoNumber == 0){
          System.out.println("Here are the results for " + searchTerm + ":");
        }
        videoNumber++;
        mappedResultsByNumber.put(videoNumber, i);
        System.out.println("\t"+videoNumber + ") " + i);
      } else {
        break;
      }
    }
    // as long as there are results, allow user to choose a video
    if(!mappedResultsByNumber.isEmpty()){
      System.out.println("Would you like to play any of the above? If yes, specify the number of the video.\n" +
              "If your answer is not a valid number, we will assume it's a no.");
      Scanner sc = new Scanner(System.in);
      try {
        int input = sc.nextInt();
        // ensuring input is within bounds
        if(input <= mappedResultsByNumber.size()){
          // using String methods to return just the title of a video
          int indexOfNextBracket = mappedResultsByNumber.get(input).indexOf("(");
          String playingVideoResult = mappedResultsByNumber.get(input).substring(0, indexOfNextBracket - 1);
          System.out.println("Playing video: " + playingVideoResult + "\n");
        } // catching incorrect type
      } catch (InputMismatchException e){
        System.out.println();
      }
    } else {
      System.out.println("No search results for " + searchTerm + "\n");
    }
  }

  public void searchVideosWithTag(String videoTag) {
    List<String> searchResults = new ArrayList<>();
    Map<Integer, String> mappedResultsByNumber = new HashMap<>();
    // all arguments from object put in an array list with the correct format
    for (int i = 0; i < videoLibrary.getVideos().size(); i++) {
      searchResults.add(videoLibrary.getVideos().get(i).getTitle() + " (" +
              videoLibrary.getVideos().get(i).getVideoId() + ") " +
              videoLibrary.getVideos().get(i).getTags().toString().replace(",",""));
    }
    // sorted list lexicographically
    Collections.sort(searchResults);
    int videoNumber = 0;
     // filtered the results in the list by search term recording indices of results to provide numbers
     // then mapped number to keys in a hashMap with results
    for(String i: searchResults){
      /** Method is the same as "searchVideos" except the input must have a hashtag
       * and parameter videoTag is used instead of searchTerm
       */
      if(i.contains(videoTag.toLowerCase()) && videoTag.contains("#")){
        if(videoNumber == 0){
          System.out.println("Here are the results for " + videoTag + ":");
        }
        videoNumber++;
        mappedResultsByNumber.put(videoNumber, i);
        System.out.println("\t"+videoNumber + ") " + i);
      } else {
        break;
      }
    }
    // as long as there are results, allow user to choose a video
    if(!mappedResultsByNumber.isEmpty()){
      System.out.println("Would you like to play any of the above? If yes, specify the number of the video.\n" +
              "If your answer is not a valid number, we will assume it's a no.");
      Scanner sc = new Scanner(System.in);
      try {
        int input = sc.nextInt();
        // ensuring input is within bounds
        if(input <= mappedResultsByNumber.size()){
          // using String methods to return just the title of a video
          int indexOfNextBracket = mappedResultsByNumber.get(input).indexOf("(");
          String playingVideoResult = mappedResultsByNumber.get(input).substring(0, indexOfNextBracket - 1);
          System.out.println("Playing video: " + playingVideoResult + "\n");
        } // catching incorrect type
      } catch (InputMismatchException e){
        System.out.println();
      }
    } else {
      System.out.println("No search results for " + videoTag + "\n");
    }
  }

  public void flagVideo(String videoId) {
    System.out.println("flagVideo needs implementation");
  }

  public void flagVideo(String videoId, String reason) {
    System.out.println("flagVideo needs implementation");
  }

  public void allowVideo(String videoId) {
    System.out.println("allowVideo needs implementation");
  }
}