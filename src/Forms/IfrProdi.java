/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import Tools.KoneksiDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author klidang lor
 */
public class IfrProdi extends javax.swing.JInternalFrame {

    Connection _Cnn;
    KoneksiDB getCnn = new KoneksiDB();
    
    String vkd_prodi, vkd_jur, vprodi;
    String sqlselect, sqlinsert, sqldelete;
    DefaultTableModel tblprodi;
    private boolean visible = false;
    
    public IfrProdi() {
        initComponents();
        
        clearInput(); disableInput(); listJurusan();
        setTabelProdi(); showDataProdi();
        this.visible= false;
    }
    
    public boolean getVisible(){
        return visible;
    }
 
    public void setStatusVisible(boolean visible) {
        this.visible = visible;
    }
    
    private void clearInput(){
        cmbJurusan.setSelectedIndex(0);
        txtKdProdi.setText("");
        txtProdi.setText("");
        btnTambah.setText("Tambah");
        btnSimpan.setText("Simpan");
        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-add.png")));
    }
    
    private void disableInput(){
        cmbJurusan.setEnabled(false);
        txtKdProdi.setEnabled(false);
        txtProdi.setEnabled(false);
        btnSimpan.setEnabled(false);
        btnHapus.setEnabled(false);
    }
    
    private void enableInput(){
        cmbJurusan.setEnabled(true);
        txtKdProdi.setEnabled(true);
        txtProdi.setEnabled(true);
        btnSimpan.setEnabled(true);
    }
    
    private void setTabelProdi(){
        String[] kolom1 = {"Jurusan", "KD. Prodi", "Program Studi"};
        tblprodi = new DefaultTableModel(null, kolom1){
            Class[] types = new Class[]{
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class
            };
            
            public Class getColumnClass(int columnIndex){
            return types [columnIndex];
            }
            
            public boolean isCellEditable(int row, int col){
            int cola = tblprodi.getColumnCount();
            return (col < cola) ? false : true;
        }
        };
        tbDataProdi.setModel(tblprodi);
        tbDataProdi.getColumnModel().getColumn(0).setPreferredWidth(250);
        tbDataProdi.getColumnModel().getColumn(1).setPreferredWidth(85);
        tbDataProdi.getColumnModel().getColumn(2).setPreferredWidth(250);
        
    }
        
    
    private void clearTabelProdi(){
        int row  = tblprodi.getRowCount();
        for(int i=0; i<row; i++){
            tblprodi.removeRow(0);
        }
    }
    
