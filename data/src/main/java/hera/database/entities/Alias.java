package hera.database.entities;

import javax.persistence.*;

@Entity
@Table(name = "alias")
public class Alias implements PersistenceEntity{

    public static final String ENTITY_NAME = "Alias";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "commandFK")
    private Command command;

    private String alias;

    @Column(name = "guildFK")
    private Long guild;

    public Alias() {
    }

    public Alias(Command command, String alias, Long guild) {
        this.command = command;
        this.alias = alias;
        this.guild = guild;
    }

    public Alias(int id, Command command, String alias, Long guild) {
        this.id = id;
        this.command = command;
        this.alias = alias;
        this.guild = guild;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Long getGuild() {
        return guild;
    }

    public void setGuild(Long guild) {
        this.guild = guild;
    }
}
