package Project3_6713225;
/*6713225 Chatchawal Labkim
6713232 Thanapon Rojanabenjakul
6713234 Thanawat Mekwattanawanit
6713241 Piyangkun Onsee
*/
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class Settings extends JPanel {

    private final JSlider volume;
    private final JLabel drawpane;
    private JLabel songAlbumCover;
    private final JButton close_button;
    private JButton sound_button;
    private final MyImageIcon closeButtonIcon, Img, background, soundIcon, MuteIcon, albumCoverIcon,
            hopMusicIcon, clumsyMusicIcon, rebornMusicIcon, pixelMusicIcon, mechanicMusicIcon;
    private final Menu mainApp;
    private int framewidth = GameConstants.SETTINGS_WIDTH;
    private int frameheight = GameConstants.SETTINGS_HEIGHT;
    private Clip themeSound,soundEffect;
    private JComboBox song_select;
    private String[] songTitles = {"Hop & Harmony", "Clumsy Music", "Reborn Music", "Pixel Music", "Mechanic Music"};
    private boolean isMuted = false;
    private JTextField name_enter;
    private String name = "guest";
    private int selectedSongIndex = 0;

    private void updateAlbumCover(int index) {
        switch (index) {
            case 0:
                songAlbumCover.setIcon(hopMusicIcon);
                break;
            case 1:
                songAlbumCover.setIcon(clumsyMusicIcon);
                break;
            case 2:
                songAlbumCover.setIcon(rebornMusicIcon);
                break;
            case 3:
                songAlbumCover.setIcon(pixelMusicIcon);
                break;
            case 4:
                songAlbumCover.setIcon(mechanicMusicIcon);
                break;
        }
    }

    public Settings(Menu app) {
        this.mainApp = app;
        setLayout(new BorderLayout());

        setVisible(false);
        background = new MyImageIcon(GameConstants.FILE_BG).resize(1280, 720);
        Img = new MyImageIcon(GameConstants.FILE_SETTINGS_BG).resize(framewidth, frameheight);
        JLabel backgroundLayer = new JLabel(background);
        backgroundLayer.setLayout(null);
        drawpane = new JLabel(Img);
        drawpane.setBounds(98, 91, framewidth, frameheight);

        close_button = new JButton();
        close_button.addActionListener((ActionEvent e) -> {
            soundEffect = Utilities.playSoundEffect(GameConstants.FILE_BUTTON_SOUND);
            app.showCard("MAIN_MENU");
        });
        closeButtonIcon = new MyImageIcon(GameConstants.FILE_CLOSE_BUTTON).resize(GameConstants.CLOSE_BUTTON_WIDTH, GameConstants.CLOSE_BUTTON_HEIGHT);
        close_button.setIcon(closeButtonIcon);
        close_button.setBorderPainted(false);
        close_button.setContentAreaFilled(false);
        close_button.setFocusPainted(false);
        close_button.setText("");
        close_button.setBounds(1098, 72, GameConstants.CLOSE_BUTTON_WIDTH, GameConstants.CLOSE_BUTTON_HEIGHT);

        //Sound
        MuteIcon = new MyImageIcon(GameConstants.FILE_MUTE_SOUND).resize(GameConstants.SOUND_BUTTON_WIDTH, GameConstants.SOUND_BUTTON_HEIGHT);
        soundIcon = new MyImageIcon(GameConstants.FILE_OPEN_SOUND).resize(GameConstants.SOUND_BUTTON_WIDTH, GameConstants.SOUND_BUTTON_HEIGHT);

        sound_button = new JButton();
        sound_button.addActionListener((ActionEvent e) -> {
            isMuted = !isMuted;

            if (isMuted) {
                sound_button.setIcon(MuteIcon);
                Utilities.setGlobalMuteState(true);
            } else {
                sound_button.setIcon(soundIcon);
                Utilities.setGlobalMuteState(false);
            }
        });
        isMuted = Utilities.isGlobalMuted();
        sound_button.setIcon(isMuted ? MuteIcon : soundIcon);
        sound_button.setBorderPainted(false);
        sound_button.setContentAreaFilled(false);
        sound_button.setFocusPainted(false);
        sound_button.setText("");
        sound_button.setBounds(928, 249, GameConstants.SOUND_BUTTON_WIDTH, GameConstants.SOUND_BUTTON_HEIGHT);

        volume = new JSlider(JSlider.HORIZONTAL, 0, 100, (int)(Utilities.getcurrentVolume()*100));
        volume.setOpaque(false);
        volume.setBounds(282, 277, 550, 27);
        volume.addChangeListener(e -> {
            float vol = volume.getValue() / 100.0f;
            Utilities.setThemeVolume(vol);
        });

        //Song Select
        hopMusicIcon = new MyImageIcon(GameConstants.FILE_HOP_MUSIC_ICON).resize(150, 150);
        clumsyMusicIcon = new MyImageIcon(GameConstants.FILE_CLUSMY_MUSIC_ICON).resize(150, 150);
        rebornMusicIcon = new MyImageIcon(GameConstants.FILE_REBORN_MUSIC_ICON).resize(150, 150);
        pixelMusicIcon = new MyImageIcon(GameConstants.FILE_PIXEL_MUSIC_ICON).resize(150, 150);
        mechanicMusicIcon = new MyImageIcon(GameConstants.FILE_MECHANIC_MUSIC_ICON).resize(150, 150);

        albumCoverIcon = new MyImageIcon(GameConstants.FILE_HOP_MUSIC_ICON).resize(150, 150);
        songAlbumCover = new JLabel(albumCoverIcon);
        songAlbumCover.setBounds(918, 392, GameConstants.ALBUM_WIDTH, GameConstants.ALBUM_HEIGHT);

        song_select = new JComboBox(songTitles);
        updateAlbumCover(Utilities.getCurrentAlbumIndex());
        song_select.setSelectedIndex(Utilities.getCurrentAlbumIndex());
        song_select.addItemListener((ItemEvent e) -> {
            int selectedIndex = song_select.getSelectedIndex();
            updateAlbumCover(selectedIndex);
            Utilities.updateThemeSong(selectedIndex, 0);
        });
        song_select.setBounds(280, 460, 565, 55);

        name_enter = new JTextField(name);
        name_enter.setBounds(280, 560, 565, 55);
        name_enter.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == ' ') {
                    e.consume();
                }
            }
        });

        name_enter.addActionListener(e -> {
            String newName = name_enter.getText().trim();
            if (!newName.isEmpty()) {
                name = newName;
                StageStatistics.setPlayerName(name);
            }
        });

        name_enter.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String newName = name_enter.getText().trim();
                if (!newName.isEmpty()) {
                    name = newName;
                    
                    StageStatistics.setPlayerName(name);
                }
            }
        });
        backgroundLayer.add(name_enter);
        backgroundLayer.add(song_select);
        backgroundLayer.add(songAlbumCover);
        backgroundLayer.add(volume);
        backgroundLayer.add(close_button);
        backgroundLayer.add(sound_button);
        backgroundLayer.add(drawpane);
        add(backgroundLayer, BorderLayout.CENTER);

    }

    public String givename() {
        return name.isEmpty() ? "guest" : name;
    }

    public int getSelectedSongIndex() {
        return selectedSongIndex;
    }

}

