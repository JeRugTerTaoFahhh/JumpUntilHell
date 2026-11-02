package Project3_6713225;
/*6713225 Chatchawal Labkim
6713232 Thanapon Rojanabenjakul
6713234 Thanawat Mekwattanawanit
6713241 Piyangkun Onsee
*/
import javax.sound.sampled.Clip;

interface GameConstants {

    //----- Resource files paths
    static final String PATH = RuntimePaths.resources();

    // Mask Dude character
    static final String PATH_MASKDUDE = PATH + "Character/maskdude/";
    static final String FILE_MASKDUDE_1 = PATH_MASKDUDE + "maskdude_run_1.png";
    static final String FILE_MASKDUDE_2 = PATH_MASKDUDE + "maskdude_run_2.png";
    static final String FILE_MASKDUDE_3 = PATH_MASKDUDE + "maskdude_run_3.png";
    static final String FILE_MASKDUDE_4 = PATH_MASKDUDE + "maskdude_run_4.png";
    static final String FILE_MASKDUDE_5 = PATH_MASKDUDE + "maskdude_run_5.png";
    static final String FILE_MASKDUDE_PREVIEW = PATH_MASKDUDE + "maskdude_preview.png";

    // Ninja Frog character
    static final String PATH_NINJAFROG = PATH + "Character/ninjafrog/";
    static final String FILE_NINJAFROG_1 = PATH_NINJAFROG + "ninjafrog_run_1.png";
    static final String FILE_NINJAFROG_2 = PATH_NINJAFROG + "ninjafrog_run_2.png";
    static final String FILE_NINJAFROG_3 = PATH_NINJAFROG + "ninjafrog_run_3.png";
    static final String FILE_NINJAFROG_4 = PATH_NINJAFROG + "ninjafrog_run_4.png";
    static final String FILE_NINJAFROG_5 = PATH_NINJAFROG + "ninjafrog_run_5.png";
    static final String FILE_NINJAFROG_PREVIEW = PATH_NINJAFROG + "ninjafrog_preview.png";

    // Pink Man character
    static final String PATH_PINKMAN = PATH + "Character/pinkman/";
    static final String FILE_PINKMAN_1 = PATH_PINKMAN + "pinkman_run_1.png";
    static final String FILE_PINKMAN_2 = PATH_PINKMAN + "pinkman_run_2.png";
    static final String FILE_PINKMAN_3 = PATH_PINKMAN + "pinkman_run_3.png";
    static final String FILE_PINKMAN_4 = PATH_PINKMAN + "pinkman_run_4.png";
    static final String FILE_PINKMAN_5 = PATH_PINKMAN + "pinkman_run_5.png";
    static final String FILE_PINKMAN_PREVIEW = PATH_PINKMAN + "pinkman_preview.png";

    // Virtual Guy Character
    static final String PATH_VIRTUALGUY = PATH + "Character/virtualguy/";
    static final String FILE_VIRTUALGUY_1 = PATH_VIRTUALGUY + "virtualguy_run_1.png";
    static final String FILE_VIRTUALGUY_2 = PATH_VIRTUALGUY + "virtualguy_run_2.png";
    static final String FILE_VIRTUALGUY_3 = PATH_VIRTUALGUY + "virtualguy_run_3.png";
    static final String FILE_VIRTUALGUY_4 = PATH_VIRTUALGUY + "virtualguy_run_4.png";
    static final String FILE_VIRTUALGUY_5 = PATH_VIRTUALGUY + "virtualguy_run_5.png";
    static final String FILE_VIRTUALGUY_PREVIEW = PATH_VIRTUALGUY + "virtualguy_preview.png";

    // Chicken Character
    static final String PATH_CHICKEN = PATH + "Character/chicken/";
    static final String FILE_CHICKEN_1 = PATH_CHICKEN + "chicken_run_1.png";
    static final String FILE_CHICKEN_2 = PATH_CHICKEN + "chicken_run_2.png";
    static final String FILE_CHICKEN_3 = PATH_CHICKEN + "chicken_run_3.png";
    static final String FILE_CHICKEN_4 = PATH_CHICKEN + "chicken_run_4.png";
    static final String FILE_CHICKEN_5 = PATH_CHICKEN + "chicken_run_5.png";
    static final String FILE_CHICKEN_PREVIEW = PATH_CHICKEN + "chicken_preview.png";

