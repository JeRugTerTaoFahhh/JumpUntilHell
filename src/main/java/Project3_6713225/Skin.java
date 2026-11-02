package Project3_6713225;
/*6713225 Chatchawal Labkim
6713232 Thanapon Rojanabenjakul
6713234 Thanawat Mekwattanawanit
6713241 Piyangkun Onsee
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.sound.sampled.Clip;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
/**
 * Character selection screen before starting the game
 */
public class Skin extends JPanel {
    private int selectedSkinIndex = 0;
    private Utilities.CharacterSkin[] availableSkins;
    
    // Load images from Utilities
    private Image backgroundImage;
    private Image skinBannerImage;
    private final Menu         mainApp;
    private final JButton                 close_button;    
    private final MyImageIcon                  closeButtonIcon;  
    private Clip soundEffect;
    
    
    public Skin(Menu mainApp) {
        setLayout(null);
        setPreferredSize(new Dimension(GameConstants.FRAME_WIDTH, GameConstants.FRAME_HEIGHT));
        
        availableSkins = Utilities.getAvailableSkins();
        this.mainApp = mainApp;
        
        // Load images using Utilities
        loadImages();
        
        // Main panel with custom background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        mainPanel.setBounds(0, 0, GameConstants.FRAME_WIDTH, GameConstants.FRAME_HEIGHT);
        mainPanel.setLayout(null);
        mainPanel.setOpaque(true);
        
        close_button = new JButton();
        close_button.addActionListener((ActionEvent e) -> {
            setVisible(false);
            soundEffect = Utilities.playSoundEffect(GameConstants.FILE_BUTTON_SOUND);
            mainApp.showCard("MAIN_MENU");
        });       
        closeButtonIcon = new MyImageIcon(GameConstants.FILE_CLOSE_BUTTON ).resize(GameConstants.CLOSE_BUTTON_WIDTH, GameConstants.CLOSE_BUTTON_HEIGHT);
        close_button.setIcon(closeButtonIcon);   
        close_button.setBorderPainted(false);       
        close_button.setContentAreaFilled(false);  
        close_button.setFocusPainted(false);      
        close_button.setText("");                       
        close_button.setBounds(1098,72,GameConstants.CLOSE_BUTTON_WIDTH, GameConstants.CLOSE_BUTTON_HEIGHT); 
        
        add(close_button);
        
        
        
        // Title panel with skin banner image
        JPanel titlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (skinBannerImage != null) {
                    g.drawImage(skinBannerImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        titlePanel.setOpaque(false);
        titlePanel.setBounds(
            GameConstants.SELECTION_BANNER_X, 
            GameConstants.SELECTION_BANNER_Y, 
            GameConstants.SELECTION_BANNER_WIDTH, 
            GameConstants.SELECTION_BANNER_HEIGHT
        );
        mainPanel.add(titlePanel);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(null); 
        centerPanel.setOpaque(false);
        centerPanel.setBounds(
            GameConstants.SELECTION_CENTER_X, 
            GameConstants.SELECTION_CENTER_Y, 
            GameConstants.SELECTION_CENTER_WIDTH, 
            GameConstants.SELECTION_CENTER_HEIGHT
        );
        
        ButtonGroup buttonGroup = new ButtonGroup();
        
        // Calculate card positions
        int totalWidth = (GameConstants.SELECTION_CARD_WIDTH * availableSkins.length) + 
                        (GameConstants.SELECTION_CARD_SPACING * (availableSkins.length - 1));
        int startX = (centerPanel.getWidth() - totalWidth) / 2;
        
        // Create character cards
        for (int i = 0; i < availableSkins.length; i++) {
            final int skinIndex = i;
            int cardX = startX + (i * (GameConstants.SELECTION_CARD_WIDTH + GameConstants.SELECTION_CARD_SPACING));
            JPanel characterCard = createCharacterCard(availableSkins[i], skinIndex, buttonGroup);
            characterCard.setBounds(cardX, 0, GameConstants.SELECTION_CARD_WIDTH, GameConstants.SELECTION_CARD_HEIGHT);
            centerPanel.add(characterCard);
        }
        
        mainPanel.add(centerPanel);
        
        
        mainPanel.setComponentZOrder(titlePanel, mainPanel.getComponentCount() - 1);
        
        
        add(mainPanel);
    }
    
        
    private void loadImages() {
        backgroundImage = Utilities.loadSingleImage(GameConstants.FILE_BACKGROUND);
        skinBannerImage = Utilities.loadSingleImage(GameConstants.FILE_SKIN_BANNER);
    }
    
    /**
     * Create a character selection card
     */
    private JPanel createCharacterCard(Utilities.CharacterSkin skin, int index, ButtonGroup group) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(5, 5));
        card.setOpaque(false);
        
        // Character preview panel
        JPanel previewPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                // Try to load and draw first frame of character
                try {
                    BufferedImage originalImage = ImageIO.read(new File(skin.previewImagePath));
                    
                    // Create high quality resized image
                    BufferedImage resizedImage = new BufferedImage(
                        GameConstants.SELECTION_PREVIEW_WIDTH, 
                        GameConstants.SELECTION_PREVIEW_HEIGHT, 
                        BufferedImage.TYPE_INT_ARGB);
                    
                    Graphics2D g2 = resizedImage.createGraphics();
                    g2.drawImage(originalImage, 0, 0, GameConstants.SELECTION_PREVIEW_WIDTH, GameConstants.SELECTION_PREVIEW_HEIGHT, null);
                    g2.dispose();
                    
                    int x = (getWidth() - GameConstants.SELECTION_PREVIEW_WIDTH) / 2;
                    int y = (getHeight() - GameConstants.SELECTION_PREVIEW_HEIGHT) / 2;
                    g2d.drawImage(resizedImage, x, y, this);
                } catch (IOException e) {
                    // Fallback placeholder
                    g2d.setColor(Color.ORANGE);
                    g2d.fillOval(30, 30, 80, 80);
                    g2d.setColor(Color.BLACK);
                    g2d.setFont(new Font("Arial", Font.BOLD, 10));
                    g2d.drawString("No Image", 40, 75);
                }
            }
        };
        previewPanel.setOpaque(false);
        previewPanel.setPreferredSize(new Dimension(150, 200));
        card.add(previewPanel, BorderLayout.CENTER);
        
        // Character name and radio button panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setOpaque(false);
        
        JLabel nameLabel = new JLabel(skin.name, JLabel.CENTER);
        nameLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
        nameLabel.setForeground(new Color(64, 224, 208));
        bottomPanel.add(nameLabel, BorderLayout.NORTH);
        
        JRadioButton selectButton = new JRadioButton("Select");
        selectButton.setHorizontalAlignment(JRadioButton.CENTER);
        selectButton.setOpaque(false);
        selectButton.setFont(new Font("Monospaced", Font.BOLD, 18));
        selectButton.setForeground(new Color(64, 224, 208));
        
        // First character selected by default
        if (index == 0) {
            selectButton.setSelected(true);
            card.setBorder(BorderFactory.createLineBorder(new Color(64, 224, 208), 4));
        } else {
            card.setBorder(null);
        }
        
        selectButton.addActionListener(e -> {
            selectedSkinIndex = index;
            Utilities.setSelectedSkin(availableSkins[index]);
            updateCardBorders(card.getParent(), index);
        });
        
        group.add(selectButton);
        bottomPanel.add(selectButton, BorderLayout.CENTER);
        
        card.add(bottomPanel, BorderLayout.SOUTH);
        
        return card;
    }
    
    /**
     * Update border colors of character cards
     */
    private void updateCardBorders(Container parent, int selectedIndex) {
        Component[] components = parent.getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof JPanel card) {
                if (i == selectedIndex) {
                    card.setBorder(BorderFactory.createLineBorder(new Color(64, 224, 208), 4));
                } else {
                    card.setBorder(null);
                }
                card.repaint();
            }
        }
    }
}