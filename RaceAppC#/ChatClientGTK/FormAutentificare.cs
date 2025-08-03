using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using networking;
using services;
using model;

namespace ChatClientGTK
{
    public partial class FormAutentificare : Form
    {
        public IService service;

        public FormAutentificare(IService s)
        {
            InitializeComponent();
            service = s;
        }


        private void buttonSignUp_Click(object sender, EventArgs e)
        {
            string pass = textBoxParola.Text, user = textBoxUsername.Text;
            if (pass == "" || user == "")
            {
                MessageBox.Show("introduceti username-ul si parola");
                return;
            }
            try
            {
                service.InsertUser(user, pass);
                MessageBox.Show("Cont adaugat cu succes");
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void buttonLogIn_Click(object sender, EventArgs e)
        {
            string pass = textBoxParola.Text, user = textBoxUsername.Text;
            if (pass == "" || user == "")
            {
                MessageBox.Show("introduceti username-ul si parola");
                return;
            }
            try
            {
                FormPrincipal fp = new FormPrincipal(service);
                if (service.UserExists(user, pass, fp) != null)
                {
                    fp.initializareComponente();
                    this.Hide();
                    fp.ShowDialog();
                    this.Close();
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void FormAutentificare_Load(object sender, EventArgs e)
        {

        }
    }
}
