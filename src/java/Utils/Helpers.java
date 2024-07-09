package Utils;

// @author phamm
import Model.Song;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Queue;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.images.Artwork;

public class Helpers {

    /**
     * Create a (Song) folder in this format:
     * (fullpath)/filename/filename.partextension
     *
     * @param fullPath
     * @param part
     * @throws IOException
     */
    public void setupSongFolder(String fullPath, Part part) throws IOException {
        // Extract the directory path from the full path
        File directory = new File(fullPath).getParentFile();

        // Ensure the directory exists
        if (!directory.exists()) {
            // Create the directory
            Files.createDirectories(Paths.get(directory.getAbsolutePath()));
        }
        // A part is a file
        part.write(fullPath);
    }

    public Song readSongMetadata(File f, boolean getImage, String destPath) throws Exception {
        try {
            // Read metadata from the song file using JAudioTagger library
            AudioFile audioFile = AudioFileIO.read(f);
            Tag tag = audioFile.getTag();

            // Extract metadata fields
            String title = tag.getFirst(FieldKey.TITLE);
            String artist = tag.getFirst(FieldKey.ARTIST);
            String album = tag.getFirst(FieldKey.ALBUM);
            int duration = audioFile.getAudioHeader().getTrackLength();

            // Default values for songId and artistId
            int songId = -1;  // Placeholder value
            int artistId = -1;  // Placeholder value, you might want to map artist to artistId based on your logic

            // Extract cover image
            byte[] coverImageData = null;
            Artwork imageData = tag.getFirstArtwork();
            if (imageData != null) {
                coverImageData = imageData.getBinaryData();
            }

            // Save the cover image if getImage is true and cover image data is available
            String songImagePath = "";
            if (getImage && coverImageData != null) {
                try (FileOutputStream fos = new FileOutputStream(destPath)) {
                    fos.write(coverImageData);
                }
                songImagePath = destPath;
            }

            // Create and return Song object using the appropriate constructor
            return new Song(songId, duration, artist, title, f.getAbsolutePath(), songImagePath, album);
        } catch (IOException | CannotReadException | InvalidAudioFrameException | ReadOnlyFileException | KeyNotFoundException | TagException e) {
            throw new Exception(e.getMessage());
        }
    }

    public String getFileNameWithoutExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return fileName;
        }
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(0, dotIndex);
        }
        return fileName; // In case there is no dot in the filename
    }

    /**
     * A function to get path to a file with the following format.<br>
     * baseDir/middleFolder/fileName/filename.desiredFormat<br>
     * Ex: build/web->Songs->Name->Name.jpg
     *
     * @param baseDir Usually getRealPath
     * @param middleFolder The folder in middle (Ex: Folder to contain both
     * songs file and image)
     * @param fileName FileName
     * @param ext File extension. Ex: mp3, jpg
     * @return
     */
    public String getNewFileLocation(String baseDir, String middleFolder, String fileName, String ext) {
        String s;
        if (middleFolder.isEmpty()) {
            System.out.println("Building dir without middleFolder");
            s = baseDir + middleFolder + File.separator + fileName + File.separator + fileName + "." + ext;
        } else {
            System.out.println("Building dir with middleFolder");
            s = baseDir + middleFolder + File.separator + fileName + File.separator + fileName + "." + ext;
        }
        return s;
    }

    /**
     * Create all folders/dirs on the path specified
     * @param fullPath
     * @throws IOException 
     */
    public void buildDirectory(String fullPath) throws IOException {
        // Extract the directory path from the full path
        File directory = new File(fullPath).getParentFile();

        // Ensure the directory exists
        if (!directory.exists()) {
            // Create the directory
            Files.createDirectories(Paths.get(directory.getAbsolutePath()));
        }
    }
    /**
     * Get the path to display resources on browser <br>
     * This one is for you if you are lazy af <br>
     * Using the args, it will generate like this: <br>
     * Ex: users/Minhkiet/Minhkiet.jpg
     * @param folderName The name of both the folder and the file
     * @param middleFolder The folder to retrieve, ex: songs, users, artists
     * @param ext The extension
     * @return 
     */
    public String generateRelativePathForObject (String middleFolder, String folderName, String ext) {
        return middleFolder + File.separator + folderName + File.separator + folderName + "." + ext;
    }
    
    public String replaceBacklash(String r) {
        return r.replace("\\", "/");
    }
}
