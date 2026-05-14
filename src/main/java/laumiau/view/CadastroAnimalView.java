package laumiau.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import laumiau.model.Animal;
import laumiau.model.Sexo;
import laumiau.service.AnimalService;
import laumiau.model.Porte;

public class CadastroAnimalView extends JFrame {

    private final Color FUNDO = new Color(255, 248, 241);
    private final Color TEXTO = new Color(31, 42, 68);
    private final Color CINZA = new Color(150, 160, 175);
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
    this.animalService = animalService;
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
    
    private void preencherCamposEdicao() {
    if (animalEditando != null) {
        campoNome.setText(animalEditando.getNome());
        campoRaca.setText(animalEditando.getRaca());
        campoIdade.setText(String.valueOf(animalEditando.getIdade()));
        comboEspecie.setSelectedItem(animalEditando.getEspecie());

        if (animalEditando.getSexo() == Sexo.MACHO) {
            comboSexo.setSelectedItem("Macho");
        } else {
            comboSexo.setSelectedItem("Fêmea");
        }

        caminhoFotoSelecionada = animalEditando.getCaminhoFoto();
    }
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

        JLabel titulo = new JLabel("Cadastrar Animal");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        titulo.setForeground(TEXTO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        campoNome = new JTextField();
        campoRaca = new JTextField();
        campoIdade = new JTextField();

        comboEspecie = new JComboBox<>(
        new String[]{"Selecione...", "Gato", "Cachorro"}
);

        comboSexo = new JComboBox<>(
        new String[]{"Selecione...", "Macho", "Fêmea"}
);

        formulario.add(titulo);
        formulario.add(Box.createVerticalStrut(35));

        formulario.add(campoTexto("Nome do animal", campoNome, "Ex: Thor"));

        formulario.add(combo("Espécie", comboEspecie));

        formulario.add(campoTexto("Raça", campoRaca, "Ex: Vira-lata / SRD"));

        formulario.add(campoTexto("Idade", campoIdade, "Ex: 2 anos"));

        formulario.add(combo("Sexo", comboSexo));
        JLabel addFoto = new JLabel("Adicionar foto");
        addFoto.setFont(new Font("SansSerif", Font.BOLD, 14));
        addFoto.setForeground(TEXTO);
        addFoto.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel foto = new RoundedPanel(20, new Color(255, 255, 255));
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

        FileNameExtensionFilter filtro =
                new FileNameExtensionFilter(
                        "Imagens",
                        "jpg",
                        "png",
                        "jpeg"
                );

        chooser.setFileFilter(filtro);

        int retorno = chooser.showOpenDialog(null);

        if (retorno == JFileChooser.APPROVE_OPTION) {

            File arquivo = chooser.getSelectedFile();
            caminhoFotoSelecionada = arquivo.getAbsolutePath();

            ImageIcon imagem = new ImageIcon(arquivo.getAbsolutePath());

            Image img = imagem.getImage().getScaledInstance(
                    150,
                    110,
                    Image.SCALE_SMOOTH
            );

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

        String nome = campoNome.getText();

        String especie = comboEspecie.getSelectedItem().toString();

        String raca = campoRaca.getText();

        int idade = Integer.parseInt(campoIdade.getText());

        String sexo = comboSexo.getSelectedItem().toString();

        System.out.println(nome);
        System.out.println(especie);
        System.out.println(raca);
        System.out.println(idade);
        System.out.println(sexo);
        System.out.println(caminhoFotoSelecionada);
        
        if (animalEditando == null) {

    Animal animal = new Animal(
            nome,
            especie,
            raca,
            idade,
            sexo.equals("Macho") ? Sexo.MACHO : Sexo.FEMEA,
            false,
            null,
            caminhoFotoSelecionada
    );

    animalService.cadastrar(animal);

    JOptionPane.showMessageDialog(
            this,
            "Animal cadastrado com sucesso!"
    );

} else {

    animalEditando.setNome(nome);

    animalEditando.setEspecie(especie);

    animalEditando.setRaca(raca);

    animalEditando.setIdade(idade);

    animalEditando.setSexo(
            sexo.equals("Macho")
                    ? Sexo.MACHO
                    : Sexo.FEMEA
    );

    animalEditando.setCaminhoFoto(caminhoFotoSelecionada);

    animalService.atualizar(animalEditando);

    JOptionPane.showMessageDialog(
            this,
            "Animal atualizado com sucesso!"
    );
}

dispose();

new AnimaisCadastradosView(animalService); 
    } catch (NumberFormatException erro) {

        JOptionPane.showMessageDialog(this,
                "Digite apenas números na idade.");

    } catch (Exception erro) {

        JOptionPane.showMessageDialog(this,
                erro.getMessage());
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

    campo.setText(placeholder);
    campo.setFont(new Font("SansSerif", Font.PLAIN, 14));
    campo.setForeground(CINZA);

    campo.setBorder(new EmptyBorder(0, 25, 0, 25));

    campo.setMaximumSize(new Dimension(600, 52));
    campo.setPreferredSize(new Dimension(600, 52));

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