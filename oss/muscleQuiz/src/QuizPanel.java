import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;

// 주의!!!!!!!!!! 기본 생성자 외에는 JFrame 에서 끈임없이 메소드 반복 실행!!!!!!!!!!
public class QuizPanel extends JFrame {

    private static final int PANEL_WIDTH = 1600;
    private static final int PANEL_HEIGHT = 900; // 화면의 너비/높이

    private static final int QUIZ_NUM = 10; // 퀴즈의 개수

    private int keyNumber; // 맵에서 키로 사용하는 숫자
    private ArrayList<Integer> usedNum = new ArrayList<>(); // 사용한 숫자 저장해두는 리스트
    private int answerCount; // 정답 카운트

    private Map<Integer, Muscle> musclesMap = MusclesImporter.loadMusclesFromJSON("muscles.json");;
    // 근육의 정보를 저장하는 맵. 숫자, 근육 이름 세 가지, 이미지 주소가 들어있다. 숫자를 키로 사용한다.

    private Image screenImage;
    private Graphics screenGraphic;
    private Image introBackGround = new ImageIcon("images/startscreen1.jpg").getImage();
    // JFrame 에서 그리는 화면들

    private Image muscleImage; // 근육의 이미지를 저장하는 Image 객체

    private JButton startButton = new JButton("시작");
    private JButton exitButton = new JButton("프로그램 종료");
    private JButton answerButton = new JButton("답 입력");
    private JButton nextButton = new JButton("다음 문제");
    private JButton resultButton = new JButton("결과 확인");
    // 퀴즈에서 사용하는 버튼들.

    private JLabel label = new JLabel("근육이름맞추기 1.0");
    private JLabel answerLabel = new JLabel();
    // 문자열 표시줄. 현재 진행도, 정답률, 정답 여부 등을 표시한다.

    private JTextField txtfld = new JTextField();
    // 정답을 입력할 필드

    private boolean isStarted = false;
    // 초기 화면과 퀴즈 진행 화면을 구분하는 flag
    
    private Font font = new Font("fonts/high1 Wonchuri Body R.ttf", Font.PLAIN, 40);
    // 프로그램에서 사용하는 폰트. 출처 - 공유마당 하이원 원추리체 R

