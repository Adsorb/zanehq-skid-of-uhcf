package cc.zanehq.development.user;


import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.*;

import cc.zanehq.development.util.ServerParticipator;

import java.util.*;

public class ConsoleUser extends ServerParticipator implements ConfigurationSerializable
{
    public static final UUID CONSOLE_UUID;
    String name;
    
    static {
        CONSOLE_UUID = UUID.fromString("908c0885-dfe9-4727-9fd6-1eb2890e108c");
    }
    
    public ConsoleUser() {
        super(ConsoleUser.CONSOLE_UUID);
        this.name = "CONSOLE";
    }
    
    public ConsoleUser(final Map<String, Object> map) {
        super(map);
        this.name = "CONSOLE";
    }
    
    @Override
    public String getName() {
        return this.name;
    }
}