    private void showDataProdi(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "select * from tbprodi a, tbjurusan b where a.kd_jur=b.kd_jur";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            clearTabelProdi();
            while(res.next()){
                String vjurusan = res.getString("jurusan");
                vkd_prodi = res.getString("kd_prodi");
                vprodi = res.getString("prodi");
                
                Object[] data = {vjurusan, vkd_prodi, vprodi};
                tblprodi.addRow(data);
            }
            
            lblRecord.setText("Record : " + tbDataProdi.getRowCount());
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method showDataProdi() : "+ex);
        }
        
    }
    
    private void aksiSimpan(){
        if(cmbJurusan.getSelectedIndex()<=0){
            JOptionPane.showMessageDialog(this, "Anda belum memilih jurusan!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(txtKdProdi.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Kode Prodi harus diisi!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(txtProdi.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Program Studi harus diisi!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else{
            vkd_prodi = txtKdProdi.getText();
            vkd_jur = KeyJur[cmbJurusan.getSelectedIndex()];
            vprodi = txtProdi.getText();
            try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                if(btnSimpan.getText().equals("Simpan")){
                    sqlinsert = "insert into tbprodi set kd_prodi='"+vkd_prodi+"',"
                            + " kd_jur='"+vkd_jur+"', prodi='"+vprodi+"' "; 
             
                }else if(btnSimpan.getText().equals("Ubah")){
                    sqlinsert = "update tbprodi set "
                            + " kd_jur='"+vkd_jur+"', prodi='"+vprodi+"' "
                            + " where kd_prodi='"+vkd_prodi+"'";
                }
                Statement stat = _Cnn.createStatement();
                stat.executeUpdate(sqlinsert);
                JOptionPane.showMessageDialog(this, "Data berhasil disimpan",
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);
                showDataProdi(); clearInput(); disableInput();
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Error method aksiSimpan() : "+ex);
            }
        }
    }
    
    private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this, "Apakah anda ingin menghapus data ini? Kode "+vkd_prodi,
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if(jawab == JOptionPane.YES_OPTION){
            try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                sqldelete = "delete from tbprodi where kd_prodi='"+vkd_prodi+"' ";
                Statement stat = _Cnn.createStatement();
                stat.executeUpdate(sqldelete);
                JOptionPane.showMessageDialog(this, "Informasi",
                        "Data berhasil dihapus", JOptionPane.INFORMATION_MESSAGE);
                showDataProdi();clearInput(); disableInput();
                
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Error method aksiHapus() : "+ex);
            }
        }
    }
    
    String[] KeyJur;
    private void listJurusan(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "select * from tbjurusan";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            cmbJurusan.removeAllItems();
            cmbJurusan.repaint();
            cmbJurusan.addItem("-- Pilih --");
            int i = 1;
            while(res.next()){
                cmbJurusan.addItem(res.getString("jurusan"));
                i++;
            }
            
            res.first();
            KeyJur = new String[i+1];   
            for(Integer x = 1; x<i; x++){
                KeyJur[x] = res.getString(1);
                res.next();//
            }
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method listJurusan() : " + ex);
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
        cmbJurusan = new javax.swing.JComboBox<>();
        txtProdi = new javax.swing.JTextField();
        txtKdProdi = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDataProdi = new javax.swing.JTable();
        lblRecord = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setClosable(true);
        setResizable(true);
        setTitle(".: Form Program Studi");
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

        cmbJurusan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --" }));
        cmbJurusan.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Jurusan :"));
        cmbJurusan.setOpaque(false);

        txtProdi.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Program Studi :"));
        txtProdi.setOpaque(false);
        txtProdi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProdiActionPerformed(evt);
            }
        });

        txtKdProdi.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Kode Prodi :"));
        txtKdProdi.setOpaque(false);
        txtKdProdi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKdProdiActionPerformed(evt);
            }
        });
        txtKdProdi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtKdProdiKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(cmbJurusan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(txtKdProdi, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtProdi)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(cmbJurusan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtKdProdi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProdi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Tabel Data Prodi  : Klik 2x untuk mengubah/menghapus data"));

        tbDataProdi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tbDataProdi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Jurusan", "Kode Prodi", "Program Studi"
            }
        ));
        tbDataProdi.setRowHeight(23);
        tbDataProdi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataProdiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDataProdi);
        tbDataProdi.getAccessibleContext().setAccessibleParent(this);

        lblRecord.setText("Record : 0");
        lblRecord.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/logo.png"))); // NOI18N
        jLabel2.setText("jLabel2");

        jLabel3.setFont(new java.awt.Font("Meiryo", 1, 12)); // NOI18N
        jLabel3.setText("Form Prodi");

        jLabel4.setText("Form ini digunakan untuk mengolah data program studi");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRecord)
                .addContainerGap())
        );

        setBounds(0, 0, 407, 592);
    }// </editor-fold>//GEN-END:initComponents

    private void txtProdiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProdiActionPerformed
        aksiSimpan();
    }//GEN-LAST:event_txtProdiActionPerformed

    private void txtKdProdiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKdProdiActionPerformed
        
    }//GEN-LAST:event_txtKdProdiActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        if(btnTambah.getText().equals("Tambah")){
            clearInput();
            enableInput();
            txtKdProdi.requestFocus(true);
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
        if(txtKdProdi.getText().equals("")){
           JOptionPane.showMessageDialog(this, "Informasi",
                   "Anda belum memilih data yang akan dihapus", JOptionPane.INFORMATION_MESSAGE);
       }else{
           aksiHapus();
       }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tbDataProdiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataProdiMouseClicked
        if(evt.getClickCount() == 2){
           int row = tbDataProdi.getSelectedRow();
           String vjurusan = tbDataProdi.getValueAt(row, 0).toString();
           vkd_prodi = tbDataProdi.getValueAt(row, 1).toString();
           vprodi = tbDataProdi.getValueAt(row, 2).toString();
           
           cmbJurusan.setSelectedItem(vjurusan);
           txtKdProdi.setText(vkd_prodi);
           txtProdi.setText(vprodi);
           
           enableInput();
           btnTambah.setEnabled(false);
           txtKdProdi.setEnabled(false);
           btnHapus.setEnabled(true);
           btnSimpan.setText("Ubah");
       }
    }//GEN-LAST:event_tbDataProdiMouseClicked

    private void txtKdProdiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKdProdiKeyTyped
        char enter=evt.getKeyChar();
        if(txtKdProdi.getText().length()==3){
            evt.consume();
        }else if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_txtKdProdiKeyTyped

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
    private javax.swing.JComboBox<String> cmbJurusan;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JTable tbDataProdi;
    private javax.swing.JTextField txtKdProdi;
    private javax.swing.JTextField txtProdi;
    // End of variables declaration//GEN-END:variables
}
