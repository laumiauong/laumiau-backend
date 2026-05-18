package br.com.laumiau.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import laumiau.model.Animal;
import laumiau.model.Sexo;
import laumiau.service.AnimalService;

public class CadastroAnimalView extends JFrame {

    private final Color FUNDO  = new Color(255, 248, 241);
    private final Color TEXTO  = new Color(31, 42, 68);
    private final Color CINZA  = new Color(150, 160, 175);
    private final Color LARANJA = new Color(255, 128, 0);

    private JLabel icon;
    private JTextField campoNome;
    private JTextField campoRaca;
    private JTextField campoIdade;

    private JComboBox<String> comboEspecie;
    private JComboBox<String> comboSexo;

    private String caminhoFotoSelecionada;
    private AnimalService animalService;
    private Animal animalEditando;

    public CadastroAnimalView(AnimalService animalService, Animal animalEditando) {
        this.animalService  = animalService;
        this.animalEditando = animalEditando;

        setTitle("Editar Animal");
        setSize(1000, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        add(criarTela(), BorderLayout.CENTER);
        preencherCamposEdicao();
        setVisible(true);
    }

    public CadastroAnimalView(AnimalService animalService) {
        this.animalService = animalService;

        setTitle("Cadastrar Animal");
        setSize(1000, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        add(criarTela(), BorderLayout.CENTER);
        setVisible(true);
    }

    private void preencherCamposEdicao() {
        if (animalEditando != null) {
            campoNome.setText(animalEditando.getNome());
            campoRaca.setText(animalEditando.getRaca());
            campoIdade.setText(String.valueOf(animalEditando.getIdade()));
            comboEspecie.setSelectedItem(animalEditando.getEspecie());
            comboSexo.setSelectedItem(animalEditando.getSexo() == Sexo.MACHO ? "Macho" : "Fêmea");
            caminhoFotoSelecionada = animalEditando.getCaminhoFoto();

            if (caminhoFotoSelecionada != null && !caminhoFotoSelecionada.isBlank()) {
                ImageIcon imagem = new ImageIcon(caminhoFotoSelecionada);
                Image img = imagem.getImage().getScaledInstance(150, 110, Image.SCALE_SMOOTH);
                icon.setText("");
                icon.setIcon(new ImageIcon(img));
            }
        }
    }

    private JPanel criarTela() {
        JPanel fundo = new JPanel(new BorderLayout());
        fundo.setBackground(FUNDO);
        fundo.setBorder(new EmptyBorder(25, 40, 25, 40));

        JLabel voltar = new JLabel("←");
        voltar.setFont(new Font("SansSerif", Font.BOLD, 28));
        voltar.setForeground(TEXTO);
        voltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        voltar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                dispose();
            }
        });

