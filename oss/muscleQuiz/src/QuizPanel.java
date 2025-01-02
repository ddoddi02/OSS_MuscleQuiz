import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class QuizPanel extends JFrame {

    private static final int PANEL_WIDTH = 1600;
    private static final int PANEL_HEIGHT = 900;

    private static final int IMAGE_WIDTH = 600;
    private static final int IMAGE_HEIGHT = 600;

    private Map<Integer, Muscle> musclesMap = MusclesImporter.loadMusclesFromJSON("muscles.json");;

    private Image screenImage;
    private Graphics screenGraphic;
    private Image introBackGround = new ImageIcon("images/startscreen1.jpg").getImage();

    private Image muscleImage = new ImageIcon(musclesMap.get(1).getImage()).getImage();

    private JButton startButton = new JButton("시작");
    private JButton exitButton = new JButton("프로그램 종료");
    private JLabel label = new JLabel("근육이름맞추기 1.0");

    private boolean isStarted = false;
    
    private Font font = new Font("fonts/high1 Wonchuri Body R.ttf", Font.PLAIN, 40);

    public QuizPanel() {
        setUndecorated(true);
        setTitle("Muscle name quiz");
        setSize(PANEL_WIDTH,PANEL_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);

        label.setBounds(635, 30, 500, 50);
        label.setFont(font);
        add(label);

        startButton.setBounds(700, 400, 200, 50);
        startButton.setBackground(new Color(0, 0, 0));
        startButton.setForeground(new Color(255, 255, 255));
        startButton.setFont(font.deriveFont(20f));
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            @Override
            public void mousePressed(MouseEvent e) {
                startButton.setVisible(false);
                exitButton.setBounds(1350, 30, 200, 50);
                setBackground(new Color(255, 255, 255, 0)); // 배경 색 지정 / 기본은 검정
                introBackGround = null;
                isStarted = true;
            }
        });
        add(startButton);

        exitButton.setBounds(700, 500, 200, 50);
        exitButton.setBackground(new Color(0, 0, 0));
        exitButton.setForeground(new Color(255, 255, 255));
        exitButton.setFont(font.deriveFont(20f));
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            @Override
            public void mousePressed(MouseEvent e) {
                System.exit(0);
            }
        });
        add(exitButton);
    }

    public void paint(Graphics g) {
        screenImage = createImage(PANEL_WIDTH, PANEL_HEIGHT);
        screenGraphic = screenImage.getGraphics();
        screenDraw(screenGraphic);
        g.drawImage(screenImage, 0, 0, null);
    }

    public void screenDraw(Graphics g) {
        g.drawImage(introBackGround, 0, 0, null);
        if (isStarted) {
            quizStarted(g);
        }
        paintComponents(g);
        this.repaint();
    }

    public void quizStarted(Graphics g) {
        List<Integer> usedNum = new ArrayList<>();
        int number = RandomInteger.getRandomInteger();
        muscleImage = new ImageIcon(musclesMap.get(number).getImage()).getImage();
        usedNum = number;
        g.drawImage(muscleImage, 0, 50, null);
    }
}
