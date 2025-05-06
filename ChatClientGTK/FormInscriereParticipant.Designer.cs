namespace ChatClientGTK
{
    partial class FormInscriereParticipant
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
            label1 = new Label();
            buttonInscriere = new Button();
            buttonInapoi = new Button();
            labelUsername = new Label();
            textBoxNume = new TextBox();
            panel2 = new Panel();
            panel1 = new Panel();
            label2 = new Label();
            textBoxEchipa = new TextBox();
            panel3 = new Panel();
            label6 = new Label();
            textBoxCNP = new TextBox();
            panel4 = new Panel();
            label3 = new Label();
            textBoxCapMotor = new TextBox();
            SuspendLayout();
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Font = new Font("MV Boli", 16.2F, FontStyle.Bold);
            label1.Location = new Point(52, 9);
            label1.Name = "label1";
            label1.Size = new Size(303, 37);
            label1.TabIndex = 0;
            label1.Text = "Inscriere Participant";
            // 
            // buttonInscriere
            // 
            buttonInscriere.BackColor = Color.MidnightBlue;
            buttonInscriere.Font = new Font("MV Boli", 16.2F, FontStyle.Bold);
            buttonInscriere.ForeColor = Color.White;
            buttonInscriere.Location = new Point(146, 500);
            buttonInscriere.Margin = new Padding(3, 4, 3, 4);
            buttonInscriere.Name = "buttonInscriere";
            buttonInscriere.Size = new Size(172, 85);
            buttonInscriere.TabIndex = 5;
            buttonInscriere.Text = "Add";
            buttonInscriere.UseVisualStyleBackColor = false;
            buttonInscriere.Click += buttonInscriere_Click;
            // 
            // buttonInapoi
            // 
            buttonInapoi.BackColor = Color.Firebrick;
            buttonInapoi.Font = new Font("MV Boli", 12F, FontStyle.Bold, GraphicsUnit.Point, 0);
            buttonInapoi.ForeColor = Color.White;
            buttonInapoi.Location = new Point(190, 593);
            buttonInapoi.Margin = new Padding(3, 4, 3, 4);
            buttonInapoi.Name = "buttonInapoi";
            buttonInapoi.Size = new Size(82, 37);
            buttonInapoi.TabIndex = 10;
            buttonInapoi.Text = "Back";
            buttonInapoi.UseVisualStyleBackColor = false;
            buttonInapoi.Click += buttonInapoi_Click;
            // 
            // labelUsername
            // 
            labelUsername.AutoSize = true;
            labelUsername.Font = new Font("MV Boli", 16.2F, FontStyle.Bold, GraphicsUnit.Point, 0);
            labelUsername.Location = new Point(53, 76);
            labelUsername.Name = "labelUsername";
            labelUsername.Size = new Size(150, 37);
            labelUsername.TabIndex = 12;
            labelUsername.Text = "Username";
            // 
            // textBoxNume
            // 
            textBoxNume.BackColor = Color.PaleTurquoise;
            textBoxNume.BorderStyle = BorderStyle.None;
            textBoxNume.Font = new Font("MV Boli", 12F, FontStyle.Bold, GraphicsUnit.Point, 0);
            textBoxNume.Location = new Point(53, 117);
            textBoxNume.Margin = new Padding(3, 4, 3, 4);
            textBoxNume.Name = "textBoxNume";
            textBoxNume.Size = new Size(345, 33);
            textBoxNume.TabIndex = 11;
            // 
            // panel2
            // 
            panel2.BorderStyle = BorderStyle.FixedSingle;
            panel2.Location = new Point(53, 149);
            panel2.Name = "panel2";
            panel2.Size = new Size(345, 1);
            panel2.TabIndex = 13;
            // 
            // panel1
            // 
            panel1.BorderStyle = BorderStyle.FixedSingle;
            panel1.Location = new Point(53, 251);
            panel1.Name = "panel1";
            panel1.Size = new Size(345, 1);
            panel1.TabIndex = 16;
            // 
            // label2
            // 
            label2.AutoSize = true;
            label2.Font = new Font("MV Boli", 16.2F, FontStyle.Bold, GraphicsUnit.Point, 0);
            label2.Location = new Point(53, 178);
            label2.Name = "label2";
            label2.Size = new Size(104, 37);
            label2.TabIndex = 15;
            label2.Text = "Echipa";
            // 
            // textBoxEchipa
            // 
            textBoxEchipa.BackColor = Color.PaleTurquoise;
            textBoxEchipa.BorderStyle = BorderStyle.None;
            textBoxEchipa.Font = new Font("MV Boli", 12F, FontStyle.Bold, GraphicsUnit.Point, 0);
            textBoxEchipa.Location = new Point(53, 219);
            textBoxEchipa.Margin = new Padding(3, 4, 3, 4);
            textBoxEchipa.Name = "textBoxEchipa";
            textBoxEchipa.Size = new Size(345, 33);
            textBoxEchipa.TabIndex = 14;
            // 
            // panel3
            // 
            panel3.BorderStyle = BorderStyle.FixedSingle;
            panel3.Location = new Point(53, 368);
            panel3.Name = "panel3";
            panel3.Size = new Size(345, 1);
            panel3.TabIndex = 19;
            // 
            // label6
            // 
            label6.AutoSize = true;
            label6.Font = new Font("MV Boli", 16.2F, FontStyle.Bold, GraphicsUnit.Point, 0);
            label6.Location = new Point(53, 295);
            label6.Name = "label6";
            label6.Size = new Size(77, 37);
            label6.TabIndex = 18;
            label6.Text = "CNP";
            // 
            // textBoxCNP
            // 
            textBoxCNP.BackColor = Color.PaleTurquoise;
            textBoxCNP.BorderStyle = BorderStyle.None;
            textBoxCNP.Font = new Font("MV Boli", 12F, FontStyle.Bold, GraphicsUnit.Point, 0);
            textBoxCNP.Location = new Point(53, 336);
            textBoxCNP.Margin = new Padding(3, 4, 3, 4);
            textBoxCNP.Name = "textBoxCNP";
            textBoxCNP.Size = new Size(345, 33);
            textBoxCNP.TabIndex = 17;
            // 
            // panel4
            // 
            panel4.BorderStyle = BorderStyle.FixedSingle;
            panel4.Location = new Point(53, 479);
            panel4.Name = "panel4";
            panel4.Size = new Size(345, 1);
            panel4.TabIndex = 22;
            // 
            // label3
            // 
            label3.AutoSize = true;
            label3.Font = new Font("MV Boli", 16.2F, FontStyle.Bold, GraphicsUnit.Point, 0);
            label3.Location = new Point(52, 406);
            label3.Name = "label3";
            label3.Size = new Size(266, 37);
            label3.TabIndex = 21;
            label3.Text = "Capacitate motor";
            // 
            // textBoxCapMotor
            // 
            textBoxCapMotor.BackColor = Color.PaleTurquoise;
            textBoxCapMotor.BorderStyle = BorderStyle.None;
            textBoxCapMotor.Font = new Font("MV Boli", 12F, FontStyle.Bold, GraphicsUnit.Point, 0);
            textBoxCapMotor.Location = new Point(53, 447);
            textBoxCapMotor.Margin = new Padding(3, 4, 3, 4);
            textBoxCapMotor.Name = "textBoxCapMotor";
            textBoxCapMotor.Size = new Size(345, 33);
            textBoxCapMotor.TabIndex = 20;
            // 
            // FormInscriereParticipant
            // 
            AutoScaleDimensions = new SizeF(8F, 20F);
            AutoScaleMode = AutoScaleMode.Font;
            BackColor = Color.PaleTurquoise;
            ClientSize = new Size(456, 643);
            Controls.Add(panel4);
            Controls.Add(label3);
            Controls.Add(textBoxCapMotor);
            Controls.Add(panel3);
            Controls.Add(label6);
            Controls.Add(textBoxCNP);
            Controls.Add(panel1);
            Controls.Add(label2);
            Controls.Add(textBoxEchipa);
            Controls.Add(panel2);
            Controls.Add(labelUsername);
            Controls.Add(textBoxNume);
            Controls.Add(buttonInapoi);
            Controls.Add(buttonInscriere);
            Controls.Add(label1);
            Margin = new Padding(3, 4, 3, 4);
            Name = "FormInscriereParticipant";
            Text = "FormInscriereParticipant";
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button buttonInscriere;
        private System.Windows.Forms.Button buttonInapoi;
        private Label labelUsername;
        private TextBox textBoxNume;
        private Panel panel2;
        private Panel panel1;
        private Label label2;
        private TextBox textBoxEchipa;
        private Panel panel3;
        private Label label6;
        private TextBox textBoxCNP;
        private Panel panel4;
        private Label label3;
        private TextBox textBoxCapMotor;
    }
}