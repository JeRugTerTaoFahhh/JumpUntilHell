package Project3_6713225;
/*6713225 Chatchawal Labkim
6713232 Thanapon Rojanabenjakul
6713234 Thanawat Mekwattanawanit
6713241 Piyangkun Onsee
*/
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.sound.sampled.*;
import javax.swing.*;

class MyImageIcon extends ImageIcon {

    public MyImageIcon(String fname) {
        super(fname);
    }

    public MyImageIcon(Image image) {
        super(image);
    }

    public MyImageIcon resize(int width, int height) {
        Image oldimg = this.getImage();
        Image newimg = oldimg.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new MyImageIcon(newimg);
    }
}

public class Utilities {

    public static ArrayList<BufferedImage> loadImages(String[] filePaths) {
        ArrayList<BufferedImage> images = new ArrayList<>();

        for (String path : filePaths) {
            BufferedImage img = loadSingleImage(path);
            if (img != null) {
                images.add(img);
            }
        }

        System.out.println("Total images loaded: " + images.size());
        return images;
    }

    public static BufferedImage loadSingleImage(String filePath) {
        try {
            File imageFile = new File(filePath);
            if (imageFile.exists()) {
                BufferedImage img = ImageIO.read(imageFile);
                System.out.println("Successfully loaded: " + filePath);
                return img;
            } else {
                System.out.println("File not found: " + filePath);
                return null;
            }
        } catch (IOException e) {
            System.out.println("Error loading " + filePath + ": " + e.getMessage());
            return null;
        }
    }

    public static ArrayList<BufferedImage> loadNumberedImages(String basePath, String prefix,
            String suffix, int startNum, int endNum) {
        ArrayList<BufferedImage> images = new ArrayList<>();

        for (int i = startNum; i <= endNum; i++) {
            String filename = basePath + prefix + i + suffix;
            BufferedImage img = loadSingleImage(filename);
            if (img != null) {
                images.add(img);
            }
        }

        return images;
    }

    public static class CharacterSkin {

        public String name;
        public String[] imagePaths;
        public String previewImagePath;

        public CharacterSkin(String name, String[] imagePaths, String previewImagePath) {
            this.name = name;
            this.imagePaths = imagePaths;
            this.previewImagePath = previewImagePath;
        }
    }

    public static CharacterSkin[] getAvailableSkins() {
        return new CharacterSkin[]{
            new CharacterSkin("Mask Dude", new String[]{
                GameConstants.FILE_MASKDUDE_1,
                GameConstants.FILE_MASKDUDE_2,
                GameConstants.FILE_MASKDUDE_3,
                GameConstants.FILE_MASKDUDE_4,
                GameConstants.FILE_MASKDUDE_5},
            GameConstants.FILE_MASKDUDE_PREVIEW
            ),
            new CharacterSkin("Ninja Frog", new String[]{
                GameConstants.FILE_NINJAFROG_1,
                GameConstants.FILE_NINJAFROG_2,
                GameConstants.FILE_NINJAFROG_3,
                GameConstants.FILE_NINJAFROG_4,
                GameConstants.FILE_NINJAFROG_5},
            GameConstants.FILE_NINJAFROG_PREVIEW
            ),
            new CharacterSkin("Pink Man", new String[]{
                GameConstants.FILE_PINKMAN_1,
                GameConstants.FILE_PINKMAN_2,
                GameConstants.FILE_PINKMAN_3,
                GameConstants.FILE_PINKMAN_4,
                GameConstants.FILE_PINKMAN_5},
            GameConstants.FILE_PINKMAN_PREVIEW
            ),
            new CharacterSkin("Virtual Guy", new String[]{
                GameConstants.FILE_VIRTUALGUY_1,
                GameConstants.FILE_VIRTUALGUY_2,
                GameConstants.FILE_VIRTUALGUY_3,
                GameConstants.FILE_VIRTUALGUY_4,
                GameConstants.FILE_VIRTUALGUY_5},
            GameConstants.FILE_VIRTUALGUY_PREVIEW
            ),
            new CharacterSkin("Chicken", new String[]{
                GameConstants.FILE_CHICKEN_1,
                GameConstants.FILE_CHICKEN_2,
                GameConstants.FILE_CHICKEN_3,
                GameConstants.FILE_CHICKEN_4,
                GameConstants.FILE_CHICKEN_5},
            GameConstants.FILE_CHICKEN_PREVIEW
            )
        };
    }

