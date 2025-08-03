namespace ChatClientGTK
{
    partial class FormAutentificare : Form
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
            buttonLogIn = new Button();
            buttonSignUp = new Button();
            textBoxUsername = new TextBox();
            textBoxParola = new TextBox();
            labelUsername = new Label();
            label1 = new Label();
            panel1 = new Panel();
            panel2 = new Panel();
            SuspendLayout();
            // 
            // buttonLogIn
            // 
            buttonLogIn.BackColor = Color.MidnightBlue;
            buttonLogIn.FlatAppearance.BorderColor = Color.White;
            buttonLogIn.FlatAppearance.BorderSize = 4;
            buttonLogIn.FlatStyle = FlatStyle.Flat;
            buttonLogIn.Font = new Font("MV Boli", 16.2F, FontStyle.Bold);
            buttonLogIn.ForeColor = Color.White;
            buttonLogIn.Location = new Point(12, 259);
            buttonLogIn.Margin = new Padding(3, 4, 3, 4);
            buttonLogIn.Name = "buttonLogIn";
            buttonLogIn.Size = new Size(407, 104);
            buttonLogIn.TabIndex = 0;
            buttonLogIn.Text = "Log in";
            buttonLogIn.UseVisualStyleBackColor = false;
            buttonLogIn.Click += buttonLogIn_Click;
            // 
            // buttonSignUp
            // 
            buttonSignUp.BackColor = Color.MidnightBlue;
            buttonSignUp.FlatAppearance.BorderColor = Color.White;
            buttonSignUp.FlatAppearance.BorderSize = 4;
            buttonSignUp.FlatStyle = FlatStyle.Flat;
            buttonSignUp.Font = new Font("MV Boli", 16.2F, FontStyle.Bold);
            buttonSignUp.ForeColor = Color.White;
            buttonSignUp.Location = new Point(425, 259);
            buttonSignUp.Margin = new Padding(3, 4, 3, 4);
            buttonSignUp.Name = "buttonSignUp";
            buttonSignUp.Size = new Size(407, 104);
            buttonSignUp.TabIndex = 1;
            buttonSignUp.Text = "Sign up";
            buttonSignUp.UseVisualStyleBackColor = false;
            buttonSignUp.Click += buttonSignUp_Click;
            // 
            // textBoxUsername
            // 
            textBoxUsername.BackColor = Color.PaleTurquoise;
            textBoxUsername.BorderStyle = BorderStyle.None;
            textBoxUsername.Font = new Font("MV Boli", 12F, FontStyle.Bold, GraphicsUnit.Point, 0);
            textBoxUsername.Location = new Point(256, 77);
            textBoxUsername.Margin = new Padding(3, 4, 3, 4);
            textBoxUsername.Name = "textBoxUsername";
            textBoxUsername.Size = new Size(345, 33);
            textBoxUsername.TabIndex = 2;
            // 
            // textBoxParola
            // 
            textBoxParola.BackColor = Color.PaleTurquoise;
            textBoxParola.BorderStyle = BorderStyle.None;
            textBoxParola.Font = new Font("MV Boli", 12F, FontStyle.Bold);
            textBoxParola.Location = new Point(257, 200);
            textBoxParola.Margin = new Padding(3, 4, 3, 4);
            textBoxParola.Name = "textBoxParola";
            textBoxParola.PasswordChar = '*';
            textBoxParola.Size = new Size(345, 33);
            textBoxParola.TabIndex = 3;
            // 
            // labelUsername
            // 
            labelUsername.AutoSize = true;
            labelUsername.Font = new Font("MV Boli", 16.2F, FontStyle.Bold, GraphicsUnit.Point, 0);
            labelUsername.Location = new Point(356, 36);
            labelUsername.Name = "labelUsername";
            labelUsername.Size = new Size(150, 37);
            labelUsername.TabIndex = 4;
            labelUsername.Text = "Username";
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Font = new Font("MV Boli", 16.2F, FontStyle.Bold, GraphicsUnit.Point, 0);
            label1.Location = new Point(356, 142);
            label1.Name = "label1";
            label1.Size = new Size(144, 37);
            label1.TabIndex = 6;
            label1.Text = "Password";
            // 
            // panel1
            // 
            panel1.BorderStyle = BorderStyle.FixedSingle;
            panel1.Location = new Point(256, 239);
            panel1.Name = "panel1";
            panel1.Size = new Size(345, 1);
            panel1.TabIndex = 7;
            // 
            // panel2
            // 
            panel2.BorderStyle = BorderStyle.FixedSingle;
            panel2.Location = new Point(256, 116);
            panel2.Name = "panel2";
            panel2.Size = new Size(345, 1);
            panel2.TabIndex = 8;
            // 
            // FormAutentificare
            // 
            AutoScaleDimensions = new SizeF(8F, 20F);
            AutoScaleMode = AutoScaleMode.Font;
            BackColor = Color.PaleTurquoise;
            ClientSize = new Size(836, 372);
            Controls.Add(panel2);
            Controls.Add(panel1);
            Controls.Add(label1);
            Controls.Add(labelUsername);
            Controls.Add(textBoxParola);
            Controls.Add(textBoxUsername);
            Controls.Add(buttonSignUp);
            Controls.Add(buttonLogIn);
            Margin = new Padding(3, 4, 3, 4);
            Name = "FormAutentificare";
            Text = "FormAutentificare";
            Load += FormAutentificare_Load;
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private System.Windows.Forms.Button buttonLogIn;
        private System.Windows.Forms.Button buttonSignUp;
        private System.Windows.Forms.TextBox textBoxUsername;
        private System.Windows.Forms.TextBox textBoxParola;
        private System.Windows.Forms.Label labelUsername;
        private Label label1;
        private Panel panel1;
        private Panel panel2;
    }
}