    public QuizPanel() {
        setUndecorated(true);
        setTitle("Muscle name quiz");
        setSize(PANEL_WIDTH,PANEL_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);
        // panel의 기본 설정. 사이즈 조절, 위치이동 불가


        // 이 아래로는 라벨, 버튼 등의 위치 및 동작 설정
        label.setBounds(500, 30, 600, 50);
        label.setFont(font);
        label.setHorizontalAlignment(SwingConstants.CENTER); // 글씨 가운데 정렬
        add(label); // 제목 : 근육이름 맞추기

        answerLabel.setBounds(600, 200, 900, 200);
        answerLabel.setFont(font.deriveFont(30f));
        answerLabel.setOpaque(true);
        answerLabel.setBackground(Color.WHITE);
        answerLabel.setForeground(Color.BLACK);
        answerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(answerLabel);
        answerLabel.setVisible(false);
        // 정답/오답 표시

        // 정답 입력하는 텍스트필드. 시작버튼 누르면 보임.
        txtfld.setBounds(875, 400, 250, 50);
        txtfld.setFont(font.deriveFont(30f));
        add(txtfld);
        txtfld.setVisible(false);


        // 시작 버튼. 누르면 사라지고 isStarted = true
        startButton.setBounds(700, 400, 200, 50);
        startButton.setBackground(Color.BLACK);
        startButton.setForeground(Color.WHITE);
        startButton.setFont(font.deriveFont(20f));
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            } // 마우스 올려두면 커서 변경
            @Override
            public void mouseExited(MouseEvent e) {
                startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            } // 마우스 나갈 때 기본 커서
            @Override
            public void mousePressed(MouseEvent e) {
                startButton.setVisible(false);
                // 시작버튼 가리기
                txtfld.setVisible(true);
                answerButton.setVisible(true);
                // 텍스트필드랑 정답제출버튼 표시
                exitButton.setBounds(1350, 50, 200, 50);
                // 종료 버튼 위치 변경
                setBackground(new Color(255, 255, 255, 0));
                // 배경 색 지정 / 기본은 검정
                introBackGround = null;
                // 배경 화면 없애기
                isStarted = true;
                // 시작화면으로 변경
                keyNumber = RandomInteger.getRandomInteger();
                // 키로 사용할 번호 뽑기
                usedNum.clear();
                answerCount = 0;
                label.setText(null);
                answerLabel.setVisible(false);
                txtfld.setText(null);
                // 사용한 번호, 정답 수, 표시줄 초기화
            }
        });
        add(startButton);


        // 종료 버튼. 누르면 종료
        exitButton.setBounds(700, 500, 200, 50);
        exitButton.setBackground(Color.BLACK);
        exitButton.setForeground(Color.WHITE);
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

        // 정답 제출 버튼. 시작 버튼 누르면 보임.
        answerButton.setBounds(900, 450, 200, 50);
        answerButton.setBackground(Color.BLACK);
        answerButton.setForeground(Color.WHITE);
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
                isAnswer(txtfld.getText());
                // 텍스트필드에서 답 가져와서 정답 확인
                answerButton.setVisible(false);
                // 정답 제출하면 정답 버튼 안보이게 함
                if(!usedNum.contains(keyNumber) && (usedNum.size() < QUIZ_NUM)) {
                    usedNum.add(keyNumber);
                } // 사용한 숫자 기록: 리스트에 없는 숫자여야 하고 리스트의 요소가 퀴즈 개수까지만 들어가도록
                label.setText("정답 제출 수 : " + Integer.toString(usedNum.size()));
                // 정답 제출 할 때 마다 현재 사용한 숫자의 개수(푼 문제의 수) 출력
                if (usedNum.size() == QUIZ_NUM) {
                    resultButton.setVisible(true);
                    txtfld.setVisible(false); // 퀴즈 개수만큼 완료하면 결과확인 버튼 보이기 / 정답버튼, 텍스트필드 가리기
                } else {
                    txtfld.setText(null);
                    nextButton.setVisible(true); // 아직 퀴즈 개수만큼 안했으면 다음 문제 버튼 표시
                }
            }
        });
        add(answerButton);
        answerButton.setVisible(false);


        // 다음 문제로 넘어가는 버튼. 정답 입력하면 보임.
        nextButton.setBounds(900, 500, 200, 50);
        nextButton.setBackground(Color.BLACK);
        nextButton.setForeground(Color.WHITE);
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
                do {
                    keyNumber = RandomInteger.getRandomInteger();
                } while(usedNum.contains(keyNumber)); // usedNum에 포함되는 숫자인경우 계속 난수 뽑기. 중복 피하기
                answerButton.setVisible(true);
                answerLabel.setVisible(false);
                nextButton.setVisible(false);
                // 정답 버튼 표시/다음 버튼 가리기, 정답 표시줄 가리기
            }
        });
        add(nextButton);
        nextButton.setVisible(false);

        // 결과 확인 버튼. 퀴즈 개수만큼 답을 제출하면 보임
        resultButton.setBounds(900, 450, 200, 50);
        resultButton.setBackground(Color.BLACK);
        resultButton.setForeground(Color.WHITE);
        resultButton.setFont(font.deriveFont(20f));
        resultButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                resultButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                resultButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            @Override
            public void mousePressed(MouseEvent e) {
                answerLabel.setVisible(false);
                // 정답 표시줄 가리기
                label.setText("맞춘 정답 수 : " + answerCount + " / " + usedNum.size() + " RETRY?"); // 기본 레이블 내용 정답 수 표시
                introBackGround = new ImageIcon("images/startscreen2.jpg").getImage(); // 배경 사진 변경
                setBackground(new Color(0, 0, 0, 0)); // 배경을 초기화면 상태로 돌림
                muscleImage = null; // 근육 사진 비우기
                isStarted = false; // 퀴즈화면도 지우기
                startButton.setText("재시작"); // 시작 버튼 글씨 변경
                startButton.setVisible(true);
                resultButton.setVisible(false); // 시작 버튼 표시, 결과 버튼 가리기
                exitButton.setBounds(700, 500, 200, 50); // 종료 버튼 위치 되돌리기
            }
        });
        add(resultButton);
        resultButton.setVisible(false);
    }


    // JFrame에서 자동으로 실행되는 paint 메소드. 기본 배경 panel을 그린다.
    public void paint(Graphics g) {
        screenImage = createImage(PANEL_WIDTH, PANEL_HEIGHT);
        screenGraphic = screenImage.getGraphics();
        screenDraw(screenGraphic);
        g.drawImage(screenImage, 0, 0, null);
    }

    // 마찬가지로 자동실행. 기본 배경 위에 컴포넌트들(버튼, 레이블, 텍스트필드)을 그린다.
    public void screenDraw(Graphics g) {
        g.drawImage(introBackGround, 0, 0, null);
        if (isStarted) {
            quizStarted(g);
        }
        paintComponents(g);
        this.repaint();
    }


    // 시작 버튼 누르면 뜨는 화면
    public void quizStarted(Graphics g) {
        muscleImage = new ImageIcon(musclesMap.get(keyNumber).getImage()).getImage();
        // keyNumber(난수)를 key로 해서 맵에서 근육 사진 추출
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
        // 근육 이름들을 가져온다.
        if (answer.equals(korean1) || answer.equals(korean2) || answer.equals(english)) {
            answerLabel.setText("<html>정답입니다<br>사용자가 입력한 답&nbsp:&nbsp;" + answer + "<br>정답&nbsp:&nbsp" + korean1 + "&nbsp/&nbsp" + korean2 + "&nbsp/&nbsp;" + english + "</html>");
            answerCount++;
            answerLabel.setVisible(true);
            // 사용자 정답과 비교해서 정답인 경우 정답 표시하고 카운트 증가. JLabel 은 줄바꿈을 html 문법으로 해야됨.
        } else {
            answerLabel.setText("<html>오답입니다<br>사용자가 입력한 답&nbsp:&nbsp;" + answer + "<br>정답&nbsp:&nbsp" + korean1 + "&nbsp/&nbsp" + korean2 + "&nbsp/&nbsp;" + english + "</html>");
            answerLabel.setVisible(true);
            // 오답인 경우 오답표시
        }
    }
}