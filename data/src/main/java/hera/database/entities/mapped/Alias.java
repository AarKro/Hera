package hera.database.entities.mapped;

import hera.database.entities.persistence.AliasPO;

public class Alias implements  IMappedEntity<AliasPO> {

    public static final String NAME = "Alias";

    private int command;

    private String alias;

    private Long guild;

    public Alias() {
    }

    public Alias(int command, String alias, Long guild) {
        this.command = command;
        this.alias = alias;
        this.guild = guild;
    }

    @Override
    public AliasPO mapToPO() {
        return new AliasPO(
                this.command,
                this.alias,
                this.guild
        );
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
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
