namespace ChatClientGTK
{
    partial class FormPrincipal
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.comboBoxEchipe = new System.Windows.Forms.ComboBox();
            this.comboBoxCapacitateMotor = new System.Windows.Forms.ComboBox();
            this.labelEchipe = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.dataGridViewParticipanti = new System.Windows.Forms.DataGridView();
            this.dataGridViewCurse = new System.Windows.Forms.DataGridView();
            this.buttonAdaugareParticipantLaCursa = new System.Windows.Forms.Button();
            this.buttonInscrieParticipant = new System.Windows.Forms.Button();
            this.buttonLogOut = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewParticipanti)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewCurse)).BeginInit();
            this.SuspendLayout();
            // 
            // comboBoxEchipe
            // 
            this.comboBoxEchipe.FormattingEnabled = true;
            this.comboBoxEchipe.Location = new System.Drawing.Point(67, 45);
            this.comboBoxEchipe.Name = "comboBoxEchipe";
            this.comboBoxEchipe.Size = new System.Drawing.Size(121, 24);
            this.comboBoxEchipe.TabIndex = 0;
            this.comboBoxEchipe.SelectedIndexChanged += new System.EventHandler(this.comboBoxEchipe_SelectedIndexChanged);
            // 
            // comboBoxCapacitateMotor
            // 
            this.comboBoxCapacitateMotor.FormattingEnabled = true;
            this.comboBoxCapacitateMotor.Location = new System.Drawing.Point(44, 287);
            this.comboBoxCapacitateMotor.Name = "comboBoxCapacitateMotor";
            this.comboBoxCapacitateMotor.Size = new System.Drawing.Size(187, 24);
            this.comboBoxCapacitateMotor.TabIndex = 1;
            this.comboBoxCapacitateMotor.SelectedIndexChanged += new System.EventHandler(this.comboBoxCapacitateMotor_SelectedIndexChanged);
            // 
            // labelEchipe
            // 
            this.labelEchipe.AutoSize = true;
            this.labelEchipe.Location = new System.Drawing.Point(75, 9);
            this.labelEchipe.Name = "labelEchipe";
            this.labelEchipe.Size = new System.Drawing.Size(103, 16);
            this.labelEchipe.TabIndex = 2;
            this.labelEchipe.Text = "Selectati echipa";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(41, 251);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(190, 16);
            this.label1.TabIndex = 3;
            this.label1.Text = "Selectati capacitatea motorului";
            // 
            // dataGridViewParticipanti
            // 
            this.dataGridViewParticipanti.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewParticipanti.Location = new System.Drawing.Point(19, 75);
            this.dataGridViewParticipanti.Name = "dataGridViewParticipanti";
            this.dataGridViewParticipanti.RowHeadersWidth = 51;
            this.dataGridViewParticipanti.RowTemplate.Height = 24;
            this.dataGridViewParticipanti.Size = new System.Drawing.Size(561, 173);
            this.dataGridViewParticipanti.TabIndex = 4;
            this.dataGridViewParticipanti.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.tabelParticipantiClick);
            // 
            // dataGridViewCurse
            // 
            this.dataGridViewCurse.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewCurse.Location = new System.Drawing.Point(19, 317);
            this.dataGridViewCurse.Name = "dataGridViewCurse";
            this.dataGridViewCurse.RowHeadersWidth = 51;
            this.dataGridViewCurse.RowTemplate.Height = 24;
            this.dataGridViewCurse.Size = new System.Drawing.Size(561, 207);
            this.dataGridViewCurse.TabIndex = 5;
            // 
            // buttonAdaugareParticipantLaCursa
            // 
            this.buttonAdaugareParticipantLaCursa.Location = new System.Drawing.Point(616, 252);
            this.buttonAdaugareParticipantLaCursa.Name = "buttonAdaugareParticipantLaCursa";
            this.buttonAdaugareParticipantLaCursa.Size = new System.Drawing.Size(117, 59);
            this.buttonAdaugareParticipantLaCursa.TabIndex = 6;
            this.buttonAdaugareParticipantLaCursa.Text = "Adauga la cursa";
            this.buttonAdaugareParticipantLaCursa.UseVisualStyleBackColor = true;
            this.buttonAdaugareParticipantLaCursa.Click += new System.EventHandler(this.buttonAdaugareParticipantLaCursa_Click);
            // 
            // buttonInscrieParticipant
            // 
            this.buttonInscrieParticipant.Location = new System.Drawing.Point(44, 559);
            this.buttonInscrieParticipant.Name = "buttonInscrieParticipant";
            this.buttonInscrieParticipant.Size = new System.Drawing.Size(117, 59);
            this.buttonInscrieParticipant.TabIndex = 7;
            this.buttonInscrieParticipant.Text = "Adauga un nou participant";
            this.buttonInscrieParticipant.UseVisualStyleBackColor = true;
            this.buttonInscrieParticipant.Click += new System.EventHandler(this.buttonInscrieParticipant_Click);
            // 
            // buttonLogOut
            // 
            this.buttonLogOut.Location = new System.Drawing.Point(704, 569);
            this.buttonLogOut.Name = "buttonLogOut";
            this.buttonLogOut.Size = new System.Drawing.Size(117, 59);
            this.buttonLogOut.TabIndex = 8;
            this.buttonLogOut.Text = "Log out";
            this.buttonLogOut.UseVisualStyleBackColor = true;
            this.buttonLogOut.Click += new System.EventHandler(this.buttonLogOut_Click);
            // 
            // FormPrincipal
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(833, 640);
            this.Controls.Add(this.buttonLogOut);
            this.Controls.Add(this.buttonInscrieParticipant);
            this.Controls.Add(this.buttonAdaugareParticipantLaCursa);
            this.Controls.Add(this.dataGridViewCurse);
            this.Controls.Add(this.dataGridViewParticipanti);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.labelEchipe);
            this.Controls.Add(this.comboBoxCapacitateMotor);
            this.Controls.Add(this.comboBoxEchipe);
            this.Name = "FormPrincipal";
            this.Text = "FormPrincipal";
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewParticipanti)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewCurse)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ComboBox comboBoxEchipe;
        private System.Windows.Forms.ComboBox comboBoxCapacitateMotor;
        private System.Windows.Forms.Label labelEchipe;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.DataGridView dataGridViewParticipanti;
        private System.Windows.Forms.DataGridView dataGridViewCurse;
        private System.Windows.Forms.Button buttonAdaugareParticipantLaCursa;
        private System.Windows.Forms.Button buttonInscrieParticipant;
        private System.Windows.Forms.Button buttonLogOut;
    }
}