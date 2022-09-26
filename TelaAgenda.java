import javax.swing.*;
import javax.swing.colorchooser.ColorSelectionModel;
import java.awt.*;
import java.util.concurrent.BlockingQueue;
import java.awt.event.*;
import java.io.*;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class TelaAgenda extends JPanel {

	JTextField tfNome, tfTelefone, tfEndereco, tfDescricao,tfId;
	JButton btInserir, btLimpar, btSair, btAtualizar, btDeletar;
	JTextArea taInformacoes;
	JLabel lnome, ltelefone, lendereco, ldescricao,lid;
	JTable tabela;
    DefaultTableModel model;

	public TelaAgenda() {
		setLayout(new GridLayout(1,1));
		JTabbedPane tabs = new JTabbedPane();
		tabs.addTab("Inserir",formulario());
		tabs.setMnemonicAt(0, KeyEvent.VK_1);

		tabs.addTab("Registros",dados());
		tabs.setMnemonicAt(1, KeyEvent.VK_1);
		
		add(tabs);
	}

	public JPanel formulario() {
		JPanel form = new JPanel();
		form.setLayout( new BoxLayout(form, BoxLayout.Y_AXIS));
		form.setBackground(Color.LIGHT_GRAY);

		JPanel jpNome = new JPanel();
		jpNome.setLayout(new FlowLayout(FlowLayout.CENTER));
		lnome = new JLabel("Nome:");
		lnome.setPreferredSize(new Dimension(200, 10));
		tfNome = new JTextField(10);
		jpNome.add(lnome);
		jpNome.add(tfNome);

		JPanel jpTelefone = new JPanel();
		jpTelefone.setLayout(new FlowLayout(FlowLayout.CENTER));
		ltelefone = new JLabel("Telefone:");
		ltelefone.setPreferredSize(new Dimension(200, 20));
		tfTelefone = new JTextField(10);
		jpTelefone.add(ltelefone);
		jpTelefone.add(tfTelefone);

		JPanel jpEndereco = new JPanel();
		jpEndereco.setLayout(new FlowLayout(FlowLayout.CENTER));
		lendereco = new JLabel("Endereco:");
		lendereco.setPreferredSize(new Dimension(200, 20));
		tfEndereco = new JTextField(10);
		jpEndereco.add(lendereco);
		jpEndereco.add(tfEndereco);

		JPanel jpDescricao = new JPanel();
		jpDescricao.setLayout(new FlowLayout(FlowLayout.CENTER));
		ldescricao = new JLabel("Descricacao:");
		ldescricao.setPreferredSize(new Dimension(200, 50));
		tfDescricao = new JTextField(10);
		jpDescricao.add(ldescricao);
		jpDescricao.add(tfDescricao);

		ActionListener gerenciamento_botao = new ObeterInfos();

		JPanel jbBotoes = new JPanel();
		jbBotoes.setLayout(new FlowLayout(FlowLayout.CENTER));
		btInserir = new JButton("Inserir");
		btInserir.addActionListener(gerenciamento_botao);
		btLimpar = new JButton("Limpar");
		btLimpar.addActionListener(gerenciamento_botao);
		jbBotoes.add(btInserir);
		jbBotoes.add(btLimpar);
		

		form.add(Box.createVerticalStrut(100));
		form.add(jpNome);
		form.add(jpTelefone);
		form.add(jpEndereco);
		form.add(jpDescricao);
		form.add(jbBotoes);
		form.add(Box.createVerticalStrut(100));
		return form;

	}

	public JPanel dados() {
		JPanel dados = new JPanel();
		dados.setLayout(new BoxLayout(dados, BoxLayout.Y_AXIS));

		//-------------------------------------------------------------
        
        String[] colunas = {"ID","Nome","Endereco","Telefone","Descricao"};
		model = new DefaultTableModel(colunas, 0);
        leTabela(model);
        tabela = new JTable(model);
        JScrollPane barraRolagem = new JScrollPane(tabela);	
		
		//-------------------------------------------------------------	
       
        //-------------------------------------------------------------	

		ActionListener gerenciamento_botao = new ObeterInfos();

		JPanel jbBotoes2 = new JPanel();
		btAtualizar = new JButton("Atualizar");
		btAtualizar.addActionListener(gerenciamento_botao);
		btDeletar = new JButton("Deletar");
		btDeletar.addActionListener(gerenciamento_botao);
		jbBotoes2.add(btAtualizar);
		jbBotoes2.add(btDeletar);
		
        dados.add(barraRolagem);		
		dados.add(jbBotoes2);		

		return dados;
	}

    protected void leTabela(DefaultTableModel model){
        File arquivo = new File("dados.txt");       
        try{
            FileReader fr = new FileReader(arquivo);
            BufferedReader br = new BufferedReader(fr);
             //enquanto houver mais linhas
            while (br.ready()) {
                String linha = br.readLine();
                if(!linha.isEmpty()){
                    String[] textoSeparado = linha.split(";");
                    model.addRow(new Object[]{ textoSeparado[0],textoSeparado[1],textoSeparado[2],textoSeparado[3],textoSeparado[4]});
                }
                        
            }
            br.close();
            fr.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

	protected int geradorDeID(){
		int cont=1;
		File arquivo = new File("dados.txt");       
        try{
            FileReader fr = new FileReader(arquivo);
            BufferedReader br = new BufferedReader(fr);
             //enquanto houver mais linhas
            while (br.ready()) {
				String linha = br.readLine();
				if(!linha.isEmpty()){
                    cont++;
                }                
            }
            br.close();
            fr.close();

			
        }catch(IOException ex){
            ex.printStackTrace();
        }
		return cont;
	}

    protected void refresh(){
        String[] colunas = {"ID","Nome","Endereco","Telefone","Descricao"};
		model = new DefaultTableModel(colunas, 0);
        leTabela(model);
        tabela.setModel(model);
        tabela.revalidate();
    }

	private class ObeterInfos implements ActionListener {

		public void actionPerformed(ActionEvent evento) {
			if (evento.getSource() == btInserir) {
				if(!tfNome.getText().isEmpty() && !tfTelefone.getText().isEmpty() && !tfEndereco.getText().isEmpty() && !tfDescricao.getText().isEmpty()){
                    File arquivo = new File("dados.txt");
                    try {
                        //Escreve no Arquivo -----------------------------------------------------------------
                        if (!arquivo.exists()) {
                        //cria um arquivo (vazio)
                            arquivo.createNewFile();
                        }
                        //caso seja um diretório, é possível listar seus arquivos e diretórios
                        File[] arquivos = arquivo.listFiles();
                        //escreve no arquivo
                        FileWriter fw = new FileWriter(arquivo, true);
                        BufferedWriter bw = new BufferedWriter(fw);
						int id = geradorDeID();
                        bw.write(id +";"+tfNome.getText()+ ";"+ tfTelefone.getText()+";"+tfEndereco.getText()+";"+tfDescricao.getText()+";");
                        bw.newLine();
                        bw.close();
                        fw.close();
                        JOptionPane.showMessageDialog(null, "Registro Completo!", "", JOptionPane.INFORMATION_MESSAGE);
                    }catch(IOException ex){
                        ex.printStackTrace();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!!", "", JOptionPane.INFORMATION_MESSAGE);
                }
				
			}

            if (evento.getSource() == btAtualizar || evento.getSource() == btInserir) {
                refresh();
			}

			if (evento.getSource() == btDeletar) {
				
			}

			if (evento.getSource() == btLimpar) {
				tfNome.setText(" ");
				tfTelefone.setText(" ");
				tfEndereco.setText(" ");
				tfDescricao.setText(" ");
			}
		}
	}
}