package Forms;

import Tools.KoneksiDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class IfrJurusan extends javax.swing.JInternalFrame {
    
    Connection _Cnn;
    KoneksiDB getCnn = new KoneksiDB();
    
    String vkd_jur, vjurusan;
    String sqlselect, sqlinsert, sqldelete;
    DefaultTableModel tbljurusan;
    private boolean visible = false;
    
    public IfrJurusan() {
        initComponents();
        
        clearInput(); disableInput();
        setTabelJurusan(); showDataJurusan();
        this.visible= false;
    }
    
    public boolean getVisible(){
        return visible;
    }
 
    public void setStatusVisible(boolean visible) {
        this.visible = visible;
    }
    
    private void clearInput(){
        txtKdJur.setText("");
        txtJurusan.setText("");
        btnTambah.setText("Tambah");
        btnSimpan.setText("Simpan");
        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-add.png")));
    }
    
    private void disableInput(){
        txtKdJur.setEnabled(false);
        txtJurusan.setEnabled(false);
        btnSimpan.setEnabled(false);
        btnHapus.setEnabled(false);
        
    }
    
    private void enableInput(){
        txtKdJur.setEnabled(true);
        txtJurusan.setEnabled(true);
        btnSimpan.setEnabled(true);
    }
    
    private void setTabelJurusan(){
        String[] kolom1 = {"KD. Jur", "Jurusan"};
        tbljurusan = new DefaultTableModel(null, kolom1){
            Class[] types = new Class[]{
                java.lang.String.class,
                java.lang.String.class
            };
            public Class getColumnClass(int columnIndex){
            return types [columnIndex];
        }
            
        //agar tabel tdk bisa diedit
        public boolean isCellEditable(int row, int col){
            int cola = tbljurusan.getColumnCount();
            return (col < cola) ? false : true;
        }
       };
        tbDataJurusan.setModel(tbljurusan);
        tbDataJurusan.getColumnModel().getColumn(0).setPreferredWidth(75);
        tbDataJurusan.getColumnModel().getColumn(1).setPreferredWidth(250);
        
    }
    
    private void clearTabelJurusan(){
        int row  = tbljurusan.getRowCount();
        for(int i=0; i<row; i++){
            tbljurusan.removeRow(0);
        }
        
    }
    
    private void showDataJurusan(){
        try{
          _Cnn = null;
          _Cnn = getCnn.getConnection();
          sqlselect = "select * from tbjurusan";
          Statement stat = _Cnn.createStatement();
          ResultSet res = stat.executeQuery(sqlselect);
          clearTabelJurusan();
          while(res.next()){
              vkd_jur = res.getString("kd_jur");
              vjurusan = res.getString("jurusan");
              
              Object[] data = {vkd_jur, vjurusan};
              tbljurusan.addRow(data);
          }
          lblRecord.setText("Record : " + tbDataJurusan.getRowCount()); 
          
          
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method showTabelJurusan() : "+ex);
            
        }
        
    }
    
    private void aksiSimpan(){
        if(txtKdJur.getText().equals("") || txtJurusan.getText().equals("")){
            JOptionPane.showMessageDialog(this, "informasi",
                    "Mohon lengkapi data!", JOptionPane.INFORMATION_MESSAGE);
        }else{
            vkd_jur = txtKdJur.getText();
            vjurusan = txtJurusan.getText();
            try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                if(btnSimpan.getText().equals("Simpan")){
                    sqlinsert = "insert into tbjurusan set kd_jur='"+vkd_jur+"', "
                            + " jurusan='"+vjurusan+"' ";
                }else if(btnSimpan.getText().equals("Ubah")){
                    sqlinsert = "update tbjurusan set"
                            + " jurusan='"+vjurusan+"' where kd_jur='"+vkd_jur+"' ";          
                }
                Statement stat = _Cnn.createStatement();
                stat.executeUpdate(sqlinsert);
                JOptionPane.showMessageDialog(this, "Informasi",
                        "Data berhasil disimpan", JOptionPane.INFORMATION_MESSAGE);
                showDataJurusan();clearInput(); disableInput();
                
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Error method aksiSimpan() : "+ex);
            }
        }
    }
    
    private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this, "Apakah anda ingin menghapus data ini? Kode "+vkd_jur,
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if(jawab == JOptionPane.YES_OPTION){
            try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                sqldelete = "delete from tbjurusan where kd_jur='"+vkd_jur+"' ";
                Statement stat = _Cnn.createStatement();
                stat.executeUpdate(sqldelete);
                JOptionPane.showMessageDialog(this, "Informasi",
                        "Data berhasil dihapus", JOptionPane.INFORMATION_MESSAGE);
                showDataJurusan();clearInput(); disableInput();
                
            }catch(SQLException ex){
                //JOptionPane.showMessageDialog(this, "Error method aksiHapus() : "+ex);
                 JOptionPane.showMessageDialog(this, "Data ini sudah di pakai");
            }
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

        jPanel1 = new javax.swing.JPanel();
        txtJurusan = new javax.swing.JTextField();
        txtKdJur = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDataJurusan = new javax.swing.JTable();
        lblRecord = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setClosable(true);
        setTitle(".: Form Jurusan");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Admin-Schoolar-Icon.png"))); // NOI18N
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameIconified(evt);
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Input Data"));
        jPanel1.setOpaque(false);

        txtJurusan.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Jurusan :"));
        txtJurusan.setOpaque(false);
        txtJurusan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJurusanActionPerformed(evt);
            }
        });
        txtJurusan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtJurusanKeyTyped(evt);
            }
        });

        txtKdJur.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Kode Jurusan :"));
        txtKdJur.setOpaque(false);
        txtKdJur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKdJurActionPerformed(evt);
            }
        });
        txtKdJur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtKdJurKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(txtKdJur, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtJurusan, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtKdJur, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtJurusan))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Navigasi"));
        jPanel2.setOpaque(false);

        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/trans-add.png"))); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/save-black.png"))); // NOI18N
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/trans-hapus.png"))); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Tabel Data Jurusan : Klik 2x untuk mengubah/menghapus data"));

        tbDataJurusan.setBorder(new javax.swing.border.MatteBorder(null));
        tbDataJurusan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Kode Jurusan", "Jurusan"
            }
        ));
        tbDataJurusan.setRowHeight(23);
        tbDataJurusan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataJurusanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDataJurusan);
        tbDataJurusan.getAccessibleContext().setAccessibleParent(this);

        lblRecord.setText("Record : 0");
        lblRecord.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/logo.png"))); // NOI18N
        jLabel2.setText("jLabel2");

        jLabel3.setFont(new java.awt.Font("Meiryo", 1, 12)); // NOI18N
        jLabel3.setText("Form Jurusan");

        jLabel4.setText("Form ini digunakan untuk mengolah data jurusan");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblRecord, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRecord)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtJurusanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJurusanActionPerformed
        aksiSimpan();
    }//GEN-LAST:event_txtJurusanActionPerformed

    private void txtKdJurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKdJurActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKdJurActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        if(btnTambah.getText().equals("Tambah")){
            clearInput();
            enableInput();
            txtKdJur.requestFocus(true);
            btnTambah.setText("Batal");
            btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/btn_delete.png")));
            
        }else if(btnTambah.getText().equals("Batal")){
            clearInput();
            disableInput();
            btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-add.png")));
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        aksiSimpan();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
       if(txtKdJur.getText().equals("")){
           JOptionPane.showMessageDialog(this, "Informasi",
                   "Anda belum memilih data yang akan dihapus", JOptionPane.INFORMATION_MESSAGE);
       }else{
           aksiHapus();
       }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tbDataJurusanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataJurusanMouseClicked
       if(evt.getClickCount() == 2){
           int row = tbDataJurusan.getSelectedRow();
           vkd_jur = tbDataJurusan.getValueAt(row, 0).toString();
           vjurusan = tbDataJurusan.getValueAt(row, 1).toString();
           
           txtKdJur.setText(vkd_jur); txtJurusan.setText(vjurusan);
           enableInput();
           btnTambah.setEnabled(false);
           txtKdJur.setEnabled(false);
           btnHapus.setEnabled(true);
           txtJurusan.requestFocus(true);
           btnSimpan.setText("Ubah");
       }
    }//GEN-LAST:event_tbDataJurusanMouseClicked

    private void txtKdJurKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKdJurKeyTyped
        char enter=evt.getKeyChar();
        if(txtKdJur.getText().length()==3){
            evt.consume();
        }else if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_txtKdJurKeyTyped

    private void txtJurusanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJurusanKeyTyped
        char enter=evt.getKeyChar();
        if(txtJurusan.getText().length()==45){
            evt.consume();
        }else if((Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_txtJurusanKeyTyped

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        setStatusVisible(false);
    }//GEN-LAST:event_formInternalFrameClosing

    private void formInternalFrameIconified(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameIconified
        setStatusVisible(true);
    }//GEN-LAST:event_formInternalFrameIconified


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JTable tbDataJurusan;
    private javax.swing.JTextField txtJurusan;
    private javax.swing.JTextField txtKdJur;
    // End of variables declaration//GEN-END:variables
}


