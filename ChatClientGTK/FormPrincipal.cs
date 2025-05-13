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
using model.ORMModel;
using services;
using static System.Windows.Forms.VisualStyles.VisualStyleElement;
using System.Collections;

namespace ChatClientGTK
{
    public partial class FormPrincipal : Form, IClientObserver
    {
        IService service;
        private static readonly ILog logger = LogManager.GetLogger(typeof(FormPrincipal));
        public FormPrincipal(IService ser)
        {
            InitializeComponent();
            service = ser;

        }

        private void initializareCombobox() {
            logger.Info("Incepere initializareCombobox");
            List<String> listaEchipa = service.GetAllTeams();
            List<Object> listaCap = service.GetCapacitati().Cast<Object>().ToList();
            listaEchipa.Insert(0, "Toate");
            listaCap.Insert(0, "Toate");

            if (comboBoxEchipe.InvokeRequired)
            {
                comboBoxEchipe.Invoke(new MethodInvoker(() =>
                {
                    comboBoxEchipe.DataSource = listaEchipa;
                }));
            }
            else
            {
                comboBoxEchipe.DataSource = listaEchipa;
            }

            if (comboBoxCapacitateMotor.InvokeRequired)
            {
                comboBoxCapacitateMotor.Invoke(new MethodInvoker(() =>
                {
                    comboBoxCapacitateMotor.DataSource = listaCap;
                }));
            }
            else
            {
                comboBoxCapacitateMotor.DataSource = listaCap;
            }

            
            logger.Info("final initializareCombobox");
        }

        private void loadTabelParticipanti()
        {
            logger.Info("Incepere loadTabelParticipanti");
            try {
                if ((string)comboBoxEchipe.SelectedItem == "Toate")
                {
                    if (dataGridViewParticipanti.InvokeRequired)
                    {
                        dataGridViewParticipanti.Invoke(new MethodInvoker(() =>
                        {
                            dataGridViewParticipanti.DataSource = service.GetAllParticipanti();
                        }));
                    }
                    else
                    {
                        dataGridViewParticipanti.DataSource = service.GetAllParticipanti();
                    }

                }
                else
                {
                    if (dataGridViewParticipanti.InvokeRequired)
                    {
                        dataGridViewParticipanti.Invoke(new MethodInvoker(() =>
                        {
                            dataGridViewParticipanti.DataSource = service.GetAllParticipantiByEchipa("" + comboBoxEchipe.SelectedItem);
                        }));
                    }
                    else
                    {
                        dataGridViewParticipanti.DataSource = service.GetAllParticipantiByEchipa("" + comboBoxEchipe.SelectedItem);
                    }
                }
                logger.Info("final loadTabelParticipanti");

            }
            catch(Exception ex)
            {
                logger.Error("Eroare in loadTabelParticipanti : " +ex);
            }
         }

        private void loadTabelCurse()
        {
            logger.Info("Incepere loadTabelCurse");
            try
            {
                if (comboBoxCapacitateMotor.Text == "Toate")
                {
                    if (dataGridViewCurse.InvokeRequired)
                    {
                        dataGridViewCurse.Invoke(new MethodInvoker(() =>
                        {
                            dataGridViewCurse.DataSource = service.GetAllCurse();
                        }));
                    }
                    else
                    {
                        dataGridViewCurse.DataSource = service.GetAllCurse();
                    }
                }

                else
                {
                    if (dataGridViewCurse.InvokeRequired)
                    {
                        dataGridViewCurse.Invoke(new MethodInvoker(() =>
                        {
                            dataGridViewCurse.DataSource = service.GetAllCurseByCapMotor((int)comboBoxCapacitateMotor.SelectedItem);
                        }));
                    }
                    else
                    {
                        dataGridViewCurse.DataSource = service.GetAllCurseByCapMotor((int)comboBoxCapacitateMotor.SelectedItem);
                    }
                }
                logger.Info("final loadTabelCurse");

            }
            catch (Exception ex)
            {
                logger.Error("Eroare in loadTabelCurse : " + ex);
            }
        }

        public void initializareComponente()
        {
            initializareCombobox();
            loadTabelParticipanti();
            loadTabelCurse();
        }

        private void buttonLogOut_Click(object sender, EventArgs e)
        {
            try
            {
                service.Logout(new User() { Id = 1, Username = "sf", Parola = "sd" });
                FormAutentificare fa = new FormAutentificare(service);
                this.Hide();
                fa.ShowDialog();
                this.Close();
            }
            catch(Exception ex)
            {
                logger.Error(ex);
            }
        }

        private void buttonInscrieParticipant_Click(object sender, EventArgs e)
        {
            FormInscriereParticipant fi = new FormInscriereParticipant(service);
            fi.ShowDialog();
        }

        private void comboBoxEchipe_SelectedIndexChanged(object sender, EventArgs e)
        {
            loadTabelParticipanti();
        }

        private void comboBoxCapacitateMotor_SelectedIndexChanged(object sender, EventArgs e)
        {
            loadTabelCurse();
            dataGridViewParticipanti.ClearSelection();
        }

        private void tabelParticipantiClick(object sender, DataGridViewCellEventArgs e)
        {
            logger.Info("starting tabelParticipantiClick");
            try
            {
                if (dataGridViewParticipanti.SelectedCells.Count == 1 || dataGridViewParticipanti.SelectedRows.Count == 1)
                {
                    Participant participant = (Participant)dataGridViewParticipanti.CurrentRow.DataBoundItem;
                    dataGridViewCurse.DataSource = service.GetCurseForParticipant(participant);
                }
                else
                {
                    logger.Info("no row selected in tabelParticipantiClick");
                }
            }
            catch(Exception ex)
            {
                logger.Error("eraore in tabelParticipantiClick : " + ex);
            }
        }

        private void buttonAdaugareParticipantLaCursa_Click(object sender, EventArgs e)
        {
            logger.Info("starting buttonAdaugareParticipantLaCursa_Click");
            try
            {
                if ((dataGridViewParticipanti.SelectedRows.Count == 1 && dataGridViewCurse.SelectedRows.Count == 1) || (dataGridViewParticipanti.SelectedCells.Count == 1 && dataGridViewCurse.SelectedCells.Count == 1))
                {
                    Participant participant = (Participant)dataGridViewParticipanti.CurrentRow.DataBoundItem;
                    Cursa cursa = (Cursa)dataGridViewCurse.CurrentRow.DataBoundItem;
                    service.AdaugaInscriere(participant, cursa);
                    MessageBox.Show(participant.Nume + " a fost inscris la cursa");
                    logger.Info("final buttonAdaugareParticipantLaCursa_Click");
                }
                else
                {
                    logger.Info("rows not selected for buttonAdaugareParticipantLaCursa_Click");
                }
            }
            catch (Exception ex)
            {
                logger.Error("eraore in tabelParticipantiClick : " + ex);
            }
        }

        public void refresh()
        {
            initializareCombobox();
            loadTabelParticipanti();
            loadTabelCurse();
        }
    }
}