    // Background (optional - if you have background image)
    static final String FILE_BACKGROUND = PATH + "GameMenu/" + "background.png";

    static final String PATH_BOSS = PATH + "Boss/";

    // Boss axe swing animation
    static final String FILE_BOSS_AXE_1 = PATH_BOSS + "boss_axe_1.png";
    static final String FILE_BOSS_AXE_2 = PATH_BOSS + "boss_axe_2.png";
    static final String FILE_BOSS_AXE_3 = PATH_BOSS + "boss_axe_3.png";
    static final String FILE_BOSS_AXE_4 = PATH_BOSS + "boss_axe_4.png";

    // Boss fireball rain animation
    static final String FILE_BOSS_FIREBALL_1 = PATH_BOSS + "boss_fireball_1.png";
    static final String FILE_BOSS_FIREBALL_2 = PATH_BOSS + "boss_fireball_2.png";
    static final String FILE_BOSS_FIREBALL_3 = PATH_BOSS + "boss_fireball_3.png";
    static final String FILE_BOSS_FIREBALL_4 = PATH_BOSS + "boss_fireball_4.png";

    // Boss summon minions animation
    static final String FILE_BOSS_SUMMON_1 = PATH_BOSS + "boss_summon_1.png";
    static final String FILE_BOSS_SUMMON_2 = PATH_BOSS + "boss_summon_2.png";
    static final String FILE_BOSS_SUMMON_3 = PATH_BOSS + "boss_summon_3.png";
    static final String FILE_BOSS_SUMMON_4 = PATH_BOSS + "boss_summon_4.png";

    static final String FILE_HEALTH_BAR = PATH_BOSS + "health_bar.png";
    static final String FILE_BOOM_1 = PATH_BOSS + "boom_1.png";
    static final String FILE_BOOM_2 = PATH_BOSS + "boom_2.png";
    static final String FILE_BOOM_3 = PATH_BOSS + "boom_3.png";
    static final String FILE_BOOM_4 = PATH_BOSS + "boom_4.png";
    
    static final String PATH_EARTH = PATH + "Earth/";
    static final String FILE_EARTH_BACKGROUND = PATH_EARTH + "bg.png";
    static final String FILE_EARTH_P1 = PATH_EARTH + "p1.png";
    static final String FILE_EARTH_P2 = PATH_EARTH + "p2.png";
    static final String FILE_EARTH_P3 = PATH_EARTH + "p3.png";
    static final String FILE_EARTH_P4 = PATH_EARTH + "p4.png";
    static final String FILE_EARTH_P5 = PATH_EARTH + "p5.png";
    static final String FILE_EARTH_P6 = PATH_EARTH + "p6.png";
    static final String FILE_EARTH_S1 = PATH_EARTH + "s1.png";
    static final String FILE_EARTH_S2 = PATH_EARTH + "s2.png";
    static final String FILE_EARTH_S3 = PATH_EARTH + "s3.png";
    static final String FILE_EARTH_S4 = PATH_EARTH + "s4.png";
    static final String FILE_EARTH_DOOR = PATH_EARTH + "door.png";
    static final String FILE_EARTH_PORTAL = PATH_EARTH + "portal.png";
    static final String FILE_EARTH_WALL = PATH_EARTH + "wall.png";
    static final String FILE_EARTH_KEY = PATH_EARTH + "key.png";
    
    static final String PATH_HEAVEN = PATH + "Heaven/";
    static final String FILE_HEAVEN_BACKGROUND = PATH_HEAVEN + "bg.png";
    static final String FILE_HEAVEN_P1 = PATH_HEAVEN + "p1.png";
    static final String FILE_HEAVEN_P2 = PATH_HEAVEN + "p2.png";
    static final String FILE_HEAVEN_P3 = PATH_HEAVEN + "p3.png";
    static final String FILE_HEAVEN_P4 = PATH_HEAVEN + "p4.png";
    static final String FILE_HEAVEN_S1 = PATH_HEAVEN + "s1.png";
    static final String FILE_HEAVEN_S2 = PATH_HEAVEN + "s2.png";
    static final String FILE_HEAVEN_PORTAL = PATH_HEAVEN + "portal.png";
    static final String FILE_HEAVEN_PORTALF = PATH_HEAVEN + "portalf.png";
    static final String FILE_HEAVEN_KEY = PATH_HEAVEN + "key.png";
    static final String FILE_HEAVEN_WALL = PATH_HEAVEN + "wall.png";
    static final String FILE_HEAVEN_CLOUD = PATH_HEAVEN + "cloud.png";
    static final String FILE_HEAVEN_DOOR = PATH_HEAVEN + "door.png";

