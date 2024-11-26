import utils.*;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private boolean captureNextPaint = true;
    private boolean stage3 = true;

    public MainPanel(int x, int y, int width, int height) {
        this.setBounds(x, y, width, height);
        new Thread(this::initScreenComponents).start();
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(200);
                    repaint();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    this.captureNextPaint = true;
                    Thread.sleep(10 * Constants.SECOND);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }).start();

    }

    public void initScreenComponents() {
        this.setBackground(Color.WHITE);
        JButton instructionsButton = new JButton(TranslationsUtils.getTranslation(TranslationKeys.instructions));
        this.setLayout(null);
        instructionsButton.setBounds(
                Constants.INSTRUCTIONS_BUTTON_STARTING_X, Constants.INSTRUCTIONS_BUTTON_STARTING_Y,
                Constants.INSTRUCTIONS_BUTTON_WIDTH, Constants.INSTRUCTIONS_BUTTON_HEIGHT);
        this.add(instructionsButton);
        instructionsButton.addActionListener((event) -> {
            String translation = String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n",
                    TranslationsUtils.getTranslation(TranslationKeys.instructions_1_1),
                    TranslationsUtils.getTranslation(TranslationKeys.instructions_1_2),
                    TranslationsUtils.getTranslation(TranslationKeys.instructions_1_3),
                    TranslationsUtils.getTranslation(TranslationKeys.instructions_1_4),
                    TranslationsUtils.getTranslation(TranslationKeys.instructions_1_5),
                    TranslationsUtils.getTranslation(TranslationKeys.instructions_1_6),
                    TranslationsUtils.getTranslation(TranslationKeys.instructions_1_7),
                    TranslationsUtils.getTranslation(TranslationKeys.instructions_1_8),
                    TranslationsUtils.getTranslation(TranslationKeys.instructions_1_9),
                    TranslationsUtils.getTranslation(TranslationKeys.instructions_1_10),
                    TranslationsUtils.getTranslation(TranslationKeys.instructions_1_11),
                    TranslationsUtils.getTranslation(TranslationKeys.instructions_1_12),
                    TranslationsUtils.getTranslation(TranslationKeys.instructions_1_13),
                    TranslationsUtils.getTranslation(TranslationKeys.instructions_1_14),
                    TranslationsUtils.getTranslation(TranslationKeys.instructions_1_15),
                    TranslationsUtils.getTranslation(TranslationKeys.instructions_1_16),
                    TranslationsUtils.getTranslation(TranslationKeys.instructions_1_17),
                    TranslationsUtils.getTranslation(TranslationKeys.instructions_1_18),
                    TranslationsUtils.getTranslation(TranslationKeys.instructions_1_19),
                    TranslationsUtils.getTranslation(TranslationKeys.instructions_1_20),
                    TranslationsUtils.getTranslation(TranslationKeys.instructions_1_21),
                    TranslationsUtils.getTranslation(TranslationKeys.instructions_1_22),
                    TranslationsUtils.getTranslation(TranslationKeys.instructions_1_23)

            );
            JOptionPane pane = new JOptionPane(translation, JOptionPane.INFORMATION_MESSAGE);
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            JDialog dialog = pane.createDialog(this, translation);
            dialog.setVisible(true);
        });
        JButton question1 = new JButton(TranslationsUtils.getTranslation(TranslationKeys.question_1));
        question1.setBounds(
                Constants.INSTRUCTIONS_BUTTON_STARTING_X, Constants.INSTRUCTIONS_BUTTON_STARTING_Y + Constants.INSTRUCTIONS_BUTTON_HEIGHT,
                Constants.INSTRUCTIONS_BUTTON_WIDTH, Constants.INSTRUCTIONS_BUTTON_HEIGHT);
        this.add(question1);
        question1.addActionListener(event -> {
            try {
                ExtraResponse response = HttpUtils.question1();
                String text = response.isSuccess() ? TranslationsUtils.getQuestion(Integer.parseInt(response.getExtra())) : TranslationsUtils.getTranslation(TranslationKeys.general_error);
                JOptionPane pane = new JOptionPane(text, JOptionPane.INFORMATION_MESSAGE);
                pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                JDialog dialog = pane.createDialog(this, text);
                dialog.setVisible(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        JButton question2 = new JButton(TranslationsUtils.getTranslation(TranslationKeys.question_2));
        question2.setBounds(
                Constants.INSTRUCTIONS_BUTTON_STARTING_X, Constants.INSTRUCTIONS_BUTTON_STARTING_Y + Constants.INSTRUCTIONS_BUTTON_HEIGHT * 2,
                Constants.INSTRUCTIONS_BUTTON_WIDTH, Constants.INSTRUCTIONS_BUTTON_HEIGHT);
        this.add(question2);
        question2.addActionListener(event -> {
            try {
                ExtraResponse response = HttpUtils.question2();
                String text = response.isSuccess() ? TranslationsUtils.getQuestion(Integer.parseInt(response.getExtra())) : TranslationsUtils.getTranslation(TranslationKeys.general_error);
                JOptionPane pane = new JOptionPane(text, JOptionPane.INFORMATION_MESSAGE);
                pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                JDialog dialog = pane.createDialog(this, text);
                dialog.setVisible(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


        JButton moveToStage2 = new JButton(TranslationsUtils.getTranslation(TranslationKeys.instructions_stage_2));
        moveToStage2.setBounds(
                Constants.INSTRUCTIONS_BUTTON_STARTING_X, Constants.INSTRUCTIONS_BUTTON_STARTING_Y + Constants.INSTRUCTIONS_BUTTON_HEIGHT * 4,
                Constants.INSTRUCTIONS_BUTTON_WIDTH, Constants.INSTRUCTIONS_BUTTON_HEIGHT);
        this.add(moveToStage2);
        moveToStage2.addActionListener(event -> {
            try {
                System.out.println("https://docs.google.com/document/d/1QkCSDGcZb0OHiyPehaBYB4z8V5r1lX2iH3ZZxxKpJyU/edit?usp=sharing");
                String translation = String.format("%s\n%s\n",
                        TranslationsUtils.getTranslation(TranslationKeys.instructions_2_1),
                        TranslationsUtils.getTranslation(TranslationKeys.instructions_2_2)
                );
                JOptionPane pane = new JOptionPane(translation, JOptionPane.INFORMATION_MESSAGE);
                pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                JDialog dialog = pane.createDialog(this, translation);
                dialog.setVisible(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        JButton myImage = new JButton(TranslationsUtils.getTranslation(TranslationKeys.my_image));
        myImage.setBounds(
                Constants.INSTRUCTIONS_BUTTON_STARTING_X, Constants.INSTRUCTIONS_BUTTON_STARTING_Y + Constants.INSTRUCTIONS_BUTTON_HEIGHT * 5,
                Constants.INSTRUCTIONS_BUTTON_WIDTH, Constants.INSTRUCTIONS_BUTTON_HEIGHT);
        this.add(myImage);
        myImage.addActionListener(event -> {
            try {
                ExtraResponse response = HttpUtils.myImage();
                String text = response.isSuccess() ? response.getExtra() : TranslationsUtils.getTranslation(TranslationKeys.general_error);
                JOptionPane pane = new JOptionPane(text, JOptionPane.INFORMATION_MESSAGE);
                pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                JDialog dialog = pane.createDialog(this, text);
                dialog.setVisible(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        JButton stage3 = new JButton(TranslationsUtils.getTranslation(TranslationKeys.instructions_stage_3));
        stage3.setBounds(
                Constants.INSTRUCTIONS_BUTTON_STARTING_X, Constants.INSTRUCTIONS_BUTTON_STARTING_Y + Constants.INSTRUCTIONS_BUTTON_HEIGHT * 7,
                Constants.INSTRUCTIONS_BUTTON_WIDTH, Constants.INSTRUCTIONS_BUTTON_HEIGHT);
        this.add(stage3);
        stage3.addActionListener(event -> {
            try {
                JOptionPane pane = new JOptionPane(TranslationsUtils.getTranslation(TranslationKeys.state_3_instructions), JOptionPane.INFORMATION_MESSAGE);
                pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                JDialog dialog = pane.createDialog(this, TranslationsUtils.getTranslation(TranslationKeys.state_3_instructions));
                dialog.setVisible(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            Thread.sleep(10000);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        doNotChange(g);
        //write your code here, do not change anything else!

    }

    private void doNotChange (Graphics g) {
        if (this.stage3) {
            Utils.colorRandomPixel(this, g);
            if (captureNextPaint) {
                captureNextPaint = false;
                Utils.capturePanel(this);
            }

        }

    }
}
