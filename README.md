
# AuraBot

A remake of Hypixel Watchdog's AuraBot

![f](https://github.com/RegularRabbit05/WatchDogAuraBot/assets/69511985/96024a36-3611-44e2-a2b1-fcd41c22ae11)


## Requirements

This plugin only runs on Spigot-1.7.10 (1_7R4) <> _1.7-1.8 ProtocolHack is supported_

Dependencies:
```txt
   NPCLib-Plugin
```

[Download NPCLib here](https://github.com/JitseB/NPCLib/tree/legacy)

## Config

```yaml
skin: 103974854 # Skin id from https://mineskin.org/ (upload a skin, scroll all the way down to "id DEPRECATED")
maxCount: 50 # Global, after how many total hits the last player to hit an entity triggers the bot (GLOBAL COUNT, EVERY PLAYER INCREASES IT)
```

## Showcase

### Detection:
![f0](https://github.com/RegularRabbit05/WatchDogAuraBot/assets/69511985/a880c73a-200a-4b36-a889-fa1a2ec91107)


### Staff's POV (Only who runs the command is able to see the bot apart from the suspect)
![f1](https://github.com/RegularRabbit05/WatchDogAuraBot/assets/69511985/c3ea414e-f67e-4edc-abf1-f456ff45f873)

### WARNING - NPCLib might require a "dirty workaround"

If errors (NullPointerException) are generated in console \
[replace these 2 lines](https://github.com/JitseB/NPCLib/blob/d3d010083007fa2a564cc98cb3c30efa9a4e641d/src/main/java/net/jitse/npclib/listeners/PacketListener.java#L65) with:
```java
    ClickType clickType;
        try {
        clickType = actionField.get(packet).toString()
        .equals("ATTACK") ? ClickType.LEFT_CLICK : ClickType.RIGHT_CLICK;
        } catch(Exception ignored) {
        clickType = ClickType.RIGHT_CLICK;
        }
```