    static final String PATH_HELL = PATH + "Hell/";
    static final String FILE_FLOOR_MID_HELL = PATH_HELL + "floor_mid_hell.png";
    static final String FILE_FIREBALL = PATH_HELL + "fireball.png";
    static final String FILE_MINION_LEFT = PATH_HELL + "minion_left.png";
    static final String FILE_MINION_RIGHT = PATH_HELL + "minion_right.png";
    static final String FILE_HELL_BACKGROUND = PATH_HELL + "bg.png";
    static final String FILE_HELL_P1 = PATH_HELL + "p1.png";
    static final String FILE_HELL_P2 = PATH_HELL + "p2.png";
    static final String FILE_HELL_P3 = PATH_HELL + "p3.png";
    static final String FILE_HELL_P4 = PATH_HELL + "p4.png";
    static final String FILE_HELL_P5 = PATH_HELL + "p5.png";
    static final String FILE_HELL_P6 = PATH_HELL + "p6.png";
    static final String FILE_HELL_P7 = PATH_HELL + "p7.png";
    static final String FILE_HELL_P8 = PATH_HELL + "p8.png";
    static final String FILE_HELL_S1 = PATH_HELL + "s1.png";
    static final String FILE_HELL_S2 = PATH_HELL + "s2.png";
    static final String FILE_HELL_S3 = PATH_HELL + "s3.png";
    static final String FILE_HELL_WALL = PATH_HELL + "wall.png";
    static final String FILE_HELL_KEY = PATH_HELL + "key.png";
    static final String FILE_HELL_PORTAL = PATH_HELL + "portal.png";
    static final String FILE_HELL_DOOR = PATH_HELL + "door.png";
    static final String FILE_HELL_UP = PATH_HELL + "up.png";
    static final String FILE_HELL_DOWN = PATH_HELL + "down.png";

    static final String FILE_SELECTION_BACKGROUND = PATH + "GameMenu/" + "background.png";
    static final String FILE_SKIN_BANNER = PATH + "GameMenu/" + "skin_banner.png";
    static final String FILE_CHARACTER_CARD = PATH + "character_card.png";

    static final String PATH_SOUND = PATH + "Sound/";
    static final String FILE_JUMP_SOUND = PATH_SOUND + "jump.wav";
    static final String FILE_KILL_MINION_SOUND = PATH_SOUND + "kill_minion.wav";
    static final String FILE_BOSS_AXE_SOUND = PATH_SOUND + "boss_axe.wav";
    static final String FILE_BOSS_FIREBALL_SOUND = PATH_SOUND + "boss_fireball.wav";
    static final String FILE_BOSS_SUMMON_SOUND = PATH_SOUND + "boss_summon.wav";
    static final String FILE_BOSS_BOOM_SOUND = PATH_SOUND + "boss_boom.wav";
    static final String FILE_GAME_OVER_SOUND = PATH_SOUND + "game_over.wav";
    static final String FILE_VICTORY_SOUND = PATH_SOUND + "victory.wav";
    static final String FILE_BUTTON_SOUND = PATH_SOUND + "button_click.wav";
    
