
# AuraBot

A remake of Hypixel Watchdog's AuraBot

![f](https://cdn.discordapp.com/attachments/748984983225499738/1014888466431098880/wdo.gif)


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
![f0](https://cdn.discordapp.com/attachments/748984983225499738/1014885605265657897/detection.gif)

### Staff's POV (Only who runs the command is able to see the bot apart from the suspect)
![f1](https://cdn.discordapp.com/attachments/748984983225499738/1014886030907801621/staff.gif)

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
