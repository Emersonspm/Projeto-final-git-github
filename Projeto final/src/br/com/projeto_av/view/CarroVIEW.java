package br.com.projeto_av.view;

import java.awt.Dimension;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import br.com.projeto_av.dto.FornecedorDTO;
import br.com.projeto_av.ctr.FornecedorCTR;
import br.com.projeto_av.dto.CarroDTO;
import br.com.projeto_av.ctr.CarroCTR;

public class CarroVIEW extends javax.swing.JInternalFrame {

    FornecedorDTO fornecedorDTO = new FornecedorDTO();
    FornecedorCTR fornecedorCTR = new FornecedorCTR();
    CarroDTO carroDTO = new CarroDTO();
    CarroCTR carroCTR = new CarroCTR();

    int gravar_alterar;

    ResultSet rs;

    DefaultTableModel modelo_jtl_consultar_carro;
    DefaultTableModel modelo_jtl_consultar_fornecedor;

    /**
     * Creates new form CarroVIEW
     */
    public CarroVIEW() {
        initComponents();

        liberaCampos(false);

        liberaBotoes(true, false, false, false, true);
        modelo_jtl_consultar_carro = (DefaultTableModel) jtl_consultar_carro.getModel();
        modelo_jtl_consultar_fornecedor = (DefaultTableModel) jtl_consultar_fornecedor.getModel();

    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    private void gravar() {
        try {
            carroDTO.setPlaca_carro(placa_carro.getText());
            carroDTO.setMarca_carro(marca_carro.getText());
            carroDTO.setModelo_carro(modelo_carro.getText());
            carroDTO.setChassi_carro(chassi_carro.getText());
            carroDTO.setAno_carro(ano_carro.getText());
            carroDTO.setCor_carro(cor_carro.getText());
            carroDTO.setValor_carro(Double.parseDouble(valor_carro.getText()));
            carroDTO.setId_carro(Integer.parseInt(id_carro.getText()));
            fornecedorDTO.setId_for(Integer.parseInt(String.valueOf(
                    jtl_consultar_fornecedor.getValueAt(
                            jtl_consultar_fornecedor.getSelectedRow(), 0))));

            JOptionPane.showMessageDialog(null,
                    carroCTR.inserirCarro(carroDTO, fornecedorDTO));
        } catch (Exception e) {
            System.out.println("Erro ao Gravar" + e.getMessage());
        }
    }

    private void alterar() {
        try {
            carroDTO.setPlaca_carro(placa_carro.getText());
            carroDTO.setMarca_carro(marca_carro.getText());
            carroDTO.setModelo_carro(modelo_carro.getText());
            carroDTO.setChassi_carro(chassi_carro.getText());
            carroDTO.setAno_carro(ano_carro.getText());
            carroDTO.setCor_carro(cor_carro.getText());
            carroDTO.setValor_carro(Double.parseDouble(valor_carro.getText()));
            carroDTO.setId_carro(Integer.parseInt(id_carro.getText()));
            fornecedorDTO.setId_for(Integer.parseInt(String.valueOf(
                    jtl_consultar_fornecedor.getValueAt(
                            jtl_consultar_fornecedor.getSelectedRow(), 0))));

            JOptionPane.showMessageDialog(null,
                    carroCTR.alterarCarro(carroDTO, fornecedorDTO));
        } catch (Exception e) {
            System.out.println("Erro ao Alterar" + e.getMessage());
        }
    }

    private void excluir() {
        if (JOptionPane.showConfirmDialog(null, "Deseja Realmente excluir o carro?", "AVISO!",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null,
                    carroCTR.excluirCarro(carroDTO));

        }
    }

    private void liberaCampos(boolean a) {
        placa_carro.setEnabled(a);
        marca_carro.setEnabled(a);
        modelo_carro.setEnabled(a);
        chassi_carro.setEnabled(a);
        ano_carro.setEnabled(a);
        cor_carro.setEnabled(a);
        valor_carro.setEnabled(a);
        id_carro.setEnabled(a);
        pesquisa_nome_fornecedor.setEnabled(a);
        btnPesquisarFornecedor.setEnabled(a);
        jtl_consultar_fornecedor.setEnabled(a);
    }

    private void liberaBotoes(boolean a, boolean b, boolean c, boolean d, boolean e) {
        btnNovo.setEnabled(a);
        btnSalvar.setEnabled(b);
        btnCancelar.setEnabled(c);
        btnExcluir.setEnabled(d);
        btnSair.setEnabled(e);
    }

    private void limpaCampos() {
        placa_carro.setText("");
        marca_carro.setText("");
        modelo_carro.setText("");
        chassi_carro.setText("");
        ano_carro.setText("");
        cor_carro.setText("");
        valor_carro.setText("");
        id_carro.setText("");
        pesquisa_nome_fornecedor.setText("");
        modelo_jtl_consultar_fornecedor.setNumRows(0);
    }

    private void preencheTabelaCarro(String placa_carro) {
        try {

            modelo_jtl_consultar_carro.setNumRows(0);

            carroDTO.setPlaca_carro(placa_carro);

            rs = carroCTR.consultarCarro(carroDTO, 1);

            while (rs.next()) {
                modelo_jtl_consultar_carro.addRow(new Object[]{
                    rs.getString("id_carro"),
                    rs.getString("placa_carro"),});

            }
        } catch (Exception e) {
            System.out.println("Erro preencheTabelaCarro: " + e.getMessage());
        } finally {
            carroCTR.CloseDB();
        }
    }

    private void preencheCamposCarro(int id_carropre) {
        try {
            carroDTO.setId_carro(id_carropre);
            rs = carroCTR.consultarCarro(carroDTO, 2);
            if (rs.next()) {
                limpaCampos();

                placa_carro.setText(rs.getString("placa_carro"));
                marca_carro.setText(rs.getString("marca_carro"));
                modelo_carro.setText(rs.getString("modelo_carro"));
                chassi_carro.setText(rs.getString("chassi_carro"));
                ano_carro.setText(rs.getString("ano_carro"));
                cor_carro.setText(rs.getString("cor_carro"));
                valor_carro.setText(rs.getString("valor_carro"));
                id_carro.setText(rs.getString("id_carro"));

                modelo_jtl_consultar_fornecedor.setNumRows(0);
                modelo_jtl_consultar_fornecedor.addRow(new Object[]{rs.getInt("id_for"), rs.getString("nome_for"),});
                jtl_consultar_fornecedor.setRowSelectionInterval(0, 0);

                gravar_alterar = 2;

                liberaCampos(true);
            }
        } catch (Exception e) {
            System.out.println("Erro preencheCamposCarro: " + e.getMessage());
        } finally {
            carroCTR.CloseDB();
        }
    }

    private void preencheTabelaFornecedor(String nome_for) {
        try {

            modelo_jtl_consultar_fornecedor.setNumRows(0);

            fornecedorDTO.setNome_for(nome_for);
            rs = fornecedorCTR.consultarFornecedor(fornecedorDTO, 1);
            while (rs.next()) {
                modelo_jtl_consultar_fornecedor.addRow(new Object[]{
                    rs.getString("id_for"),
                    rs.getString("nome_for"),});
            }
        } catch (Exception e) {
            System.out.println("Erro preencheTabelaFornecedor: " + e.getMessage());
        } finally {
            carroCTR.CloseDB();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        placa_carro = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtl_consultar_carro = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtl_consultar_fornecedor = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        pesquisa_nome_carro = new javax.swing.JTextField();
        btnPesquisarCarro = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        pesquisa_nome_fornecedor = new javax.swing.JTextField();
        btnPesquisarFornecedor = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        marca_carro = new javax.swing.JTextField();
        id_carro = new javax.swing.JTextField();
        modelo_carro = new javax.swing.JTextField();
        chassi_carro = new javax.swing.JTextField();
        ano_carro = new javax.swing.JTextField();
        cor_carro = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        valor_carro = new javax.swing.JTextField();

        jLabel1.setText("Placa:");

        jLabel2.setText("Marca:");

        jLabel3.setText("ID:");

        jLabel4.setText("Modelo:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Carro");

        jLabel6.setText("Chassi:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Consulta");

        jtl_consultar_carro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtl_consultar_carro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtl_consultar_carroMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtl_consultar_carro);
        if (jtl_consultar_carro.getColumnModel().getColumnCount() > 0) {
            jtl_consultar_carro.getColumnModel().getColumn(0).setResizable(false);
            jtl_consultar_carro.getColumnModel().getColumn(1).setResizable(false);
        }

        jtl_consultar_fornecedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jtl_consultar_fornecedor);
        if (jtl_consultar_fornecedor.getColumnModel().getColumnCount() > 0) {
            jtl_consultar_fornecedor.getColumnModel().getColumn(0).setResizable(false);
            jtl_consultar_fornecedor.getColumnModel().getColumn(1).setResizable(false);
        }

        jLabel8.setText("Nome:");

        btnPesquisarCarro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projeto_av/view/imagens/pesquisar.png"))); // NOI18N
        btnPesquisarCarro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarCarroActionPerformed(evt);
            }
        });

        jLabel9.setText("Fornecedor:");

        btnPesquisarFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projeto_av/view/imagens/pesquisar.png"))); // NOI18N
        btnPesquisarFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarFornecedorActionPerformed(evt);
            }
        });

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projeto_av/view/imagens/novo.png"))); // NOI18N
        btnNovo.setText("Novo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projeto_av/view/imagens/salvar.png"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projeto_av/view/imagens/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projeto_av/view/imagens/excluir.png"))); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projeto_av/view/imagens/sair.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        jLabel10.setText("Ano:");

        jLabel11.setText("Cor:");

        marca_carro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                marca_carroActionPerformed(evt);
            }
        });

        jLabel12.setText("Valor Aluguel:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSair))
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(256, 256, 256)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(pesquisa_nome_carro, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPesquisarCarro, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(valor_carro, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pesquisa_nome_fornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPesquisarFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnNovo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnExcluir))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(modelo_carro, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(placa_carro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(marca_carro, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(id_carro))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chassi_carro, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGap(14, 14, 14)
                                    .addComponent(jLabel11)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cor_carro))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addComponent(jLabel10)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(ano_carro, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(chassi_carro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(placa_carro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(marca_carro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10)
                                    .addComponent(ano_carro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(id_carro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11)
                                    .addComponent(cor_carro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(modelo_carro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnSair))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(valor_carro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(pesquisa_nome_fornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(3, 3, 3))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnPesquisarFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnPesquisarCarro)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(pesquisa_nome_carro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo)
                    .addComponent(btnSalvar)
                    .addComponent(btnCancelar)
                    .addComponent(btnExcluir))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        liberaCampos(true);
        liberaBotoes(false, true, true, false, true);
        gravar_alterar = 1;
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnPesquisarFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarFornecedorActionPerformed
        preencheTabelaFornecedor(pesquisa_nome_fornecedor.getText());
    }//GEN-LAST:event_btnPesquisarFornecedorActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if (gravar_alterar == 1) {
            gravar();
            gravar_alterar = 0;
        } else {
            if (gravar_alterar == 2) {
                alterar();
                gravar_alterar = 0;
            } else {
                JOptionPane.showMessageDialog(null, "Erro no sistema!");
            }
        }

        limpaCampos();
        liberaCampos(false);
        liberaBotoes(true, false, false, false, true);

    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnPesquisarCarroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarCarroActionPerformed
        preencheTabelaCarro(pesquisa_nome_carro.getText());
    }//GEN-LAST:event_btnPesquisarCarroActionPerformed

    private void jtl_consultar_carroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtl_consultar_carroMouseClicked

        preencheCamposCarro(Integer.parseInt(String.valueOf(
                jtl_consultar_carro.getValueAt(
                        jtl_consultar_carro.getSelectedRow(), 0))));
        liberaBotoes(false, true, true, true, true);

    }//GEN-LAST:event_jtl_consultar_carroMouseClicked

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        excluir();
        limpaCampos();
        liberaCampos(false);
        liberaBotoes(true, false, false, false, true);
        modelo_jtl_consultar_carro.setNumRows(0);

    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limpaCampos();
        liberaCampos(false);
        modelo_jtl_consultar_carro.setNumRows(0);
        liberaBotoes(true, false, false, false, true);
        gravar_alterar = 0;
        
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void marca_carroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_marca_carroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_marca_carroActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ano_carro;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisarCarro;
    private javax.swing.JButton btnPesquisarFornecedor;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JTextField chassi_carro;
    private javax.swing.JTextField cor_carro;
    private javax.swing.JTextField id_carro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jtl_consultar_carro;
    private javax.swing.JTable jtl_consultar_fornecedor;
    private javax.swing.JTextField marca_carro;
    private javax.swing.JTextField modelo_carro;
    private javax.swing.JTextField pesquisa_nome_carro;
    private javax.swing.JTextField pesquisa_nome_fornecedor;
    private javax.swing.JTextField placa_carro;
    private javax.swing.JTextField valor_carro;
    // End of variables declaration//GEN-END:variables
}