    static final String PATH_MENU = "GameMenu/";
    static final String FILE_TITLE = PATH + PATH_MENU + "title.png";
    static final String FILE_BG = PATH + PATH_MENU + "background.png";
    static final String FILE_START_BUTTON = PATH + PATH_MENU + "start_button.png";
    static final String FILE_SKIN_BUTTON = PATH + PATH_MENU + "skin_button.png";
    static final String FILE_SETTINGS_BUTTON = PATH + PATH_MENU + "settings_button.png";
    static final String FILE_CREDIT_BUTTON = PATH + PATH_MENU + "credit_button.png";
    static final String FILE_HTP_BUTTON = PATH + PATH_MENU + "howtoplay_button.png";
    static final String FILE_EXIT_BUTTON = PATH + PATH_MENU + "exit_button.png";
    
    
    static final String FILE_LEVEL_SELECT = PATH + PATH_MENU + "level_select.png";
    static final String FILE_CLOSE_BUTTON = PATH + PATH_MENU + "close_button.png";
    static final String FILE_HOME_BUTTON = PATH + PATH_MENU + "home_button.png";
    static final String FILE_EARTH_BUTTON = PATH + PATH_MENU + "earth_level.png";
    static final String FILE_HEAVEN_BUTTON = PATH + PATH_MENU + "heaven_level.png";
    static final String FILE_HEAVENLOCK_BUTTON = PATH + PATH_MENU + "heaven_level_lock.png";
    static final String FILE_HELL_BUTTON = PATH + PATH_MENU + "hell_level.png";
    static final String FILE_HELLLOCK_BUTTON = PATH + PATH_MENU + "hell_level_lock.png";
    
    
    
    static final String FILE_HTP_BG = PATH + PATH_MENU + "howtoplay_frame.png";
    static final String FILE_CREDIT_BG = PATH + PATH_MENU + "credit_frame.png";
    static final String FILE_SETTINGS_BG = PATH + PATH_MENU + "settings_frame.png";
    static final String FILE_OPEN_SOUND = PATH + PATH_MENU + "open_sound.png";
    static final String FILE_MUTE_SOUND = PATH + PATH_MENU + "mute_sound.png";
    static final String FILE_CLUSMY_MUSIC_ICON = PATH + PATH_MENU + "clusmy_music.png";
    static final String FILE_HOP_MUSIC_ICON = PATH + PATH_MENU + "hop_music.png";
    static final String FILE_PIXEL_MUSIC_ICON = PATH + PATH_MENU + "pixel_music.png";
    static final String FILE_REBORN_MUSIC_ICON = PATH + PATH_MENU + "reborn_music.png";
    static final String FILE_MECHANIC_MUSIC_ICON = PATH + PATH_MENU + "mechanic_music.png";

    static final String PATH_MUSIC = "Music/";
    static final String FILE_CLUSMY_MUSIC = PATH + PATH_MUSIC + "clusmy_music.png";
    static final String FILE_HOP_MUSIC  = PATH + PATH_MUSIC + "hop_music.png";
    static final String FILE_PIXEL_MUSIC = PATH + PATH_MUSIC + "pixel_music.png";
    static final String FILE_REBORN_MUSIC = PATH + PATH_MUSIC + "reborn_music.png";
    static final String FILE_MECHANIC_MUSIC = PATH + PATH_MUSIC + "mechanic_music.png";
    
    static final String[][] ALBUM_MUSIC_PATHS = {
        {
            PATH + PATH_MUSIC + "hop_earth.wav",
            PATH + PATH_MUSIC + "hop_heaven.wav",
            PATH + PATH_MUSIC + "hop_hell.wav",
            PATH + PATH_MUSIC + "boss_theme_sound.wav"
        },
    
        {
            PATH + PATH_MUSIC + "clumsy_earth.wav",
            PATH + PATH_MUSIC + "clumsy_heaven.wav",
            PATH + PATH_MUSIC + "clumsy_hell.wav",
            PATH + PATH_MUSIC + "boss_theme_sound.wav"
        },
        
        {
            PATH + PATH_MUSIC + "reborn_earth.wav",
            PATH + PATH_MUSIC + "reborn_heaven.wav",
            PATH + PATH_MUSIC + "reborn_hell.wav",
            PATH + PATH_MUSIC + "boss_theme_sound.wav"
        },
        
        {
            PATH + PATH_MUSIC + "pixel_earth.wav",
            PATH + PATH_MUSIC + "pixel_heaven.wav",
            PATH + PATH_MUSIC + "pixel_hell.wav",
            PATH + PATH_MUSIC + "boss_theme_sound.wav"
        },
        
        {
            PATH + PATH_MUSIC + "mechanic_earth.wav",
            PATH + PATH_MUSIC + "mechanic_heaven.wav",
            PATH + PATH_MUSIC + "mechanic_hell.wav",
            PATH + PATH_MUSIC + "boss_theme_sound.wav"
        }
    };
    static final int FRAME_WIDTH = 1280;
    static final int FRAME_HEIGHT = 720;

