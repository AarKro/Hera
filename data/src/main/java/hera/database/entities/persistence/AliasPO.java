package hera.database.entities.persistence;

import hera.database.entities.mapped.Alias;

import javax.persistence.*;

@Entity
@Table(name = "alias")
public class AliasPO implements IPersistenceEntity<Alias> {

    public static final String ENTITY_NAME = "AliasPO";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int commandFK;

    private String alias;

    private Long guildFK;

    public AliasPO() {
    }

    public AliasPO(int commandFK, String alias, Long guildFK) {
        this.commandFK = commandFK;
        this.alias = alias;
        this.guildFK = guildFK;
    }

    @Override
    public Alias mapToNonePO() {
        return new Alias(
                this.commandFK,
                this.alias,
                this.guildFK
        );
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommandFK() {
        return commandFK;
    }

    public void setCommandFK(int commandFK) {
        this.commandFK = commandFK;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Long getGuildFK() {
        return guildFK;
    }

    public void setGuildFK(Long guildFK) {
        this.guildFK = guildFK;
    }
}
