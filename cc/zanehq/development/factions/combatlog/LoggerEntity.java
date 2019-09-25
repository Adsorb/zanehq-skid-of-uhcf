package cc.zanehq.development.factions.combatlog;

import java.util.UUID;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;

import cc.zanehq.development.Base;

public abstract interface LoggerEntity
{
    public abstract void postSpawn(Base paramHCF);

    public abstract CraftPlayer getBukkitEntity();

    public abstract UUID getUniqueID();

    public abstract void destroy();
}
