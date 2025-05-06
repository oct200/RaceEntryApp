using log4net;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using model;
using services;

namespace ChatClientGTK
{
    public partial class FormInscriereParticipant : Form
    {
        IService service;
        private static readonly ILog logger = LogManager.GetLogger(typeof(FormPrincipal));
        public FormInscriereParticipant(IService service)
        {
            InitializeComponent();
            this.service = service;
        }

        private int getCapMotorFromText()
        {
            try
            {
                int x = Convert.ToInt32(textBoxCapMotor.Text);
                return x;
            }
            catch(Exception ex)
            {
                MessageBox.Show("Capacitate Motor gresita");
            }
            return -1;
        }

        private void buttonInscriere_Click(object sender, EventArgs e)
        {
            logger.Info("Incepere buttonInscriere_Click");
            string nume = textBoxNume.Text,cnp = textBoxCNP.Text, echipa = textBoxEchipa.Text;
            int capMotor = getCapMotorFromText();
            if (capMotor < 0 || nume == "" || cnp == "" || echipa == "")
            {
                MessageBox.Show("Introduceti toate datele");
                return;
            }
            try
            {
                service.InscrieParticipant(nume, cnp, capMotor, echipa);
                MessageBox.Show("Adaugat cu succes");
            }
            catch(Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            this.Close();
        }

        private void buttonInapoi_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}