class SettingsOverlay extends JLabel {

    private final JSlider volume;
    private final JLabel drawpane;
    private JLabel songAlbumCover;
    private final JButton close_button, home_button;
    private JButton sound_button;
    private final MyImageIcon closeButtonIcon, Img, soundIcon, MuteIcon, albumCoverIcon,
            hopMusicIcon, clumsyMusicIcon, rebornMusicIcon, pixelMusicIcon, mechanicMusicIcon, homeButtonIcon;
    private final int framewidth = GameConstants.SETTINGS_WIDTH;
    private final int frameheight = GameConstants.SETTINGS_HEIGHT;
    private JComboBox song_select;
    private String[] songTitles = {"Hop & Harmony", "Clumsy Music", "Reborn Music", "Pixel Music", "Mechanic Music"};
    private boolean isMuted = false;
    private Clip soundEffect;
    private final Runnable onCloseCallback;

    private void updateAlbumCover(int index) {
        switch (index) {
            case 0:
                songAlbumCover.setIcon(hopMusicIcon);
                break;
            case 1:
                songAlbumCover.setIcon(clumsyMusicIcon);
                break;
            case 2:
                songAlbumCover.setIcon(rebornMusicIcon);
                break;
            case 3:
                songAlbumCover.setIcon(pixelMusicIcon);
                break;
            case 4:
                songAlbumCover.setIcon(mechanicMusicIcon);
                break;
        }
    }