    static final int CHARACTER_WIDTH = 50;
    static final int CHARACTER_HEIGHT = 50;
    static final int CHARACTER_SPEED = 4;
    static final int CHARACTER_START_X = 50;//50
    static final int CHARACTER_START_Y = 550;//550
    static final int SELECTION_PREVIEW_WIDTH = 150;
    static final int SELECTION_PREVIEW_HEIGHT = 193;

    static final int SELECTION_BANNER_X = 98;
    static final int SELECTION_BANNER_Y = 92;
    static final int SELECTION_BANNER_WIDTH = 1083;
    static final int SELECTION_BANNER_HEIGHT = 536;

    static final int SELECTION_CENTER_X = 100;
    static final int SELECTION_CENTER_Y = 234;
    static final int SELECTION_CENTER_WIDTH = 1080;
    static final int SELECTION_CENTER_HEIGHT = 350;

    static final int SELECTION_CARD_WIDTH = 160;
    static final int SELECTION_CARD_HEIGHT = 240;
    static final int SELECTION_CARD_SPACING = 56;

    static final int GROUND_Y = 550;

    static final double GRAVITY = 0.5;
    static final double JUMP_STRENGTH = -11.0;

    static final int FRAME_SPEED = 5;
    static final int TIMER_DELAY = 16; // ~60 FPS

    static final int GROUND_R = 34;
    static final int GROUND_G = 139;
    static final int GROUND_B = 34;

    static final int BOSS_WIDTH = 835;
    static final int BOSS_HEIGHT = 646;
    static final int BOSS_HEALTH = 200;
    static final int BOSS_ATTACK_COOLDOWN = 180;
    static final int BOSS_FRAME_SPEED = 10;
    
    
    //Game Title
    static final int TITLE_WIDTH   = 583;
    static final int TITLE_HEIGHT  = 244;   
    
    //Skin&Exit Button
    static final int SKINEXIT_BUTTON_WIDTH   = 311;
    static final int SKINEXIT_BUTTON_HEIGHT  = 86;   
    
    //Start Button
    static final int START_BUTTON_WIDTH   = 462;
    static final int START_BUTTON_HEIGHT  = 155;   
    
    //Settings Button
    static final int SETTINGS_BUTTON_WIDTH   = 148;
    static final int SETTINGS_BUTTON_HEIGHT  = 148;
    static final int SETTINGS_WIDTH   = 1083;
    static final int SETTINGS_HEIGHT  = 536; 
    
    //Credit Button & Credit label
    static final int CREDIT_BUTTON_WIDTH   = 143;
    static final int CREDIT_BUTTON_HEIGHT  = 140; 
    static final int CREDIT_WIDTH   = 1083;
    static final int CREDIT_HEIGHT  = 536; 
    
    //Howtoplay Button
    static final int HTP_BUTTON_WIDTH   = 131;
    static final int HTP_BUTTON_HEIGHT  = 161; 
    static final int HTP_WIDTH   = 1083;
    static final int HTP_HEIGHT  = 536; 
    
    //Close Button
    static final int CLOSE_BUTTON_WIDTH   = 99;
    static final int CLOSE_BUTTON_HEIGHT  = 99; 
    static final int HOME_BUTTON_WIDTH   = 97;
    static final int HOME_BUTTON_HEIGHT  = 96; 
    
//Sound Button & Album
    static final int SOUND_BUTTON_WIDTH   = 86;
    static final int SOUND_BUTTON_HEIGHT  = 84;
    static final int ALBUM_WIDTH   = 193;
    static final int ALBUM_HEIGHT  = 193;
     //Earth Level
    static final int EARTH_LEVEL_WIDTH   = 463;
    static final int EARTH_LEVEL_HEIGHT  = 143;

    //Heaven Level
    static final int HEAVEN_LEVEL_WIDTH   = 465;
    static final int HEAVEN_LEVEL_HEIGHT  = 137;

    //Hell Level
    static final int HELL_LEVEL_WIDTH   = 458;
    static final int HELL_LEVEL_HEIGHT  = 139;
}
