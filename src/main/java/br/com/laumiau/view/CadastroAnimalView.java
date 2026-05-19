package br.com.laumiau.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import laumiau.model.Animal;
import laumiau.model.Porte;
import laumiau.model.Sexo;
import laumiau.service.AnimalService;

public class CadastroAnimalView extends JFrame {

    private final Color FUNDO   = new Color(255, 248, 241);
    private final Color TEXTO   = new Color(31, 42, 68);
    private final Color CINZA   = new Color(150, 160, 175);
    private final Color LARANJA = new Color(255, 128, 0);

    private JLabel icon;

    private JTextField campoNome;
    private JTextField campoRaca;
    private JTextField campoIdade;
    private JTextField campoPeso;
    private JTextField campoCor;
    private JTextField campoResponsavel;
    private JTextField campoDescricao;
    private JCheckBox checkVacinado;

    private JComboBox<String> comboEspecie;
    private JComboBox<String> comboSexo;
    private JComboBox<String> comboPorte;

    private String caminhoFotoSelecionada;
    private AnimalService animalService;
    private Animal animalEditando;

    public CadastroAnimalView(AnimalService animalService, Animal animalEditando) {
        this.animalService  = animalService;
        this.animalEditando = animalEditando;

        setTitle("Editar Animal");
        setSize(1000, 780);
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
        setSize(1000, 780);
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

            if(animalEditando.getPorte() != null) {
                comboPorte.setSelectedItem(animalEditando.getPorte().toString());
            }
            campoPeso.setText(animalEditando.getPeso() != null ? animalEditando.getPeso() : "");
            campoCor.setText(animalEditando.getCor() != null ? animalEditando.getCor() : "");
            campoResponsavel.setText(animalEditando.getResponsavel() != null ? animalEditando.getResponsavel() : "");
            campoDescricao.setText(animalEditando.getDescricao() != null ? animalEditando.getDescricao() : "");
            checkVacinado.setSelected(animalEditando.isVacinado());

            caminhoFotoSelecionada = animalEditando.getCaminhoFoto();

            if (caminhoFotoSelecionada != null && !caminhoFotoSelecionada.isBlank()) {
                try {
                    ImageIcon imagem = new ImageIcon(caminhoFotoSelecionada);
                    Image img = imagem.getImage().getScaledInstance(150, 110, Image.SCALE_SMOOTH);
                    icon.setText("");
                    icon.setIcon(new ImageIcon(img));
                } catch (Exception e) {
                    System.err.println("Não foi possível carregar a imagem na edição.");
                }
            }
        }
    }

    private JScrollPane criarTela() {
        JPanel fundo = new JPanel();
        fundo.setLayout(new BoxLayout(fundo, BoxLayout.Y_AXIS));
        fundo.setBackground(FUNDO);
        fundo.setBorder(new EmptyBorder(25, 260, 25, 260));

        JPanel painelVoltar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelVoltar.setOpaque(false);
        painelVoltar.setMaximumSize(new Dimension(600, 40));

        JLabel voltar = new JLabel("←");
        voltar.setFont(new Font("SansSerif", Font.BOLD, 28));
        voltar.setForeground(TEXTO);
        voltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        voltar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                dispose();
                new AnimaisCadastradosView(animalService).setVisible(true);
            }
        });
        painelVoltar.add(voltar);

        JLabel titulo = new JLabel(animalEditando == null ? "Cadastrar Animal" : "Editar Animal");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        titulo.setForeground(TEXTO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        campoNome  = new JTextField();
        campoRaca  = new JTextField();
        campoIdade = new JTextField();
        campoPeso  = new JTextField();
        campoCor   = new JTextField();
        campoResponsavel = new JTextField();
        campoDescricao = new JTextField();

        comboEspecie = new JComboBox<>(new String[]{"Selecione...", "Gato", "Cachorro"});
        comboSexo    = new JComboBox<>(new String[]{"Selecione...", "Macho", "Fêmea"});
        comboPorte   = new JComboBox<>(new String[]{"Selecione...", "PEQUENO", "MEDIO", "GRANDE"});

        checkVacinado = new JCheckBox("Vacinado");
        checkVacinado.setFont(new Font("SansSerif", Font.BOLD, 14));
        checkVacinado.setForeground(TEXTO);
        checkVacinado.setOpaque(false);
        checkVacinado.setAlignmentX(Component.CENTER_ALIGNMENT);

        fundo.add(painelVoltar);
        fundo.add(titulo);
        fundo.add(Box.createVerticalStrut(25));

        fundo.add(campoTexto("Nome do animal", campoNome, "Ex: Thor"));
        fundo.add(combo("Espécie", comboEspecie));
        fundo.add(campoTexto("Raça", campoRaca, "Ex: Vira-lata / SRD"));
        fundo.add(campoTexto("Idade (em meses)", campoIdade, "Ex: 24"));
        fundo.add(combo("Sexo", comboSexo));
        fundo.add(combo("Porte", comboPorte));
        fundo.add(campoTexto("Peso", campoPeso, "Ex: 5kg"));
        fundo.add(campoTexto("Cor", campoCor, "Ex: Preto e Branco"));
        fundo.add(campoTexto("Responsável / ONG", campoResponsavel, "Ex: ONG Lau & Miau"));
        fundo.add(campoTexto("Breve Descrição", campoDescricao, "Ex: Muito dócil e brincalhão"));

        fundo.add(Box.createVerticalStrut(10));
        fundo.add(checkVacinado);
        fundo.add(Box.createVerticalStrut(20));

        JLabel addFoto = new JLabel("Adicionar foto");
        addFoto.setFont(new Font("SansSerif", Font.BOLD, 14));
        addFoto.setForeground(TEXTO);
        addFoto.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel foto = new AnimaisCadastradosView.RoundedPanel(20, Color.WHITE);
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

        fundo.add(addFoto);
        fundo.add(Box.createVerticalStrut(10));
        foto.setAlignmentX(Component.CENTER_ALIGNMENT);
        fundo.add(foto);

        JButton salvar = new AnimaisCadastradosView.RoundedButton("Salvar Cadastro", LARANJA, Color.WHITE);
        salvar.setFont(new Font("SansSerif", Font.BOLD, 16));
        salvar.setAlignmentX(Component.CENTER_ALIGNMENT);
        salvar.setMaximumSize(new Dimension(220, 45));

        fundo.add(Box.createVerticalStrut(35));
        fundo.add(salvar);
        fundo.add(Box.createVerticalStrut(30));

        salvar.addActionListener(e -> salvarAnimal());

        JScrollPane scroll = new JScrollPane(fundo);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    private void salvarAnimal() {
        try {
            String nome    = campoNome.getText().trim();
            String especie = comboEspecie.getSelectedItem().toString();
            String raca    = campoRaca.getText().trim();
            String idadeTxt= campoIdade.getText().trim();
            String sexo    = comboSexo.getSelectedItem().toString();

            String porteSel = comboPorte.getSelectedItem().toString();
            String peso     = campoPeso.getText().trim();
            String cor      = campoCor.getText().trim();
            String respons  = campoResponsavel.getText().trim(); // Mapeado como respons
            String desc     = campoDescricao.getText().trim();
            boolean vacina  = checkVacinado.isSelected();

            if (nome.isEmpty() || especie.equals("Selecione...") || raca.isEmpty() || sexo.equals("Selecione...") || idadeTxt.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios (Nome, Espécie, Raça, Idade, Sexo).");
                return;
            }

            int idade = Integer.parseInt(idadeTxt);
            Sexo sexoEnum = sexo.equals("Macho") ? Sexo.MACHO : Sexo.FEMEA;
            Porte porteEnum = null;
            if (!porteSel.equals("Selecione...")) {
                porteEnum = Porte.valueOf(porteSel);
            }

            peso = peso.startsWith("Ex:") ? "" : peso;
            cor = cor.startsWith("Ex:") ? "" : cor;
            respons = respons.startsWith("Ex:") ? "" : respons;
            desc = desc.startsWith("Ex:") ? "" : desc;

            if (animalEditando == null) {
                Animal animal = new Animal(nome, especie, raca, idade, sexoEnum, vacina, porteEnum, caminhoFotoSelecionada);
                animal.setPeso(peso);
                animal.setCor(cor);
                animal.setResponsavel(respons);
                animal.setDescricao(desc);

                animalService.cadastrar(animal);
                JOptionPane.showMessageDialog(this, "Animal cadastrado com sucesso!");
            } else {
                animalEditando.setNome(nome);
                animalEditando.setEspecie(especie);
                animalEditando.setRaca(raca);
                animalEditando.setIdade(idade);
                animalEditando.setSexo(sexoEnum);
                animalEditando.setPorte(porteEnum);
                animalEditando.setVacinado(vacina);
                animalEditando.setPeso(peso);
                animalEditando.setCor(cor);
                animalEditando.setResponsavel(respons);
                animalEditando.setDescricao(desc);
                animalEditando.setCaminhoFoto(caminhoFotoSelecionada);

                animalService.atualizar(animalEditando);
                JOptionPane.showMessageDialog(this, "Animal atualizado com sucesso!");
            }

            dispose();
            new AnimaisCadastradosView(animalService).setVisible(true);

        } catch (NumberFormatException erro) {
            JOptionPane.showMessageDialog(this, "Digite apenas números na idade.");
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + erro.getMessage());
        }
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
}