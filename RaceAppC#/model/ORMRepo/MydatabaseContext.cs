using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using model.ORMModel;


namespace model.ORMModel;

public partial class MydatabaseContext : DbContext
{
    public MydatabaseContext()
    {
    }

    public MydatabaseContext(DbContextOptions<MydatabaseContext> options)
        : base(options)
    {
    }

    public virtual DbSet<Cursa> Cursas { get; set; }

    public virtual DbSet<Echipa> Echipas { get; set; }

    public virtual DbSet<Participant> Participants { get; set; }

    public virtual DbSet<User> Users { get; set; }

    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        => optionsBuilder.UseSqlite("DataSource=D:\\GithubRepos\\mpp-proiect-csharp-oct200\\server\\bin\\Debug\\net8.0\\mydatabase.db");

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Cursa>(entity =>
        {
            entity.ToTable("Cursa");

            entity.Property(e => e.Id).HasColumnName("id");
            entity.Property(e => e.CapMotor).HasColumnName("capMotor");
            entity.Property(e => e.NumarParticipanti).HasColumnName("numarParticipanti");

            entity.HasMany(d => d.Participants).WithMany(p => p.Cursas)
                .UsingEntity<Dictionary<string, object>>(
                    "Inscriere",
                    r => r.HasOne<Participant>().WithMany().HasForeignKey("ParticipantId"),
                    l => l.HasOne<Cursa>().WithMany().HasForeignKey("CursaId"),
                    j =>
                    {
                        j.HasKey("CursaId", "ParticipantId");
                        j.ToTable("Inscriere");
                        j.IndexerProperty<int>("CursaId").HasColumnName("cursa_id");
                        j.IndexerProperty<int>("ParticipantId").HasColumnName("participant_id");
                    });
        });

        modelBuilder.Entity<Echipa>(entity =>
        {
            entity.ToTable("Echipa");

            entity.HasIndex(e => e.Nume, "IX_Echipa_nume").IsUnique();

            entity.Property(e => e.Id).HasColumnName("id");
            entity.Property(e => e.Nume).HasColumnName("nume");
        });

        modelBuilder.Entity<Participant>(entity =>
        {
            entity.ToTable("Participant");

            entity.HasIndex(e => e.Cnp, "IX_Participant_cnp").IsUnique();

            entity.Property(e => e.Id).HasColumnName("id");
            entity.Property(e => e.CapMotor).HasColumnName("capMotor");
            entity.Property(e => e.Cnp).HasColumnName("cnp");
            entity.Property(e => e.Echipa).HasColumnName("echipa");
            entity.Property(e => e.Nume).HasColumnName("nume");
        });

        modelBuilder.Entity<User>(entity =>
        {
            entity.ToTable("User");

            entity.HasIndex(e => e.Username, "IX_User_username").IsUnique();

            entity.Property(e => e.Id).HasColumnName("id");
            entity.Property(e => e.Parola).HasColumnName("parola");
            entity.Property(e => e.Username).HasColumnName("username");
        });

        OnModelCreatingPartial(modelBuilder);
    }

    partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
}