        JPanel formulario = new JPanel();
        formulario.setOpaque(false);
        formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));
        formulario.setBorder(new EmptyBorder(10, 260, 10, 260));

        JLabel titulo = new JLabel(animalEditando == null ? "Cadastrar Animal" : "Editar Animal");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        titulo.setForeground(TEXTO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        campoNome  = new JTextField();
        campoRaca  = new JTextField();
        campoIdade = new JTextField();

        comboEspecie = new JComboBox<>(new String[]{"Selecione...", "Gato", "Cachorro"});
        comboSexo    = new JComboBox<>(new String[]{"Selecione...", "Macho", "Fêmea"});

        formulario.add(titulo);
        formulario.add(Box.createVerticalStrut(35));
        formulario.add(campoTexto("Nome do animal", campoNome, "Ex: Thor"));
        formulario.add(combo("Espécie", comboEspecie));
        formulario.add(campoTexto("Raça", campoRaca, "Ex: Vira-lata / SRD"));
        formulario.add(campoTexto("Idade (em meses)", campoIdade, "Ex: 24"));
        formulario.add(combo("Sexo", comboSexo));

        JLabel addFoto = new JLabel("Adicionar foto");
        addFoto.setFont(new Font("SansSerif", Font.BOLD, 14));
        addFoto.setForeground(TEXTO);
        addFoto.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel foto = new RoundedPanel(20, Color.WHITE);
        foto.setPreferredSize(new Dimension(150, 110));
        foto.setMaximumSize(new Dimension(150, 110));
        foto.setBorder(BorderFactory.createDashedBorder(CINZA));
        foto.setLayout(new BorderLayout());

        icon = new JLabel("🖼+", SwingConstants.CENTER);
        icon.setFont(new Font("SansSerif", Font.PLAIN, 28));
        icon.setForeground(CINZA);
        foto.add(icon, BorderLayout.CENTER);
        foto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        foto.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileNameExtensionFilter("Imagens", "jpg", "png", "jpeg"));

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File arquivo = chooser.getSelectedFile();
                    caminhoFotoSelecionada = arquivo.getAbsolutePath();

                    Image img = new ImageIcon(caminhoFotoSelecionada)
                            .getImage()
                            .getScaledInstance(150, 110, Image.SCALE_SMOOTH);
                    icon.setText("");
                    icon.setIcon(new ImageIcon(img));
                }
            }
        });

        formulario.add(Box.createVerticalStrut(25));
        formulario.add(addFoto);
        formulario.add(Box.createVerticalStrut(10));
        foto.setAlignmentX(Component.CENTER_ALIGNMENT);
        formulario.add(foto);

        JButton salvar = new RoundedButton("Salvar Cadastro", LARANJA, Color.WHITE);
        salvar.setFont(new Font("SansSerif", Font.BOLD, 16));
        salvar.setAlignmentX(Component.CENTER_ALIGNMENT);
        salvar.setMaximumSize(new Dimension(220, 45));

        formulario.add(Box.createVerticalStrut(25));
        formulario.add(salvar);

        salvar.addActionListener(e -> {
            try {
                String nome    = campoNome.getText().trim();
                String especie = comboEspecie.getSelectedItem().toString();
                String raca    = campoRaca.getText().trim();
                int    idade   = Integer.parseInt(campoIdade.getText().trim());
                String sexo    = comboSexo.getSelectedItem().toString();

                if (nome.isEmpty() || especie.equals("Selecione...") || raca.isEmpty() || sexo.equals("Selecione...")) {
                    JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.");
                    return;
                }

                Sexo sexoEnum = sexo.equals("Macho") ? Sexo.MACHO : Sexo.FEMEA;

                if (animalEditando == null) {
                    Animal animal = new Animal(nome, especie, raca, idade, sexoEnum, false, null, caminhoFotoSelecionada);
                    animalService.cadastrar(animal);
                    JOptionPane.showMessageDialog(this, "Animal cadastrado com sucesso!");
                } else {
                    animalEditando.setNome(nome);
                    animalEditando.setEspecie(especie);
                    animalEditando.setRaca(raca);
                    animalEditando.setIdade(idade);
                    animalEditando.setSexo(sexoEnum);
                    animalEditando.setCaminhoFoto(caminhoFotoSelecionada);
                    animalService.atualizar(animalEditando);
                    // ✅ CORRIGIDO: mensagem em português
                    JOptionPane.showMessageDialog(this, "Animal atualizado com sucesso!");
                }

                dispose();
                new AnimaisCadastradosView(animalService);

            } catch (NumberFormatException erro) {
                JOptionPane.showMessageDialog(this, "Digite apenas números na idade.");
            } catch (Exception erro) {
                JOptionPane.showMessageDialog(this, erro.getMessage());
            }
        });

        fundo.add(voltar, BorderLayout.NORTH);
        fundo.add(formulario, BorderLayout.CENTER);

        return fundo;
    }

    private JPanel campoTexto(String label, JTextField campo, String placeholder) {
        JPanel painel = new JPanel();
        painel.setOpaque(false);
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setForeground(TEXTO);

        campo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        campo.setForeground(CINZA);
        campo.setBorder(new EmptyBorder(0, 25, 0, 25));
        campo.setMaximumSize(new Dimension(600, 52));
        campo.setPreferredSize(new Dimension(600, 52));

        if (campo.getText().isEmpty()) {
            campo.setText(placeholder);
        }

        campo.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (campo.getText().equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(TEXTO);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (campo.getText().isEmpty()) {
                    campo.setText(placeholder);
                    campo.setForeground(CINZA);
                }
            }
        });

        painel.add(lbl);
        painel.add(Box.createVerticalStrut(8));
        painel.add(campo);
        painel.add(Box.createVerticalStrut(20));

        return painel;
    }

    private JPanel combo(String label, JComboBox<String> combo) {
        JPanel painel = new JPanel();
        painel.setOpaque(false);
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setForeground(TEXTO);

        combo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        combo.setBackground(Color.WHITE);
        combo.setMaximumSize(new Dimension(600, 52));
        combo.setPreferredSize(new Dimension(600, 52));

        painel.add(lbl);
        painel.add(Box.createVerticalStrut(8));
        painel.add(combo);
        painel.add(Box.createVerticalStrut(20));

        return painel;
    }

    static class RoundedPanel extends JPanel {
        private final int radius;

        public RoundedPanel(int radius, Color bg) {
            this.radius = radius;
            setOpaque(false);
            setBackground(bg);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
        }
    }

    static class RoundedButton extends JButton {
        private final Color bg;

        public RoundedButton(String text, Color bg, Color fg) {
            super(text);
            this.bg = bg;
            setForeground(fg);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setBorder(new EmptyBorder(12, 24, 12, 24));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bg);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            super.paintComponent(g);
            g2.dispose();
        }
    }
}