    public static Clip playSound(String soundFilePath) {
        try {
            File soundFile = new File(soundFilePath);
            if (!soundFile.exists()) {
                System.out.println("Sound file not found: " + soundFilePath);
                return null;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            return clip;

            // Clean up after sound finishes
        } catch (Exception e) {
            System.out.println("Error playing sound: " + e.getMessage());
            return null;
        }
    }

    public static Clip playLoopSong(String soundFilePath) {
        try {
            File soundFile = new File(soundFilePath);
            if (!soundFile.exists()) {
                System.out.println("Theme song file not found: " + soundFile);
                return null;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(javax.sound.sampled.Clip.LOOP_CONTINUOUSLY);
            clip.start();

            System.out.println("Theme song started (looping)");
            return clip;

        } catch (Exception e) {
            System.out.println("Error playing theme song: " + e.getMessage());
            return null;
        }
    }

    public static void stopLoopSong(Clip clip) {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
            System.out.println("Theme song stopped");
        }
    }

    public static void setVolume(Clip clip, float gain) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        if (gain < 0.0f) {
            gain = 0.0f;
        }
        if (gain > 1.0f) {
            gain = 1.0f;
        }
        float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }

    private static CharacterSkin selectedSkin;

    public static void setSelectedSkin(CharacterSkin skin) {
        selectedSkin = skin;
    }

    public static CharacterSkin getSelectedSkin() {
        return selectedSkin;
    }
    
    private static Clip themeSound; 
    private static float currentVolume = 0.3f;
    private static int currentAlbumIndex = 0; 
    private static int currentLevelIndex = 0;
    private static boolean isThemeMuted = false;
    private static boolean globalMuteState = false;
    
    
    
    public static void updateThemeSong(int newAlbumIndex, int newLevelIndex) {
        currentAlbumIndex = newAlbumIndex;
        currentLevelIndex = newLevelIndex;
        stopTheme();
        boolean isDead = false;
        
        
        
        if (newAlbumIndex < 0 || newAlbumIndex >= GameConstants.ALBUM_MUSIC_PATHS.length ||
            newLevelIndex < 0 || newLevelIndex >= GameConstants.ALBUM_MUSIC_PATHS[0].length) {
            System.out.println("Error: Index out of bounds.");
            return;
        }
        
        String path = GameConstants.ALBUM_MUSIC_PATHS[newAlbumIndex][newLevelIndex];
        themeSound = playLoopSong(path);
        
        if (themeSound != null) {
            setVolume(themeSound,currentVolume );
            if (!isThemeMuted) { 
            themeSound.loop(Clip.LOOP_CONTINUOUSLY); 
        } else {
            
            themeSound.stop(); 
          }
        }
    }
    
    public static void setThemeVolume(float value) {
        currentVolume = value;
        
        if (themeSound != null) {
            setVolume(themeSound, value);
        }
    }
    
    public static int getCurrentAlbumIndex() { return currentAlbumIndex; }
    public static int getCurrentLevelIndex() { return currentLevelIndex; }
    public static float getcurrentVolume()       {return currentVolume;}
  public static boolean getisThemeMuted()     {return isThemeMuted;}
    
    public static void muteTheme() {
        isThemeMuted = true;
        if (themeSound != null) themeSound.stop(); 
    }
    
    public static void unmuteTheme(){
        isThemeMuted = false;
        if (themeSound != null) themeSound.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public static void stopTheme() {
    if (themeSound != null) {
        themeSound.stop();
        themeSound.close();
        themeSound = null;
        System.out.println("Theme stopped and cleaned up");
    }
}
     public static void setGlobalMuteState(boolean muted) {
        globalMuteState = muted;
        if (muted) {
            muteTheme();
        } else {
            unmuteTheme();
        }
    }
    
    public static boolean isGlobalMuted() {
        return globalMuteState;
    }
    
    public static Clip playSoundEffect(String soundFilePath) {
    if (globalMuteState) {
        return null;
    }
    
    Clip clip = playSound(soundFilePath);
    if (clip != null) {
        setVolume(clip, currentVolume * 3);
    }
    return clip;
}
}
