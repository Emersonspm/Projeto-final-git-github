package br.com.projeto_av.view;

import java.awt.Dimension;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import br.com.projeto_av.dto.AluguelDTO;
import br.com.projeto_av.ctr.AluguelCTR;
import br.com.projeto_av.dto.CarroDTO;
import br.com.projeto_av.ctr.CarroCTR;
import br.com.projeto_av.dto.ClienteDTO;
import br.com.projeto_av.ctr.ClienteCTR;

import java.util.Date;
import javax.sound.midi.SysexMessage;

public class AluguelVIEW extends javax.swing.JInternalFrame {

    AluguelCTR aluguelCTR = new AluguelCTR();
    AluguelDTO aluguelDTO = new AluguelDTO();
    CarroCTR carroCTR = new CarroCTR();
    CarroDTO carroDTO = new CarroDTO();
    ClienteCTR clienteCTR = new ClienteCTR();
    ClienteDTO clienteDTO = new ClienteDTO();

    ResultSet rs;

    DefaultTableModel modelo_jtl_consultar_cli;
    DefaultTableModel modelo_jtl_consultar_carro;
    DefaultTableModel modelo_jtl_consultar_carro_selecionado;

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    private void gravar() {
        aluguelDTO.setDat_aluguel(new Date());
        aluguelDTO.setVal_aluguel(Double.parseDouble(ValorTotal.getText()));
        clienteDTO.setId_cli(Integer.parseInt(String.valueOf(
                jtl_consultar_cli.getValueAt(jtl_consultar_cli.getSelectedRow(), 0))));

        JOptionPane.showMessageDialog(null,
                aluguelCTR.inserirVenda(aluguelDTO, clienteDTO, jtl_consultar_carro_selecionado));
    }

    private void preencheTabelaCliente(String nome_cli) {
        try {

            modelo_jtl_consultar_cli.setNumRows(0);

            clienteDTO.setNome_cli(nome_cli);
            rs = clienteCTR.consultarCliente(clienteDTO, 1);

            while (rs.next()) {
                modelo_jtl_consultar_cli.addRow(new Object[]{
                    rs.getString("id_cli"),
                    rs.getString("nome_cli")
                });
            }
        } catch (Exception erTab) {
            System.out.println("Erro SQL: " + erTab);
        }
    }

    private void preencheTabelaCarro(String placa_carro) {
        try {

            modelo_jtl_consultar_carro.setNumRows(0);

            carroDTO.setPlaca_carro(placa_carro);
            rs = carroCTR.consultarCarro(carroDTO, 1);

            while (rs.next()) {
                modelo_jtl_consultar_carro.addRow(new Object[]{
                    rs.getString("id_carro"),
                    rs.getString("placa_carro"),
                    rs.getDouble("valor_carro")
                });
            }
        } catch (Exception erTab) {
            System.out.println("Erro SQL: " + erTab);
        }
    }

    private void calculaValorTotal() {
        try {
            double total = 0;
            for (int cont = 0; cont < jtl_consultar_carro_selecionado.getRowCount(); cont++) {
                total += (Double.parseDouble(String.valueOf(
                        jtl_consultar_carro_selecionado.getValueAt(cont, 2)))
                        * Integer.parseInt(String.valueOf(
                                jtl_consultar_carro_selecionado.getValueAt(cont, 3))));
            }
            ValorTotal.setText(String.valueOf(total));
        } catch (Exception erTab) {
            System.out.println("Erro SQL: " + erTab);
        }
    }

    private void adicionarCarroSelecionado(int id_carro, String placa_carro, double valor_carro) {
        try {
            modelo_jtl_consultar_carro_selecionado.addRow(new Object[]{
                id_carro,
                placa_carro,
                valor_carro
            });
        } catch (Exception erTab) {
            System.out.println("Erro SQL: " + erTab);
        }
    }

    private void removeCarroSelecionado(int linha_selecionada) {
        try {
            if (linha_selecionada >= 0) {
                modelo_jtl_consultar_carro_selecionado.removeRow(linha_selecionada);
                calculaValorTotal();
            }
        } catch (Exception erTab) {
            System.out.println("Erro SQL: " + erTab);
        }
    }

    private void liberaCampos(boolean a) {
        pesquisa_nome_cli.setEnabled(a);
        btnPesquisarCli.setEnabled(a);
        jtl_consultar_cli.setEnabled(a);
        pesquisa_placa_carro.setEnabled(a);
        btnPesquisarCarro.setEnabled(a);
        jtl_consultar_carro.setEnabled(a);
        btnProRem.setEnabled(a);
        btnProdAdd.setEnabled(a);
        jtl_consultar_carro_selecionado.setEnabled(a);
        ValorTotal.setText("0.00");
    }

