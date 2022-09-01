package github.regularrabbit05.anticheat.aurabot;

import net.jitse.npclib.api.NPC;
import net.jitse.npclib.nms.v1_7_R4.packets.PacketPlayOutEntityHeadRotationWrapper;
import net.minecraft.server.v1_7_R4.PacketPlayOutAnimation;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityTeleport;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collections;

public class Runner {
    private final Plugin plugin;
    public Runner(Plugin plugin, CommandSender summoner) {
        this.plugin = plugin;
        this.summoner = summoner;
    }
    public Runner(Plugin plugin) {
        this.plugin = plugin;
        this.summoner = null;
    }

    public Integer getId() {
        return (npc == null) ? null : npc.getEntityId();
    }

    private BukkitTask task = null;
    private int loop = 0;

    private float fr = 0.0f;

    private final CommandSender summoner;
    private boolean isCommandSenderPlayer = false;

    private Player target;

    @SuppressWarnings("UnusedReturnValue")
    public BukkitTask make(Player player) {
        if (task != null) return null;

        this.target = player;

        npc = plugin.getLib().createNPC(plugin.getBotSkin(), Collections.singletonList("§c"+ getRandomName()));
        npc.disableFOV();
        Location pl = player.getLocation();
        pl.setPitch(0);
        pl.setYaw(0);
        npc.create(pl);
        npc.show(player);
        if (summoner != null && summoner instanceof Player && summoner != player) {
            npc.show((Player) summoner);
            isCommandSenderPlayer = true;
        }

        AnimationPacketFactory animationPacketFactory = new AnimationPacketFactory(npc.getEntityId());
        PacketPlayOutAnimation animPack = null;
        try {
            animPack = animationPacketFactory.getPacked(player);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }

        final int m = 4;

        PacketPlayOutAnimation finalAnimPack = animPack;
        PacketPlayOutEntityHeadRotationWrapper rot = new PacketPlayOutEntityHeadRotationWrapper();
        task = plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            if (!player.isOnline()) {
                cancel();
                return;
            }
            Location l = player.getLocation();

            fr = (fr >= (360*m)) ? 1 : fr + 60;
            double current = 6.24 / (360*m) * fr;

            float ox = (float) ((float) (Math.cos(current) * 1.5) + l.getX());
            float oz = (float) ((float) (Math.sin(current) * 1.5) + l.getZ());

            float x = (int) (ox * 32.0D);
            float z = (int) (oz * 32.0D);
            float y = (int) ((l.getY()+1.5) * 32.0D);

            Location nl = l.clone();

            nl.setX(ox);
            nl.setZ(oz);
            nl.setY(l.getY()+1.5);

            double[] P_Y = directionToEuler(l.clone().subtract(nl));
            byte yaw = (byte) ((P_Y[0] * 256.0F) / 360.0F);

            PacketPlayOutEntityHeadRotation rotPack = rot.create(new Location(l.getWorld(), ox, l.getY()+1, oz, (float) P_Y[0], (float) P_Y[1]), this.npc.getEntityId()); // no rotation?
            PacketPlayOutEntityTeleport pack = new PacketPlayOutEntityTeleport(this.npc.getEntityId(), (int) x, (int) y, (int) z, yaw, (byte) P_Y[1]);
            CraftPlayer p = (CraftPlayer) player;
            boolean a = false;
            if (finalAnimPack != null && loop % 10 == 0) {
                p.getHandle().playerConnection.sendPacket(finalAnimPack);
                a = true;
            }
            p.getHandle().playerConnection.sendPacket(pack);
            p.getHandle().playerConnection.sendPacket(rotPack);

            if (isCommandSenderPlayer) {
                //noinspection ConstantConditions
                CraftPlayer t = (CraftPlayer) summoner;
                assert t != null;
                t.getHandle().playerConnection.sendPacket(pack);
                t.getHandle().playerConnection.sendPacket(rotPack);
                if (a) t.getHandle().playerConnection.sendPacket(finalAnimPack);
            }

            loop++;
            if (loop >= 80) {
                if (task != null) {
                    cancel();
                    task = null;
                    plugin.taskDone(this);
                }
            }
        }, 0L, 1L);
        return task;
    }

    // author @Minikloon
    private double[] directionToEuler(Location dir) {
        double xzLength = Math.sqrt(dir.getX()*dir.getX() + dir.getZ()*dir.getZ());
        double pitch = Math.atan2(xzLength, dir.getY()) - Math.PI / 2;
        double yaw = -Math.atan2(dir.getX(), dir.getZ()) + Math.PI / 4;
        return new double[] {yaw, pitch};
    }

    private NPC npc = null;

    @SuppressWarnings("UnusedReturnValue")
    public boolean destroyNPC() {
        if (npc == null) return false;
        npc.destroy();
        npc = null;
        return true;
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean cancel() {
        destroyNPC();
        if (task == null) return false;
        task.cancel();
        return true;
    }

    private String getRandomName(){
        StringBuilder sb = new StringBuilder();
        while(sb.length() < 8) sb.append(Integer.toHexString(plugin.getRandom().nextInt()));
        return sb.substring(0, 8);
    }

    private int clicks = 0;
    @SuppressWarnings("UnusedReturnValue")
    public int click() {
        clicks++;
        return clicks;
    }

    public int getClicks() {
        return clicks;
    }

    public CommandSender getSummoner() {
        return summoner;
    }

    public Player getTarget() {
        return target;
    }
}
