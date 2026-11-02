package Project3_6713225;
/*6713225 Chatchawal Labkim
6713232 Thanapon Rojanabenjakul
6713234 Thanawat Mekwattanawanit
6713241 Piyangkun Onsee
*/
import java.io.*;
import java.util.Scanner;


public class StageStatistics {
    private static StageStatistics instance;
    
    private int totalSessionDeaths = 0;
    
    private static final String STATS_FILE = RuntimePaths.data("game_statistics.txt");
    private static String PLAYER_NAME = "guest";
    
    private StageStatistics() {
        loadFromFile();
    }
    
    public static StageStatistics getInstance() {
        if (instance == null) {
            instance = new StageStatistics();
        }
        return instance;
    }
    public static void setPlayerName(String newName) {
    if (newName != null && !newName.trim().isEmpty()) {
        PLAYER_NAME = newName.trim();
        if (instance != null) {
            instance.loadFromFile();
        }
    }
}
    private void loadFromFile() {
        try {
            File inFile = new File(STATS_FILE);
            
            if (!inFile.exists()) {
                totalSessionDeaths = 0;
                saveToFile();
                return;
            }
            
            Scanner fileScan = new Scanner(inFile);
            boolean found = false;
            
            while (fileScan.hasNext()) {
                String name = fileScan.next();
                
                if (name.equals(PLAYER_NAME)) {
                    if (fileScan.hasNextInt()) {
                        totalSessionDeaths = fileScan.nextInt();
                        found = true;
                    }
                    break;
                }
                
                if (fileScan.hasNextLine()) {
                    fileScan.nextLine();
                }
            }
            
            if (!found) {
                totalSessionDeaths = 0;
            }
            
            fileScan.close();
            
        } catch (FileNotFoundException e) {
            System.err.println("Statistics file not found. Creating new file.");
            totalSessionDeaths = 0;
            saveToFile();
        } catch (Exception e) {
            System.err.println("Error loading statistics: " + e.getMessage());
            totalSessionDeaths = 0;
        }
    }
    
   
    private void saveToFile() {
        try {
            File inFile = new File(STATS_FILE);
            File tempFile = new File(RuntimePaths.data("temp_statistics.txt"));
            
            boolean found = false;
            
            if (inFile.exists()) {
                Scanner fileScan = new Scanner(inFile);
                PrintWriter writer = new PrintWriter(new FileWriter(tempFile));
                
                while (fileScan.hasNextLine()) {
                    String line = fileScan.nextLine();
                    String[] parts = line.split("\\s+");
                    
                    if (parts.length > 0 && parts[0].equals(PLAYER_NAME)) {
                        writer.println(PLAYER_NAME + " " + totalSessionDeaths);
                        found = true;
                    } else {
                        writer.println(line);
                    }
                }
                
                if (!found) {
                    writer.println(PLAYER_NAME + " " + totalSessionDeaths);
                }
                
                fileScan.close();
                writer.close();
                
                if (inFile.delete()) {
                    tempFile.renameTo(inFile);
                }
                
            } else {
                PrintWriter writer = new PrintWriter(new FileWriter(inFile));
                writer.println(PLAYER_NAME + " " + totalSessionDeaths);
                writer.close();
            }
            
        } catch (IOException e) {
            System.err.println("Error saving statistics: " + e.getMessage());
        }
    }
    
   
    public void addOneDeath() {
        totalSessionDeaths++;
    }
   
    public int getTotalSessionDeaths() {
        return totalSessionDeaths;
    }
    
    public void save() {
        saveToFile();
    }
}