    private void limpaCampos() {
        pesquisa_nome_cli.setText("");
        modelo_jtl_consultar_cli.setNumRows(0);
        pesquisa_placa_carro.setText("");
        modelo_jtl_consultar_carro.setNumRows(0);
        modelo_jtl_consultar_carro_selecionado.setNumRows(0);
    }

    private void liberaBotoes(boolean a, boolean b, boolean c, boolean d) {
        btnNovo.setEnabled(a);
        btnSalvar.setEnabled(b);
        btnCancelar.setEnabled(c);
        btnSair.setEnabled(d);

    }

    private boolean verificaPreenchimento() {
        if (jtl_consultar_cli.getSelectedRowCount() <= 0) {
            JOptionPane.showMessageDialog(null, "Deve ser selecionado um Cliente!");
            jtl_consultar_cli.requestFocus();
            return false;
        } else {
            if (jtl_consultar_carro_selecionado.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(null, "É necessario adicionar um Carro!");
                jtl_consultar_carro_selecionado.requestFocus();
                return false;
            } else {
                int verifica = 0;
                for (int cont = 0; cont < jtl_consultar_carro_selecionado.getRowCount(); cont++) {
                    if (String.valueOf(jtl_consultar_carro_selecionado.getValueAt(
                            cont, 3)).equalsIgnoreCase("null")) {
                        verifica++;
                    }
                }
                if (verifica > 0) {
                    JOptionPane.showMessageDialog(null,
                            "A quantidade de dias deve ser informada");
                    jtl_consultar_carro_selecionado.requestFocus();
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    public AluguelVIEW() {
        initComponents();

        liberaCampos(false);

        liberaBotoes(true, false, false, true);

        modelo_jtl_consultar_cli = (DefaultTableModel) jtl_consultar_cli.getModel();
        modelo_jtl_consultar_carro = (DefaultTableModel) jtl_consultar_carro.getModel();
        modelo_jtl_consultar_carro_selecionado = (DefaultTableModel) jtl_consultar_carro_selecionado.getModel();

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
        pesquisa_nome_cli = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        ValorTotal = new javax.swing.JLabel();
        btnPesquisarCli = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtl_consultar_cli = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtl_consultar_carro = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtl_consultar_carro_selecionado = new javax.swing.JTable();
        btnNovo = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        btnPesquisarCarro = new javax.swing.JButton();
        pesquisa_placa_carro = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnProRem = new javax.swing.JButton();
        btnProdAdd = new javax.swing.JButton();

        jLabel1.setText("Cliente:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Dados");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Aluguel");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setText("Total Aluguel:");

        ValorTotal.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        ValorTotal.setForeground(new java.awt.Color(92, 234, 88));
        ValorTotal.setText("0");

        btnPesquisarCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projeto_av/view/imagens/pesquisar.png"))); // NOI18N
        btnPesquisarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarCliActionPerformed(evt);
            }
        });

        jtl_consultar_cli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome           "
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jtl_consultar_cli);
        if (jtl_consultar_cli.getColumnModel().getColumnCount() > 0) {
            jtl_consultar_cli.getColumnModel().getColumn(0).setResizable(false);
            jtl_consultar_cli.getColumnModel().getColumn(0).setPreferredWidth(10);
            jtl_consultar_cli.getColumnModel().getColumn(1).setResizable(false);
            jtl_consultar_cli.getColumnModel().getColumn(1).setPreferredWidth(200);
        }

        jtl_consultar_carro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Placa", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jtl_consultar_carro);
        if (jtl_consultar_carro.getColumnModel().getColumnCount() > 0) {
            jtl_consultar_carro.getColumnModel().getColumn(0).setResizable(false);
            jtl_consultar_carro.getColumnModel().getColumn(0).setPreferredWidth(10);
            jtl_consultar_carro.getColumnModel().getColumn(1).setResizable(false);
            jtl_consultar_carro.getColumnModel().getColumn(1).setPreferredWidth(120);
            jtl_consultar_carro.getColumnModel().getColumn(2).setResizable(false);
            jtl_consultar_carro.getColumnModel().getColumn(2).setPreferredWidth(80);
        }

        jtl_consultar_carro_selecionado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Placa", "Valor", "Qtd de Dias"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtl_consultar_carro_selecionado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtl_consultar_carro_selecionadoKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(jtl_consultar_carro_selecionado);
        if (jtl_consultar_carro_selecionado.getColumnModel().getColumnCount() > 0) {
            jtl_consultar_carro_selecionado.getColumnModel().getColumn(0).setResizable(false);
            jtl_consultar_carro_selecionado.getColumnModel().getColumn(0).setPreferredWidth(10);
            jtl_consultar_carro_selecionado.getColumnModel().getColumn(1).setResizable(false);
            jtl_consultar_carro_selecionado.getColumnModel().getColumn(1).setPreferredWidth(80);
            jtl_consultar_carro_selecionado.getColumnModel().getColumn(2).setResizable(false);
            jtl_consultar_carro_selecionado.getColumnModel().getColumn(2).setPreferredWidth(60);
            jtl_consultar_carro_selecionado.getColumnModel().getColumn(3).setResizable(false);
            jtl_consultar_carro_selecionado.getColumnModel().getColumn(3).setPreferredWidth(60);
        }

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

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projeto_av/view/imagens/sair.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        btnPesquisarCarro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projeto_av/view/imagens/pesquisar.png"))); // NOI18N
        btnPesquisarCarro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarCarroActionPerformed(evt);
            }
        });

        jLabel6.setText("Placa:");

        btnProRem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projeto_av/view/imagens/prod_rem.png"))); // NOI18N
        btnProRem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProRemActionPerformed(evt);
            }
        });

        btnProdAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projeto_av/view/imagens/prod_add.png"))); // NOI18N
        btnProdAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProdAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pesquisa_nome_cli, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPesquisarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(198, 198, 198))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(pesquisa_placa_carro, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnPesquisarCarro, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(27, 27, 27))
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addComponent(btnSair))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnNovo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSalvar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ValorTotal)
                                        .addGap(248, 248, 248))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(btnProdAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(42, 42, 42)))
                                .addComponent(btnProRem, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSair)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(pesquisa_nome_cli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnPesquisarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(pesquisa_placa_carro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnPesquisarCarro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnProdAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnProRem)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(ValorTotal)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo)
                    .addComponent(btnSalvar)
                    .addComponent(btnCancelar))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        liberaCampos(true);
        liberaBotoes(false, true, true, true);
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limpaCampos();
        liberaCampos(false);
        modelo_jtl_consultar_cli.setNumRows(0);
        modelo_jtl_consultar_carro.setNumRows(0);
        liberaBotoes(true, false, false, true);

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnPesquisarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarCliActionPerformed
        preencheTabelaCliente(pesquisa_nome_cli.getText());
    }//GEN-LAST:event_btnPesquisarCliActionPerformed

    private void btnPesquisarCarroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarCarroActionPerformed
        preencheTabelaCarro(pesquisa_placa_carro.getText());
    }//GEN-LAST:event_btnPesquisarCarroActionPerformed

    private void btnProdAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProdAddActionPerformed
        adicionarCarroSelecionado(
                Integer.parseInt(String.valueOf(jtl_consultar_carro.getValueAt(
                        jtl_consultar_carro.getSelectedRow(), 0))),
                String.valueOf(jtl_consultar_carro.getValueAt(jtl_consultar_carro.getSelectedRow(), 1)),
                Double.parseDouble(String.valueOf(jtl_consultar_carro.getValueAt(
                        jtl_consultar_carro.getSelectedRow(), 2))));

    }//GEN-LAST:event_btnProdAddActionPerformed

    private void btnProRemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProRemActionPerformed
        removeCarroSelecionado(jtl_consultar_carro_selecionado.getSelectedRow());
    }//GEN-LAST:event_btnProRemActionPerformed

    private void jtl_consultar_carro_selecionadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtl_consultar_carro_selecionadoKeyReleased
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
            calculaValorTotal();
        }
    }//GEN-LAST:event_jtl_consultar_carro_selecionadoKeyReleased

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
     if(verificaPreenchimento()){
         gravar();
         limpaCampos();
         liberaCampos(false);
         liberaBotoes(true, false, false, true);
     }
    }//GEN-LAST:event_btnSalvarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ValorTotal;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisarCarro;
    private javax.swing.JButton btnPesquisarCli;
    private javax.swing.JButton btnProRem;
    private javax.swing.JButton btnProdAdd;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jtl_consultar_carro;
    private javax.swing.JTable jtl_consultar_carro_selecionado;
    private javax.swing.JTable jtl_consultar_cli;
    private javax.swing.JTextField pesquisa_nome_cli;
    private javax.swing.JTextField pesquisa_placa_carro;
    // End of variables declaration//GEN-END:variables
}
