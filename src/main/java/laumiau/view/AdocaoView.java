package laumiau.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdocaoView extends JFrame {

    private JCheckBox checkTermo;
    private JButton btnConfirmar;
    private JButton btnVoltar;

    public AdocaoView() {

        setTitle("Lau Miau - Adoção");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        criarTela();
    }

    private void criarTela() {

        JPanel fundo = new JPanel(new BorderLayout());
        fundo.setBackground(new Color(255, 250, 243));
        fundo.setBorder(new EmptyBorder(30, 50, 30, 50));

        JPanel topo = new JPanel(new BorderLayout());
        topo.setOpaque(false);

        btnVoltar = new JButton("←");

        btnVoltar.setFont(new Font("Arial", Font.BOLD, 22));
        btnVoltar.setBackground(Color.WHITE);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setBorderPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnVoltar.addActionListener(e -> {
            dispose();
        });

        JLabel logo = new JLabel("LAU 🐾 MIAU", SwingConstants.CENTER);

        logo.setFont(new Font("Arial", Font.BOLD, 28));
        logo.setForeground(new Color(255, 102, 0));

        topo.add(btnVoltar, BorderLayout.WEST);
        topo.add(logo, BorderLayout.CENTER);

        JPanel centro = new JPanel();

        centro.setOpaque(false);
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBorder(new EmptyBorder(40, 200, 20, 200));

        JLabel titulo = new JLabel("Confirmar Adoção");

        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(new Color(20, 30, 50));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Confira os dados antes de finalizar");

        subtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitulo.setForeground(new Color(100, 110, 120));
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel card = new JPanel();

        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(30, 40, 30, 40));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setMaximumSize(new Dimension(600, 330));

        JLabel animalLabel = new JLabel("Animal: Preciosinha");
        JLabel clienteLabel = new JLabel("Adotante: Lauanda Lara");
        JLabel statusLabel = new JLabel("Status: disponível para adoção responsável");

        animalLabel.setFont(new Font("Arial", Font.BOLD, 20));
        clienteLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        animalLabel.setForeground(new Color(20, 30, 50));
        clienteLabel.setForeground(new Color(70, 70, 70));
        statusLabel.setForeground(new Color(70, 70, 70));

        JTextArea texto = new JTextArea(
                "Ao confirmar esta adoção, você declara que o termo foi assinado "
                + "e que o adotante se responsabiliza pelos cuidados, segurança, saúde "
                + "e bem-estar do animal."
        );

        texto.setWrapStyleWord(true);
        texto.setLineWrap(true);
        texto.setEditable(false);
        texto.setOpaque(false);
        texto.setFont(new Font("Arial", Font.PLAIN, 15));
        texto.setForeground(new Color(80, 80, 80));

        checkTermo = new JCheckBox("Li e confirmo que o termo de adoção foi assinado");

        checkTermo.setOpaque(false);
        checkTermo.setFont(new Font("Arial", Font.PLAIN, 15));
        checkTermo.setForeground(new Color(30, 40, 60));

        btnConfirmar = new JButton("❤ Confirmar adoção");

        btnConfirmar.setBackground(new Color(255, 102, 0));
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setFont(new Font("Arial", Font.BOLD, 17));
        btnConfirmar.setFocusPainted(false);
        btnConfirmar.setBorderPainted(false);
        btnConfirmar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConfirmar.setMaximumSize(new Dimension(600, 55));

        btnConfirmar.addActionListener(e -> confirmarAdocao());

        card.add(animalLabel);
        card.add(Box.createVerticalStrut(15));

        card.add(clienteLabel);
        card.add(Box.createVerticalStrut(15));

        card.add(statusLabel);
        card.add(Box.createVerticalStrut(25));

        card.add(texto);
        card.add(Box.createVerticalStrut(25));

        card.add(checkTermo);

        centro.add(titulo);
        centro.add(Box.createVerticalStrut(10));

        centro.add(subtitulo);
        centro.add(Box.createVerticalStrut(40));

        centro.add(card);
        centro.add(Box.createVerticalStrut(30));

        centro.add(btnConfirmar);

        fundo.add(topo, BorderLayout.NORTH);
        fundo.add(centro, BorderLayout.CENTER);

        add(fundo);
    }

    private void confirmarAdocao() {

        if (!checkTermo.isSelected()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Você precisa confirmar o termo.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Adoção realizada com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new AdocaoView().setVisible(true);

        });
    }
}