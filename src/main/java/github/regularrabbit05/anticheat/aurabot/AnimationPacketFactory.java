package github.regularrabbit05.anticheat.aurabot;

import net.minecraft.server.v1_7_R4.PacketPlayOutAnimation;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class AnimationPacketFactory {
    private final int spoofId, animationType;
    public AnimationPacketFactory(int spoofId) {
        this.spoofId = spoofId;
        this.animationType = 0;
    }

    @SuppressWarnings("unused")
    public AnimationPacketFactory(int spoofId, int animationType) {
        this.spoofId = spoofId;
        this.animationType = animationType;
    }

    @SuppressWarnings("unused")
    public PacketPlayOutAnimation getPacked(Player spoofSource) throws NoSuchFieldException, IllegalAccessException {
        CraftPlayer craftPlayer = ((CraftPlayer) spoofSource);
        PacketPlayOutAnimation packet = new PacketPlayOutAnimation(craftPlayer.getHandle(), this.animationType);
        Field f1 = packet.getClass().getDeclaredField("a");
        f1.setAccessible(true);
        f1.set(packet, spoofId);
        return packet;
    }
}