    public SettingsOverlay(Runnable onClose) {
        this.onCloseCallback = onClose;
        setLayout(new BorderLayout());
        setBounds(0, 0, 1280, 720);

        //background = new MyImageIcon(GameConstants.FILE_BG).resize(1280, 720);
        Img = new MyImageIcon(GameConstants.FILE_SETTINGS_BG).resize(framewidth, frameheight);
        JLabel backgroundLayer = new JLabel();
        backgroundLayer.setLayout(null);
        drawpane = new JLabel(Img);
        drawpane.setBounds(98, 91, framewidth, frameheight);
        

        // Close button
        close_button = new JButton();
        close_button.addActionListener((ActionEvent e) -> {
            if (onCloseCallback != null) {
                onCloseCallback.run();
               soundEffect = Utilities.playSoundEffect(GameConstants.FILE_BUTTON_SOUND);
            }
        });
        closeButtonIcon = new MyImageIcon(GameConstants.FILE_CLOSE_BUTTON).resize(GameConstants.CLOSE_BUTTON_WIDTH, GameConstants.CLOSE_BUTTON_HEIGHT);
        close_button.setIcon(closeButtonIcon);
        close_button.setBorderPainted(false);
        close_button.setContentAreaFilled(false);
        close_button.setFocusPainted(false);
        close_button.setText("");
        close_button.setBounds(1098, 72, GameConstants.CLOSE_BUTTON_WIDTH, GameConstants.CLOSE_BUTTON_HEIGHT);

        // Sound button
        MuteIcon = new MyImageIcon(GameConstants.FILE_MUTE_SOUND).resize(GameConstants.SOUND_BUTTON_WIDTH, GameConstants.SOUND_BUTTON_HEIGHT);
        soundIcon = new MyImageIcon(GameConstants.FILE_OPEN_SOUND).resize(GameConstants.SOUND_BUTTON_WIDTH, GameConstants.SOUND_BUTTON_HEIGHT);

        sound_button = new JButton();
        sound_button.addActionListener((ActionEvent e) -> {
            isMuted = !isMuted;

            if (isMuted) {
                sound_button.setIcon(MuteIcon);
                Utilities.setGlobalMuteState(true);
            } else {
                sound_button.setIcon(soundIcon);
                Utilities.setGlobalMuteState(false);
            }
        });
        isMuted = Utilities.isGlobalMuted();
        sound_button.setIcon(isMuted ? MuteIcon : soundIcon);
        sound_button.setBorderPainted(false);
        sound_button.setContentAreaFilled(false);
        sound_button.setFocusPainted(false);
        sound_button.setText("");
        sound_button.setBounds(928, 249, GameConstants.SOUND_BUTTON_WIDTH, GameConstants.SOUND_BUTTON_HEIGHT);

        // Volume slider
        volume = new JSlider(JSlider.HORIZONTAL, 0, 100,(int)(Utilities.getcurrentVolume()*100));
        volume.setOpaque(false);
        volume.setBounds(282, 277, 550, 27);
        volume.addChangeListener(e -> {
            float vol = volume.getValue() / 100.0f;
            Utilities.setThemeVolume(vol);
        });

        // Song Select
        hopMusicIcon = new MyImageIcon(GameConstants.FILE_HOP_MUSIC_ICON).resize(150, 150);
        clumsyMusicIcon = new MyImageIcon(GameConstants.FILE_CLUSMY_MUSIC_ICON).resize(150, 150);
        rebornMusicIcon = new MyImageIcon(GameConstants.FILE_REBORN_MUSIC_ICON).resize(150, 150);
        pixelMusicIcon = new MyImageIcon(GameConstants.FILE_PIXEL_MUSIC_ICON).resize(150, 150);
        mechanicMusicIcon = new MyImageIcon(GameConstants.FILE_MECHANIC_MUSIC_ICON).resize(150, 150);

        albumCoverIcon = new MyImageIcon(GameConstants.FILE_HOP_MUSIC_ICON).resize(150, 150);
        songAlbumCover = new JLabel(albumCoverIcon);
        songAlbumCover.setBounds(918, 392, GameConstants.ALBUM_WIDTH, GameConstants.ALBUM_HEIGHT);

        song_select = new JComboBox(songTitles);
        updateAlbumCover(Utilities.getCurrentAlbumIndex());
        song_select.setSelectedIndex(Utilities.getCurrentAlbumIndex());
        song_select.addItemListener((ItemEvent e) -> {
            int selectedIndex = song_select.getSelectedIndex();
            updateAlbumCover(selectedIndex);
            Utilities.updateThemeSong(selectedIndex, Utilities.getCurrentLevelIndex());

        });

        home_button = new JButton();
        home_button.addActionListener((ActionEvent e) -> {
            soundEffect = Utilities.playSoundEffect(GameConstants.FILE_BUTTON_SOUND);
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
            Menu app = new Menu();
        });
        homeButtonIcon = new MyImageIcon(GameConstants.FILE_HOME_BUTTON).resize(GameConstants.HOME_BUTTON_WIDTH, GameConstants.HOME_BUTTON_HEIGHT);
        home_button.setIcon(homeButtonIcon);
        home_button.setBorderPainted(false);
        home_button.setContentAreaFilled(false);
        home_button.setFocusPainted(false);
        home_button.setText("");
        home_button.setBounds(115, 506, GameConstants.HOME_BUTTON_WIDTH, GameConstants.HOME_BUTTON_HEIGHT);

        song_select.setBounds(280, 460, 565, 55);

        backgroundLayer.add(song_select);
        backgroundLayer.add(songAlbumCover);
        backgroundLayer.add(volume);
        backgroundLayer.add(home_button);
        backgroundLayer.add(close_button);
        backgroundLayer.add(sound_button);
        backgroundLayer.add(drawpane);
        add(backgroundLayer, BorderLayout.CENTER);

        // ESC key listener
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (onCloseCallback != null) {
                        soundEffect = Utilities.playSoundEffect(GameConstants.FILE_BUTTON_SOUND);
                        onCloseCallback.run();
                    }
                }
            }
        });

    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }

}
