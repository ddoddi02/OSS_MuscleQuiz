import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;

public class QuizPanel extends JFrame {

    private static final int PANEL_WIDTH = 1600;
    private static final int PANEL_HEIGHT = 900;

    // private static final int IMAGE_WIDTH = 600;
    // private static final int IMAGE_HEIGHT = 600;

    private int keyNumber;
    private ArrayList<Integer> usedNum = new ArrayList<>();
    private int answerCount = 0;

    private Map<Integer, Muscle> musclesMap = MusclesImporter.loadMusclesFromJSON("muscles.json");;

    private Image screenImage;
    private Graphics screenGraphic;
    private Image introBackGround = new ImageIcon("images/startscreen1.jpg").getImage();

    private Image muscleImage;

    private JButton startButton = new JButton("시작");
    private JButton exitButton = new JButton("프로그램 종료");
    private JButton answerButton = new JButton("답 입력");
    private JButton nextButton = new JButton("다음 문제");
    private JButton restartButton = new JButton("재시작");

    private JLabel label = new JLabel("근육이름맞추기 1.0");
    private JLabel answerLabel = new JLabel();

    private JTextField txtfld = new JTextField();

    private boolean isStarted = false;
    private boolean isAnswerHanded = false;
    private boolean isNext = false;
    private boolean isEnd = false;
    
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
        add(label); // 제목 : 근육이름 맞추기

        answerLabel.setBounds(700, 200, 1000, 200);
        answerLabel.setFont(font.deriveFont(30f));
        add(answerLabel);
        answerLabel.setVisible(false);
        // 정답/오답 표시


        // 시작 버튼. 누르면 사라지고 isStarted = true
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
                label.setVisible(false);
            }
        });
        add(startButton);


        // 종료 버튼. 누르면 종료
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


        // 정답 입력하는 텍스트필드. 시작버튼 누르면 보임.
        txtfld.setBounds(800, 400, 200, 50);
        add(txtfld);
        txtfld.setVisible(false);


        // 정답 제출 버튼. 시작 버튼 누르면 보임.
        answerButton.setBounds(800, 450, 200, 50);
        answerButton.setBackground(new Color(0, 0, 0));
        answerButton.setForeground(new Color(255, 255, 255));
        answerButton.setFont(font.deriveFont(20f));
        answerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                answerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                answerButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            @Override
            public void mousePressed(MouseEvent e) {
                isAnswerHanded = true;
                isNext = false;
                if(!usedNum.contains(keyNumber) && (usedNum.size() < 32)) {
                    usedNum.add(keyNumber);
                } // 사용한 숫자 기록: 리스트에 없는 숫자여야 하고 리스트의 요소가 32개까지만 들어가도록
            }
        });
        add(answerButton);
        answerButton.setVisible(false);


        // 다음 문제로 넘어가는 버튼. 정답 입력하면 보임.
        nextButton.setBounds(800, 500, 200, 50);
        nextButton.setBackground(new Color(0, 0, 0));
        nextButton.setForeground(new Color(255, 255, 255));
        nextButton.setFont(font.deriveFont(20f));
        nextButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                nextButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                nextButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            @Override
            public void mousePressed(MouseEvent e) {
                keyNumber = RandomInteger.getRandomInteger();
                txtfld.setText(null);
                isNext = true;
            }
        });
        add(nextButton);
        nextButton.setVisible(false);

        restartButton.setBounds(700, 400, 200, 50);
        restartButton.setBackground(new Color(0, 0, 0));
        restartButton.setForeground(new Color(255, 255, 255));
        restartButton.setFont(font.deriveFont(20f));
        restartButton.addMouseListener(new MouseAdapter() {
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
                restartButton.setVisible(false);
                exitButton.setBounds(1350, 30, 200, 50);
                usedNum.clear();
                isStarted = true;
                label.setVisible(false);
            }
        });
        add(startButton);
        restartButton.setVisible(false);
    }


    // JFrame에서 자동으로 실행되는 paint 메소드.
    public void paint(Graphics g) {
        screenImage = createImage(PANEL_WIDTH, PANEL_HEIGHT);
        screenGraphic = screenImage.getGraphics();
        screenDraw(screenGraphic);
        g.drawImage(screenImage, 0, 0, null);
    }

    // 마찬가지로 자동실행. 여기서 화면 그리는걸 state 패턴으로 하면 좋을듯
    public void screenDraw(Graphics g) {
        g.drawImage(introBackGround, 0, 0, null);
        if (isStarted) {
            quizStarted(g);
        }
        if (isAnswerHanded) {
            isAnswer(txtfld.getText());
        }
        if (isNext) {
            nextQuiz(g);
            nextButton.setVisible(false);
        }
        if (isEnd) {
            endQuiz(g);
            answerButton.setVisible(false);
            txtfld.setVisible(false);
            answerLabel.setVisible(false);
        }
        paintComponents(g);
        this.repaint();
    }


    // 시작 버튼 누르면 뜨는 화면
    public void quizStarted(Graphics g) {
        keyNumber = RandomInteger.getRandomInteger();
        muscleImage = new ImageIcon(musclesMap.get(keyNumber).getImage()).getImage();
        // keyNumber(난수)를 key로 해서 맵에서 근육 사진 추출
        txtfld.setVisible(true);
        answerButton.setVisible(true);
        // 텍스트필드랑 정답제출버튼 표시
        g.drawImage(muscleImage, 0, 50, null);
        // 근육 사진 화면에 표시
    }


    // 정답인지 확인
    public void isAnswer(String answer) {
        Muscle realAnswer = musclesMap.get(keyNumber);
        // 이전에 사용한 숫자를 키로 사용
        String korean1 = realAnswer.getNewVersionKorean();
        String korean2 = realAnswer.getOldVersionKorean();
        String english = realAnswer.getEnglishAnswer();
        if (answer.equals(korean1) || answer.equals(korean2) || answer.equals(english)) {
            answerLabel.setText("<html>정답입니다<br>사용자가 입력한 답&nbsp:&nbsp;" + answer + "<br>정답&nbsp:&nbsp" + korean1 + "&nbsp/&nbsp" + korean2 + "&nbsp/&nbsp;" + english + "</html>");
            answerCount++;
            answerLabel.setVisible(true);
        } else {
            answerLabel.setText("<html>오답입니다<br>사용자가 입력한 답&nbsp:&nbsp;" + answer + "<br>정답&nbsp:&nbsp" + korean1 + "&nbsp/&nbsp" + korean2 + "&nbsp/&nbsp;" + english + "</html>");
            answerLabel.setVisible(true);
        }

        if (usedNum.size() == 32) {
            isEnd = true;
        } else {
            nextButton.setVisible(true);
        }
    }

    public void nextQuiz(Graphics g) {
        muscleImage = new ImageIcon(musclesMap.get(keyNumber).getImage()).getImage();
        g.drawImage(muscleImage, 0, 50, null);
        answerLabel.setText(null);
    }

    public void endQuiz(Graphics g) {
        label.setText("맞춘 정답 수 : " + answerCount + " / " + usedNum.size() + " RETRY?");
        label.setVisible(true);
        answerLabel.setVisible(false);
    }
}